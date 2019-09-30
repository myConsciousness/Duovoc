package android.app.java.com.duovoc.communicate.property;

import android.app.java.com.duovoc.framework.communicate.property.IRequestQuery;

public enum LoginQuery implements IRequestQuery {
    Login("login"),
    Password("password");

    private String query;
    LoginQuery(String query) {
        this.query = query;
    }

    @Override
    public String getQueryName() {
        return this.query;
    }
}
