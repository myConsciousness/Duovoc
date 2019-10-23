package android.app.java.com.duovoc.model.holder;

import java.util.Objects;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : UserHolder.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 論理モデル名「ユーザ情報」を操作する際に使用するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see android.app.java.com.duovoc.model.UserInformation
 * @since 1.0
 */
final public class UserHolder {

    /**
     * 認証処理後のレスポンスコードを格納するフィールドです。
     *
     * @see #getResponse()
     * @see #setResponse(String)
     */
    private String response = "";

    /**
     * ユーザIDを格納するフィールドです。
     *
     * @see #getUserId()
     * @see #setUserId(String)
     */
    private String userId = "";

    /**
     * ユーザ名を格納するフィールドです。
     *
     * @see #getUserName()
     * @see #setUserName(String)
     */
    private String userName = "";

    /**
     * ログイン時のユーザ名を格納するフィールドです。
     *
     * @see #getLoginName()
     * @see #setLoginName(String)
     */
    private String loginName = "";

    /**
     * ログイン時のパスワードを格納するフィールドです。
     *
     * @see #getLoginPassword()
     * @see #setLoginPassword(String)
     */
    private String loginPassword = "";

    /**
     * 認証処理後のレスポンスコードを返却するGetterメソッドです。
     *
     * @return 認証処理後のレスポンスコード。
     */
    public String getResponse() {
        return this.response;
    }

    /**
     * 認証処理後のレスポンスコードを設定するSetterメソッドです。
     *
     * @param response 認証処理後のレスポンスコード。
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * ユーザIDを返却するGetterメソッドです。
     *
     * @return ユーザID。
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * ユーザIDを設定するSetterメソッドです。
     *
     * @param userId ユーザID。
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * ユーザ名を返却するGetterメソッドです。
     *
     * @return ユーザ名。
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * ユーザ名を設定するSetterメソッドです。
     *
     * @param userName ユーザ名。
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * ログイン時のユーザ名を返却するGetterメソッドです。
     *
     * @return ログイン時のユーザ名。
     */
    public String getLoginName() {
        return this.loginName;
    }

    /**
     * ログイン時のユーザ名を設定するSetterメソッドです。
     *
     * @param loginName ログイン時のユーザ名。
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * ログイン時のパスワードを返却するGetterメソッドです。
     *
     * @return ログイン時のパスワード。
     */
    public String getLoginPassword() {
        return this.loginPassword;
    }

    /**
     * ログイン時のパスワードを設定するSetterメソッドです。
     *
     * @param loginPassword ログイン時のパスワード。
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "response='" + this.response + '\'' +
                ", userId='" + this.userId + '\'' +
                ", userName='" + this.userName + '\'' +
                ", loginName='" + this.loginName + '\'' +
                ", loginPassword='" + this.loginPassword + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        UserHolder that = (UserHolder) o;
        return Objects.equals(this.getResponse(), that.getResponse()) &&
                Objects.equals(this.getUserId(), that.getUserId()) &&
                Objects.equals(this.getUserName(), that.getUserName()) &&
                Objects.equals(this.getLoginName(), that.getLoginName()) &&
                Objects.equals(this.getLoginPassword(), that.getLoginPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.getResponse(),
                this.getUserId(),
                this.getUserName(),
                this.getLoginName(),
                this.getLoginPassword());
    }
}
