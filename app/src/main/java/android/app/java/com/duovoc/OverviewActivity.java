package android.app.java.com.duovoc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.java.com.duovoc.adapter.OverviewAdapter;
import android.app.java.com.duovoc.adapter.SwitchFromLanguageAdapter;
import android.app.java.com.duovoc.adapter.SwitchLearningLanguageAdapter;
import android.app.java.com.duovoc.communicate.HttpAsyncOverview;
import android.app.java.com.duovoc.communicate.HttpAsyncSwitchLanguage;
import android.app.java.com.duovoc.communicate.HttpAsyncVersionInfo;
import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.CommonConstants;
import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.MessageID;
import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.StringHandler;
import android.app.java.com.duovoc.framework.communicate.holder.HttpAsyncResults;
import android.app.java.com.duovoc.holder.FromLanguageSingleRow;
import android.app.java.com.duovoc.holder.LearningLanguageSingleRow;
import android.app.java.com.duovoc.holder.OverviewSingleRow;
import android.app.java.com.duovoc.model.CurrentUserInformation;
import android.app.java.com.duovoc.model.OverviewInformation;
import android.app.java.com.duovoc.model.OverviewRelatedLexemeInformation;
import android.app.java.com.duovoc.model.SupportedLanguageInformation;
import android.app.java.com.duovoc.model.holder.CurrentUserHolder;
import android.app.java.com.duovoc.model.holder.OverviewHolder;
import android.app.java.com.duovoc.model.holder.OverviewRelatedLexemeHolder;
import android.app.java.com.duovoc.model.holder.SupportedLanguageHolder;
import android.app.java.com.duovoc.model.holder.SwitchLanguageHolder;
import android.app.java.com.duovoc.model.property.CurrentUserColumnKey;
import android.app.java.com.duovoc.model.property.OverviewColumnKey;
import android.app.java.com.duovoc.model.property.SupportedLanguageColumnKey;
import android.app.java.com.duovoc.property.IntentExtraKey;
import android.app.java.com.duovoc.property.SupportedLanguage;
import android.app.java.com.duovoc.property.TransitionOriginalScreenId;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : OverviewActivity.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 概要画面の表示処理を行うアクティビティです。
 * また、概要情報を取得する際に非同期処理を行います。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see BaseActivity
 * @see DuovocBaseActivity
 * @see HttpAsyncOverview
 * @since 1.0
 */
final public class OverviewActivity extends DuovocBaseActivity {

    /**
     * クラス名。
     */
    private static final String TAG = OverviewActivity.class.getSimpleName();

    /**
     * 概要リストを操作するアダプタオブジェクト。
     */
    private OverviewAdapter overviewAdapter;

    /**
     * 学習言語選択ダイアログのオブジェクト。
     */
    private AlertDialog switchLanguageDialog;

    /**
     * 当該クラスのコンストラクタです。
     * 概要画面のレイアウトを適用するために基底クラスへ概要画面のレイアウトを渡します。
     */
    public OverviewActivity() {
        super(R.layout.activity_listview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        final int itemId = item.getItemId();

        if (itemId == R.id.menu_sync_button) {
            if (super.isOnlineMode()) {
                this.synchronizeOverviewInformation();
            } else {
                super.showAuthenticationDialog();
            }
        } else if (itemId == R.id.menu_switch_language) {
            if (super.isOnlineMode()) {
                this.buildSwitchLanguageDialog();
            } else {
                super.showAuthenticationDialog();
            }
        }

        return true;
    }

    @Override
    protected void initializeView() {
        final String methodName = "initializeView";
        Logger.Info.write(TAG, methodName, "START");

        this.refreshOverviewList();

        Logger.Info.write(TAG, methodName, "END");
    }

    @Override
    protected void setListeners() {
        final String methodName = "setListeners";
        Logger.Info.write(TAG, methodName, "START");

        this.setSwipeRefreshLayout();
        this.setSearchFilter();

        final ListView listView = this.findViewById(R.id.listview);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            final OverviewSingleRow selected = this.overviewAdapter.getListViewItemsList().get(position);
            final String overviewId = selected.getOverviewId();

            final Map<String, String> extras = new HashMap<>();
            extras.put(IntentExtraKey.OverviewId.getKeyName(), overviewId);
            extras.put(IntentExtraKey.ViewTransferId.getKeyName(), TransitionOriginalScreenId.DetailActivity.getScreenName());

            super.startActivity(DetailActivity.class, extras);
        });

