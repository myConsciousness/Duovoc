package android.app.java.com.duovoc.model;

import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.framework.model.BaseModel;
import android.app.java.com.duovoc.framework.model.holder.InsertHolder;
import android.app.java.com.duovoc.framework.model.holder.SelectHolder;
import android.app.java.com.duovoc.model.holder.OverviewHolder;
import android.app.java.com.duovoc.model.property.CurrentUserColumnKey;
import android.app.java.com.duovoc.model.property.OverviewColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * テーブル名「overview_information」に対するトランザクション処理を管理するモデルオブジェクトです。
 * 当該モデルオブジェクトでは以下のトランザクション操作が実装されています。
 *
 * @author Kato Shinya
 * @version 1.0
 * 1, Select
 * ├引数として渡された情報を基にレコードの検索処理を実行します。
 * └検索結果はモデルリストに格納され{@code getModelInfo()}を実行することで取得できます。
 * @see #replace(List)
 * 2, updateDatetime
 * ├引数として渡された情報を基にレコードの挿入・更新処理を実行します。
 * └当該処理に依ってモデルリストは更新されません。
 * @see BaseModel
 * @since 1.0
 */
public final class OverviewInformation extends BaseModel {

    /**
     * 定数 : クラス名を保持する。
     */
    private static final String TAG = OverviewInformation.class.getSimpleName();

    /**
     * 変数 : 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static OverviewInformation thisInstance = null;

    /**
     * 当該クラスのコンストラクタ。
     * 当該クラスにシングルトンパターンを適用するため修飾子をprivate指定する。
     *
     * @param context アプリケーション情報。
     * @see #getInstance(Context)
     */
    private OverviewInformation(Context context) {
        super(context, Table.OverviewInformation);
    }

    /**
     * 当該クラスのインスタンスを返却する。
     * 初回実行時に当該クラスのインスタンスを生成した後に返却し、
     * 初回以降に実行された際には初回時に生成されたインスタンスを返却する。
     *
     * @param context アプリケーション情報。
     * @return 当該クラスのインスタンス。
     * @see #OverviewInformation(Context)
     */
    public static OverviewInformation getInstance(Context context) {

        if (thisInstance == null) {
            thisInstance = new OverviewInformation(context);
        }

        return thisInstance;
    }

    public void selectByCurrentUserInformation(
            final String userId,
            final String language,
            final String fromLanguage) {

        if (!StringChecker.isEffectiveString(userId)
                || !StringChecker.isEffectiveString(language)
                || !StringChecker.isEffectiveString(fromLanguage)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        final String[] selections = new String[]{
                String.format(FORMAT_WHERE_CLAUSE, CurrentUserColumnKey.UserId.getKeyName()),
                String.format(FORMAT_WHERE_CLAUSE, CurrentUserColumnKey.Language.getKeyName()),
                String.format(FORMAT_WHERE_CLAUSE, CurrentUserColumnKey.FromLanguage.getKeyName())
        };

        final SelectHolder selectHolder = new SelectHolder();
        selectHolder.setSelection(String.join(Operand.AND.getValue(), selections));
        selectHolder.setSelectionArgs(new String[]{userId, language, fromLanguage});

        super.select(selectHolder);
    }

    /**
     * プライマリキーを基にレコードの検索処理を行います。
     * 検索結果はモデルリストに格納され、
     * {@code getModelInfo()}を実行することで取得できます。
     *
     * @param primaryKey 主キー。
     * @see BaseModel#select(SelectHolder)
     * @see #onPostSelect(Cursor)
     * @see #getModelInfo()
     */
    public void selectByPrimaryKey(final String primaryKey) {
        super.selectByPrimaryKey(OverviewColumnKey.Id, primaryKey);
    }

    @Override
    protected ModelList<ModelMap<OverviewColumnKey, Object>> onPostSelect(final Cursor cursor) {

        final ModelList<ModelMap<OverviewColumnKey, Object>> modelInfo = new ModelList<>(cursor.getCount());

        if (cursor.moveToFirst()) {
            final OverviewColumnKey[] overviewColumnKeys = OverviewColumnKey.values();

            for (int i = 0, countRecords = cursor.getCount(); i < countRecords; i++) {
                final ModelMap<OverviewColumnKey, Object> modelMap = new ModelMap<>(OverviewColumnKey.class);

                for (OverviewColumnKey column : overviewColumnKeys) {
                    column.setModelMap(cursor, modelMap);
                }

                modelInfo.add(modelMap);
                cursor.moveToNext();
            }
        }

        return modelInfo;
    }

    /**
     * 渡された引数の情報を基にレコードの挿入処理を実行します。
     * 当該処理に依ってモデルリストは更新されません。
     *
     * @param overviewHolderList 挿入処理を行う際に必要な情報が格納されたデータクラスのリスト。
     * @see BaseModel#replaceAll(List)
     */
    public void replace(final List<OverviewHolder> overviewHolderList) {

        final List<InsertHolder> insertHolderList = new ArrayList<>();
        final OverviewColumnKey[] overviewColumnKeys = OverviewColumnKey.values();

        for (OverviewHolder overviewHolder : overviewHolderList) {
            final InsertHolder insertHolder = new InsertHolder();
            final ContentValues contentValues = insertHolder.getContentValues();

            for (OverviewColumnKey column : overviewColumnKeys) {
                column.setContentValues(contentValues, overviewHolder);
            }

            insertHolderList.add(insertHolder);
        }

        super.replaceAll(insertHolderList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ModelList<ModelMap<OverviewColumnKey, Object>> getModelInfo() {
        return super.modelInfo;
    }
}
