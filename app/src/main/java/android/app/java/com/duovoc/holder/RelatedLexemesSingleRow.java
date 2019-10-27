package android.app.java.com.duovoc.holder;

import java.util.Objects;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : RelatedLexemesSingleRow.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 詳細画面における語彙素リストの単一行を表現するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see android.app.java.com.duovoc.adapter.OverviewRelatedLexemesAdapter
 * @since 1.0
 */
public final class RelatedLexemesSingleRow {

    /**
     * 当該項目に紐付くIDを格納するフィールドです。
     *
     * @see #getId()
     * @see #setId(long)
     */
    private long id = 0L;

    /**
     * 概要情報に紐付く識別IDを格納するフィールドです。
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
     * 概要情報に紐付く識別IDを返却するGetterメソッドです。
     *
     * @return 概要情報に紐付く識別ID。
     */
    public String getOverviewId() {
        return this.overviewId;
    }

    /**
     * 概要情報に紐付く識別IDを設定するSetterメソッドです。
     *
     * @param overviewId 概要情報に紐付く識別ID。
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
        return "RelatedLexemesSingleRow{" +
                "id=" + this.id +
                ", overviewId='" + this.overviewId + '\'' +
                ", word='" + this.word + '\'' +
                ", lessonName='" + this.lessonName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        RelatedLexemesSingleRow that = (RelatedLexemesSingleRow) o;
        return this.getId() == that.getId() &&
                Objects.equals(this.getOverviewId(), that.getOverviewId()) &&
                Objects.equals(this.getWord(), that.getWord()) &&
                Objects.equals(this.getLessonName(), that.getLessonName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.getId(),
                this.getOverviewId(),
                this.getWord(),
                this.getLessonName());
    }
}
