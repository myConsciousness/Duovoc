package android.app.java.com.duovoc;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.adapter.OverviewRelatedLexemesAdapter;
import android.app.java.com.duovoc.adapter.OverviewTranslationAdapter;
import android.app.java.com.duovoc.communicate.HttpAsyncOverviewTranslation;
import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.MessageID;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.holder.HintSingleRow;
import android.app.java.com.duovoc.holder.OverviewTranslationHolder;
import android.app.java.com.duovoc.holder.RelatedLexemesSingleRow;
import android.app.java.com.duovoc.model.OverviewInformation;
import android.app.java.com.duovoc.model.OverviewTranslationInformation;
import android.app.java.com.duovoc.model.property.OverviewColumnKey;
import android.app.java.com.duovoc.model.property.OverviewTranslationColumnKey;
import android.app.java.com.duovoc.model.property.UserColumnKey;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

final public class DetailActivity extends BaseActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private static final String VALUE_UNDEFINED = "-";

    private final OverviewInformation overviewInformation = OverviewInformation.getInstance(this);
    private final OverviewTranslationInformation overviewTranslationInformation = OverviewTranslationInformation.getInstance(this);

    private OverviewRelatedLexemesAdapter overviewRelatedLexemesAdapter;

    public DetailActivity() {
        super(R.layout.activity_detail);
    }

    @Override
    protected void initializeView() {
        final String methodName = "initializeView";
        Logger.Info.write(TAG, methodName, "START");

        final String overviewId = getIntent().getStringExtra(OverviewColumnKey.Id.getKeyName());

        if (!this.overviewInformation.selectByPrimaryKey(overviewId)) {
            /** TODO: 復帰不可能エラー */
            super.showInformationToast(MessageID.IJP00008);
            finish();
        }

        if (this.overviewTranslationInformation.selectByPrimaryKey(overviewId)) {

            final ModelMap<OverviewTranslationColumnKey, Object> modelMap = this.overviewTranslationInformation.getModelInfo();

            final String[] hintsArray = modelMap.getString(OverviewTranslationColumnKey.Translation).split(",");
            final List<String> hintsList = new ArrayList<>();

            for (String hint : hintsArray) {
                hintsList.add(hint);
            }

            this.refreshHintsList(hintsList);

        } else {
            if (super.isOnlineMode()) {
                // 翻訳情報を取得するために非同期処理を行う
                this.getTranslation(this.overviewInformation.getModelInfo().get(0));
            } else {
                // ヒント欄に表示する情報が存在しないため"-"を設定する
                /**TODO: メッセージ */
                super.showInformationToast(MessageID.IJP00008);
                this.refreshHintsList(new ArrayList<>());
            }
        }

        final ModelMap<OverviewColumnKey, Object> modelMap = this.overviewInformation.getModelInfo().get(0);

        this.setTextViews(modelMap);
        this.setViewRelatedLexemes(modelMap.getStringList(OverviewColumnKey.RelatedLexemes));

        Logger.Info.write(TAG, methodName, "END");
    }

    private void setTextViews(final ModelMap<OverviewColumnKey, Object> modelMap) {

        final TextView textViewLanguage = findViewById(R.id.outputLanguage);
        final TextView textViewWord = findViewById(R.id.outputWord);
        final TextView textViewGender = findViewById(R.id.outputGender);
        final TextView textViewInfinitive = findViewById(R.id.outputInfinitive);

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

        final List<RelatedLexemesSingleRow> relatedLexemesSingleRowList = new ArrayList<>();

        if (relatedLexemes.isEmpty()) {

            // リストが空の場合は"-"を表示する
            final RelatedLexemesSingleRow relatedLexemesSingleRow = new RelatedLexemesSingleRow();
            relatedLexemesSingleRow.setWord(VALUE_UNDEFINED);
            relatedLexemesSingleRowList.add(relatedLexemesSingleRow);

        } else {
            for (String relatedLexeme : relatedLexemes) {

                final RelatedLexemesSingleRow relatedLexemesSingleRow = new RelatedLexemesSingleRow();

                if (StringChecker.isEffectiveString(relatedLexeme)) {
                    if (!this.overviewInformation.selectByPrimaryKey(relatedLexeme)) {
                        /** TODO: 業務エラーメッセージ */
                        return;
                    }

                    final ModelMap<OverviewColumnKey, Object> modelMap = this.overviewInformation.getModelInfo().get(0);
                    relatedLexemesSingleRow.setOverviewId(modelMap.getString(OverviewColumnKey.Id));
                    relatedLexemesSingleRow.setWord(modelMap.getString(OverviewColumnKey.WordString));
                    relatedLexemesSingleRow.setLessonName(modelMap.getString(OverviewColumnKey.Skill));
                }

                relatedLexemesSingleRowList.add(relatedLexemesSingleRow);
            }
        }

        this.overviewRelatedLexemesAdapter = new OverviewRelatedLexemesAdapter(this, relatedLexemesSingleRowList);
        final ListView listViewRelatedLexemes = findViewById(R.id.outputRelatedLexemes);

        listViewRelatedLexemes.setAdapter(this.overviewRelatedLexemesAdapter);
    }

    private void getTranslation(final ModelMap<OverviewColumnKey, Object> modelMap) {
        final String methodName = "getTranslation";
        Logger.Info.write(TAG, methodName, "START");

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
            }

            @Override
            protected void onPostExecute(OverviewTranslationHolder overviewTranslationHolder) {
                super.onPostExecute(overviewTranslationHolder);

                if (!DetailActivity.this.overviewTranslationInformation.replace(overviewTranslationHolder)) {
                    /** TODO: 業務エラー */
                    return;
                }

                DetailActivity.this.refreshHintsList(overviewTranslationHolder.getHints());
            }
        };

        asyncOverviewTranslation.execute(word, format);

        Logger.Info.write(TAG, methodName, "END");
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
        final ListView listViewTranslation = findViewById(R.id.outputTranslation);

        listViewTranslation.setAdapter(overviewTranslationAdapter);
    }

    @Override
    protected void setListeners() {
        final String methodName = "setListeners";
        Logger.Info.write(TAG, methodName, "START");

        final ListView listView = findViewById(R.id.outputRelatedLexemes);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            final RelatedLexemesSingleRow selected = this.overviewRelatedLexemesAdapter.getListViewItemsList().get(position);
            final String overviewId = selected.getOverviewId();

            final Intent intent = new Intent(getApplication(), DetailActivity.class);
            intent.putExtra(OverviewColumnKey.Id.getKeyName(), overviewId);
            startActivity(intent);
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
            final ModelMap<OverviewColumnKey, Object> modelMap = this.overviewInformation.getModelInfo().get(0);
            final String userId = modelMap.getString(OverviewColumnKey.UserId);

            final Intent intent = new Intent(getApplication(), ListViewActivity.class);
            intent.putExtra(UserColumnKey.UserId.getKeyName(), userId);
            startActivity(intent);
        }

        return super.onKeyDown(keyCode, event);
    }
}
