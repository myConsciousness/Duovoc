package android.app.java.com.duovoc.model.property;

import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.holder.CurrentUserHolder;
import android.content.ContentValues;
import android.database.Cursor;

public enum CurrentUserColumnKey implements IModelMapKey {
    UserId(Key.user_id) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<CurrentUserColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, CurrentUserHolder currentUserHolder) {
            contentValues.put(this.getKeyName(), currentUserHolder.getUserId());
        }
    },

    Language(Key.language) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<CurrentUserColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, CurrentUserHolder currentUserHolder) {
            contentValues.put(this.getKeyName(), currentUserHolder.getLanguage());
        }
    },

    FromLanguage(Key.from_language) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<CurrentUserColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, CurrentUserHolder currentUserHolder) {
            contentValues.put(this.getKeyName(), currentUserHolder.getFromLanguage());
        }
    };

    private Key key;

    CurrentUserColumnKey(Key key) {
        this.key = key;
    }

    protected void setStringIfNotEmpty(final Cursor cursor, final ModelMap<CurrentUserColumnKey, Object> modelMap) {

        final int index = cursor.getColumnIndex(this.getKeyName());

        if (index >= 0) {
            modelMap.put(this, cursor.getString(index));
        }
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
    }
}
