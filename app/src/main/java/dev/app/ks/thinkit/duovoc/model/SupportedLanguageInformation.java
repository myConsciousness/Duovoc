package dev.app.ks.thinkit.duovoc.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import dev.app.ks.thinkit.duovoc.framework.ModelList;
import dev.app.ks.thinkit.duovoc.framework.ModelMap;
import dev.app.ks.thinkit.duovoc.framework.model.BaseModel;
import dev.app.ks.thinkit.duovoc.framework.model.holder.InsertHolder;
import dev.app.ks.thinkit.duovoc.framework.model.holder.SelectHolder;
import dev.app.ks.thinkit.duovoc.model.holder.SupportedLanguageHolder;
import dev.app.ks.thinkit.duovoc.model.property.SupportedLanguageColumnKey;
import dev.app.ks.thinkit.duovoc.model.property.Table;

public final class SupportedLanguageInformation extends BaseModel {

    /**
     * クラス名。
     */
    private static final String TAG = SupportedLanguageInformation.class.getName();

    /**
     * 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static SupportedLanguageInformation thisInstance = null;

    /**
     * 当該クラスのコンストラクタ。
     * 当該クラスにシングルトンパターンを適用するため修飾子をprivate指定する。
     *
     * @param context アプリケーション情報。
     * @see #getInstance(Context)
     */
    private SupportedLanguageInformation(final Context context) {
        super(context, Table.SupportedLanguageInformation);
    }

    /**
     * 当該クラスのインスタンスを返却する。
     * 初回実行時に当該クラスのインスタンスを生成した後に返却し、
     * 初回以降に実行された際には初回時に生成されたインスタンスを返却する。
     *
     * @param context アプリケーション情報。
     * @return 当該クラスのインスタンス。
     * @see #SupportedLanguageInformation(Context)
     */
    public static SupportedLanguageInformation getInstance(final Context context) {

        if (thisInstance == null) {
            thisInstance = new SupportedLanguageInformation(context);
        }

        return thisInstance;
    }

    public void selectByPrimaryKey(final String primaryKey) {
        super.selectByPrimaryKey(SupportedLanguageColumnKey.FromLanguage, primaryKey);
    }

    public void selectAll() {

        final SelectHolder selectHolder = new SelectHolder();
        selectHolder.setColumns(null);

        super.select(selectHolder);
    }

    @Override
    protected ModelList<ModelMap<SupportedLanguageColumnKey, Object>> onPostSelect(final Cursor cursor) {

        final ModelList<ModelMap<SupportedLanguageColumnKey, Object>> modelInfo = new ModelList<>(cursor.getCount());

        if (cursor.moveToFirst()) {
            final SupportedLanguageColumnKey[] supportedLanguageColumnKeys = SupportedLanguageColumnKey.values();

            for (int i = 0, countRecords = cursor.getCount(); i < countRecords; i++) {
                final ModelMap<SupportedLanguageColumnKey, Object> modelMap = new ModelMap<>(SupportedLanguageColumnKey.class);

                for (SupportedLanguageColumnKey column : supportedLanguageColumnKeys) {
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
     * @param supportedLanguageHolderList 挿入処理を行う際に必要な情報が格納されたデータクラスのリスト。
     * @see BaseModel#replaceAll(List)
     */
    public void replace(final List<SupportedLanguageHolder> supportedLanguageHolderList) {

        final List<InsertHolder> insertHolderList = new ArrayList<>();
        final SupportedLanguageColumnKey[] supportedLanguageColumnKeys = SupportedLanguageColumnKey.values();


        for (SupportedLanguageHolder supportedLanguageHolder : supportedLanguageHolderList) {
            final InsertHolder insertHolder = new InsertHolder();
            final ContentValues contentValues = insertHolder.getContentValues();

            for (SupportedLanguageColumnKey column : supportedLanguageColumnKeys) {
                column.setContentValues(contentValues, supportedLanguageHolder);
            }

            insertHolderList.add(insertHolder);
        }

        super.replaceAll(insertHolderList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ModelList<ModelMap<SupportedLanguageColumnKey, Object>> getModelInfo() {
        return super.modelInfo;
    }
}