        Logger.Info.write(TAG, methodName, "END");
    }

    @Override
    public void onStart() {
        super.onStart();

        final String screenId = this.getIntent().getStringExtra(IntentExtraKey.ViewTransferId.getKeyName());

        if (super.isOnlineMode()
                && TransitionOriginalScreenId.LoginActivity.getScreenName().equals(screenId)) {

            final SupportedLanguageInformation supportedLanguageInformation = this.getSupportedLanguageInformation();
            supportedLanguageInformation.selectAll();

            if (supportedLanguageInformation.getModelInfo().isEmpty()) {
                // 初期起動時のみ実行する
                this.synchronizeSupportedLanguage();
                this.synchronizeOverviewInformation();
            } else {
                this.autoResynchronizeOnStart();
            }
        }
    }

    /**
     * アクティビティの再開始イベントにおける自動再同期処理を定義したメソッドです。
     * 概要情報が存在しない場合は初回時利用ダイアログを出力します。
     */
    private void autoResynchronizeOnStart() {

        final SupportedLanguageInformation supportedLanguageInformation = this.getSupportedLanguageInformation();

        final String supportedLanguageModifiedDatetime
                = supportedLanguageInformation.getModelInfo().get(0).getString(SupportedLanguageColumnKey.ModifiedDatetime);

        if (super.getElapsedDay(supportedLanguageModifiedDatetime) > 15) {
            // 最終更新日から15日が経過した場合
            this.synchronizeSupportedLanguage();
        }

        final ModelList<ModelMap<OverviewColumnKey, Object>> overviewList = this.getOverviewInformation().getModelInfo();

        if (!overviewList.isEmpty()) {

            final ModelMap<OverviewColumnKey, Object> modelMap = overviewList.get(0);
            final String overviewModifiedDatetime = modelMap.getString(OverviewColumnKey.ModifiedDatetime);

            if (super.getElapsedDay(overviewModifiedDatetime) > 7) {
                // 最終更新日から7日が経過した場合
                this.synchronizeOverviewInformation();
            }
        } else {
            this.synchronizeOverviewInformation();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /*
             * ログイン画面へ遷移することを抑止するため、
             * 一覧画面から「戻る」ボタンが押下された場合は、
             * ホーム画面へ戻す処理を定義する。
             */
            final Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(homeIntent);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        if (view.getId() == R.id.listview) {
            this.getMenuInflater().inflate(R.menu.overview_list_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final int itemId = item.getItemId();
        final AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final List<OverviewSingleRow> listViewItemsList = this.overviewAdapter.getListViewItemsList();
        final OverviewSingleRow overviewSingleRow = listViewItemsList.get(adapterContextMenuInfo.position);

        if (itemId == R.id.learn_on_duolingo) {

            // 該当のレッスンページへ遷移させる
            if (super.isOnlineMode() && super.isActiveNetwork()) {

                final OverviewInformation overviewInformation = this.getOverviewInformation();
                overviewInformation.selectByPrimaryKey(overviewSingleRow.getOverviewId());

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

                super.startActivityOnBrowser(parsedUrl);
            }
        } else if (itemId == R.id.copy_word) {
            if (!super.copyToClipboard(this, overviewSingleRow.getWord())) {
                // TODO: コピー時エラー
                super.showInformationToast(MessageID.IJP00008);
                return true;
            }
        } else if (itemId == R.id.bookmark) {

        }

        return true;
    }

    /**
     * 画面に出力されている概要情報リストをクリアする処理を定義したメソッドです。
     */
    private void clearOverviewList() {
        this.overviewAdapter = new OverviewAdapter(this, new ArrayList<>());
        final ListView listview = this.findViewById(R.id.listview);
        listview.setAdapter(this.overviewAdapter);
    }

    /**
     * 概要画面のリストビューをリフレッシュする処理を定義したメソッドです。
     * 当該メソッドではDuolingoと同期化は行わず、
     * 既に論理モデル名「概要情報」に登録されている情報を取得し、
     * リストビューへ再設定する処理を行います。
     * <p>
     * 非同期処理を行いモデルに登録されている概要情報を更新したい場合は、
     * 下記参照のメソッドを使用してください。
     *
     * @see #synchronizeOverviewInformation()
     */
    private void refreshOverviewList() {

        final String userId = this.getIntent().getStringExtra(IntentExtraKey.UserId.getKeyName());

        final CurrentUserInformation currentUserInformation = this.getCurrentUserInformation();
        currentUserInformation.selectByPrimaryKey(userId);

        if (currentUserInformation.isEmpty()) {
            /** TODO: メッセージ */
            super.showInformationToast(MessageID.IJP00008);
            return;
        }

        final ModelMap<CurrentUserColumnKey, Object> currentUserInfo = currentUserInformation.getModelInfo().get(0);

        final String language = currentUserInfo.getString(CurrentUserColumnKey.Language);
        final String fromLanguage = currentUserInfo.getString(CurrentUserColumnKey.FromLanguage);

        final OverviewInformation overviewInformation = this.getOverviewInformation();
        overviewInformation.selectByCurrentUserInformation(userId, language, fromLanguage);

        if (overviewInformation.isEmpty()) {
            super.showInformationToast(MessageID.IJP00008);
            return;
        }

        final ModelList<ModelMap<OverviewColumnKey, Object>> modelMaps = overviewInformation.getModelInfo();
        final List<OverviewSingleRow> listViewItemsList = new ArrayList<>();

        for (ModelMap<OverviewColumnKey, Object> overview : modelMaps) {
            final OverviewSingleRow listViewItems = new OverviewSingleRow();
            listViewItems.setOverviewId(overview.getString(OverviewColumnKey.Id));
            listViewItems.setWord(overview.getString(OverviewColumnKey.WordString));
            listViewItems.setNormalizedWord(overview.getString(OverviewColumnKey.NormalizedString));
            listViewItems.setLessonName(overview.getString(OverviewColumnKey.Skill));
            listViewItems.setLastPracticed(overview.getString(OverviewColumnKey.LastPracticed));

            listViewItemsList.add(listViewItems);
        }

        this.overviewAdapter = new OverviewAdapter(this, listViewItemsList);

        final ListView listview = this.findViewById(R.id.listview);
        listview.setAdapter(this.overviewAdapter);

        super.registerForContextMenu(listview);
    }

    /**
     * ユーザの学習中言語を変更する処理を定義したメソッドです。
     * 学習中言語の変更処理が正常終了した場合は、
     * 論理モデル名「カレントユーザ情報」の情報を更新し、
     * 更新された情報を基に概要情報の取得処理を行います。
     * <p>
     * 学習中言語の変更処理でエラーが発生した場合は、
     * メッセージを出力し当該メソッドの処理を終了します。
     * <p>
     * 同期化処理はバックグラウンド上で行い、
     * 処理中はキャンセル不可なプログレスダイアログを画面上に出力します。
     * <p>
     * 以下の場合は同期化処理を行うことができません。
     * 1, ネットワーク接続が行われていない場合。
     * 2, Wifi接続時のみ同期化処理を行う設定にしている際にWifi接続が行われていない場合。
     * <p>
     * 上記パターンの何れの場合も対応したメッセージを出力して当該メソッド処理を終了します。
     *
     * @param fromLanguage     学習時使用言語。
     * @param learningLanguage 学習言語。
     * @see HttpAsyncSwitchLanguage
     * @see #synchronizeOverviewInformation()
     */
    private void switchLanguage(final String fromLanguage, final String learningLanguage) {

        if (!this.isNetworkConnectable()) {
            return;
        }

        final String userId = this.getIntent().getStringExtra(IntentExtraKey.UserId.getKeyName());

        final CurrentUserInformation currentUserInformation = this.getCurrentUserInformation();
        currentUserInformation.selectByPrimaryKey(userId);

        if (currentUserInformation.isEmpty()) {
            //TODO: 業務エラー
            return;
        }

        final ModelMap<CurrentUserColumnKey, Object> modelMap = currentUserInformation.getModelInfo().get(0);
        final String currentFromLanguage = modelMap.getString(CurrentUserColumnKey.FromLanguage);
        final String currentLearningLanguage = modelMap.getString(CurrentUserColumnKey.Language);

        if (fromLanguage.equals(currentFromLanguage)
                && learningLanguage.equals(currentLearningLanguage)) {
            // TODO: 変更する必要がないため
            return;
        }

        @SuppressLint("StaticFieldLeak")
        HttpAsyncSwitchLanguage httpAsyncSwitchLanguage
                = new HttpAsyncSwitchLanguage(learningLanguage, fromLanguage) {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                OverviewActivity.super.showSpinnerDialog("Switching", "Please wait for a little...");
            }

            @Override
            protected void onPostExecute(HttpAsyncResults httpAsyncResults) {
                super.onPostExecute(httpAsyncResults);

                if (!httpAsyncResults.isHttpStatusOk()) {
                    // TODO: 通信エラー（メッセージ中にステータスとコードをバインドする）
                    OverviewActivity.super.dismissDialog();
                    OverviewActivity.super.showInformationToast(MessageID.IJP00008);
                    return;
                }

                final CurrentUserHolder currentUserHolder = new CurrentUserHolder();
                currentUserHolder.setUserId(userId);
                currentUserHolder.setLanguage(learningLanguage);
                currentUserHolder.setFromLanguage(fromLanguage);

                final CurrentUserInformation currentUserInformation = OverviewActivity.this.getCurrentUserInformation();
                currentUserInformation.replace(currentUserHolder);

                @SuppressWarnings("unchecked") final List<SwitchLanguageHolder> switchLanguageHolderList
                        = (List<SwitchLanguageHolder>) httpAsyncResults.getModelAccessorList();

                if (switchLanguageHolderList.get(0).isFirstTime()) {
                    OverviewActivity.super.dismissDialog();
                    OverviewActivity.this.switchLanguageDialog.dismiss();
                    OverviewActivity.this.clearOverviewList();
                    OverviewActivity.super.showTheFirstDayOfClassDialog(learningLanguage);
                } else {
                    // 切り替え後の同期化処理を行う
                    OverviewActivity.super.dismissDialog();
                    OverviewActivity.this.switchLanguageDialog.dismiss();
                    OverviewActivity.this.synchronizeOverviewInformation();
                }
            }
        };

        httpAsyncSwitchLanguage.execute();
    }

    /**
     * 概要画面の検索フィルターへ入力が発生した際のイベントを定義したメソッドです。
     * フィルタリング処理は下記参照のアダプタ内で定義されています。
     *
     * @see OverviewAdapter
     */
    private void setSearchFilter() {

        final EditText searchFilter = this.findViewById(R.id.searchFilter);

        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                OverviewActivity.this.overviewAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    /**
     * 概要画面のリストビューでスワイプ操作を行った際に発生するイベントを定義したメソッドです。
     * <p>
     * 以下のイベントが定義されています。
     * 1, リストビューのリフレッシュ処理（下方向へのスワイプ操作）
     */
    private void setSwipeRefreshLayout() {

        final SwipeRefreshLayout swipeRefreshLayout = this.findViewById(R.id.swipe);
        final Resources resources = this.getResources();

        swipeRefreshLayout.setColorSchemeColors(
                resources.getColor(R.color.flatShamrock),
                resources.getColor(R.color.flatLightCoral),
                resources.getColor(R.color.flatWhiteSmoke)
        );

        swipeRefreshLayout.setOnRefreshListener(() -> {

            this.refreshOverviewList();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    /**
     * Duolingoと概要情報の同期化を行う処理を定義したメソッドです。
     * 同期化された情報は論理モデル名「概要情報」へ登録されます。
     * <p>
     * 同期化処理はバックグラウンド上で行い、
     * 処理中はキャンセル不可なプログレスダイアログを画面上に出力します。
     * <p>
     * 以下の場合は同期化処理を行うことができません。
     * 1, ネットワーク接続が行われていない場合。
     * 2, Wifi接続時のみ同期化処理を行う設定にしている際にWifi接続が行われていない場合。
     * <p>
     * 上記パターンの何れの場合も対応したメッセージを出力して当該メソッド処理を終了します。
     *
     * @see HttpAsyncOverview
     */
    private void synchronizeOverviewInformation() {

        if (!this.isNetworkConnectable()) {
            return;
        }

        final String userId = this.getIntent().getStringExtra(IntentExtraKey.UserId.getKeyName());

        @SuppressLint("StaticFieldLeak")
        HttpAsyncOverview asyncOverview = new HttpAsyncOverview(userId) {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                OverviewActivity.super.showSpinnerDialog("Syncing", "Please wait for a little...");
            }

            @Override
            protected void onPostExecute(HttpAsyncResults httpAsyncResults) {
                super.onPostExecute(httpAsyncResults);

                if (!httpAsyncResults.isHttpStatusOk()) {
                    // TODO: 通信エラー（メッセージ中にステータスとコードをバインドする）
                    OverviewActivity.super.showInformationToast(MessageID.IJP00008);
                    return;
                }

                @SuppressWarnings("unchecked") final List<OverviewHolder> overviewHolderList
                        = (List<OverviewHolder>) httpAsyncResults.getModelAccessorList();

                if (overviewHolderList.isEmpty()) {
                    OverviewActivity.super.dismissDialog();
                    OverviewActivity.super.showTheFirstDayOfClassDialog(super.getLearningLanguage());
                    return;
                }

                this.updateOverviewInformation(overviewHolderList);
                this.updateOverviewRelatedLexemeInformation(overviewHolderList);
                this.updateCurrentUserInformation(overviewHolderList.get(0));

                OverviewActivity.super.dismissDialog();
                OverviewActivity.this.refreshOverviewList();
            }

            private void updateOverviewInformation(final List<OverviewHolder> overviewHolderList) {
                final OverviewInformation overviewInformation = OverviewActivity.super.getOverviewInformation();
                overviewInformation.replace(overviewHolderList);
            }

            private void updateOverviewRelatedLexemeInformation(final List<OverviewHolder> overviewHolderList) {

                final List<OverviewRelatedLexemeHolder> overviewRelatedLexemeHolderList = new ArrayList<>();

                for (OverviewHolder overviewHolder : overviewHolderList) {
                    if (!overviewHolder.getRelatedLexemes().isEmpty()) {
                        final OverviewRelatedLexemeHolder overviewRelatedLexemeHolder = new OverviewRelatedLexemeHolder();
                        overviewRelatedLexemeHolder.setLexemeId(overviewHolder.getLexemeId());
                        overviewRelatedLexemeHolder.setOverviewId(overviewHolder.getId());
                        overviewRelatedLexemeHolder.setWord(overviewHolder.getWordString());
                        overviewRelatedLexemeHolder.setLessonName(overviewHolder.getSkill());

                        overviewRelatedLexemeHolderList.add(overviewRelatedLexemeHolder);
                    }
                }

                final OverviewRelatedLexemeInformation overviewRelatedLexemeInformation
                        = OverviewActivity.super.getOverviewRelatedLexemeInformation();

                overviewRelatedLexemeInformation.replace(overviewRelatedLexemeHolderList);
            }

            private void updateCurrentUserInformation(final OverviewHolder overviewHolder) {
                final CurrentUserHolder currentUserHolder = new CurrentUserHolder();
                currentUserHolder.setUserId(overviewHolder.getUserId());
                currentUserHolder.setLanguage(overviewHolder.getLanguage());
                currentUserHolder.setFromLanguage(overviewHolder.getFromLanguage());

                final CurrentUserInformation currentUserInformation = OverviewActivity.super.getCurrentUserInformation();

                // 管理するカレントユーザ情報は必ず一件のみ
                currentUserInformation.deleteAll();
                currentUserInformation.insert(currentUserHolder);
            }
        };

        asyncOverview.execute();
    }

    /**
     * Duolingoと最新バージョン情報の同期化を行う処理を定義したメソッドです。
     * 同期化された情報は論理モデル名「サポート言語情報」へ登録されます。
     * <p>
     * 同期化処理はバックグラウンド上で行い、
     * 処理中はキャンセル不可なプログレスダイアログを画面上に出力します。
     * <p>
     * 以下の場合は同期化処理を行うことができません。
     * 1, ネットワーク接続が行われていない場合。
     * 2, Wifi接続時のみ同期化処理を行う設定にしている際にWifi接続が行われていない場合。
     * <p>
     * 上記パターンの何れの場合も対応したメッセージを出力して当該メソッド処理を終了します。
     *
     * @see HttpAsyncVersionInfo
     */
    private void synchronizeSupportedLanguage() {

        if (!this.isNetworkConnectable()) {
            return;
        }

        @SuppressLint("StaticFieldLeak")
        HttpAsyncVersionInfo httpAsyncVersionInfo = new HttpAsyncVersionInfo() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(final HttpAsyncResults httpAsyncResults) {
                super.onPostExecute(httpAsyncResults);

                if (!httpAsyncResults.isHttpStatusOk()) {
                    // TODO: 通信エラー（メッセージ中にステータスとコードをバインドする）
                    OverviewActivity.super.showInformationToast(MessageID.IJP00008);
                    return;
                }

                @SuppressWarnings("unchecked") final List<SupportedLanguageHolder> supportedLanguageHolderList
                        = (List<SupportedLanguageHolder>) httpAsyncResults.getModelAccessorList();

                if (supportedLanguageHolderList.isEmpty()) {
                    OverviewActivity.super.dismissDialog();
                    OverviewActivity.super.showInformationToast(MessageID.IJP00008);
                    return;
                }

                final SupportedLanguageInformation supportedLanguageInformation
                        = OverviewActivity.super.getSupportedLanguageInformation();

                supportedLanguageInformation.replace(supportedLanguageHolderList);
            }
        };

        httpAsyncVersionInfo.execute();
    }

    /**
     * 学習言語変更ダイアログのオブジェクトを構築し画面上に出力します。
     */
    protected void buildSwitchLanguageDialog() {

        final View viewDialog = this.getLayoutInflater().inflate(R.layout.switch_language_dialog, null);
        this.initializeSwitchLanguageDialog(viewDialog);

        if (this.switchLanguageDialog == null) {

            this.setListenerSwitchLanguageDialog(viewDialog);

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setView(viewDialog);
            this.switchLanguageDialog = dialogBuilder.create();
        }

        this.switchLanguageDialog.show();
    }

    /**
     * 学習言語変更ダイアログの画面上に出力する情報を初期化します。
     *
     * @param viewDialog 学習言語変更ダイアログのオブジェクト。
     */
    private void initializeSwitchLanguageDialog(final View viewDialog) {

        final SupportedLanguageInformation supportedLanguageInformation = this.getSupportedLanguageInformation();
        supportedLanguageInformation.selectAll();

        if (supportedLanguageInformation.isEmpty()) {
            // TODO: 業務エラー
            return;
        }

        final List<FromLanguageSingleRow> fromLanguageSingleRowList = new ArrayList<>();
        final ModelList<ModelMap<SupportedLanguageColumnKey, Object>> modelMaps = supportedLanguageInformation.getModelInfo();

        for (ModelMap<SupportedLanguageColumnKey, Object> modelMap : modelMaps) {
            final String fromLanguageCode = modelMap.getString(SupportedLanguageColumnKey.FromLanguage);
            final SupportedLanguage fromLanguage = SupportedLanguage.getSupportedLanguageFromCode(fromLanguageCode);

            final FromLanguageSingleRow fromLanguageSingleRow = new FromLanguageSingleRow();
            fromLanguageSingleRow.setFromLanguage(fromLanguage.getDisplayEnglishName());
            fromLanguageSingleRow.setFromLanguageCode(fromLanguage.getLanguageCode());
            fromLanguageSingleRowList.add(fromLanguageSingleRow);
        }

        final SwitchFromLanguageAdapter switchLanguageAdapter = new SwitchFromLanguageAdapter(this, fromLanguageSingleRowList);
        final Spinner spinnerFromLanguage = viewDialog.findViewById(R.id.spinner_from_language);
        spinnerFromLanguage.setAdapter(switchLanguageAdapter);

        final CurrentUserInformation currentUserInformation = this.getCurrentUserInformation();
        final String userId = this.getIntent().getStringExtra(IntentExtraKey.UserId.getKeyName());

        currentUserInformation.selectByPrimaryKey(userId);

        if (currentUserInformation.isEmpty()) {
            // TODO: 業務エラー
            return;
        }

        // カレントユーザ情報から学習時使用言語の初期値を設定する
        final ModelMap<CurrentUserColumnKey, Object> modelMap = currentUserInformation.getModelInfo().get(0);
        final String currentFromLanguage = modelMap.getString(CurrentUserColumnKey.FromLanguage);

        for (int i = 0, rowCount = fromLanguageSingleRowList.size(); i < rowCount; i++) {
            if (currentFromLanguage.equals(fromLanguageSingleRowList.get(i).getFromLanguageCode())) {
                spinnerFromLanguage.setSelection(i);
                break;
            }
        }
    }

    /**
     * 学習言語変更ダイアログの各部品にイベントをバインドします。
     * 当該処理は学習言語変更ダイアログを初回起動した時に実行されます。
     *
     * @param viewDialog 学習言語変更ダイアログのオブジェクト。
     */
    private void setListenerSwitchLanguageDialog(final View viewDialog) {

        final Spinner spinnerFromLanguage = viewDialog.findViewById(R.id.spinner_from_language);
        final Spinner spinnerLearningLanguage = viewDialog.findViewById(R.id.spinner_learning_language);
        final Button buttonCancel = viewDialog.findViewById(R.id.dialog_switch_language_cancel);
        final Button buttonChange = viewDialog.findViewById(R.id.dialog_switch_language_change);

        spinnerFromLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final FromLanguageSingleRow fromLanguageSingleRow
                        = (FromLanguageSingleRow) adapterView.getItemAtPosition(i);

                this.refreshLearningLanguageSpinner(fromLanguageSingleRow);
            }

            private void refreshLearningLanguageSpinner(final FromLanguageSingleRow fromLanguageSingleRow) {

                final String fromLanguageCode = fromLanguageSingleRow.getFromLanguageCode();

                final SupportedLanguageInformation supportedLanguageInformation = OverviewActivity.this.getSupportedLanguageInformation();
                supportedLanguageInformation.selectByPrimaryKey(fromLanguageCode);

                if (supportedLanguageInformation.isEmpty()) {
                    // TODO: 業務エラー
                    return;
                }

                final ModelMap<SupportedLanguageColumnKey, Object> modelMap = supportedLanguageInformation.getModelInfo().get(0);

                final String csvLanguageDirections = modelMap.getString(SupportedLanguageColumnKey.LearningLanguage);
                final String[] languageDirections = StringHandler.split(csvLanguageDirections, CommonConstants.CHAR_SEPARATOR_PERIOD);

                final List<LearningLanguageSingleRow> learningLanguageSingleRowList = new ArrayList<>();

                for (String languageDirection : languageDirections) {
                    final SupportedLanguage learningLanguage = SupportedLanguage.getSupportedLanguageFromCode(languageDirection);

                    final LearningLanguageSingleRow learningLanguageSingleRow = new LearningLanguageSingleRow();
                    learningLanguageSingleRow.setLearningLanguage(learningLanguage.getDisplayEnglishName());
                    learningLanguageSingleRow.setLearningLanguageCode(learningLanguage.getLanguageCode());
                    learningLanguageSingleRowList.add(learningLanguageSingleRow);
                }

                final SwitchLearningLanguageAdapter switchFromLanguageAdapter
                        = new SwitchLearningLanguageAdapter(OverviewActivity.this, learningLanguageSingleRowList);
                final Spinner spinnerLearningLanguage = viewDialog.findViewById(R.id.spinner_learning_language);

                spinnerLearningLanguage.setAdapter(switchFromLanguageAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        buttonCancel.setOnClickListener(view ->
                OverviewActivity.this.switchLanguageDialog.dismiss());

        buttonChange.setOnClickListener(view -> {
            final FromLanguageSingleRow fromLanguageSingleRow = (FromLanguageSingleRow) spinnerFromLanguage.getSelectedItem();
            final LearningLanguageSingleRow learningLanguageSingleRow = (LearningLanguageSingleRow) spinnerLearningLanguage.getSelectedItem();

            final String fromLanguageCode = fromLanguageSingleRow.getFromLanguageCode();
            final String learningLanguageCode = learningLanguageSingleRow.getLearningLanguageCode();

            OverviewActivity.this.switchLanguage(fromLanguageCode, learningLanguageCode);
        });
    }

    /**
     * ネットワーク接続の可否を判定し、
     * 判定結果を真偽値で返却します。
     * ネットワーク接続が不可の場合はメッセージを出力します。
     *
     * @return ネットワーク接続が可能な場合は {@code true}、それ以外は{@code false}
     */
    private boolean isNetworkConnectable() {

        if (!super.isActiveNetwork()) {
            this.showInformationToast(MessageID.IJP00006);
            return false;
        }

        if (!super.isActiveWifiNetwork()) {
            this.showInformationToast(MessageID.IJP00007);
            return false;
        }

        return true;
    }

    // ================= 以下注意 =====================================

    @Override
    protected void onPreAuthentication() {
        /*
         * 認証ダイアログでの処理を行う際に、
         * 本来意図された動作が行われなくなるため概要画面で当該メソッドの実装は禁止。
         */
        super.onPreAuthentication();
    }

    @Override
    protected void onPostAuthentication() {
        /*
         * 認証ダイアログでの処理を行う際に、
         * 本来意図された動作が行われなくなるため概要画面で当該メソッドの実装は禁止。
         */
        super.onPostAuthentication();
    }

    @Override
    protected void startActivityOnPostAuthentication(final String userId) {
        /*
         * 認証ダイアログでの処理を行う際に、
         * 本来意図された動作が行われなくなるため概要画面で当該メソッドの実装は禁止。
         */
        super.startActivityOnPostAuthentication(userId);
    }
}
