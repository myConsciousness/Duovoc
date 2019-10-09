package android.app.java.com.duovoc;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.adapter.OverviewRelatedLexemesAdapter;
import android.app.java.com.duovoc.adapter.OverviewTranslationAdapter;
import android.app.java.com.duovoc.communicate.HttpAsyncOverview;
import android.app.java.com.duovoc.communicate.HttpAsyncOverviewTranslation;
import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.CommonConstants;
import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.MessageID;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.framework.StringHandler;
import android.app.java.com.duovoc.holder.HintSingleRow;
import android.app.java.com.duovoc.holder.OverviewTranslationHolder;
import android.app.java.com.duovoc.holder.RelatedLexemesSingleRow;
import android.app.java.com.duovoc.model.OverviewInformation;
import android.app.java.com.duovoc.model.OverviewTranslationInformation;
import android.app.java.com.duovoc.model.property.OverviewColumnKey;
import android.app.java.com.duovoc.model.property.OverviewTranslationColumnKey;
import android.app.java.com.duovoc.model.property.UserColumnKey;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : DetailActivity.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 詳細画面の表示処理を行うアクティビティです。
 * また、ヒント情報の取得を行う際に非同期処理を行います。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see BaseActivity
 * @see DuovocBaseActivity
 * @see HttpAsyncOverview
 * @since 1.0
 */
final public class DetailActivity extends DuovocBaseActivity {

    /**
     * クラス名。
     */
    private static final String TAG = DetailActivity.class.getSimpleName();

    /**
     * 詳細情報の初期値。
     */
    private static final String VALUE_UNDEFINED = "-";

    /**
     * 詳細画面の語彙素リストを操作するアダプタオブジェクト。
     */
    private OverviewRelatedLexemesAdapter overviewRelatedLexemesAdapter;

    /**
     * 当該クラスのコンストラクタです。
     * 詳細画面のレイアウトを適用するために基底クラスへログイン画面のレイアウトを渡します。
     */
    public DetailActivity() {
        super(R.layout.activity_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        final MenuItem syncButton = menu.findItem(R.id.menu_sync_button);
        final MenuItem switchLanguageButton = menu.findItem(R.id.menu_switch_language);
        syncButton.setVisible(false);
        switchLanguageButton.setVisible(false);

        return true;
    }

    @Override
    protected void initializeView() {
        final String methodName = "initializeView";
        Logger.Info.write(TAG, methodName, "START");

        final String overviewId = this.getIntent().getStringExtra(OverviewColumnKey.Id.getKeyName());
        final OverviewTranslationInformation overviewTranslationInformation
                = this.getOverviewTranslationInformation();

        if (overviewTranslationInformation.selectByPrimaryKey(overviewId)) {
            final ModelMap<OverviewTranslationColumnKey, Object> modelMap = overviewTranslationInformation.getModelInfo();
            final String hints = modelMap.getString(OverviewTranslationColumnKey.Translation);
            final String[] hintsArray = StringHandler.split(hints, CommonConstants.CHAR_SEPARATOR_PERIOD);

            final List<String> hintsList = new ArrayList<>();
            Collections.addAll(hintsList, hintsArray);

            this.refreshHintsList(hintsList);

        } else {
            // ヒントリストを"-"で設定する
            this.refreshHintsList(new ArrayList<>());
        }

        final OverviewInformation overviewInformation = this.getOverviewInformation();
        if (!overviewInformation.selectByPrimaryKey(overviewId)) {
            // TODO: エラーダイアログ
            return;
        }

        final ModelMap<OverviewColumnKey, Object> modelMap = overviewInformation.getModelInfo().get(0);

        this.setTextViews(modelMap);
        this.setViewRelatedLexemes(modelMap.getStringList(OverviewColumnKey.RelatedLexemes));

        Logger.Info.write(TAG, methodName, "END");
    }

    @Override
    protected void setListeners() {
        final String methodName = "setListeners";
        Logger.Info.write(TAG, methodName, "START");

        final ListView listView = this.findViewById(R.id.outputRelatedLexemes);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            final RelatedLexemesSingleRow selected = this.overviewRelatedLexemesAdapter.getListViewItemsList().get(position);
            final String overviewId = selected.getOverviewId();

            final Map<String, String> extras = new HashMap<>();
            extras.put(OverviewColumnKey.Id.getKeyName(), overviewId);

            super.startActivity(DetailActivity.class, extras);
        });

        Logger.Info.write(TAG, methodName, "END");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        /*
         * 詳細画面で戻るボタンが押下された場合は、
         * 関連語彙を参照していた場合でも一覧画面へ戻す。
         */
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 既に詳細画面での検索処理でモデルマップが作成されるため再検索は不要
            final OverviewInformation overviewInformation = this.getOverviewInformation();
            final ModelMap<OverviewColumnKey, Object> overviewMap = overviewInformation.getModelInfo().get(0);
            final String userId = overviewMap.getString(OverviewColumnKey.UserId);

