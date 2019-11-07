package android.app.ks.thinkit.duovoc.framework;

public enum PreferenceKey implements IPreferenceKey {
    SecretKey(Key.secret_key),
    GeneralDataProtectionRegulation(Key.general_data_protection_regulation);

    private Key key;

    PreferenceKey(Key key) {
        this.key = key;
    }

    @Override
    public String getKeyName() {
        return this.key.name();
    }

    private enum Key {
        secret_key,
        general_data_protection_regulation,
    }
}
