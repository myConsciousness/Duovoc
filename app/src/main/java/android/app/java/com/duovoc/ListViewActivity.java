package android.app.java.com.duovoc;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.adapter.OverviewAdapter;
import android.app.java.com.duovoc.communicate.HttpAsyncOverview;
import android.app.java.com.duovoc.communicate.HttpAsyncSwitchLanguage;
import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.MessageID;
import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.communicate.holder.HttpAsyncResults;
import android.app.java.com.duovoc.framework.communicate.property.HttpStatusCode;
import android.app.java.com.duovoc.holder.CurrentUserHolder;
import android.app.java.com.duovoc.holder.OverviewHolder;
import android.app.java.com.duovoc.holder.OverviewSingleRow;
import android.app.java.com.duovoc.holder.SwitchLanguageHolder;
import android.app.java.com.duovoc.model.CurrentUserInformation;
import android.app.java.com.duovoc.model.OverviewInformation;
import android.app.java.com.duovoc.model.property.CurrentUserColumnKey;
import android.app.java.com.duovoc.model.property.OverviewColumnKey;
import android.app.java.com.duovoc.model.property.UserColumnKey;
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
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : ListViewActivity.java
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
final public class ListViewActivity extends DuovocBaseActivity {

    /**
     * クラス名。
     */
    private static final String TAG = ListViewActivity.class.getSimpleName();

    /**
     * 概要リストを操作するアダプタオブジェクト。
     */
    private OverviewAdapter overviewAdapter;

    /**
     * 当該クラスのコンストラクタです。
     * 概要画面のレイアウトを適用するために基底クラスへ概要画面のレイアウトを渡します。
     */
    public ListViewActivity() {
        super(R.layout.activity_listview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        final int itemId = item.getItemId();

        if (itemId == R.id.menu_sync_button) {
            if (super.isOnlineMode()) {
                this.syncOverviewInformation();
            } else {
                super.buildAuthenticationDialog();
            }
        } else if (itemId == R.id.menu_switch_language) {
            if (super.isOnlineMode()) {
                this.switchLanguage();
            } else {
                super.buildAuthenticationDialog();
            }
        }

        return true;
    }

    @Override
    protected void initializeView() {
        final String methodName = "initializeView";
        Logger.Info.write(TAG, methodName, "START");

        this.refreshListView();

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
            extras.put(OverviewColumnKey.Id.getKeyName(), overviewId);

            super.startActivity(DetailActivity.class, extras);
        });

