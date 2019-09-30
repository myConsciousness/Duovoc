package android.app.java.com.duovoc.model;

import android.app.java.com.duovoc.framework.MasterDataMap;
import android.app.java.com.duovoc.framework.model.ModelBase;
import android.app.java.com.duovoc.model.holder.SelectHolder;
import android.app.java.com.duovoc.model.property.MasterMessageColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.Context;
import android.database.Cursor;

final public class MasterMessageInformation extends ModelBase {

    /**
     * 定数 : クラス名を保持する。
     */
    private static final String TAG = MasterMessageInformation.class.getSimpleName();

    /**
     * 定数 : システム情報から取得した改行コードを保持する。
     */
    private static final String SYSTEM_BR = System.getProperty("line.separator");

    /**
     * 定数 : 改行コードに置換するためのデリミタを保持する。
     */
    private static final String MESSAGE_DELIMITER = "@";

    /**
     * 定数 : 当該モデルで扱う全カラムの振る舞いを保持する。
     */
    private static final MasterMessageColumnKey[] MASTER_MESSAGE_COLUMN_KEYS = MasterMessageColumnKey.values();

    /**
     * 変数 : 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static MasterMessageInformation thisInstance = null;

    /**
     * 変数 : 検索結果を格納するマスタデータリスト。
     * 各レコード情報を取得する際には、
     * {@link android.app.java.com.duovoc.model.property.MasterMessageColumnKey}を使用する必要があります。
     *
     * @see #searchMasterByPrimaryKey(String)
     * @see android.app.java.com.duovoc.model.property.MasterMessageColumnKey
     */
    private MasterDataMap<MasterMessageColumnKey, Object> masterDataMap = new MasterDataMap<>();

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
     * @return 検索処理が成功した場合は{@code true}、その他の場合は{@code false}。
     * @see ModelBase#select(SelectHolder)
     * @see #onPostSelect(Cursor)
     * @see #getMasterDataMap()
     */
    public boolean searchMasterByPrimaryKey(final String primaryKey) {

        return super.selectByPrimaryKey(MasterMessageColumnKey.MessageId, primaryKey);
    }

    @Override
    protected boolean onPostSelect(Cursor cursor) {

        if (!super.isSucceeded(cursor)) {
            return false;
        }

        MasterDataMap<MasterMessageColumnKey, Object> masterDataMap = new MasterDataMap<>();

        if (cursor.moveToFirst()) {
            for (MasterMessageColumnKey column : MASTER_MESSAGE_COLUMN_KEYS) {
                column.setMasterDataMap(cursor, masterDataMap);
            }
        }

        this.setMasterDataMap(masterDataMap);

        return true;
    }

    /**
     * 検索結果で取得したモデル情報を格納したマップを返却します。
     * 検索処理が行われていない状態では空のマップが返却されます。
     * そのため、呼び出し元で検索結果を取得したい場合は、
     * 当該メソッドを実行する前に必ず検索処理を実行する必要があります。
     *
     * @return 検索処理結果を格納したマスタデータマップ。
     * @see #searchMasterByPrimaryKey(String)
     */
    public MasterDataMap<MasterMessageColumnKey, Object> getMasterDataMap() {
        return this.masterDataMap;
    }

    /**
     * 検索処理で取得したマスタデータマップを格納する。
     *
     * @param masterDataMap 検索処理結果を格納したマップ。
     * @see #searchMasterByPrimaryKey(String)
     * @see #getMasterDataMap()
     */
    private void setMasterDataMap(MasterDataMap<MasterMessageColumnKey, Object> masterDataMap) {
        this.masterDataMap = masterDataMap;
    }

    /**
     * 検索結果で取得したマスタデータ情報からメッセージを返却します。
     * メッセージを取得する場合は検索処理を終えてから当該メソッドを実行してください。
     *
     * @return メッセージ
     * @see #searchMasterByPrimaryKey(String)
     */
    public String getMessage() {
        return this.getMasterDataMap().getString(MasterMessageColumnKey.Message).replaceAll(MESSAGE_DELIMITER, SYSTEM_BR);
    }
}
