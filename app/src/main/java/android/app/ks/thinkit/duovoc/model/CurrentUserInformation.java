package android.app.ks.thinkit.duovoc.model;

import android.app.ks.thinkit.duovoc.framework.ModelList;
import android.app.ks.thinkit.duovoc.framework.ModelMap;
import android.app.ks.thinkit.duovoc.framework.model.BaseModel;
import android.app.ks.thinkit.duovoc.framework.model.holder.DeleteHolder;
import android.app.ks.thinkit.duovoc.framework.model.holder.InsertHolder;
import android.app.ks.thinkit.duovoc.framework.model.holder.SelectHolder;
import android.app.ks.thinkit.duovoc.model.holder.CurrentUserHolder;
import android.app.ks.thinkit.duovoc.model.property.CurrentUserColumnKey;
import android.app.ks.thinkit.duovoc.model.property.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public final class CurrentUserInformation extends BaseModel {

    private static final String TAG = CurrentUserInformation.class.getName();

    private static CurrentUserInformation thisInstance = null;

    private CurrentUserInformation(final Context context) {
        super(context, Table.CurrentUserInformation);
    }

    public static CurrentUserInformation getInstance(final Context context) {

        if (thisInstance == null) {
            thisInstance = new CurrentUserInformation(context);
        }

        return thisInstance;
    }

    public void selectAll() {

        final SelectHolder selectHolder = new SelectHolder();
        selectHolder.setColumns(null);

        super.select(selectHolder);
    }

    public void selectByPrimaryKey(final String primaryKey) {
        super.selectByPrimaryKey(CurrentUserColumnKey.UserId, primaryKey);
    }

    @Override
    protected ModelList<ModelMap<CurrentUserColumnKey, Object>> onPostSelect(final Cursor cursor) {

        final ModelList<ModelMap<CurrentUserColumnKey, Object>> modelInfo = new ModelList<>(cursor.getCount());

        if (cursor.moveToFirst()) {
            final ModelMap<CurrentUserColumnKey, Object> modelMap = new ModelMap<>(CurrentUserColumnKey.class);
            final CurrentUserColumnKey[] currentUserColumnKeys = CurrentUserColumnKey.values();

            // 一意制約検索のため検索結果は一件のみ
            for (CurrentUserColumnKey column : currentUserColumnKeys) {
                column.setModelMap(cursor, modelMap);
            }

            modelInfo.add(modelMap);
        }

        return modelInfo;
    }

    public void insert(final CurrentUserHolder currentUserHolder) {

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();

        final CurrentUserColumnKey[] currentUserColumnKeys = CurrentUserColumnKey.values();

        for (CurrentUserColumnKey column : currentUserColumnKeys) {
            column.setContentValues(contentValues, currentUserHolder);
        }

        super.insert(insertHolder);
    }

    public void replace(final CurrentUserHolder currentUserHolder) {

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();

        final CurrentUserColumnKey[] currentUserColumnKeys = CurrentUserColumnKey.values();

        for (CurrentUserColumnKey column : currentUserColumnKeys) {
            column.setContentValues(contentValues, currentUserHolder);
        }

        super.replace(insertHolder);
    }

    public void deleteAll() {
        super.delete(new DeleteHolder());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ModelList<ModelMap<CurrentUserColumnKey, Object>> getModelInfo() {
        return super.modelInfo;
    }
}
