package android.app.java.com.duovoc;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.adapter.OverviewRelatedLexemesAdapter;
import android.app.java.com.duovoc.adapter.OverviewTranslationAdapter;
import android.app.java.com.duovoc.communicate.HttpAsyncOverviewTranslation;
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

final public class DetailActivity extends DuovocBaseActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private static final String VALUE_UNDEFINED = "-";

    private OverviewRelatedLexemesAdapter overviewRelatedLexemesAdapter;

    public DetailActivity() {
        super(R.layout.activity_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        final MenuItem syncButton = menu.findItem(R.id.menuRefreshButton);
        syncButton.setVisible(false);

        return true;
    }

    @Override
    protected void initializeView() {
        final String methodName = "initializeView";
        Logger.Info.write(TAG, methodName, "START");

        final String overviewId = this.getIntent().getStringExtra(OverviewColumnKey.Id.getKeyName());
        final OverviewTranslationInformation overviewTranslationInformation = this.getOverviewTranslationInformation(this);

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

        final OverviewInformation overviewInformation = this.getOverviewInformation(this);
        overviewInformation.selectByPrimaryKey(overviewId);
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
            final OverviewInformation overviewInformation = this.getOverviewInformation(this);
            final ModelMap<OverviewColumnKey, Object> modelMap = overviewInformation.getModelInfo().get(0);
            final String userId = modelMap.getString(OverviewColumnKey.UserId);

            final Map<String, String> extras = new HashMap<>();
            extras.put(UserColumnKey.UserId.getKeyName(), userId);

            super.startActivity(ListViewActivity.class, extras);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (super.isOnlineMode()) {
            // 翻訳情報を取得するために非同期処理を行う
            this.getTranslation(this.getOverviewInformation(this).getModelInfo().get(0));
        }
    }

    private void setTextViews(final ModelMap<OverviewColumnKey, Object> modelMap) {

        final TextView textViewLanguage = this.findViewById(R.id.outputLanguage);
        final TextView textViewWord = this.findViewById(R.id.outputWord);
        final TextView textViewGender = this.findViewById(R.id.outputGender);
        final TextView textViewInfinitive = this.findViewById(R.id.outputInfinitive);

        textViewLanguage.setText(modelMap.getString(OverviewColumnKey.LanguageString));
        textViewWord.setText(modelMap.getString(OverviewColumnKey.WordString));
        textViewGender.setText(this.convertOutput(modelMap.getString(OverviewColumnKey.Gender)));
        textViewInfinitive.setText(this.convertOutput(modelMap.getString(OverviewColumnKey.Infinitive)));
    }

    private String convertOutput(final String value) {
        final String methodName = "convertOutput";
        Logger.Info.write(TAG, methodName, "START");

        Logger.Info.write(TAG, methodName, "END");
        return StringChecker.isEffectiveString(value) ? value : VALUE_UNDEFINED;
    }

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
                final OverviewInformation overviewInformation = this.getOverviewInformation(this);

                if (StringChecker.isEffectiveString(relatedLexeme)) {
                    if (!overviewInformation.selectByPrimaryKey(relatedLexeme)) {
                        /** TODO: 業務エラーメッセージ */
                        return;
                    }

                    final ModelMap<OverviewColumnKey, Object> modelMap = overviewInformation.getModelInfo().get(0);
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

    private void getTranslation(final ModelMap<OverviewColumnKey, Object> modelMap) {
        final String methodName = "getTranslation";
        Logger.Info.write(TAG, methodName, "START");

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
        final String word = modelMap.getString(OverviewColumnKey.WordString);
        final String format = "new";

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
                            = DetailActivity.super.getOverviewTranslationInformation(DetailActivity.this);

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
