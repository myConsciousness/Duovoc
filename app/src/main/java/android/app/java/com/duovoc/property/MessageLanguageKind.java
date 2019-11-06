package android.app.java.com.duovoc.property;

public enum MessageLanguageKind {
    English(LanguageKind.en),
    Japanese(LanguageKind.ja);

    private LanguageKind languageKind;

    MessageLanguageKind(LanguageKind languageKind) {
        this.languageKind = languageKind;
    }

    public String getKindName() {
        return this.languageKind.name();
    }

    private enum LanguageKind {
        en,
        ja
    }
}
