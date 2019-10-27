package android.app.java.com.duovoc;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.adapter.OverviewRelatedLexemesAdapter;
import android.app.java.com.duovoc.adapter.OverviewTranslationAdapter;
import android.app.java.com.duovoc.communicate.HttpAsyncOverviewTranslation;
import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.CalendarHandler;
import android.app.java.com.duovoc.framework.CommonConstants;
import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.MessageID;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.framework.StringHandler;
import android.app.java.com.duovoc.holder.HintSingleRow;
import android.app.java.com.duovoc.holder.RelatedLexemesSingleRow;
import android.app.java.com.duovoc.model.OverviewInformation;
import android.app.java.com.duovoc.model.OverviewRelatedLexemeInformation;
import android.app.java.com.duovoc.model.OverviewTranslationInformation;
import android.app.java.com.duovoc.model.UserMemoInformation;
import android.app.java.com.duovoc.model.holder.OverviewTranslationHolder;
import android.app.java.com.duovoc.model.holder.UserMemoHolder;
import android.app.java.com.duovoc.model.property.OverviewColumnKey;
import android.app.java.com.duovoc.model.property.OverviewRelatedLexemeColumnKey;
import android.app.java.com.duovoc.model.property.OverviewTranslationColumnKey;
import android.app.java.com.duovoc.model.property.UserMemoColumnKey;
import android.app.java.com.duovoc.property.IntentExtraKey;
import android.app.java.com.duovoc.property.TransitionOriginalScreenId;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
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
 * 当該画面では主に以下の機能を提供します。
 * <p>
 * 1, 詳細情報の表示
 * 概要情報で選択された情報に紐づく詳細情報を画面へ出力します。
 * <p>
 * 2 ヒント情報の取得
 * アクティビティの開始時に概要情報で選択された単語に紐づくヒント情報を取得します。
 * 取得したヒント情報は「概要翻訳情報」へ登録されます。
 * <p>
 * 3, ユーザメモ
 * 概要情報で選択された情報に紐づく詳細情報に対してユーザがメモを登録できる機能を提供します。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see BaseActivity
 * @see DuovocBaseActivity
 * @see HttpAsyncOverviewTranslation
 * @since 1.0
 */
public final class DetailActivity extends DuovocBaseActivity {

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

        final MenuItem menuItemSync = menu.findItem(R.id.menu_sync_button);
        final MenuItem menuItemSwitchLanguage = menu.findItem(R.id.menu_switch_language);

        menuItemSync.setVisible(false);
        menuItemSwitchLanguage.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        final int itemId = item.getItemId();

        if (itemId == R.id.menu_learn_on_duolingo) {
            if (!super.isActiveNetwork()) {
                // TODO: メッセージ
                this.showInformationToast(MessageID.IJP00006);
                return true;
            }

            if (!super.isOnlineMode()) {
                // TODO: メッセージ
                this.showInformationToast(MessageID.IJP00006);
                return true;
            }

            final OverviewInformation overviewInformation = this.getOverviewInformation();

            if (overviewInformation.isEmpty()) {
                // TODO: 業務エラー
                super.showInformationToast(MessageID.IJP00008);
                return true;
            }

            final ModelMap<OverviewColumnKey, Object> modelMap = overviewInformation.getModelInfo().get(0);
            final String language = modelMap.getString(OverviewColumnKey.Language);
            final String skillUrlTitle = modelMap.getString(OverviewColumnKey.SkillUrlTitle);

            final String URL_LESSON_PAGE = "https://www.duolingo.com/skill/%s/%s/practice";
            final Uri parsedUrl = Uri.parse(String.format(URL_LESSON_PAGE, language, skillUrlTitle));

            // 該当のレッスンページへ遷移させる
            super.startActivityOnBrowser(parsedUrl);
        }

