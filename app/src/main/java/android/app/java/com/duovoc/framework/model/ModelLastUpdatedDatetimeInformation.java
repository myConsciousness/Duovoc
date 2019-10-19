package android.app.java.com.duovoc.framework.model;

import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.holder.InsertHolder;
import android.app.java.com.duovoc.framework.model.holder.ModelLastUpdatedDatetimeHolder;
import android.app.java.com.duovoc.framework.model.property.ModelLastUpdatedDatetimeColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public final class ModelLastUpdatedDatetimeInformation extends BaseModel {

    /**
     * クラス名。
     */
    private static final String TAG = ModelLastUpdatedDatetimeInformation.class.getSimpleName();

    /**
     * 当該モデルで扱う全カラムの振る舞いを保持する。
     */
    private static final ModelLastUpdatedDatetimeColumnKey[] MODEL_LAST_UPDATED_DATETIME_COLUMN_KEYS
            = ModelLastUpdatedDatetimeColumnKey.values();

    /**
     * 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static ModelLastUpdatedDatetimeInformation thisInstance = null;

    /**
     * 変数 : 検索結果を格納するモデルマップ。
     * 各レコード情報を取得する際には、
     * {@link ModelLastUpdatedDatetimeColumnKey}を使用する必要があります。
     *
     * @see #selectByPrimaryKey(String)
     * @see ModelLastUpdatedDatetimeColumnKey
     */
    private ModelMap<ModelLastUpdatedDatetimeColumnKey, Object> modelInfo = new ModelMap<>(ModelLastUpdatedDatetimeColumnKey.class);

    /**
     * 当該クラスのコンストラクタ。
     * 当該クラスにシングルトンパターンを適用するため修飾子をprivate指定する。
     *
     * @param context アプリケーション情報。
     * @see #getInstance(Context)
     */
    private ModelLastUpdatedDatetimeInformation(final Context context) {
        super(context, Table.ModelLastUpdatedDatetimeInformation);
    }

    /**
     * 当該クラスのインスタンスを返却する。
     * 初回実行時に当該クラスのインスタンスを生成した後に返却し、
     * 初回以降に実行された際には初回時に生成されたインスタンスを返却する。
     *
     * @param context アプリケーション情報。
     * @return 当該クラスのインスタンス。
     * @see #ModelLastUpdatedDatetimeInformation(Context)
     */
    public static ModelLastUpdatedDatetimeInformation getInstance(final Context context) {

        if (thisInstance == null) {
            thisInstance = new ModelLastUpdatedDatetimeInformation(context);
        }

        return thisInstance;
    }

    public boolean selectByPrimaryKey(final String primaryKey) {
        return super.selectByPrimaryKey(ModelLastUpdatedDatetimeColumnKey.ModelPhysicalName, primaryKey);
    }

    @Override
    protected boolean onPostSelect(final Cursor cursor) {

        if (!super.isSucceeded(cursor)) {
            return false;
        }

        final ModelMap<ModelLastUpdatedDatetimeColumnKey, Object> modelMap
                = new ModelMap<>(ModelLastUpdatedDatetimeColumnKey.class);

        if (cursor.moveToFirst()) {
            for (ModelLastUpdatedDatetimeColumnKey column : MODEL_LAST_UPDATED_DATETIME_COLUMN_KEYS) {
                column.setModelMap(cursor, modelMap);
            }
        }

        this.setModelInfo(modelMap);

        return true;
    }

    public boolean replace(final ModelLastUpdatedDatetimeHolder modelLastUpdatedDatetimeHolder) {

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();

        for (ModelLastUpdatedDatetimeColumnKey column : MODEL_LAST_UPDATED_DATETIME_COLUMN_KEYS) {
            column.setContentValues(contentValues, modelLastUpdatedDatetimeHolder);
        }

        return super.replace(insertHolder);
    }

    public ModelMap<ModelLastUpdatedDatetimeColumnKey, Object> getModelInfo() {
        return this.modelInfo;
    }

    private void setModelInfo(final ModelMap<ModelLastUpdatedDatetimeColumnKey, Object> modelInfo) {
        this.modelInfo = modelInfo;
    }
}
