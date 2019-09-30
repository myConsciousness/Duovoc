package android.app.java.com.duovoc.model;

import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.ModelBase;
import android.app.java.com.duovoc.holder.CurrentApplicationHolder;
import android.app.java.com.duovoc.model.holder.InsertHolder;
import android.app.java.com.duovoc.model.property.CurrentApplicationColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

final public class CurrentApplicationInformation extends ModelBase {

    private static final String TAG = CurrentApplicationInformation.class.getSimpleName();

    private static final CurrentApplicationColumnKey[] CURRENT_APPLICATION_COLUMN_KEYS = CurrentApplicationColumnKey.values();

    private static CurrentApplicationInformation thisInstance = null;

    private ModelMap<CurrentApplicationColumnKey, Object> modelMap = new ModelMap<>();

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

        if (!super.isSucceeded(cursor)) {
            // should not be happened
            return false;
        }

        if (cursor.moveToFirst()) {

            final ModelMap<CurrentApplicationColumnKey, Object> modelMap = new ModelMap<>();

            // 一意制約検索のため検索結果は一件のみ
            for (CurrentApplicationColumnKey column : CURRENT_APPLICATION_COLUMN_KEYS) {
                column.setModelMap(cursor, modelMap);
            }

            this.setModelInfo(modelMap);
        }

        return true;
    }

    public boolean replace(final CurrentApplicationHolder currentApplicationHolder) {

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();

        for (CurrentApplicationColumnKey column : CURRENT_APPLICATION_COLUMN_KEYS) {
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

    public enum ConfigName {
        UsesWifiOnCommunicate(Key.uses_wifi_on_communicate);

        private Key key;

        ConfigName(Key key) {
            this.key = key;
        }

        public String getConfigName() {
            return this.key.name();
        }

        private enum Key {
            uses_wifi_on_communicate,
        }
    }
}
