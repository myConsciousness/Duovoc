package android.app.java.com.duovoc.model.holder;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : CurrentUserHolder.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 論理モデル名「カレントユーザ情報」を操作する際に使用するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see android.app.java.com.duovoc.model.CurrentUserInformation
 * @since 1.0
 */
final public class CurrentUserHolder {

    /**
     * 現在操作中のユーザに紐づくIDを格納するフィールドです。
     *
     * @see #getUserId()
     * @see #setUserId(String)
     */
    private String userId = "";

    /**
     * 現在学習している言語の区分を格納するフィールドです。
     *
     * @see #getLanguage()
     * @see #setLanguage(String)
     */
    private String language = "";

    /**
     * 現在学習時に使用している言語の区分を格納するフィールドです。
     *
     * @see #getFromLanguage()
     * @see #setFromLanguage(String)
     */
    private String fromLanguage = "";

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
     * 言語区分を返却するGetterメソッドです。
     *
     * @return 言語区分。
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     * 言語区分を設定するSetterメソッドです。
     *
     * @param language 言語区分。
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 学習時に使用している言語区分を返却するGetterメソッドです。
     *
     * @return 学習時に使用している言語区分。
     */
    public String getFromLanguage() {
        return this.fromLanguage;
    }

    /**
     * 学習時に使用している言語区分を設定するSetterメソッドです。
     *
     * @param fromLanguage 学習時に使用している言語区分。
     */
    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }
}