            final Map<String, String> extras = new HashMap<>();
            extras.put(UserColumnKey.UserId.getKeyName(), userId);

            super.startActivity(ListViewActivity.class, extras);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStart() {
        super.onStart();

        final ModelMap<OverviewTranslationColumnKey, Object> hintsMap
                = this.getOverviewTranslationInformation().getModelInfo();

        if (super.isOnlineMode()) {

            /*
             * onCreateイベントで語彙素IDを検索した際にモデルオブジェクトの状態が変わっているため、
             * 再度インテントに設定されたキーで検索処理を行う。
             */
            final String overviewId = this.getIntent().getStringExtra(OverviewColumnKey.Id.getKeyName());

            final OverviewInformation overviewInformation = this.getOverviewInformation();
            if (!overviewInformation.selectByPrimaryKey(overviewId)) {
                // TODO: エラーダイアログ。
                return;
            }

            final ModelMap<OverviewColumnKey, Object> overviewMap = overviewInformation.getModelInfo().get(0);

            if (hintsMap.isEmpty()) {
                // 初期起動時の処理
                this.getTranslation(overviewMap);
            } else if (false) {
                // TODO: 最終更新日時から1ヶ月経過していた場合に実行する。
                this.getTranslation(overviewMap);
            }
        } else {
            if (hintsMap.isEmpty()) {
                // TODO: メッセージ内容
                super.showInformationToast(MessageID.IJP00008);
            }
        }
    }

