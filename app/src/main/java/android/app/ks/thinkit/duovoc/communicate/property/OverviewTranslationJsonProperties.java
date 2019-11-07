package android.app.ks.thinkit.duovoc.communicate.property;

import android.app.ks.thinkit.duovoc.framework.IJsonProperties;
import android.app.ks.thinkit.duovoc.framework.StringChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public enum OverviewTranslationJsonProperties implements IJsonProperties {
    Hint(Key.hint) {
        @Override
        public void set(JSONObject jsonObject, List<String> hintsList) throws JSONException {
            final String value = this.getString(jsonObject, this);

            if (StringChecker.isEffectiveString(value)) {
                hintsList.add(value);
            }
        }
    };

    private Key key;

    OverviewTranslationJsonProperties(Key key) {
        this.key = key;
    }

    protected String getString(JSONObject jsonObject, IJsonProperties key) throws JSONException {
        return (String) jsonObject.get(key.getKeyName());
    }

    @Override
    public String getKeyName() {
        return this.key.name();
    }

    public abstract void set(JSONObject jsonObject, List<String> hintsList) throws JSONException;

    private enum Key {
        hint,
    }
}
