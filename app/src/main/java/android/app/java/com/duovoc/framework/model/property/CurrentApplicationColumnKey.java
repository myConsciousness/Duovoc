package android.app.java.com.duovoc.framework.model.property;

import android.app.java.com.duovoc.framework.CommonConstants;
import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.CursorHandler;
import android.app.java.com.duovoc.framework.model.holder.CurrentApplicationHolder;
import android.content.ContentValues;
import android.database.Cursor;

public enum CurrentApplicationColumnKey implements IModelMapKey {
    ConfigName(Key.config_name) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<CurrentApplicationColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, CurrentApplicationHolder currentApplicationHolder) {
            contentValues.put(this.getKeyName(), currentApplicationHolder.getConfigName());
        }
    },

    ConfigValue(Key.config_value) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<CurrentApplicationColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, CurrentApplicationHolder currentApplicationHolder) {
            contentValues.put(this.getKeyName(), currentApplicationHolder.getConfigValue());
        }
    },

    ModifiedDatetime(Key.modified_datetime) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<CurrentApplicationColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, CurrentApplicationHolder currentApplicationHolder) {
            contentValues.put(this.getKeyName(), CommonConstants.STRING_DUMMY);
        }
    };

    private Key key;

    CurrentApplicationColumnKey(Key key) {
        this.key = key;
    }

    @Override
    public String getKeyName() {
        return this.key.name();
    }

    public abstract void setModelMap(Cursor cursor, ModelMap<CurrentApplicationColumnKey, Object> modelMap);

    public abstract void setContentValues(ContentValues contentValues, CurrentApplicationHolder currentApplicationHolder);

    private enum Key {
        config_name,
        config_value,
        modified_datetime,
    }
}
