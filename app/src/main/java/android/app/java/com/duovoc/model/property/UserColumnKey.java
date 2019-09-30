package android.app.java.com.duovoc.model.property;

import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.holder.UserHolder;
import android.content.ContentValues;
import android.database.Cursor;

public enum UserColumnKey implements IModelMapKey {
    UserId(Key.user_id) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserColumnKey, Object> modelMap) {
            this._setModelMap(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserHolder userHolder) {
            contentValues.put(this.getKeyName(), userHolder.getUserId());
        }
    },

    LoginName(Key.login_name) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserColumnKey, Object> modelMap) {
            this._setModelMap(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserHolder userHolder) {
            contentValues.put(this.getKeyName(), userHolder.getLoginName());
        }
    },

    LoginPassword(Key.login_password) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserColumnKey, Object> modelMap) {
            this._setModelMap(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserHolder userHolder) {
            contentValues.put(this.getKeyName(), userHolder.getLoginPassword());
        }
    },

    UserName(Key.user_name) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<UserColumnKey, Object> modelMap) {
            this._setModelMap(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, UserHolder userHolder) {
            contentValues.put(this.getKeyName(), userHolder.getUserName());
        }
    };

    private Key key;

    UserColumnKey(Key key) {
        this.key = key;
    }

    protected void _setModelMap(Cursor cursor, ModelMap<UserColumnKey, Object> modelMap) {

        final int index = cursor.getColumnIndex(this.getKeyName());

        if (index >= 0) {
            modelMap.put(this, cursor.getString(index));
        }
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
    }
}
