package android.app.java.com.duovoc.holder;

final public class LanguageHolder {

    /** 学習している言語の文字列 */
    private String language = "";

    /** 学習している言語の管理コード */
    private String learningLanguage = "";

    /** 学習するために使用している言語の管理コード */
    private String fromLanguage = "";

    /**
     * 学習している言語の文字列を取得する
     *
     * @return 学習している言語の文字列
     * @see #setLanguage(String)
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 学習している言語の文字列を設定する
     *
     * @return 学習している言語の文字列
     * @see #getLanguage()
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLearningLanguage() {
        return learningLanguage;
    }

    public void setLearningLanguage(String learningLanguage) {
        this.learningLanguage = learningLanguage;
    }

    public String getFromLanguage() {
        return fromLanguage;
    }

    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }
}