    /**
     * 概要情報から取得した値を、
     * 詳細画面の各テキストビューに設定する処理を定義したメソッドです。
     * 設定する値が存在しない場合は初期値として"-"を設定します。
     * <p>
     * 初期値が設定される場合のあるテキストビューの項目は以下の通りです。
     * 1, Gender
     * 2, Infinitive
     *
     * @param modelMap 詳細情報。
     */
    private void setTextViews(final ModelMap<OverviewColumnKey, Object> modelMap) {
        final String methodName = "setTextViews";
        Logger.Info.write(TAG, methodName, "START");

        final TextView textViewLanguage = this.findViewById(R.id.outputLanguage);
        final TextView textViewWord = this.findViewById(R.id.outputWord);
        final TextView textViewGender = this.findViewById(R.id.outputGender);
        final TextView textViewInfinitive = this.findViewById(R.id.outputInfinitive);

        textViewLanguage.setText(modelMap.getString(OverviewColumnKey.LanguageString));
        textViewWord.setText(modelMap.getString(OverviewColumnKey.WordString));
        textViewGender.setText(this.convertOutput(modelMap.getString(OverviewColumnKey.Gender)));
        textViewInfinitive.setText(this.convertOutput(modelMap.getString(OverviewColumnKey.Infinitive)));

        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * 入力値が空またはから文字列の場合に、
     * 初期値として"-"を返却する処理を定義したメソッドです。
     * 入力値が有効な場合は入力された値を返却します。
     *
     * @param value 判定対象の値。
     * @return 入力値が空またはから文字列の場合は {@code "-"}、それ以外は入力された値
     */
    private String convertOutput(final String value) {
        final String methodName = "convertOutput";
        Logger.Info.write(TAG, methodName, "START");

        Logger.Info.write(TAG, methodName, "END");
        return StringChecker.isEffectiveString(value) ? value : VALUE_UNDEFINED;
    }

    /**
     * 概要情報から取得した語彙素の値を、
     * 詳細画面の語彙素リストに設定する処理を定義したメソッドです。
     * 語彙素リストに設定する値が存在しない場合は、
     * 初期値として"-"を設定します。
     *
     * @param relatedLexemes 語彙素が格納されたリスト。
     */
    private void setViewRelatedLexemes(final List<String> relatedLexemes) {
        final String methodName = "setViewRelatedLexemes";
        Logger.Info.write(TAG, methodName, "START");

        final List<RelatedLexemesSingleRow> relatedLexemesSingleRowList = new ArrayList<>();

        if (relatedLexemes.isEmpty()) {
            // リストが空の場合は"-"を表示する
            final RelatedLexemesSingleRow relatedLexemesSingleRow = new RelatedLexemesSingleRow();
            relatedLexemesSingleRow.setWord(VALUE_UNDEFINED);
            relatedLexemesSingleRowList.add(relatedLexemesSingleRow);

        } else {
            for (String relatedLexeme : relatedLexemes) {
                final RelatedLexemesSingleRow relatedLexemesSingleRow = new RelatedLexemesSingleRow();
                final OverviewInformation overviewInformation = this.getOverviewInformation();

                if (StringChecker.isEffectiveString(relatedLexeme)) {
                    if (!overviewInformation.selectByLexemeId(relatedLexeme)) {
                        /** TODO: 業務エラーメッセージ */
                        return;
                    }

                    final ModelMap<OverviewColumnKey, Object> modelMap = overviewInformation.getModelInfo().get(0);
                    relatedLexemesSingleRow.setLexemeId(relatedLexeme);
                    relatedLexemesSingleRow.setOverviewId(modelMap.getString(OverviewColumnKey.Id));
                    relatedLexemesSingleRow.setWord(modelMap.getString(OverviewColumnKey.WordString));
                    relatedLexemesSingleRow.setLessonName(modelMap.getString(OverviewColumnKey.Skill));
                }

                relatedLexemesSingleRowList.add(relatedLexemesSingleRow);
            }
        }

        this.overviewRelatedLexemesAdapter = new OverviewRelatedLexemesAdapter(this, relatedLexemesSingleRowList);
        final ListView listViewRelatedLexemes = this.findViewById(R.id.outputRelatedLexemes);

        listViewRelatedLexemes.setAdapter(this.overviewRelatedLexemesAdapter);
        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * 詳細画面のヒント情報取得処理を定義したメソッドです。
     * <p>
     * ヒント情報の取得処理はバックグラウンド上で行い、
     * 処理中はキャンセル不可なプログレスダイアログを画面上に出力します。
     * <p>
     * ヒント情報を取得した場合は論理モデル名「概要翻訳情報」へ登録処理を行い、
     * 詳細画面のヒントリストへ設定する処理を行います。
     * <p>
     * ヒント情報を取得できなかった場合は初期値として"-"を設定します。
     * <p>
     * 以下の場合は取得処理を行うことができません。
     * 1, ユーザが未ログインの場合。
     * 2, ネットワーク接続が行われていない場合。
     * 3, Wifi接続時のみ同期化処理を行う設定にしている際にWifi接続が行われていない場合。
     * <p>
     * 上記の3パターンの何れの場合も対応したメッセージを出力して当該メソッド処理を終了します。
     */
    private void getTranslation(final ModelMap<OverviewColumnKey, Object> modelMap) {
        final String methodName = "getTranslation";
        Logger.Info.write(TAG, methodName, "START");

        if (!super.isOnlineMode()) {
            this.showInformationToast(MessageID.IJP00005);
            return;
        }

        if (!super.isActiveNetwork()) {
            this.showInformationToast(MessageID.IJP00006);
            this.refreshHintsList(new ArrayList<>());
            return;
        }

        if (!super.isActiveWifiNetwork()) {
            this.showInformationToast(MessageID.IJP00007);
            this.refreshHintsList(new ArrayList<>());
            return;
        }

        final String overviewId = modelMap.getString(OverviewColumnKey.Id);
        final String language = modelMap.getString(OverviewColumnKey.Language);
        final String fromLanguage = modelMap.getString(OverviewColumnKey.FromLanguage);

        @SuppressLint("StaticFieldLeak")
        HttpAsyncOverviewTranslation asyncOverviewTranslation = new HttpAsyncOverviewTranslation(overviewId, language, fromLanguage) {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                DetailActivity.super.showSpinnerDialog("Getting translations", "Please wait for a little...");
            }

            @Override
            protected void onPostExecute(OverviewTranslationHolder overviewTranslationHolder) {
                super.onPostExecute(overviewTranslationHolder);

                try {
                    final OverviewTranslationInformation overviewTranslationInformation
                            = DetailActivity.super.getOverviewTranslationInformation();

                    if (!overviewTranslationInformation.replace(overviewTranslationHolder)) {
                        /** TODO: 業務エラー */
                        return;
                    }

                    DetailActivity.this.refreshHintsList(overviewTranslationHolder.getHints());
                } finally {
                    DetailActivity.super.dismissDialog();
                }
            }
        };

        final String word = modelMap.getString(OverviewColumnKey.WordString);
        final String format = "new";

        Logger.Info.write(TAG, methodName, "END");
        asyncOverviewTranslation.execute(word, format);
    }

    private void refreshHintsList(final List<String> hintsList) {

        final List<HintSingleRow> listViewItemsList = new ArrayList<>();

        if (hintsList.isEmpty()) {
            // リストが空の場合は"-"を表示する
            final HintSingleRow hintSingleRow = new HintSingleRow();
            hintSingleRow.setHint(VALUE_UNDEFINED);
            listViewItemsList.add(hintSingleRow);

        } else {
            for (String hint : hintsList) {
                final HintSingleRow listViewItems = new HintSingleRow();
                listViewItems.setHint(hint);
                listViewItemsList.add(listViewItems);
            }
        }

        final OverviewTranslationAdapter overviewTranslationAdapter = new OverviewTranslationAdapter(this, listViewItemsList);
        final ListView listViewTranslation = this.findViewById(R.id.outputTranslation);

        listViewTranslation.setAdapter(overviewTranslationAdapter);
    }
}
