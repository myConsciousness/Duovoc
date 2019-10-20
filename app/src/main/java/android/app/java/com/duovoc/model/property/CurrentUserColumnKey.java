package android.app.java.com.duovoc.model.property;

import android.app.java.com.duovoc.framework.CommonConstants;
import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.CursorHandler;
import android.app.java.com.duovoc.model.holder.CurrentUserHolder;
import android.content.ContentValues;
import android.database.Cursor;

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
            contentValues.put(this.getKeyName(), CommonConstants.STRING_DUMMY);
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
