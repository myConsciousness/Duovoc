package android.app.java.com.duovoc.framework.model;

import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.holder.CurrentApplicationHolder;
import android.app.java.com.duovoc.framework.model.holder.InsertHolder;
import android.app.java.com.duovoc.framework.model.property.CurrentApplicationColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

final public class CurrentApplicationInformation extends BaseModel {

    private static final String TAG = CurrentApplicationInformation.class.getSimpleName();

    private static CurrentApplicationInformation thisInstance = null;

    private ModelMap<CurrentApplicationColumnKey, Object> modelMap = new ModelMap<>(CurrentApplicationColumnKey.class);

    private CurrentApplicationInformation(final Context context) {
        super(context, Table.CurrentApplicationInformation);
    }

    public static CurrentApplicationInformation getInstance(final Context context) {

        if (thisInstance == null) {
            thisInstance = new CurrentApplicationInformation(context);
        }

        return thisInstance;
    }

    public boolean selectByPrimaryKey(final String primaryKey) {

        return super.selectByPrimaryKey(CurrentApplicationColumnKey.ConfigName, primaryKey);
    }

    @Override
    protected boolean onPostSelect(final Cursor cursor) {

        this.setModelInfo(new ModelMap<>(CurrentApplicationColumnKey.class));

        if (!super.isSucceeded(cursor)) {
            // should not be happened
            return false;
        }

        if (cursor.moveToFirst()) {
            final ModelMap<CurrentApplicationColumnKey, Object> modelMap = new ModelMap<>(CurrentApplicationColumnKey.class);
            final CurrentApplicationColumnKey[] currentApplicationColumnKeys = CurrentApplicationColumnKey.values();

            // 一意制約検索のため検索結果は一件のみ
            for (CurrentApplicationColumnKey column : currentApplicationColumnKeys) {
                column.setModelMap(cursor, modelMap);
            }

            this.setModelInfo(modelMap);
        }

        return true;
    }

    public boolean replace(final CurrentApplicationHolder currentApplicationHolder) {

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();
        final CurrentApplicationColumnKey[] currentApplicationColumnKeys = CurrentApplicationColumnKey.values();

        for (CurrentApplicationColumnKey column : currentApplicationColumnKeys) {
            column.setContentValues(contentValues, currentApplicationHolder);
        }

        return super.replace(insertHolder);
    }

    public ModelMap<CurrentApplicationColumnKey, Object> getModelInfo() {
        return this.modelMap;
    }

    private void setModelInfo(ModelMap<CurrentApplicationColumnKey, Object> modelMap) {
        this.modelMap = modelMap;
    }

    public String getConfigValue() {
        return this.getModelInfo().getString(CurrentApplicationColumnKey.ConfigValue);
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
