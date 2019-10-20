package android.app.java.com.duovoc.model.property;

public enum SupportedLanguage {
    English(LanguageCode.en, "English"),
    Polish(LanguageCode.pl, "Polish"),
    Japanese(LanguageCode.ja, "Japanese"),
    German(LanguageCode.de, "German"),
    Italian(LanguageCode.it, "Italian"),
    Spanish(LanguageCode.es, "Spanish"),
    Chinese(LanguageCode.zs, "Chinese"),
    Russian(LanguageCode.ru, "Russian"),
    Portuguese(LanguageCode.pt, "Portuguese"),
    Dutch(LanguageCode.dn, "Dutch"),
    French(LanguageCode.fr, "French"),
    Korean(LanguageCode.ko, "Korean"),
    Swahili(LanguageCode.sw, "Swahili"),
    Latin(LanguageCode.la, "Latin"),
    Welsh(LanguageCode.cy, "Welsh"),
    Arabic(LanguageCode.ar, "Arabic"),
    Irish(LanguageCode.ga, "Irish"),
    Czech(LanguageCode.cs, "Czech"),
    Indonesian(LanguageCode.id, "Indonesian"),
    Greek(LanguageCode.el, "Greek"),
    Norwegian(LanguageCode.nb, "Norwegian"),
    Turkish(LanguageCode.tr, "Turkish"),
    Vietnamese(LanguageCode.vi, "Vietnamese"),
    Romanian(LanguageCode.ro, "Romanian"),
    Hawaiian(LanguageCode.hw, "Hawaiian"),
    Danish(LanguageCode.da, "Danish"),
    Hindi(LanguageCode.hi, "Hindi"),
    Hungarian(LanguageCode.hu, "Hungarian"),
    Hebrew(LanguageCode.he, "Hebrew"),
    Swedish(LanguageCode.sv, "Swedish"),
    Navajo(LanguageCode.nv, "Navajo"),
    Ukrainian(LanguageCode.uk, "Ukrainian"),
    Klingon(LanguageCode.kl, "Klingon"),
    Esperanto(LanguageCode.eo, "Esperanto"),
    HighValyrian(LanguageCode.hv, "High Valyrian");

    private LanguageCode languageCode;
    private String displayEnglishName;

    SupportedLanguage(LanguageCode languageCode, String displayEnglishName) {
        this.languageCode = languageCode;
        this.displayEnglishName = displayEnglishName;
    }

    public static SupportedLanguage getSupportedLanguageFromCode(final String languageCode) {

        final SupportedLanguage[] supportedLanguages = SupportedLanguage.values();

        for (SupportedLanguage supportedLanguage : supportedLanguages) {
            if (supportedLanguage.getLanguageCode().equals(languageCode)) {
                return supportedLanguage;
            }
        }

        return SupportedLanguage.English;
    }

    public String getLanguageCode() {
        return this.languageCode.name();
    }

    public String getDisplayEnglishName() {
        return this.displayEnglishName;
    }

    private enum LanguageCode {
        en,
        el,
        eo,
        sw,
        it,
        cy,
        ar,
        ga,
        cs,
        id,
        es,
        zs,
        ru,
        pt,
        la,
        nb,
        tr,
        vi,
        ro,
        pl,
        dn,
        fr,
        de,
        hv,
        hw,
        da,
        hi,
        hu,
        ja,
        he,
        ko,
        sv,
        kl,
        nv,
        uk,
    }
}
