package android.app.ks.thinkit.duovoc.model.property;

import android.app.ks.thinkit.duovoc.framework.CalendarHandler;
import android.app.ks.thinkit.duovoc.framework.IModelMapKey;
import android.app.ks.thinkit.duovoc.framework.ModelMap;
import android.app.ks.thinkit.duovoc.framework.model.CursorHandler;
import android.app.ks.thinkit.duovoc.model.holder.UserMemoHolder;
import android.content.ContentValues;
import android.database.Cursor;

public enum UserMemoColumnKey implements IModelMapKey {

    /**
     * 物理カラム名「user_id」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<UserMemoColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, UserMemoHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, UserMemoHolder)
     * @see Key#user_id
     */
    UserId(Key.user_id) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserMemoColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserMemoHolder userMemoHolder) {
            contentValues.put(this.getKeyName(), userMemoHolder.getUserId());
        }
    },

    /**
     * 物理カラム名「overview_id」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<UserMemoColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, UserMemoHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, UserMemoHolder)
     * @see Key#overview_id
     */
    OverviewId(Key.overview_id) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserMemoColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserMemoHolder userMemoHolder) {
            contentValues.put(this.getKeyName(), userMemoHolder.getOverviewId());
        }
    },

    /**
     * 物理カラム名「memo」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<UserMemoColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, UserMemoHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, UserMemoHolder)
     * @see Key#memo
     */
    Memo(Key.memo) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserMemoColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserMemoHolder userMemoHolder) {
            contentValues.put(this.getKeyName(), userMemoHolder.getMemo());
        }
    },

    /**
     * 物理カラム名「modified_datetime」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<UserMemoColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, UserMemoHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, UserMemoHolder)
     * @see Key#modified_datetime
     */
    ModifiedDatetime(Key.modified_datetime) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserMemoColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserMemoHolder userMemoHolder) {
            final String currentClientDatetime = CalendarHandler.getClientDatetime();
            contentValues.put(this.getKeyName(), currentClientDatetime);
        }
    };

    /**
     * カラムの物理名を格納するフィールドです。
     *
     * @see Key#user_id
     * @see Key#overview_id
     * @see Key#memo
     * @see Key#modified_datetime
     */
    private Key key;


    /**
     * 当該Enumのコンストラクタです。
     *
     * @see Key#user_id
     * @see Key#overview_id
     * @see Key#memo
     * @see Key#modified_datetime
     */
    UserMemoColumnKey(Key key) {
        this.key = key;
    }

    /**
     * 当該項目に紐付くカラムの物理名を文字列として返却します。
     *
     * @return カラムの物理名。
     */
    public String getKeyName() {
        return this.key.name();
    }

    /**
     * モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * 当該Enumクラスの項目は当該抽象メソッドを必ず実装する必要があります。
     *
     * @param cursor   カーソルオブジェクト。
     * @param modelMap 値を設定するモデルマップ。
     */
    public abstract void setModelMap(Cursor cursor, ModelMap<UserMemoColumnKey, Object> modelMap);

    /**
     * モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     * 当該Enumクラスの項目は当該抽象メソッドを必ず実装する必要があります。
     *
     * @param contentValues  挿入情報を保持するオブジェクト。
     * @param userMemoHolder ユーザメモ情報を保持するオブジェクト。
     */
    public abstract void setContentValues(ContentValues contentValues, UserMemoHolder userMemoHolder);

    /**
     * 論理モデル名「ユーザメモ情報」のカラムに紐付く物理名を定義したEnumクラスです。
     * 概要情報のカラムに紐付く物理名は必ず当該Enumクラスへ定義する必要があります。
     *
     * @see #getKeyName()
     */
    private enum Key {
        user_id,
        overview_id,
        memo,
        modified_datetime,
    }

}
