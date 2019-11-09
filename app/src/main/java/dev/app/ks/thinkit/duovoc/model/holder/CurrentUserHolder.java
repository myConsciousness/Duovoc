package dev.app.ks.thinkit.duovoc.model.holder;

import java.util.Objects;

import dev.app.ks.thinkit.duovoc.model.CurrentUserInformation;

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
 * @see CurrentUserInformation
 * @since 1.0
 */
public final class CurrentUserHolder {

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

    @Override
    public String toString() {
        return "CurrentUserHolder{" +
                "userId='" + this.userId + '\'' +
                ", language='" + this.language + '\'' +
                ", fromLanguage='" + this.fromLanguage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        CurrentUserHolder that = (CurrentUserHolder) o;
        return this.getUserId().equals(that.getUserId()) &&
                this.getLanguage().equals(that.getLanguage()) &&
                this.getFromLanguage().equals(that.getFromLanguage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId(), this.getLanguage(), this.getFromLanguage());
    }
}
