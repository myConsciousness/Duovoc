package android.app.ks.thinkit.duovoc.holder;

import android.app.ks.thinkit.duovoc.adapter.SwitchFromLanguageAdapter;

import java.util.Objects;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : FromLanguageSingleRow.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 学習言語変更画面における学習時使用言語リストの単一行を表現するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see SwitchFromLanguageAdapter
 * @since 1.0
 */
public final class FromLanguageSingleRow {

    /**
     * 当該項目に紐付くIDを格納するフィールドです。
     *
     * @see #getId()
     * @see #setId(long)
     */
    private long id = 0L;

    /**
     * 学習時使用言語を格納するフィールドです。
     *
     * @see #getFromLanguage()
     * @see #setFromLanguage(String)
     */
    private String fromLanguage = "";

    /**
     * 学習時使用言語の言語コードを格納するフィールドです。
     *
     * @see #getFromLanguageCode()
     * @see #setFromLanguageCode(String)
     */
    private String fromLanguageCode = "";

    /**
     * 当該項目に紐付くIDを返却するGetterメソッドです。
     *
     * @return 当該項目に紐付くID。
     */
    public long getId() {
        return this.id;
    }

    /**
     * 当該項目に紐付くIDを設定するSetterメソッドです。
     *
     * @param id 当該項目に紐付くID。
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 学習時使用言語を返却するGetterメソッドです。
     *
     * @return 学習時使用言語。
     */
    public String getFromLanguage() {
        return this.fromLanguage;
    }

    /**
     * 学習時使用言語を設定するSetterメソッドです。
     *
     * @param fromLanguage 学習時使用言語。
     */
    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    /**
     * 学習時使用言語の言語コードを返却するGetterメソッドです。
     *
     * @return 学習時使用言語の言語コード。
     */
    public String getFromLanguageCode() {
        return this.fromLanguageCode;
    }

    /**
     * 学習時使用言語の言語コードを設定するSetterメソッドです。
     *
     * @param fromLanguageCode 学習時使用言語の言語コード。
     */
    public void setFromLanguageCode(String fromLanguageCode) {
        this.fromLanguageCode = fromLanguageCode;
    }

    @Override
    public String toString() {
        return "FromLanguageSingleRow{" +
                "id=" + this.id +
                ", fromLanguage='" + this.fromLanguage + '\'' +
                ", fromLanguageCode='" + this.fromLanguageCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        FromLanguageSingleRow that = (FromLanguageSingleRow) o;
        return this.getId() == that.getId() &&
                Objects.equals(this.getFromLanguage(), that.getFromLanguage()) &&
                Objects.equals(this.getFromLanguageCode(), that.getFromLanguageCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.getId(),
                this.getFromLanguage(),
                this.getFromLanguageCode());
    }
}
