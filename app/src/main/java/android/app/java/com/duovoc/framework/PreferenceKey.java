package android.app.java.com.duovoc.framework;

public enum PreferenceKey implements IPreferenceKey {
    SecretKey(Key.secret_key);

    private Key key;
    PreferenceKey(Key key) {
        this.key = key;
    }

    private enum Key {
        secret_key,
    }

    @Override
    public String getKeyName() {
        return this.key.name();
    }
}
