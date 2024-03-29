package dev.app.ks.thinkit.duovoc.model.holder;

import java.util.Objects;

import dev.app.ks.thinkit.duovoc.framework.model.holder.ModelAccessor;
import dev.app.ks.thinkit.duovoc.model.OverviewRelatedLexemeInformation;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : OverviewRelatedLexemeHolder.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 論理モデル名「概要語彙素情報」を操作する際に使用するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see OverviewRelatedLexemeInformation
 * @since 1.0
 */
public final class OverviewRelatedLexemeHolder extends ModelAccessor {

    /**
     * 概要語彙素情報に紐付く識別IDを格納するフィールドです。
     *
     * @see #getLexemeId()
     * @see #setLexemeId(String)
     */
    private String lexemeId = "";

    /**
     * 概要IDを格納するフィールドです。
     *
     * @see #getOverviewId()
     * @see #setOverviewId(String)
     */
    private String overviewId = "";

    /**
     * 単語を格納するフィールドです。
     *
     * @see #getWord()
     * @see #setWord(String)
     */
    private String word = "";

    /**
     * レッスン名を格納するフィールドです。
     *
     * @see #getLessonName()
     * @see #setLessonName(String)
     */
    private String lessonName = "";

    /**
     * 概要語彙素情報に紐付く語彙素の識別IDを返却するGetterメソッドです。
     *
     * @return 当該項目に紐づく語彙素の識別ID。
     */
    public String getLexemeId() {
        return this.lexemeId;
    }

    /**
     * 概要語彙素情報に紐付く語彙素の識別IDを設定するSetterメソッドです。
     *
     * @param lexemeId 当該項目に紐づく語彙素の識別ID。
     */
    public void setLexemeId(String lexemeId) {
        this.lexemeId = lexemeId;
    }

    /**
     * 概要IDを返却するGetterメソッドです。
     *
     * @return 概要ID。
     */
    public String getOverviewId() {
        return this.overviewId;
    }

    /**
     * 概要IDを設定するSetterメソッドです。
     *
     * @param overviewId 概要ID。
     */
    public void setOverviewId(String overviewId) {
        this.overviewId = overviewId;
    }

    /**
     * 単語を返却するGetterメソッドです。
     *
     * @return 単語。
     */
    public String getWord() {
        return this.word;
    }

    /**
     * 単語を設定するSetterメソッドです。
     *
     * @param word 単語。
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * レッスン名を返却するGetterメソッドです。
     *
     * @return レッスン名。
     */
    public String getLessonName() {
        return this.lessonName;
    }

    /**
     * レッスン名を設定するSetterメソッドです。
     *
     * @param lessonName レッスン名。
     */
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    @Override
    public String toString() {
        return "OverviewRelatedLexemeHolder{" +
                "lexemeId='" + this.lexemeId + '\'' +
                ", overviewId='" + this.overviewId + '\'' +
                ", word='" + this.word + '\'' +
                ", lessonName='" + this.lessonName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        OverviewRelatedLexemeHolder that = (OverviewRelatedLexemeHolder) o;
        return Objects.equals(this.getLexemeId(), that.getLexemeId()) &&
                Objects.equals(this.getOverviewId(), that.getOverviewId()) &&
                Objects.equals(this.getWord(), that.getWord()) &&
                Objects.equals(this.getLessonName(), that.getLessonName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.getLexemeId(),
                this.getOverviewId(),
                this.getWord(),
                this.getLessonName());
    }
}
