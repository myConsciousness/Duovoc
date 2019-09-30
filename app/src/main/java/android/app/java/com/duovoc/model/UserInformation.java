package android.app.java.com.duovoc.model;

import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.ModelBase;
import android.app.java.com.duovoc.holder.UserHolder;
import android.app.java.com.duovoc.model.holder.InsertHolder;
import android.app.java.com.duovoc.model.holder.SelectHolder;
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
 * @see ModelBase
 * @since 1.0
 */
final public class UserInformation extends ModelBase {

    /**
     * 定数 : クラス名を保持する。
     */
    private static final String TAG = UserInformation.class.getSimpleName();

    /**
     * 定数 : 当該モデルで扱う全カラムの振る舞いを保持する。
     */
    private static final UserColumnKey[] USER_COLUMN_KEYS = UserColumnKey.values();

    /**
     * 変数 : 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static UserInformation thisInstance = null;

    /**
     * 変数 : 検索結果を格納するモデルマップ。
     * 各レコード情報を取得する際には、
     * {@link android.app.java.com.duovoc.model.property.UserColumnKey}を使用する必要があります。
     *
     * @see #selectAll()
     * @see android.app.java.com.duovoc.model.property.UserColumnKey
     */
    private ModelMap<UserColumnKey, Object> modelInfo = new ModelMap<>();

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

    /**
     * 渡された引数の情報を基にレコードの検索処理を実行します。
     * 検索結果はモデルリストに格納され、
     * {@code getModelInfo()}を実行することで取得できます。
     *
     * @return 検索処理が成功した場合は{@code true}、その他の場合は{@code false}。
     * @see ModelBase#select(SelectHolder)
     * @see #onPostSelect(Cursor)
     * @see #getModelInfo()
     */
    public boolean selectAll() {

        SelectHolder selectHolder = new SelectHolder();
        selectHolder.setColumns(null);

        return super.select(selectHolder);
    }

    @Override
    protected boolean onPostSelect(Cursor cursor) {

        if (!super.isSucceeded(cursor)) {
            return false;
        }

        ModelMap<UserColumnKey, Object> modelMap = new ModelMap<>();

        if (cursor.moveToFirst()) {
            for (UserColumnKey userColumnKey : USER_COLUMN_KEYS) {
                userColumnKey.setModelMap(cursor, modelMap);
            }
        }

        this.setModelInfo(modelMap);

        return true;
    }

    /**
     * 渡された引数の情報を基にレコードの挿入処理を実行します。
     * 当該処理に依ってモデルマップは更新されません。
     *
     * @param userHolder 挿入処理を行う際に必要な情報が格納されたデータクラス。
     * @return 挿入処理が成功した場合は{@code true}、その他の場合は{@code false}。
     * @see ModelBase#insert(InsertHolder)
     */
    public boolean insert(UserHolder userHolder) {

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();

        for (UserColumnKey userColumnKey : USER_COLUMN_KEYS) {
            userColumnKey.setContentValues(contentValues, userHolder);
        }

        return super.insert(insertHolder);
    }

    public boolean clear() {

        return super.delete();
    }

    /**
     * 検索結果で取得したモデル情報を格納したマップを返却します。
     * 検索処理が行われていない状態では空のマップが返却されます。
     * そのため、呼び出し元で検索結果を取得したい場合は、
     * 当該メソッドを実行する前に必ず検索処理を実行する必要があります。
     *
     * @return 検索処理結果を格納したモデルマップ。
     * @see #selectAll()
     */
    public ModelMap<UserColumnKey, Object> getModelInfo() {
        return this.modelInfo;
    }

    /**
     * 検索処理で取得したモデルマップを格納する。
     *
     * @param modelInfo 検索処理結果を格納したモデルマップ。
     * @see #selectAll()
     * @see #getModelInfo()
     */
    private void setModelInfo(ModelMap<UserColumnKey, Object> modelInfo) {
        this.modelInfo = modelInfo;
    }
}
