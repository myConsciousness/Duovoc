package android.app.java.com.duovoc.model.property;

import android.app.java.com.duovoc.framework.ITableInfo;

public enum Table implements ITableInfo {
    MasterInformation(Key.master_information),
    MasterMessageInformation(Key.master_message_information),
    UserInformation(Key.user_information),
    LanguageInformation(Key.language_information),
    OverviewInformation(Key.overview_information),
    OverviewTranslationInformation(Key.overview_translation_information),
    CurrentUserInformation(Key.current_user_information),
    CurrentApplicationInformation(Key.current_application_information);

    private final Key key;
    Table(Key key) {
        this.key = key;
    }

    private enum Key {
        master_information,
        master_message_information,
        user_information,
        language_information,
        overview_information,
        overview_translation_information,
        current_user_information,
        current_application_information,
    }

    @Override
    public String getName() {
        return this.key.name();
    }
}
