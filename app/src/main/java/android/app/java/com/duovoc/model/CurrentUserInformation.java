package android.app.java.com.duovoc.model;

import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.BaseModel;
import android.app.java.com.duovoc.framework.model.holder.InsertHolder;
import android.app.java.com.duovoc.framework.model.holder.SelectHolder;
import android.app.java.com.duovoc.model.holder.CurrentUserHolder;
import android.app.java.com.duovoc.model.property.CurrentUserColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

final public class CurrentUserInformation extends BaseModel {

    private static final String TAG = CurrentUserInformation.class.getSimpleName();

    private static CurrentUserInformation thisInstance = null;

    private ModelMap<CurrentUserColumnKey, Object> modelMap = new ModelMap<>(CurrentUserColumnKey.class);

    private CurrentUserInformation(final Context context) {
        super(context, Table.CurrentUserInformation);
    }

    public static CurrentUserInformation getInstance(final Context context) {

        if (thisInstance == null) {
            thisInstance = new CurrentUserInformation(context);
        }

        return thisInstance;
    }

    public boolean selectAll() {

        final SelectHolder selectHolder = new SelectHolder();
        selectHolder.setColumns(null);

        return super.select(selectHolder);
    }

    public boolean selectByPrimaryKey(final String primaryKey) {

        return super.selectByPrimaryKey(CurrentUserColumnKey.UserId, primaryKey);
    }

    @Override
    protected boolean onPostSelect(final Cursor cursor) {

        this.setModelInfo(new ModelMap<>(CurrentUserColumnKey.class));

        if (!super.isSucceeded(cursor)) {
            // should not be happened
            return false;
        }

        if (cursor.moveToFirst()) {
            final ModelMap<CurrentUserColumnKey, Object> modelMap = new ModelMap<>(CurrentUserColumnKey.class);
            final CurrentUserColumnKey[] currentUserColumnKeys = CurrentUserColumnKey.values();

            // 一意制約検索のため検索結果は一件のみ
            for (CurrentUserColumnKey column : currentUserColumnKeys) {
                column.setModelMap(cursor, modelMap);
            }

            this.setModelInfo(modelMap);
        }

        return true;
    }

    public boolean replace(final CurrentUserHolder currentUserHolder) {

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();

        final CurrentUserColumnKey[] currentUserColumnKeys = CurrentUserColumnKey.values();

        for (CurrentUserColumnKey column : currentUserColumnKeys) {
            column.setContentValues(contentValues, currentUserHolder);
        }

        return super.replace(insertHolder);
    }

    public ModelMap<CurrentUserColumnKey, Object> getModelInfo() {
        return this.modelMap;
    }

    private void setModelInfo(ModelMap<CurrentUserColumnKey, Object> modelMap) {
        this.modelMap = modelMap;
    }
}
