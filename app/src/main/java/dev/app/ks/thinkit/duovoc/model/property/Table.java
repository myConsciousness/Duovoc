package dev.app.ks.thinkit.duovoc.model.property;

import dev.app.ks.thinkit.duovoc.framework.ITableInfo;

public enum Table implements ITableInfo {
    MasterMessageInformation(TableName.master_message_information),
    UserInformation(TableName.user_information),
    LanguageInformation(TableName.language_information),
    OverviewInformation(TableName.overview_information),
    OverviewTranslationInformation(TableName.overview_translation_information),
    CurrentUserInformation(TableName.current_user_information),
    CurrentApplicationInformation(TableName.current_application_information),
    SupportedLanguageInformation(TableName.supported_language_information),
    OverviewRelatedLexemeInformation(TableName.overview_related_lexeme_information),
    UserMemoInformation(TableName.user_memo_information),
    AutoSyncIntervalInformation(TableName.auto_sync_interval_information);

    private final TableName key;

    Table(TableName key) {
        this.key = key;
    }

    @Override
    public String getName() {
        return this.key.name();
    }

    private enum TableName {
        master_message_information,
        user_information,
        language_information,
        overview_information,
        overview_translation_information,
        current_user_information,
        current_application_information,
        supported_language_information,
        overview_related_lexeme_information,
        user_memo_information,
        auto_sync_interval_information,
    }
}
