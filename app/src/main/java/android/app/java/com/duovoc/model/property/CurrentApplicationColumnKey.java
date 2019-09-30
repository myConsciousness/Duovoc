package android.app.java.com.duovoc.model.property;

import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.holder.CurrentApplicationHolder;
import android.content.ContentValues;
import android.database.Cursor;

public enum CurrentApplicationColumnKey implements IModelMapKey {
    ConfigName(Key.config_name) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<CurrentApplicationColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, CurrentApplicationHolder currentApplicationHolder) {
            contentValues.put(this.getKeyName(), currentApplicationHolder.getConfigName());
        }
    },

    ConfigValue(Key.config_value) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<CurrentApplicationColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, CurrentApplicationHolder currentApplicationHolder) {
            contentValues.put(this.getKeyName(), currentApplicationHolder.getConfigValue());
        }
    };

    private Key key;
    CurrentApplicationColumnKey(Key key) {
        this.key = key;
    }

    private enum Key {
        config_name,
        config_value,
    }

    protected void setStringIfNotEmpty(final Cursor cursor, final ModelMap<CurrentApplicationColumnKey, Object> modelMap) {

        final int index = cursor.getColumnIndex(this.getKeyName());

        if (index >= 0) {
            modelMap.put(this, cursor.getString(index));
        }
    }

    @Override
    public String getKeyName() {
        return this.key.name();
    }

    public abstract void setModelMap(Cursor cursor, ModelMap<CurrentApplicationColumnKey, Object> modelMap);
    public abstract void setContentValues(ContentValues contentValues, CurrentApplicationHolder currentApplicationHolder);
}
