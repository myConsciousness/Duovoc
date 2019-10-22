package android.app.java.com.duovoc.framework.model;

import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.framework.model.adapter.DatabaseAdapter;
import android.app.java.com.duovoc.framework.model.holder.InsertHolder;
import android.app.java.com.duovoc.framework.model.holder.SelectHolder;
import android.app.java.com.duovoc.model.property.Table;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.List;

public abstract class BaseModel<E extends Enum<E> & IModelMapKey> {

    /**
     * 定数 : where句のフォーマットを保持する。
     */
    protected static final String FORMAT_WHERE_CLAUSE = "%s = ?";
    /**
     * 定数 : クラス名を保持する。
     */
    private static final String TAG = BaseModel.class.getSimpleName();
    protected ModelList<ModelMap<E, Object>> modelInfo = new ModelList<>(0);
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
     * @throws IllegalArgumentException 不正な入力を検知した際に発生します。
     * @see Table
     */
    protected BaseModel(final Context context, final Table table) {

        if (context == null
                || table == null) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        this.databaseAdapter = new DatabaseAdapter(context);
        this.TABLE = table;
    }

    /**
     * 検索処理後に実行する処理を定義する抽象メソッドです。
     * 当該基底クラスを継承するクラスは当該メソッドを実装する必要があります。
     *
     * @param cursor 検索処理の結果。
     * @return 検索後処理が成功した場合は {@code true}、失敗した場合は {@code false}を返却します。
     */
    protected abstract ModelList<ModelMap<E, Object>> onPostSelect(Cursor cursor);

    public abstract ModelList<ModelMap<E, Object>> getModelInfo();

    private void setModelInfo(ModelList<ModelMap<E, Object>> modelInfo) {
        this.modelInfo = modelInfo;
    }

    protected final void selectByPrimaryKey(final IModelMapKey primaryKeyName, final String primaryKey) {

        if (primaryKeyName == null
                || !StringChecker.isEffectiveString(primaryKey)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        SelectHolder selectHolder = new SelectHolder();
        selectHolder.setColumns(null);
        selectHolder.setSelection(String.format(FORMAT_WHERE_CLAUSE, primaryKeyName.getKeyName()));
        selectHolder.setSelectionArgs(new String[]{primaryKey});

        this.select(selectHolder);
    }

    protected final void select(final SelectHolder selectHolder) {

        if (selectHolder == null) {
            // should not be happened
            throw new IllegalArgumentException();
        }

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

            if (cursor == null) {
                // should not be happened
                throw new SQLException();
            }

            final ModelList<ModelMap<E, Object>> modelInfo = this.onPostSelect(cursor);
            this.setModelInfo(modelInfo);

        } finally {
            this.databaseAdapter.close();
        }
    }

    protected final void replaceAll(final List<InsertHolder> insertHolderList) {

        if (insertHolderList == null) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        try {
            this.databaseAdapter.open();
            this.databaseAdapter.beginTransaction();

            for (InsertHolder insertHolder : insertHolderList) {
                this.databaseAdapter.getDatabase().replace(
                        this.TABLE.getName(),
                        insertHolder.getNullColumnHack(),
                        insertHolder.getContentValues()
                );
            }

            this.databaseAdapter.setTransactionSuccessful();

        } finally {
            this.databaseAdapter.endTransaction();
            this.databaseAdapter.close();
        }
    }

    protected final void replace(final InsertHolder insertHolder) {

        if (insertHolder == null) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        try {
            this.databaseAdapter.open();
            this.databaseAdapter.beginTransaction();

            this.databaseAdapter.getDatabase().replace(
                    this.TABLE.getName(),
                    insertHolder.getNullColumnHack(),
                    insertHolder.getContentValues()
            );

            this.databaseAdapter.setTransactionSuccessful();

        } finally {
            this.databaseAdapter.endTransaction();
            this.databaseAdapter.close();
        }
    }

    protected final void insert(final InsertHolder insertHolder) {

        if (insertHolder == null) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        try {
            this.databaseAdapter.open();
            this.databaseAdapter.beginTransaction();

            this.databaseAdapter.getDatabase().insert(
                    this.TABLE.getName(),
                    insertHolder.getNullColumnHack(),
                    insertHolder.getContentValues()
            );

            this.databaseAdapter.setTransactionSuccessful();

        } finally {
            this.databaseAdapter.endTransaction();
            this.databaseAdapter.close();
        }
    }

    protected final void delete() {

        try {
            this.databaseAdapter.open();

            this.databaseAdapter.getDatabase().delete(
                    this.TABLE.getName(),
                    null,
                    null
            );

        } finally {
            this.databaseAdapter.close();
        }
    }

    public final boolean isEmpty() {
        return this.modelInfo.isEmpty();
    }

    /**
     * 検索条件で使用するオペランドを保持する。
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
