package android.app.java.com.duovoc.communicate.property;

import android.app.java.com.duovoc.framework.communicate.property.IRequestQuery;

public enum SwitchLanguageQuery implements IRequestQuery {
    LearningLanguage(Key.learning_language),
    FromLanguage(Key.from_language);

    private Key key;

    SwitchLanguageQuery(Key key) {
        this.key = key;
    }

    public String getQueryName() {
        return this.key.name();
    }

    private enum Key {
        learning_language,
        from_language,
    }
}
