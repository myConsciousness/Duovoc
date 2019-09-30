package android.app.java.com.duovoc.communicate.property;

import android.app.java.com.duovoc.framework.IJsonProperties;

public enum LanguageJsonProperties implements IJsonProperties {
    Language(Key.language_string),
    LearningLanguage(Key.learning_language),
    FromLanguage(Key.from_language),
    LanguageInformation(Key.language_information),
    PronounMapping(Key.pronoun_mapping),
    Tenses(Key.tenses);

    private Key key;

    LanguageJsonProperties(Key key) {
        this.key = key;
    }

    public String getKeyName() {
        return this.key.name();
    }

    private enum Key {
        language_string,
        learning_language,
        from_language,
        language_information,
        pronoun_mapping,
        tenses,
    }
}
