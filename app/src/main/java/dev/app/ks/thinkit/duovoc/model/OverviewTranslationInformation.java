package dev.app.ks.thinkit.duovoc.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import dev.app.ks.thinkit.duovoc.framework.ModelList;
import dev.app.ks.thinkit.duovoc.framework.ModelMap;
import dev.app.ks.thinkit.duovoc.framework.model.BaseModel;
import dev.app.ks.thinkit.duovoc.framework.model.holder.InsertHolder;
import dev.app.ks.thinkit.duovoc.model.holder.OverviewTranslationHolder;
import dev.app.ks.thinkit.duovoc.model.property.OverviewTranslationColumnKey;
import dev.app.ks.thinkit.duovoc.model.property.Table;

public final class OverviewTranslationInformation extends BaseModel {

    /**
     * 定数 : クラス名を保持する。
     */
    private static final String TAG = OverviewTranslationInformation.class.getName();

    /**
     * 変数 : 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static OverviewTranslationInformation thisInstance = null;

    /**
     * 当該クラスのコンストラクタ。
     * 当該クラスにシングルトンパターンを適用するため修飾子をprivate指定する。
     *
     * @param context アプリケーション情報。
     * @see #getInstance(Context)
     */
    private OverviewTranslationInformation(final Context context) {
        super(context, Table.OverviewTranslationInformation);
    }

    /**
     * 当該クラスのインスタンスを返却する。
     * 初回実行時に当該クラスのインスタンスを生成した後に返却し、
     * 初回以降に実行された際には初回時に生成されたインスタンスを返却する。
     *
     * @param context アプリケーション情報。
     * @return 当該クラスのインスタンス。
     * @see #OverviewTranslationInformation(Context)
     */
    public static OverviewTranslationInformation getInstance(final Context context) {

        if (thisInstance == null) {
            thisInstance = new OverviewTranslationInformation(context);
        }

        return thisInstance;
    }

    public void selectByPrimaryKey(final String primaryKey) {
        super.selectByPrimaryKey(OverviewTranslationColumnKey.Id, primaryKey);
    }

    @Override
    protected ModelList<ModelMap<OverviewTranslationColumnKey, Object>> onPostSelect(final Cursor cursor) {

        final ModelList<ModelMap<OverviewTranslationColumnKey, Object>> modelInfo = new ModelList<>(cursor.getCount());

        if (cursor.moveToFirst()) {
            final ModelMap<OverviewTranslationColumnKey, Object> modelMap = new ModelMap<>(OverviewTranslationColumnKey.class);
            final OverviewTranslationColumnKey[] overviewTranslationColumnKeys = OverviewTranslationColumnKey.values();

            // 一意制約検索のため検索結果は一件のみ
            for (OverviewTranslationColumnKey column : overviewTranslationColumnKeys) {
                column.setModelMap(cursor, modelMap);
            }

            modelInfo.add(modelMap);
        }

        return modelInfo;
    }

    /**
     * 渡された引数の情報を基にレコードの挿入処理を実行します。
     * 当該処理に依ってモデルリストは更新されません。
     *
     * @param overviewTranslationHolder 挿入処理を行う際に必要な情報が格納されたデータクラスのリスト。
     * @see BaseModel#replace(InsertHolder)
     */
    public void replace(final OverviewTranslationHolder overviewTranslationHolder) {

        final OverviewTranslationColumnKey[] overviewTranslationColumnKeys = OverviewTranslationColumnKey.values();

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();

        for (OverviewTranslationColumnKey column : overviewTranslationColumnKeys) {
            column.setContentValues(contentValues, overviewTranslationHolder);
        }

        super.replace(insertHolder);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ModelList<ModelMap<OverviewTranslationColumnKey, Object>> getModelInfo() {
        return super.modelInfo;
    }
}
