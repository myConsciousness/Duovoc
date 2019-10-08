package android.app.java.com.duovoc.framework.model;

import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.framework.model.adapter.DatabaseAdapter;
import android.app.java.com.duovoc.model.holder.InsertHolder;
import android.app.java.com.duovoc.model.holder.SelectHolder;
import android.app.java.com.duovoc.model.property.Table;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

public abstract class ModelBase {

    /**
     * 定数 : where句のフォーマットを保持する。
     */
    protected static final String FORMAT_WHERE_CLAUSE = "%s = ?";
    /**
     * 定数 : クラス名を保持する。
     */
    private static final String TAG = ModelBase.class.getSimpleName();
    /**
     * 変数 : 操作するモデル情報を保持する。
     */
    private Table TABLE;
    /**
     * 変数 : データベース操作を行うアダプターを保持する。
     */
    private DatabaseAdapter databaseAdapter;

    /**
     * 当該基底クラスのコンストラクタ。
     *
     * @param context アプリケーション情報。
     * @param table   操作するテーブルの情報。
     * @see Table
     */
    protected ModelBase(final Context context, final Table table) {
        this.TABLE = table;
        this.databaseAdapter = new DatabaseAdapter(context);
    }

    /**
     * 検索処理後に実行する処理を定義する抽象メソッドです。
     * 当該基底クラスを継承するクラスは当該メソッドを実装する必要があります。
     *
     * @param cursor 検索処理の結果。
     * @return 検索後処理が成功した場合は {@code true}、失敗した場合は {@code false}を返却します。
     */
    protected abstract boolean onPostSelect(Cursor cursor);

    protected boolean selectByPrimaryKey(final IModelMapKey primaryKeyName, final String primaryKey) {

        if (primaryKeyName == null
                || !StringChecker.isEffectiveString(primaryKey)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        SelectHolder selectHolder = new SelectHolder();
        selectHolder.setColumns(null);
        selectHolder.setSelection(String.format(FORMAT_WHERE_CLAUSE, primaryKeyName.getKeyName()));
        selectHolder.setSelectionArgs(new String[]{primaryKey});

        return this.select(selectHolder);
    }

    protected boolean select(SelectHolder selectHolder) {

        try {
            this.databaseAdapter.open();

            final Cursor cursor = this.databaseAdapter.getDatabase().query(
                    this.TABLE.getName(),
                    selectHolder.getColumns(),
                    selectHolder.getSelection(),
                    selectHolder.getSelectionArgs(),
                    selectHolder.getGroupBy(),
                    selectHolder.getHaving(),
                    selectHolder.getOrderBy(),
                    selectHolder.getLimit()
            );

            return this.onPostSelect(cursor);

        } finally {
            this.databaseAdapter.close();
        }
    }

    protected boolean replaceAll(List<InsertHolder> insertHolderList) {

        try {
            this.databaseAdapter.open();
            this.databaseAdapter.beginTransaction();

            for (InsertHolder insertHolder : insertHolderList) {
                final long id = this.databaseAdapter.getDatabase().replace(
                        this.TABLE.getName(),
                        insertHolder.getNullColumnHack(),
                        insertHolder.getContentValues()
                );

                if (id <= 0) {
                    return false;
                }
            }

            this.databaseAdapter.setTransactionSuccessful();

        } finally {
            this.databaseAdapter.endTransaction();
            this.databaseAdapter.close();
        }

        return true;
    }

    protected boolean replace(InsertHolder insertHolder) {

        try {
            this.databaseAdapter.open();
            this.databaseAdapter.beginTransaction();

            final long id = this.databaseAdapter.getDatabase().replace(
                    this.TABLE.getName(),
                    insertHolder.getNullColumnHack(),
                    insertHolder.getContentValues()
            );

            if (id <= 0) {
                return false;
            }

            this.databaseAdapter.setTransactionSuccessful();

        } finally {
            this.databaseAdapter.endTransaction();
            this.databaseAdapter.close();
        }

        return true;
    }

    protected boolean insert(InsertHolder insertHolder) {

        try {
            this.databaseAdapter.open();
            this.databaseAdapter.beginTransaction();

            final long id = this.databaseAdapter.getDatabase().insert(
                    this.TABLE.getName(),
                    insertHolder.getNullColumnHack(),
                    insertHolder.getContentValues()
            );

            if (id <= 0) {
                return false;
            }

            this.databaseAdapter.setTransactionSuccessful();

        } finally {
            this.databaseAdapter.endTransaction();
            this.databaseAdapter.close();
        }

        return true;
    }

    protected boolean delete() {

        try {
            this.databaseAdapter.open();

            final int count = this.databaseAdapter.getDatabase().delete(
                    this.TABLE.getName(),
                    null,
                    null
            );

            return count > 0;

        } finally {
            this.databaseAdapter.close();
        }
    }

    protected boolean isSucceeded(final Cursor cursor) {
        return cursor != null && cursor.getCount() > 0;
    }

    /**
     * 定数 : 検索条件で使用するオペランドを保持する。
     * 処理中で検索条件を作成する際に使用する。
     */
    protected enum Operand {
        AND(" and "),
        OR(" or ");

        private String value;

        Operand(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
