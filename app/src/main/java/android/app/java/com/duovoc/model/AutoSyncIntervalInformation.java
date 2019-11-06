package android.app.java.com.duovoc.model;

import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.BaseModel;
import android.app.java.com.duovoc.framework.model.holder.InsertHolder;
import android.app.java.com.duovoc.framework.model.holder.SelectHolder;
import android.app.java.com.duovoc.model.holder.AutoSyncIntervalHolder;
import android.app.java.com.duovoc.model.property.AutoSyncIntervalColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public final class AutoSyncIntervalInformation extends BaseModel {

    /**
     * クラス名。
     */
    private static final String TAG = AutoSyncIntervalInformation.class.getSimpleName();

    /**
     * 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static AutoSyncIntervalInformation thisInstance = null;

    /**
     * 当該クラスのコンストラクタ。
     * 当該クラスにシングルトンパターンを適用するため修飾子をprivate指定する。
     *
     * @param context アプリケーション情報。
     * @see #getInstance(Context)
     */
    private AutoSyncIntervalInformation(Context context) {
        super(context, Table.AutoSyncIntervalInformation);
    }

    /**
     * 当該クラスのインスタンスを返却する。
     * 初回実行時に当該クラスのインスタンスを生成した後に返却し、
     * 初回以降に実行された際には初回時に生成されたインスタンスを返却する。
     *
     * @param context アプリケーション情報。
     * @return 当該クラスのインスタンス。
     * @see #AutoSyncIntervalInformation(Context)
     */
    public static AutoSyncIntervalInformation getInstance(final Context context) {

        if (thisInstance == null) {
            thisInstance = new AutoSyncIntervalInformation(context);
        }

        return thisInstance;
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
        super.selectByPrimaryKey(AutoSyncIntervalColumnKey.ItemName, primaryKey);
    }

    @Override
    protected ModelList<ModelMap<AutoSyncIntervalColumnKey, Object>> onPostSelect(final Cursor cursor) {

        final ModelList<ModelMap<AutoSyncIntervalColumnKey, Object>> modelInfo = new ModelList<>(cursor.getCount());

        if (cursor.moveToFirst()) {
            final AutoSyncIntervalColumnKey[] autoSyncIntervalColumnKeys = AutoSyncIntervalColumnKey.values();
            final ModelMap<AutoSyncIntervalColumnKey, Object> modelMap = new ModelMap<>(AutoSyncIntervalColumnKey.class);

            for (AutoSyncIntervalColumnKey column : autoSyncIntervalColumnKeys) {
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
     * @param autoSyncIntervalHolderList 挿入処理を行う際に必要な情報が格納されたデータクラスのリスト。
     * @see BaseModel#replaceAll(List)
     */
    public void replace(final List<AutoSyncIntervalHolder> autoSyncIntervalHolderList) {

        final List<InsertHolder> insertHolderList = new ArrayList<>();
        final AutoSyncIntervalColumnKey[] autoSyncIntervalColumnKeys = AutoSyncIntervalColumnKey.values();

        for (AutoSyncIntervalHolder autoSyncIntervalHolder : autoSyncIntervalHolderList) {
            final InsertHolder insertHolder = new InsertHolder();
            final ContentValues contentValues = insertHolder.getContentValues();

            for (AutoSyncIntervalColumnKey column : autoSyncIntervalColumnKeys) {
                column.setContentValues(contentValues, autoSyncIntervalHolder);
            }

            insertHolderList.add(insertHolder);
        }

        super.replaceAll(insertHolderList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ModelList<ModelMap<AutoSyncIntervalColumnKey, Object>> getModelInfo() {
        return super.modelInfo;
    }
}
