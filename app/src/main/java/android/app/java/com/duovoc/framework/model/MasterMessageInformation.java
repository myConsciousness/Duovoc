package android.app.java.com.duovoc.framework.model;

import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.holder.SelectHolder;
import android.app.java.com.duovoc.framework.model.property.MasterMessageColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.Context;
import android.database.Cursor;

public final class MasterMessageInformation extends BaseModel {

    /**
     * 定数 : クラス名を保持する。
     */
    private static final String TAG = MasterMessageInformation.class.getSimpleName();

    /**
     * 定数 : 改行コードに置換するためのデリミタを保持する。
     */
    private static final String MESSAGE_DELIMITER = "@";

    /**
     * 変数 : 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static MasterMessageInformation thisInstance = null;

    /**
     * 当該クラスのコンストラクタ。
     * 当該クラスにシングルトンパターンを適用するため修飾子をprivate指定する。
     *
     * @param context アプリケーション情報。
     * @see #getInstance(Context)
     */
    private MasterMessageInformation(final Context context) {
        super(context, Table.MasterMessageInformation);
    }

    /**
     * 当該クラスのインスタンスを返却する。
     * 初回実行時に当該クラスのインスタンスを生成した後に返却し、
     * 初回以降に実行された際には初回時に生成されたインスタンスを返却する。
     *
     * @param context アプリケーション情報。
     * @return 当該クラスのインスタンス。
     * @see #MasterMessageInformation(Context)
     */
    public static MasterMessageInformation getInstance(final Context context) {

        if (thisInstance == null) {
            thisInstance = new MasterMessageInformation(context);
        }

        return thisInstance;
    }

    /**
     * 渡された引数の情報を基にレコードの検索処理を実行します。
     * 検索結果はモデルリストに格納され、
     * {@code getModelInfo()}を実行することで取得できます。
     *
     * @see BaseModel#select(SelectHolder)
     * @see #onPostSelect(Cursor)
     * @see #getModelInfo()
     */
    public void searchMasterByPrimaryKey(final String primaryKey) {
        super.selectByPrimaryKey(MasterMessageColumnKey.MessageId, primaryKey);
    }

    @Override
    protected ModelList<ModelMap<MasterMessageColumnKey, Object>> onPostSelect(final Cursor cursor) {

        final ModelList<ModelMap<MasterMessageColumnKey, Object>> modelInfo = new ModelList<>(cursor.getCount());

        if (cursor.moveToFirst()) {
            final ModelMap<MasterMessageColumnKey, Object> modelMap = new ModelMap<>(MasterMessageColumnKey.class);
            final MasterMessageColumnKey[] masterMessageColumnKeys = MasterMessageColumnKey.values();

            for (MasterMessageColumnKey column : masterMessageColumnKeys) {
                column.setModelMap(cursor, modelMap);
            }

            modelInfo.add(modelMap);
        }

        return modelInfo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ModelList<ModelMap<MasterMessageColumnKey, Object>> getModelInfo() {
        return super.modelInfo;
    }

    /**
     * 検索結果で取得したマスタデータ情報からメッセージを返却します。
     * メッセージを取得する場合は検索処理を終えてから当該メソッドを実行してください。
     *
     * @return メッセージ
     * @see #searchMasterByPrimaryKey(String)
     */
    public String getMessage() {
        final String message = this.getModelInfo().get(0).getString(MasterMessageColumnKey.Message);
        return message.replaceAll(MESSAGE_DELIMITER, "\n");
    }
}
