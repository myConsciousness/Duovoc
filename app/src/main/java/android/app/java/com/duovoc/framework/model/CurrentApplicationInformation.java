package android.app.java.com.duovoc.framework.model;

import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.holder.CurrentApplicationHolder;
import android.app.java.com.duovoc.framework.model.holder.InsertHolder;
import android.app.java.com.duovoc.framework.model.property.CurrentApplicationColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public final class CurrentApplicationInformation extends BaseModel {

    private static final String TAG = CurrentApplicationInformation.class.getName();

    private static CurrentApplicationInformation thisInstance = null;

    private CurrentApplicationInformation(final Context context) {
        super(context, Table.CurrentApplicationInformation);
    }

    public static CurrentApplicationInformation getInstance(final Context context) {

        if (thisInstance == null) {
            thisInstance = new CurrentApplicationInformation(context);
        }

        return thisInstance;
    }

    public void selectByPrimaryKey(final ConfigName primaryKey) {
        super.selectByPrimaryKey(CurrentApplicationColumnKey.ConfigName, primaryKey.getKeyName());
    }

    @Override
    protected ModelList<ModelMap<CurrentApplicationColumnKey, Object>> onPostSelect(final Cursor cursor) {

        final ModelList<ModelMap<CurrentApplicationColumnKey, Object>> modelInfo = new ModelList<>(cursor.getCount());

        if (cursor.moveToFirst()) {
            final ModelMap<CurrentApplicationColumnKey, Object> modelMap = new ModelMap<>(CurrentApplicationColumnKey.class);
            final CurrentApplicationColumnKey[] currentApplicationColumnKeys = CurrentApplicationColumnKey.values();

            // 一意制約検索のため検索結果は一件のみ
            for (CurrentApplicationColumnKey column : currentApplicationColumnKeys) {
                column.setModelMap(cursor, modelMap);
            }

            modelInfo.add(modelMap);
        }

        return modelInfo;
    }

    public void replace(final CurrentApplicationHolder currentApplicationHolder) {

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();
        final CurrentApplicationColumnKey[] currentApplicationColumnKeys = CurrentApplicationColumnKey.values();

        for (CurrentApplicationColumnKey column : currentApplicationColumnKeys) {
            column.setContentValues(contentValues, currentApplicationHolder);
        }

        super.replace(insertHolder);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ModelList<ModelMap<CurrentApplicationColumnKey, Object>> getModelInfo() {
        return super.modelInfo;
    }

    public String getConfigValue() {
        return this.getModelInfo().get(0).getString(CurrentApplicationColumnKey.ConfigValue);
    }

    public enum ConfigName implements IModelMapKey {
        UsesWifiOnCommunicate(Key.uses_wifi_on_communicate);

        private Key key;

        ConfigName(Key key) {
            this.key = key;
        }

        public String getConfigName() {
            return this.key.name();
        }

        public String getKeyName() {
            return this.key.name();
        }

        private enum Key {
            uses_wifi_on_communicate,
        }
    }
}
