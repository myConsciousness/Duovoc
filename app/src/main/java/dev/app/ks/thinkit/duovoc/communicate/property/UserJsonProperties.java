package dev.app.ks.thinkit.duovoc.communicate.property;

import org.json.JSONException;
import org.json.JSONObject;

import dev.app.ks.thinkit.duovoc.framework.IJsonProperties;
import dev.app.ks.thinkit.duovoc.model.holder.UserHolder;

public enum UserJsonProperties implements IJsonProperties {
    Response(Key.response) {
        @Override
        public void set(JSONObject jsonObject, UserHolder userHolder) throws JSONException {
            final String value = this.getString(jsonObject, this);
            userHolder.setResponse(value);
        }
    },

    Username(Key.username) {
        @Override
        public void set(JSONObject jsonObject, UserHolder userHolder) throws JSONException {
            final String value = this.getString(jsonObject, this);
            userHolder.setUserName(value);
        }
    },

    UserId(Key.user_id) {
        @Override
        public void set(JSONObject jsonObject, UserHolder userHolder) throws JSONException {
            final String value = this.getString(jsonObject, this);
            userHolder.setUserId(value);
        }
    };

    private Key key;

    UserJsonProperties(Key key) {
        this.key = key;
    }

    protected String getString(JSONObject jsonObject, IJsonProperties key) throws JSONException {
        return (String) jsonObject.get(key.getKeyName());
    }

    public String getKeyName() {
        return this.key.name();
    }

    public abstract void set(JSONObject jsonObject, UserHolder userHolder) throws JSONException;

    private enum Key {
        response,
        username,
        user_id,
    }
}
