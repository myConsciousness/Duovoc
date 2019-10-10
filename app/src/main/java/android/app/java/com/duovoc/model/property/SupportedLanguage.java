package android.app.java.com.duovoc.model.property;

public enum SupportedLanguage {
    Greek(LanguageCode.el, "Greek"),
    Esperanto(LanguageCode.eo, "Esperanto"),
    Swahili(LanguageCode.sw, "Swahili"),
    Italian(LanguageCode.it, "Italian"),
    Welsh(LanguageCode.cy, "Welsh"),
    Arabic(LanguageCode.ar, "Arabic"),
    Irish(LanguageCode.ga, "Irish"),
    Czech(LanguageCode.cs, "Czech"),
    Indonesian(LanguageCode.id, "Indonesian"),
    Spanish(LanguageCode.es, "Spanish"),
    Chinese(LanguageCode.zs, "Chinese"),
    Russian(LanguageCode.ru, "Russian"),
    Portuguese(LanguageCode.pt, "Portuguese"),
    Latin(LanguageCode.la, "Latin"),
    Norwegian(LanguageCode.nb, "Norwegian"),
    Turkish(LanguageCode.tr, "Turkish"),
    Vietnamese(LanguageCode.vi, "Vietnamese"),
    Romanian(LanguageCode.ro, "Romanian"),
    Polish(LanguageCode.pl, "Polish"),
    Dutch(LanguageCode.dn, "Dutch"),
    French(LanguageCode.fr, "French"),
    German(LanguageCode.de, "German"),
    HighValyrian(LanguageCode.hv, "High Valyrian"),
    Hawaiian(LanguageCode.hw, "Hawaiian"),
    Danish(LanguageCode.da, "Danish"),
    Hindi(LanguageCode.hi, "Hindi"),
    Hungarian(LanguageCode.hu, "Hungarian"),
    Japanese(LanguageCode.ja, "Japanese"),
    Hebrew(LanguageCode.he, "Hebrew"),
    Korean(LanguageCode.ko, "Korean"),
    Swedish(LanguageCode.sv, "Swedish"),
    Klingon(LanguageCode.kl, "Klingon"),
    Navajo(LanguageCode.nv, "Navajo"),
    Ukrainian(LanguageCode.uk, "Ukrainian");

    private LanguageCode languageCode;
    private String displayEnglishName;

    SupportedLanguage(LanguageCode languageCode, String displayEnglishName) {
        this.languageCode = languageCode;
        this.displayEnglishName = displayEnglishName;
    }

    public String getCodeName() {
        return this.languageCode.name();
    }

    public String getDisplayEnglishName() {
        return this.displayEnglishName;
    }

    private enum LanguageCode {
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
