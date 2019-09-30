package android.app.java.com.duovoc.holder;

final public class CurrentUserHolder {

    /** ユーザに紐づくID */
    private String userId = "";

    /** 現在学習している言語の区分 */
    private String language = "";

    /** 現在学習時に使用している言語の区分 */
    private String fromLanguage = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFromLanguage() {
        return fromLanguage;
    }

    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }
}
