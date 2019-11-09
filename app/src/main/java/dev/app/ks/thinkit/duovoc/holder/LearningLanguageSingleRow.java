package dev.app.ks.thinkit.duovoc.holder;

import java.util.Objects;

import dev.app.ks.thinkit.duovoc.adapter.SwitchLearningLanguageAdapter;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : LearningLanguageSingleRow.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 学習言語変更画面における学習言語リストの単一行を表現するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see SwitchLearningLanguageAdapter
 * @since 1.0
 */
public final class LearningLanguageSingleRow {

    /**
     * 当該項目に紐付くIDを格納するフィールドです。
     *
     * @see #getId()
     * @see #setId(long)
     */
    private long id = 0L;

    /**
     * 学習言語を格納するフィールドです。
     *
     * @see #getLearningLanguage()
     * @see #setLearningLanguage(String)
     */
    private String learningLanguage = "";

    /**
     * 学習言語の言語コードを格納するフィールドです。
     *
     * @see #getLearningLanguageCode()
     * @see #setLearningLanguageCode(String)
     */
    private String learningLanguageCode = "";

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
     * 学習言語を返却するGetterメソッドです。
     *
     * @return 学習言語。
     */
    public String getLearningLanguage() {
        return this.learningLanguage;
    }

    /**
     * 学習言語を設定するSetterメソッドです。
     *
     * @param learningLanguage 学習言語。
     */
    public void setLearningLanguage(String learningLanguage) {
        this.learningLanguage = learningLanguage;
    }

    /**
     * 学習言語の言語コードを返却するGetterメソッドです。
     *
     * @return 学習言語の言語コード。
     */
    public String getLearningLanguageCode() {
        return this.learningLanguageCode;
    }

    /**
     * 学習言語の言語コードを設定するSetterメソッドです。
     *
     * @param learningLanguageCode 学習言語の言語コード。
     */
    public void setLearningLanguageCode(String learningLanguageCode) {
        this.learningLanguageCode = learningLanguageCode;
    }

    @Override
    public String toString() {
        return "LearningLanguageSingleRow{" +
                "id=" + this.id +
                ", learningLanguage='" + this.learningLanguage + '\'' +
                ", learningLanguageCode='" + this.learningLanguageCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        LearningLanguageSingleRow that = (LearningLanguageSingleRow) o;
        return this.getId() == that.getId() &&
                Objects.equals(this.getLearningLanguage(), that.getLearningLanguage()) &&
                Objects.equals(this.getLearningLanguageCode(), that.getLearningLanguageCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.getId(),
                this.getLearningLanguage(),
                this.getLearningLanguageCode());
    }
}