        return true;
    }

    @Override
    protected void initializeView() {
        final String methodName = "initializeView";
        Logger.Info.write(TAG, methodName, "START");

        if (!BuildConfig.PAID) {
            super.displayBannerAdvertisement(R.id.advertisement_detail_activity_top);
            super.displayBannerAdvertisement(R.id.advertisement_detail_activity_bottom);

            final TextView textViewMemo = this.findViewById(R.id.detail_title_memo);
            final TextInputLayout editTextMemo = this.findViewById(R.id.layout_output_memo);

            final LinearLayout layoutDetailScrollView = this.findViewById(R.id.layout_detail_scroll_view);
            layoutDetailScrollView.removeView(textViewMemo);
            layoutDetailScrollView.removeView(editTextMemo);
        } else {
            // 広告バナーのコンポーネントを除去する
            super.removeBannerAdvertisement(
                    R.id.layout_detail_scroll_view,
                    R.id.advertisement_detail_activity_top,
                    R.id.advertisement_detail_activity_bottom);
        }

        super.displayBackButtonOnActionBar();

        final String overviewId = this.getIntent().getStringExtra(IntentExtraKey.OverviewId.getKeyName());
        this.initializeHintList(overviewId);

        final OverviewInformation overviewInformation = this.getOverviewInformation();
        overviewInformation.selectByPrimaryKey(overviewId);

        if (overviewInformation.isEmpty()) {
            // TODO: エラーダイアログ(起こりえないが、概要画面での再同期化を促す)
            this.showInformationToast(MessageID.IJP00001);
            this.finish();
            return;
        }

        final ModelMap<OverviewColumnKey, Object> modelMap = overviewInformation.getModelInfo().get(0);

        this.setProficiencyRate(modelMap);
        this.setTextViews(modelMap);
        this.setViewRelatedLexemes(modelMap.getStringList(OverviewColumnKey.RelatedLexemes));

        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * 概要画面で選択された情報に紐づくヒント情報を
     * 画面へ出力するリストへ設定する処理を定義したメソッドです。
     * 概要情報に紐づくヒントがモデルに存在しない場合は、
     * アクティビティの開始時にヒント情報の同期化処理が発生します。
     *
     * @param overviewId 概要画面で選択された概要情報の識別ID。
     */
    private void initializeHintList(final String overviewId) {

        final OverviewTranslationInformation overviewTranslationInformation = this.getOverviewTranslationInformation();
        overviewTranslationInformation.selectByPrimaryKey(overviewId);

        if (!overviewTranslationInformation.isEmpty()) {
            final ModelMap<OverviewTranslationColumnKey, Object> modelMap = overviewTranslationInformation.getModelInfo().get(0);
            final String hints = modelMap.getString(OverviewTranslationColumnKey.Translation);
            final String[] hintsArray = StringHandler.split(hints, CommonConstants.CHAR_SEPARATOR_PERIOD);

            final List<String> hintsList = new ArrayList<>();
            Collections.addAll(hintsList, hintsArray);

            this.refreshHintsList(hintsList);

        } else {
            // ヒントリストを"-"で設定する
            this.refreshHintsList(new ArrayList<>());
        }
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
            extras.put(IntentExtraKey.OverviewId.getKeyName(), overviewId);
            extras.put(IntentExtraKey.ViewTransferId.getKeyName(), TransitionOriginalScreenId.DetailActivity.getScreenName());

            super.startActivity(DetailActivity.class, extras);
            this.finish();
        });

        if (BuildConfig.PAID) {
            this.setListenersMemo();
        }

        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * メモ機能のイベントリスナーを定義するメソッドです。
     * メモでは以下の機能を提供します。
     * <p>
     * 1, Register
     * ユーザが入力した文字列をモデル「ユーザメモ情報」へ登録します。
     * ユーザが入力した文字列に対して検査は行いません。
     * 登録処理が完了した場合は完了メッセージを出力します。
     * <p>
     * 2, Undo
     * モデル「ユーザメモ情報」に登録されている情報を画面部品に再設定します。
     * 登録されているメモ情報が存在しない場合は再設定処理は行われません。
     */
    private void setListenersMemo() {

        final EditText editTextMemo = this.findViewById(R.id.detail_output_memo);
        final Button buttonRegisterMemo = this.findViewById(R.id.detail_button_register_memo);
        final Button buttonUndoMemo = this.findViewById(R.id.detail_button_undo_memo);

        buttonRegisterMemo.setOnClickListener(v -> {

            final String userMemo = editTextMemo.getText().toString();

            if (!StringChecker.isEffectiveString(StringHandler.trim(userMemo))) {
                // TODO: メッセージ
                DetailActivity.super.showInformationToast(MessageID.IJP00001);
                return;
            }

            // TODO 確認メッセージ
            final OverviewInformation overviewInformation = this.getOverviewInformation();
            final ModelMap<OverviewColumnKey, Object> overviewModelInfo = overviewInformation.getModelInfo().get(0);

            final UserMemoHolder userMemoHolder = new UserMemoHolder();
            userMemoHolder.setUserId(overviewModelInfo.getString(OverviewColumnKey.UserId));
            userMemoHolder.setOverviewId(overviewModelInfo.getString(OverviewColumnKey.Id));
            userMemoHolder.setMemo(userMemo);

            final UserMemoInformation userMemoInformation = this.getUserMemoInformation();
            userMemoInformation.replace(userMemoHolder);

            // TODO: 完了メッセージ
            this.showInformationToast(MessageID.IJP00001);
        });

        buttonUndoMemo.setOnClickListener(v -> {

            final OverviewInformation overviewInformation = this.getOverviewInformation();
            final ModelMap<OverviewColumnKey, Object> overviewModelInfo = overviewInformation.getModelInfo().get(0);

            final String userId = overviewModelInfo.getString(OverviewColumnKey.UserId);
            final String overviewId = overviewModelInfo.getString(OverviewColumnKey.Id);

            final UserMemoInformation userMemoInformation = this.getUserMemoInformation();
            userMemoInformation.selectByUserInformation(userId, overviewId);

            if (!userMemoInformation.isEmpty()) {
                final ModelMap<UserMemoColumnKey, Object> modelMap = userMemoInformation.getModelInfo().get(0);
                editTextMemo.setText(modelMap.getString(UserMemoColumnKey.Memo));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        final OverviewTranslationInformation overviewTranslationInformation = this.getOverviewTranslationInformation();

        if (super.isOnlineMode()) {
            final OverviewInformation overviewInformation = this.getOverviewInformation();
            final ModelMap<OverviewColumnKey, Object> overviewMap = overviewInformation.getModelInfo().get(0);

            if (overviewTranslationInformation.isEmpty()) {
                // 初期起動時の処理
                this.synchronizeHintInformation(overviewMap);
            } else {

                final ModelMap<OverviewTranslationColumnKey, Object> hintsMap = this.getOverviewTranslationInformation().getModelInfo().get(0);
                final String hintModifiedDatetime = hintsMap.getString(OverviewTranslationColumnKey.ModifiedDatetime);

                if (super.getElapsedDay(hintModifiedDatetime) > 30) {
                    // 最終更新日時から1ヶ月経過していた場合
                    this.synchronizeHintInformation(overviewMap);
                }
            }
        } else {
            if (overviewTranslationInformation.isEmpty()) {
                // TODO: メッセージ内容
                super.showInformationToast(MessageID.IJP00008);
            }
        }
    }


    /**
     * 概要情報から取得した値を詳細画面の各テキストビューに設定する処理を定義したメソッドです。
     * 設定する値が存在しない場合は初期値として"-"を設定します。
     *
     * @param modelMap 概要情報のモデル情報。
     */
    private void setTextViews(final ModelMap<OverviewColumnKey, Object> modelMap) {
        final String methodName = "setTextViews";
        Logger.Info.write(TAG, methodName, "START");

        final TextView textViewSkillName = this.findViewById(R.id.detail_output_skill_name);
        textViewSkillName.setText(modelMap.getString(OverviewColumnKey.Skill));

        final TextView textViewLastPracticed = this.findViewById(R.id.detail_output_last_practiced);
        final String displayLastPracticed = modelMap.getString(OverviewColumnKey.DisplayLastPracticed);
        textViewLastPracticed.setText(displayLastPracticed.substring(0, displayLastPracticed.indexOf(" ")));

        final TextView textViewElapsedDay = this.findViewById(R.id.detail_output_elapsed_day);

        final String lastPracticed = modelMap.getString(OverviewColumnKey.LastPracticed);
        final String parsedDatetime = CalendarHandler.parseDatetime(lastPracticed, CommonConstants.FORMAT_ISO_DATE_TIME);
        final int elapsedDay = this.getElapsedDay(parsedDatetime);
        final String displayDay = elapsedDay > 1 ? "days" : "day";
        final String displayElapsedDay = elapsedDay + " " + displayDay;

        textViewElapsedDay.setText(displayElapsedDay);

        final TextView textViewLanguage = this.findViewById(R.id.outputLanguage);
        final TextView textViewWord = this.findViewById(R.id.outputWord);
        final TextView textViewPosition = this.findViewById(R.id.detail_output_position);
        final TextView textViewInfinitive = this.findViewById(R.id.outputInfinitive);
        final TextView textViewGender = this.findViewById(R.id.outputGender);

        textViewLanguage.setText(modelMap.getString(OverviewColumnKey.LanguageString));
        textViewWord.setText(modelMap.getString(OverviewColumnKey.WordString));
        textViewPosition.setText(this.convertOutput(modelMap.getString(OverviewColumnKey.Pos)));
        textViewInfinitive.setText(this.convertOutput(modelMap.getString(OverviewColumnKey.Infinitive)));
        textViewGender.setText(this.convertOutput(modelMap.getString(OverviewColumnKey.Gender)));

        if (BuildConfig.PAID) {
            this.initializeViewMemo(modelMap);
        }

        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * アクティビティ生成時におけるメモ部品の初期化処理を定義したメソッドです。
     * ユーザが登録したメモ情報が存在する場合は画面部品に設定します。
     *
     * @param modelMap 概要情報のモデル情報。
     */
    private void initializeViewMemo(final ModelMap<OverviewColumnKey, Object> modelMap) {

        final String userId = modelMap.getString(OverviewColumnKey.UserId);
        final String overviewId = modelMap.getString(OverviewColumnKey.Id);

        final UserMemoInformation userMemoInformation = this.getUserMemoInformation();
        userMemoInformation.selectByUserInformation(userId, overviewId);

        if (!userMemoInformation.isEmpty()) {
            final ModelMap<UserMemoColumnKey, Object> userMemoModelInfo = userMemoInformation.getModelInfo().get(0);
            final EditText editTextMemo = this.findViewById(R.id.detail_output_memo);
            editTextMemo.setText(userMemoModelInfo.getString(UserMemoColumnKey.Memo));
        }
    }

    /**
     * 画面に出力する習熟度グラフの設定処理を行うメソッドです。
     */
    @SuppressLint("SetTextI18n")
    private void setProficiencyRate(final ModelMap<OverviewColumnKey, Object> modelMap) {

        final double proficiencyRate = modelMap.getDouble(OverviewColumnKey.Strength);

        // 小数点第1位で切り捨て
        final BigDecimal bigDecimal = new BigDecimal(proficiencyRate * 100);
        final BigDecimal scaledBigDecimal = bigDecimal.setScale(0, BigDecimal.ROUND_DOWN);
        final int displayProficiencyRate = scaledBigDecimal.intValue();

        final ProgressBar statusProgressOfStrength = this.findViewById(R.id.status_progress);
        final TextView textViewRateOfStrength = this.findViewById(R.id.rate_of_strength);

        statusProgressOfStrength.setProgress(displayProficiencyRate);
        textViewRateOfStrength.setText(displayProficiencyRate + "%");
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

            final OverviewRelatedLexemeInformation overviewRelatedLexemeInformation = this.getOverviewRelatedLexemeInformation();

            for (String relatedLexeme : relatedLexemes) {
                final RelatedLexemesSingleRow relatedLexemesSingleRow = new RelatedLexemesSingleRow();
                overviewRelatedLexemeInformation.selectByPrimaryKey(relatedLexeme);

                if (overviewRelatedLexemeInformation.isEmpty()) {
                    // TODO: 業務エラーメッセージ(ありえないが、概要情報の再同期を促す)
                    this.finish();
                    return;
                }

                final ModelMap<OverviewRelatedLexemeColumnKey, Object> modelMap = overviewRelatedLexemeInformation.getModelInfo().get(0);
                relatedLexemesSingleRow.setOverviewId(modelMap.getString(OverviewRelatedLexemeColumnKey.OverviewId));
                relatedLexemesSingleRow.setWord(modelMap.getString(OverviewRelatedLexemeColumnKey.Word));
                relatedLexemesSingleRow.setLessonName(modelMap.getString(OverviewRelatedLexemeColumnKey.LessonName));

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
    private void synchronizeHintInformation(final ModelMap<OverviewColumnKey, Object> modelMap) {
        final String methodName = "synchronizeHintInformation";
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

                final OverviewTranslationInformation overviewTranslationInformation = DetailActivity.super.getOverviewTranslationInformation();
                overviewTranslationInformation.replace(overviewTranslationHolder);

                // 登録した情報をモデルオブジェクトに展開
                overviewTranslationInformation.selectByPrimaryKey(overviewTranslationHolder.getId());

                DetailActivity.this.refreshHintsList(overviewTranslationHolder.getHints());
                DetailActivity.super.dismissDialog();
            }
        };

        final String word = modelMap.getString(OverviewColumnKey.WordString);
        final String format = "new";

        Logger.Info.write(TAG, methodName, "END");
        asyncOverviewTranslation.execute(word, format);
    }

    /**
     * 画面に出力するヒントリストを設定する処理を定義したメソッドです。
     * 画面へ出力するヒントリストが存在しない場合は初期値として"-"を設定します。
     *
     * @param hintsList ヒントリスト。
     */
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
