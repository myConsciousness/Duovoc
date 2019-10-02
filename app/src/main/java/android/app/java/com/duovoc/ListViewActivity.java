package android.app.java.com.duovoc;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.adapter.OverviewAdapter;
import android.app.java.com.duovoc.communicate.HttpAsyncOverview;
import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.MessageID;
import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.holder.CurrentUserHolder;
import android.app.java.com.duovoc.holder.OverviewHolder;
import android.app.java.com.duovoc.holder.OverviewSingleRow;
import android.app.java.com.duovoc.model.CurrentUserInformation;
import android.app.java.com.duovoc.model.OverviewInformation;
import android.app.java.com.duovoc.model.property.CurrentUserColumnKey;
import android.app.java.com.duovoc.model.property.OverviewColumnKey;
import android.app.java.com.duovoc.model.property.UserColumnKey;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Parcelable;
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
import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

final public class ListViewActivity extends BaseActivity {

    private static final String TAG = ListViewActivity.class.getSimpleName();
    private final OverviewInformation overviewInformation = OverviewInformation.getInstance(this);
    private final CurrentUserInformation currentUserInformation = CurrentUserInformation.getInstance(this);
    private OverviewAdapter overviewAdapter;

    public ListViewActivity() {
        super(R.layout.activity_listview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        final int itemId = item.getItemId();

        if (itemId == R.id.menuRefreshButton) {

            if (super.isOnlineMode()) {
                this.syncWithDuolingo();
            } else {
                super.buildSigninDialog();
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

        final ListView listView = findViewById(R.id.listview);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            final OverviewSingleRow selected = this.overviewAdapter.getListViewItemsList().get(position);
            final String overviewId = selected.getOverviewId();

            Intent intent = new Intent(getApplication(), DetailActivity.class);
            intent.putExtra(OverviewColumnKey.Id.getKeyName(), overviewId);
            startActivity(intent);
        });

        Logger.Info.write(TAG, methodName, "END");
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
            startActivity(homeIntent);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        if (view.getId() == R.id.listview) {
            getMenuInflater().inflate(R.menu.overview_list_context_menu, menu);
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
            if (super.isOnlineMode()
                    && super.isActiveNetwork()) {

                if (!this.overviewInformation.selectByPrimaryKey(overviewSingleRow.getOverviewId())) {
                    // TODO: 検索エラー
                    return true;
                }

                final ModelMap<OverviewColumnKey, Object> modelMap = this.overviewInformation.getModelInfo().get(0);
                final String language = modelMap.getString(OverviewColumnKey.Language);
                final String skillUrlTitle = modelMap.getString(OverviewColumnKey.SkillUrlTitle);

                final String URL_LESSON_PAGE = "https://www.duolingo.com/skill/%s/%s/practice";
                final Uri parsedUrl = Uri.parse(String.format(URL_LESSON_PAGE, language, skillUrlTitle));

                try {
                    startActivity(this.getBrowserIntent(parsedUrl));
                } catch (ActivityNotFoundException e) {
                    // should not be happened
                    e.printStackTrace();
                }
            }
        } else if (itemId == R.id.copy_word) {

            if (!super.copyToClipboard(this, overviewSingleRow.getWord())) {
                // TODO: コピー時エラー
                return true;
            }
        }

        return true;
    }

    private Intent getBrowserIntent(final Uri uri) {

        // HTTPS通信に対応したデフォルトブラウザを取得する
        final Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"));
        final ResolveInfo defaultResInfo = getPackageManager().resolveActivity(browser, PackageManager.MATCH_DEFAULT_ONLY);

        // デフォルトブラウザが存在する場合
        if (defaultResInfo != null) {
            final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage(defaultResInfo.activityInfo.packageName);

            return intent;
        }

        // デフォルトブラウザが存在しない場合はユーザに選択させる
        final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        final List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        List<Intent> intentList = new ArrayList<>();

        for (ResolveInfo resolveInfo : resolveInfoList) {
            final Intent targeted = new Intent(intent);
            final String packageName = resolveInfo.activityInfo.packageName;

            if (getPackageName().equals(packageName)) {
                // 自分のアプリを選択から外す
                continue;
            }

            targeted.setPackage(packageName);
            intentList.add(targeted);
        }

        final Intent chooser = Intent.createChooser(new Intent(), "Open in browser");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[0]));

        return chooser;
    }

    private void refreshListView() {

        final String userId = getIntent().getStringExtra(UserColumnKey.UserId.getKeyName());

        if (!this.currentUserInformation.selectByPrimaryKey(userId)) {
            /** TODO: メッセージ */
            super.showInformationToast(MessageID.IJP00008);
            return;
        }

        final ModelMap<CurrentUserColumnKey, Object> currentUserInfo = currentUserInformation.getModelInfo();

        final String language = currentUserInfo.getString(CurrentUserColumnKey.Language);
        final String fromLanguage = currentUserInfo.getString(CurrentUserColumnKey.FromLanguage);

        if (!this.overviewInformation.selectByCurrentUserInformation(userId, language, fromLanguage)) {
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

        final ListView listview = findViewById(R.id.listview);
        listview.setAdapter(this.overviewAdapter);

        super.registerForContextMenu(listview);
    }

    private void setSearchFilter() {

        final EditText searchFilter = findViewById(R.id.searchFilter);

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

    private void setSwipeRefreshLayout() {

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        final Resources resources = getResources();

        swipeRefreshLayout.setColorSchemeColors(
                resources.getColor(R.color.colorPrimary),
                resources.getColor(R.color.colorPrimaryDark),
                resources.getColor(R.color.accent500)
        );

        swipeRefreshLayout.setOnRefreshListener(() -> {

            refreshListView();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void syncWithDuolingo() {

        if (!super.isOnlineMode()) {
            /** TODO: メッセージID */
            super.showInformationToast(MessageID.IJP00006);
            return;
        }

        if (!super.isActiveNetwork()) {
            this.showInformationToast(MessageID.IJP00006);
            return;
        }

        if (!super.isActiveWifiNetwork()) {
            this.showInformationToast(MessageID.IJP00007);
            return;
        }

        @SuppressLint("StaticFieldLeak")
        HttpAsyncOverview asyncOverview = new HttpAsyncOverview(getIntent()) {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                ListViewActivity.super.showSpinnerDialog("Syncing", "Please wait for a little...");
            }

            @Override
            protected void onPostExecute(List<OverviewHolder> overviewHolderList) {
                super.onPostExecute(overviewHolderList);

                try {
                    if (!ListViewActivity.this.overviewInformation.replace(overviewHolderList)) {
                        /** TODO: エラーメッセージ */
                        return;
                    }

                    if (!this.updateCurrentUserInformation(overviewHolderList.get(0))) {
                        /** TODO: エラーメッセージ */
                        return;
                    }

                    ListViewActivity.this.refreshListView();

                } finally {
                    ListViewActivity.super.dismissDialog();
                }
            }

            private boolean updateCurrentUserInformation(final OverviewHolder overviewHolder) {

                final CurrentUserHolder currentUserHolder = new CurrentUserHolder();
                currentUserHolder.setUserId(overviewHolder.getUserId());
                currentUserHolder.setLanguage(overviewHolder.getLanguage());
                currentUserHolder.setFromLanguage(overviewHolder.getFromLanguage());

                return ListViewActivity.this.currentUserInformation.replace(currentUserHolder);
            }
        };

        asyncOverview.execute();
    }
}
