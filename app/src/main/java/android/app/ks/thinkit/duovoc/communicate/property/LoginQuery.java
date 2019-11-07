package android.app.ks.thinkit.duovoc.communicate.property;

import android.app.ks.thinkit.duovoc.framework.communicate.property.IRequestQuery;

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
