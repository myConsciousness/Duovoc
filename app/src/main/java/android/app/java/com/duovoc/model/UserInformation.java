package android.app.java.com.duovoc.model;

import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.BaseModel;
import android.app.java.com.duovoc.framework.model.holder.DeleteHolder;
import android.app.java.com.duovoc.framework.model.holder.InsertHolder;
import android.app.java.com.duovoc.framework.model.holder.SelectHolder;
import android.app.java.com.duovoc.model.holder.UserHolder;
import android.app.java.com.duovoc.model.property.Table;
import android.app.java.com.duovoc.model.property.UserColumnKey;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * テーブル名「overview_information」に対するトランザクション処理を管理するモデルオブジェクトです。
 * 当該モデルオブジェクトでは以下のトランザクション操作が実装されています。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see #selectAll()
 * 1, Select
 * ├引数として渡された情報を基にレコードの検索処理を実行します。
 * └検索結果はモデルマップに格納され{@code getModelInfo()}を実行することで取得できます。
 * @see #insert(UserHolder)
 * 2, insert
 * ├引数として渡された情報を基にレコードの挿入処理を実行します。
 * └当該処理に依ってモデルマップは更新されません。
 * @see BaseModel
 * @since 1.0
 */
public final class UserInformation extends BaseModel {

    /**
     * 定数 : クラス名を保持する。
     */
    private static final String TAG = UserInformation.class.getName();

    /**
     * 変数 : 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static UserInformation thisInstance = null;

    /**
     * 当該クラスのコンストラクタ。
     * 当該クラスにシングルトンパターンを適用するため修飾子をprivate指定する。
     *
     * @param context アプリケーション情報。
     * @see #getInstance(Context)
     */
    private UserInformation(Context context) {
        super(context, Table.UserInformation);
    }

    /**
     * 当該クラスのインスタンスを返却する。
     * 初回実行時に当該クラスのインスタンスを生成した後に返却し、
     * 初回以降に実行された際には初回時に生成されたインスタンスを返却する。
     *
     * @param context アプリケーション情報。
     * @return 当該クラスのインスタンス。
     * @see #UserInformation(Context)
     */
    public static UserInformation getInstance(Context context) {

        if (thisInstance == null) {
            thisInstance = new UserInformation(context);
        }

        return thisInstance;
    }

    public void selectByPrimaryKey(final String primaryKey) {
        super.selectByPrimaryKey(UserColumnKey.UserId, primaryKey);
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
    public void selectAll() {

        final SelectHolder selectHolder = new SelectHolder();
        selectHolder.setColumns(null);

        super.select(selectHolder);
    }

    @Override
    protected ModelList<ModelMap<UserColumnKey, Object>> onPostSelect(final Cursor cursor) {

        final ModelList<ModelMap<UserColumnKey, Object>> modelInfo = new ModelList<>(cursor.getCount());

        if (cursor.moveToFirst()) {
            final ModelMap<UserColumnKey, Object> modelMap = new ModelMap<>(UserColumnKey.class);
            final UserColumnKey[] userColumnKeys = UserColumnKey.values();

            for (UserColumnKey column : userColumnKeys) {
                column.setModelMap(cursor, modelMap);
            }

            modelInfo.add(modelMap);
        }

        return modelInfo;
    }

    /**
     * 渡された引数の情報を基にレコードの挿入処理を実行します。
     * 当該処理に依ってモデルマップは更新されません。
     *
     * @param userHolder 挿入処理を行う際に必要な情報が格納されたデータクラス。
     * @see BaseModel#insert(InsertHolder)
     */
    public void insert(final UserHolder userHolder) {

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();
        final UserColumnKey[] userColumnKeys = UserColumnKey.values();

        for (UserColumnKey column : userColumnKeys) {
            column.setContentValues(contentValues, userHolder);
        }

        super.insert(insertHolder);
    }

    public void deleteAll() {
        super.delete(new DeleteHolder());
    }

    public void deleteByPrimaryKey(final String primaryKey) {
        super.deleteByPrimaryKey(UserColumnKey.UserId, primaryKey);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ModelList<ModelMap<UserColumnKey, Object>> getModelInfo() {
        return super.modelInfo;
    }
}
