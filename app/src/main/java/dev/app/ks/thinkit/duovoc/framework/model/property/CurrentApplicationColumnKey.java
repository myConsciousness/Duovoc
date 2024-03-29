package dev.app.ks.thinkit.duovoc.framework.model.property;

import android.content.ContentValues;
import android.database.Cursor;

import dev.app.ks.thinkit.duovoc.framework.CalendarHandler;
import dev.app.ks.thinkit.duovoc.framework.IModelMapKey;
import dev.app.ks.thinkit.duovoc.framework.ModelMap;
import dev.app.ks.thinkit.duovoc.framework.model.CursorHandler;
import dev.app.ks.thinkit.duovoc.framework.model.holder.CurrentApplicationHolder;

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
            final String currentClientDatetime = CalendarHandler.getClientDatetime();
            contentValues.put(this.getKeyName(), currentClientDatetime);
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
