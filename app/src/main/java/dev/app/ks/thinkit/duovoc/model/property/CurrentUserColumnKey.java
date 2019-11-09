package dev.app.ks.thinkit.duovoc.model.property;

import android.content.ContentValues;
import android.database.Cursor;

import dev.app.ks.thinkit.duovoc.framework.CalendarHandler;
import dev.app.ks.thinkit.duovoc.framework.IModelMapKey;
import dev.app.ks.thinkit.duovoc.framework.ModelMap;
import dev.app.ks.thinkit.duovoc.framework.model.CursorHandler;
import dev.app.ks.thinkit.duovoc.model.holder.CurrentUserHolder;

public enum CurrentUserColumnKey implements IModelMapKey {
    UserId(Key.user_id) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<CurrentUserColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, CurrentUserHolder currentUserHolder) {
            contentValues.put(this.getKeyName(), currentUserHolder.getUserId());
        }
    },

    Language(Key.language) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<CurrentUserColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, CurrentUserHolder currentUserHolder) {
            contentValues.put(this.getKeyName(), currentUserHolder.getLanguage());
        }
    },

    FromLanguage(Key.from_language) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<CurrentUserColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, CurrentUserHolder currentUserHolder) {
            contentValues.put(this.getKeyName(), currentUserHolder.getFromLanguage());
        }
    },

    ModifiedDatetime(Key.modified_datetime) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<CurrentUserColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, CurrentUserHolder currentUserHolder) {
            final String currentClientDatetime = CalendarHandler.getClientDatetime();
            contentValues.put(this.getKeyName(), currentClientDatetime);
        }
    };

    private Key key;

    CurrentUserColumnKey(Key key) {
        this.key = key;
    }

    @Override
    public String getKeyName() {
        return this.key.name();
    }

    public abstract void setModelMap(Cursor cursor, ModelMap<CurrentUserColumnKey, Object> modelMap);

    public abstract void setContentValues(ContentValues contentValues, CurrentUserHolder currentUserHolder);

    private enum Key {
        user_id,
        language,
        from_language,
        modified_datetime,
    }
}
