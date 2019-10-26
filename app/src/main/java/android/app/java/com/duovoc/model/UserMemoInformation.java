package android.app.java.com.duovoc.model;

import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.framework.model.BaseModel;
import android.app.java.com.duovoc.framework.model.holder.InsertHolder;
import android.app.java.com.duovoc.framework.model.holder.SelectHolder;
import android.app.java.com.duovoc.model.holder.UserMemoHolder;
import android.app.java.com.duovoc.model.property.Table;
import android.app.java.com.duovoc.model.property.UserMemoColumnKey;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public final class UserMemoInformation extends BaseModel {

    /**
     * クラス名。
     */
    private static final String TAG = UserMemoInformation.class.getSimpleName();

    /**
     * 変数 : 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static UserMemoInformation thisInstance = null;

    /**
     * 当該クラスのコンストラクタ。
     * 当該クラスにシングルトンパターンを適用するため修飾子をprivate指定する。
     *
     * @param context アプリケーション情報。
     * @see #getInstance(Context)
     */
    private UserMemoInformation(final Context context) {
        super(context, Table.UserMemoInformation);
    }

    /**
     * 当該クラスのインスタンスを返却する。
     * 初回実行時に当該クラスのインスタンスを生成した後に返却し、
     * 初回以降に実行された際には初回時に生成されたインスタンスを返却する。
     *
     * @param context アプリケーション情報。
     * @return 当該クラスのインスタンス。
     * @see #UserMemoInformation(Context)
     */
    public static UserMemoInformation getInstance(final Context context) {

        if (thisInstance == null) {
            thisInstance = new UserMemoInformation(context);
        }

        return thisInstance;
    }

    public void selectByUserInformation(
            final String userId,
            final String overviewId) {

        if (!StringChecker.isEffectiveString(userId)
                || !StringChecker.isEffectiveString(overviewId)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        final String[] selections = new String[]{
                String.format(FORMAT_WHERE_CLAUSE, UserMemoColumnKey.UserId.getKeyName()),
                String.format(FORMAT_WHERE_CLAUSE, UserMemoColumnKey.OverviewId.getKeyName())
        };

        final SelectHolder selectHolder = new SelectHolder();
        selectHolder.setSelection(String.join(Operand.AND.getValue(), selections));
        selectHolder.setSelectionArgs(new String[]{userId, overviewId});

        super.select(selectHolder);
    }

    @Override
    protected ModelList<ModelMap<UserMemoColumnKey, Object>> onPostSelect(final Cursor cursor) {

        final ModelList<ModelMap<UserMemoColumnKey, Object>> modelInfo = new ModelList<>(cursor.getCount());

        if (cursor.moveToFirst()) {
            final ModelMap<UserMemoColumnKey, Object> modelMap = new ModelMap<>(UserMemoColumnKey.class);
            final UserMemoColumnKey[] userMemoColumnKeys = UserMemoColumnKey.values();

            // 一意制約検索のため検索結果は一件のみ
            for (UserMemoColumnKey column : userMemoColumnKeys) {
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
     * @param userMemoHolder 挿入処理を行う際に必要な情報が格納されたデータクラスのリスト。
     * @see BaseModel#replace(InsertHolder)
     */
    public void replace(final UserMemoHolder userMemoHolder) {

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();
        final UserMemoColumnKey[] userMemoColumnKeys = UserMemoColumnKey.values();

        for (UserMemoColumnKey column : userMemoColumnKeys) {
            column.setContentValues(contentValues, userMemoHolder);
        }

        super.replace(insertHolder);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ModelList<ModelMap<UserMemoColumnKey, Object>> getModelInfo() {
        return super.modelInfo;
    }

}
