package android.app.java.com.duovoc.communicate.property;

import android.app.java.com.duovoc.framework.communicate.property.IRequestQuery;

public enum LoginQuery implements IRequestQuery {
    Login(Key.login),
    Password(Key.password);

    private Key key;

    LoginQuery(Key key) {
        this.key = key;
    }

    @Override
    public String getQueryName() {
        return this.key.name();
    }

    private enum Key {
        login,
        password,
    }
}
