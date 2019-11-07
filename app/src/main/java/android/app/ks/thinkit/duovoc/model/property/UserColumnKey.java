package android.app.ks.thinkit.duovoc.model.property;

import android.app.ks.thinkit.duovoc.framework.CalendarHandler;
import android.app.ks.thinkit.duovoc.framework.IModelMapKey;
import android.app.ks.thinkit.duovoc.framework.ModelMap;
import android.app.ks.thinkit.duovoc.framework.model.CursorHandler;
import android.app.ks.thinkit.duovoc.model.holder.UserHolder;
import android.content.ContentValues;
import android.database.Cursor;

public enum UserColumnKey implements IModelMapKey {
    UserId(Key.user_id) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserHolder userHolder) {
            contentValues.put(this.getKeyName(), userHolder.getUserId());
        }
    },

    LoginName(Key.login_name) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserHolder userHolder) {
            contentValues.put(this.getKeyName(), userHolder.getLoginName());
        }
    },

    LoginPassword(Key.login_password) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserHolder userHolder) {
            contentValues.put(this.getKeyName(), userHolder.getLoginPassword());
        }
    },

    UserName(Key.user_name) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserHolder userHolder) {
            contentValues.put(this.getKeyName(), userHolder.getUserName());
        }
    },

    ModifiedDatetime(Key.modified_datetime) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserHolder userHolder) {
            final String currentClientDatetime = CalendarHandler.getClientDatetime();
            contentValues.put(this.getKeyName(), currentClientDatetime);
        }
    };

    private Key key;

    UserColumnKey(Key key) {
        this.key = key;
    }

    @Override
    public String getKeyName() {
        return this.key.name();
    }

    public abstract void setModelMap(Cursor cursor, ModelMap<UserColumnKey, Object> modelMap);

    public abstract void setContentValues(ContentValues contentValues, UserHolder userHolder);

    private enum Key {
        user_id,
        login_name,
        login_password,
        user_name,
        modified_datetime,
    }
}
