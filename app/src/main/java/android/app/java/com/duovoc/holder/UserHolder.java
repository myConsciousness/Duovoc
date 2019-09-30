package android.app.java.com.duovoc.holder;

final public class UserHolder {

    /**
     * json実行後のレスポンスコードを格納する
     */
    private String response = "";

    /**
     * ユーザIDを格納する
     */
    private String userId = "";

    /**
     * ユーザ名を格納する
     */
    private String userName = "";

    /**
     * ログイン時のユーザ名を格納する
     */
    private String loginName = "";

    /**
     * ログイン時のパスワードを格納する
     */
    private String loginPassword = "";

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "response='" + response + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                '}';
    }
}
