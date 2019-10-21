package android.app.java.com.duovoc.model;

import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.BaseModel;
import android.app.java.com.duovoc.framework.model.holder.InsertHolder;
import android.app.java.com.duovoc.model.holder.OverviewTranslationHolder;
import android.app.java.com.duovoc.model.property.OverviewTranslationColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

final public class OverviewTranslationInformation extends BaseModel {

    /**
     * 定数 : クラス名を保持する。
     */
    private static final String TAG = OverviewTranslationInformation.class.getSimpleName();

    /**
     * 変数 : 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static OverviewTranslationInformation thisInstance = null;
    /**
     * 変数 : 検索結果を格納するモデルマップ。
     * 各レコード情報を取得する際には、
     * {@link android.app.java.com.duovoc.model.property.OverviewTranslationColumnKey}を使用する必要があります。
     *
     * @see android.app.java.com.duovoc.model.property.OverviewColumnKey
     */
    private ModelMap<OverviewTranslationColumnKey, Object> modelMap = new ModelMap<>(OverviewTranslationColumnKey.class);

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

    public boolean selectByPrimaryKey(final String primaryKey) {

        return super.selectByPrimaryKey(OverviewTranslationColumnKey.Id, primaryKey);
    }

    @Override
    protected boolean onPostSelect(final Cursor cursor) {

        if (!super.isSucceeded(cursor)) {
            // should not be happened
            return false;
        }

        if (cursor.moveToFirst()) {
            final ModelMap<OverviewTranslationColumnKey, Object> modelMap = new ModelMap<>(OverviewTranslationColumnKey.class);
            final OverviewTranslationColumnKey[] overviewTranslationColumnKeys = OverviewTranslationColumnKey.values();

            // 一意制約検索のため検索結果は一件のみ
            for (OverviewTranslationColumnKey column : overviewTranslationColumnKeys) {
                column.setModelMap(cursor, modelMap);
            }

            this.setModelInfo(modelMap);
        }

        return true;
    }

    /**
     * 渡された引数の情報を基にレコードの挿入処理を実行します。
     * 当該処理に依ってモデルリストは更新されません。
     *
     * @param overviewTranslationHolder 挿入処理を行う際に必要な情報が格納されたデータクラスのリスト。
     * @return 挿入処理が成功した場合は{@code true}、その他の場合は{@code false}。
     * @see BaseModel#replace(InsertHolder)
     */
    public boolean replace(OverviewTranslationHolder overviewTranslationHolder) {

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();
        final OverviewTranslationColumnKey[] overviewTranslationColumnKeys = OverviewTranslationColumnKey.values();

        for (OverviewTranslationColumnKey column : overviewTranslationColumnKeys) {
            column.setContentValues(contentValues, overviewTranslationHolder);
        }

        return super.replace(insertHolder);
    }

    public ModelMap<OverviewTranslationColumnKey, Object> getModelInfo() {
        return this.modelMap;
    }

    private void setModelInfo(ModelMap<OverviewTranslationColumnKey, Object> modelMap) {
        this.modelMap = modelMap;
    }

}