        Logger.Info.write(TAG, methodName, "END");
    }

    @Override
    public void onStart() {
        super.onStart();

        final ModelList<ModelMap<OverviewColumnKey, Object>> overviewList
                = this.getOverviewInformation().getModelInfo();

        if (super.isOnlineMode()) {
            if (overviewList.isEmpty()) {
                // 初期起動時のみ実行する
                this.syncOverviewInformation();
            } else if (false) {
                // TODO: 最終同期日時から1日経過していた場合は同期化を行う
                this.syncOverviewInformation();
            }
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

                final OverviewInformation overviewInformation
                        = this.getOverviewInformation();

                if (!overviewInformation.selectByPrimaryKey(overviewSingleRow.getOverviewId())) {
                    // TODO: 検索エラー
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
                return true;
            }
        } else if (itemId == R.id.bookmark) {

        }

        return true;
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
     * @see #syncOverviewInformation()
     */
    private void refreshListView() {

        final String userId = this.getIntent().getStringExtra(UserColumnKey.UserId.getKeyName());
        final CurrentUserInformation currentUserInformation = this.getCurrentUserInformation();

        if (!currentUserInformation.selectByPrimaryKey(userId)) {
            /** TODO: メッセージ */
            super.showInformationToast(MessageID.IJP00008);
            return;
        }

        final ModelMap<CurrentUserColumnKey, Object> currentUserInfo = currentUserInformation.getModelInfo();

        final String language = currentUserInfo.getString(CurrentUserColumnKey.Language);
        final String fromLanguage = currentUserInfo.getString(CurrentUserColumnKey.FromLanguage);
        final OverviewInformation overviewInformation = this.getOverviewInformation();

        if (!overviewInformation.selectByCurrentUserInformation(userId, language, fromLanguage)) {
            super.showInformationToast(MessageID.IJP00008);
            return;
        }

        final ModelList<ModelMap<OverviewColumnKey, Object>> modelMaps = overviewInformation.getModelInfo();
        final List<OverviewSingleRow> listViewItemsList = new ArrayList<>();

        for (ModelMap<OverviewColumnKey, Object> overview : modelMaps) {
            final OverviewSingleRow listViewItems = new OverviewSingleRow();
            listViewItems.setOverviewId(overview.getString(OverviewColumnKey.Id));
            listViewItems.setWord(overview.getString(OverviewColumnKey.WordString));
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
     * @see HttpAsyncSwitchLanguage
     * @see #syncOverviewInformation()
     */
    private void switchLanguage() {

        if (!this.isNetworkConnectable()) {
            return;
        }

        final String userId = this.getIntent().getStringExtra(UserColumnKey.UserId.getKeyName());
        final String learningLanguage = "dn";
        final String fromLanguage = "en";

        @SuppressLint("StaticFieldLeak")
        HttpAsyncSwitchLanguage httpAsyncSwitchLanguage
                = new HttpAsyncSwitchLanguage(learningLanguage, fromLanguage) {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                ListViewActivity.super.showSpinnerDialog("Switching", "Please wait for a little...");
            }

            @Override
            protected void onPostExecute(HttpAsyncResults httpAsyncResults) {
                super.onPostExecute(httpAsyncResults);

                if (!httpAsyncResults.isHttpStatusOk()) {
                    // TODO: 通信エラー（メッセージ中にステータスとコードをバインドする）
                    ListViewActivity.super.dismissDialog();
                    ListViewActivity.super.showInformationToast(MessageID.IJP00008);
                    return;
                }

                final CurrentUserHolder currentUserHolder = new CurrentUserHolder();
                currentUserHolder.setUserId(userId);
                currentUserHolder.setLanguage(learningLanguage);
                currentUserHolder.setFromLanguage(fromLanguage);

                final CurrentUserInformation currentUserInformation
                        = ListViewActivity.this.getCurrentUserInformation();

                if (!currentUserInformation.replace(currentUserHolder)) {
                    // TODO: モデル更新時のエラーメッセージ
                    ListViewActivity.super.dismissDialog();
                    return;
                }

                final SwitchLanguageHolder switchLanguageHolder
                        = (SwitchLanguageHolder) httpAsyncResults.getModelAccessor();

                if (switchLanguageHolder.isFirstTime()) {
                    // TODO: 初回時は同期化する情報が存在しないため
                    ListViewActivity.super.dismissDialog();
                    ListViewActivity.super.showInformationToast(MessageID.IJP00008);
                } else {
                    // 切り替え後の同期化処理を行う
                    ListViewActivity.super.dismissDialog();
                    ListViewActivity.this.syncOverviewInformation();
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
                ListViewActivity.this.overviewAdapter.getFilter().filter(charSequence);
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
                resources.getColor(R.color.colorPrimary),
                resources.getColor(R.color.colorPrimaryDark),
                resources.getColor(R.color.accent500)
        );

        swipeRefreshLayout.setOnRefreshListener(() -> {

            this.refreshListView();
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
    private void syncOverviewInformation() {

        if (!this.isNetworkConnectable()) {
            return;
        }

        @SuppressLint("StaticFieldLeak")
        HttpAsyncOverview asyncOverview = new HttpAsyncOverview(this.getIntent()) {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                ListViewActivity.super.showSpinnerDialog("Syncing", "Please wait for a little...");
            }

            @Override
            protected void onPostExecute(List<OverviewHolder> overviewHolderList) {
                super.onPostExecute(overviewHolderList);

                if (overviewHolderList.isEmpty()) {
                    ListViewActivity.super.dismissDialog();
                    ListViewActivity.super.showInformationToast(MessageID.IJP00008);
                    return;
                }

                final OverviewInformation overviewInformation
                        = ListViewActivity.super.getOverviewInformation();

                if (!overviewInformation.replace(overviewHolderList)) {
                    /** TODO: エラーメッセージ */
                    ListViewActivity.super.dismissDialog();
                    return;
                }

                if (!this.updateCurrentUserInformation(overviewHolderList.get(0))) {
                    /** TODO: エラーメッセージ */
                    ListViewActivity.super.dismissDialog();
                    return;
                }

                ListViewActivity.super.dismissDialog();
                ListViewActivity.this.refreshListView();
            }

            private boolean updateCurrentUserInformation(final OverviewHolder overviewHolder) {

                final CurrentUserHolder currentUserHolder = new CurrentUserHolder();
                currentUserHolder.setUserId(overviewHolder.getUserId());
                currentUserHolder.setLanguage(overviewHolder.getLanguage());
                currentUserHolder.setFromLanguage(overviewHolder.getFromLanguage());

                final CurrentUserInformation currentUserInformation
                        = ListViewActivity.super.getCurrentUserInformation();

                return currentUserInformation.replace(currentUserHolder);
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
    private void syncVersionInfo() {
        
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
            protected void onPostExecute(HttpAsyncResults httpAsyncResults) {
                super.onPostExecute(httpAsyncResults);

                if (!httpAsyncResults.isHttpStatusOk()) {
                    // TODO: 通信エラー（メッセージ中にステータスとコードをバインドする）
                    ListViewActivity.super.showInformationToast(MessageID.IJP00008);
                    return;
                }
                
                final List<SupportedLanguageHolder> supportedLanguageHolderList 
                       = (List<SupportedLanguageHolder>) httpAsyncResults.getModelAccessorList();

                if (supportedLanguageHolderList.isEmpty()) {
                    ListViewActivity.super.dismissDialog();
                    ListViewActivity.super.showInformationToast(MessageID.IJP00008);
                    return;
                }

                final SupportedLanguageInformation supportedLanguageInformation
                        = ListViewActivity.super.getSupportedLanguageInformation();

                if (!supportedLanguageInformation.replaceAll(supportedLanguageHolderList)) {
                    /** TODO: エラーメッセージ */
                    ListViewActivity.super.dismissDialog();
                    return;
                }
            }
        };
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
}
