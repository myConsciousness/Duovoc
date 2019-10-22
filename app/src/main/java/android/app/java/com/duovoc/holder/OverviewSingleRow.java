package android.app.java.com.duovoc.holder;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : OverviewSingleRow.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 一覧画面における概要リストの単一行を表現するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see android.app.java.com.duovoc.adapter.OverviewAdapter
 * @since 1.0
 */
final public class OverviewSingleRow {

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
     * 発音記号を除去した単語を格納するフィールドです。
     *
     * @see #getNormalizedWord()
     * @see #setNormalizedWord(String)
     */
    private String normalizedWord = "";

    /**
     * レッスン名を格納するフィールです。
     *
     * @see #getLessonName()
     * @see #setLessonName(String)
     */
    private String lessonName = "";

    /**
     * 最終学習時の日付と時刻を格納するフィールドです。
     *
     * @see #getLastPracticed()
     * @see #setLastPracticed(String)
     */
    private String lastPracticed = "";

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
     * @return 当該項目に紐づく識別ID。
     */
    public String getOverviewId() {
        return this.overviewId;
    }

    /**
     * 概要情報に紐付く識別IDを設定するSetterメソッドです。
     *
     * @param overviewId 当該項目に紐づく識別ID。
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
     * 発音記号を除去した単語を返却するGetterメソッドです。
     *
     * @return 発音記号を除去した単語。
     */
    public String getNormalizedWord() {
        return this.normalizedWord;
    }

    /**
     * 発音記号を除去した単語を設定するSetterメソッドです。
     *
     * @param normalizedWord 発音記号を除去した単語。
     */
    public void setNormalizedWord(String normalizedWord) {
        this.normalizedWord = normalizedWord;
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

    /**
     * 最終学習時の日付と時刻を返却するGetterメソッドです。
     *
     * @return 最終学習時の日付と時刻。
     */
    public String getLastPracticed() {
        return this.lastPracticed;
    }

    /**
     * 最終学習時の日付と時刻を設定するSetterメソッドです。
     *
     * @param lastPracticed 最終学習時の日付と時刻。
     */
    public void setLastPracticed(String lastPracticed) {
        this.lastPracticed = lastPracticed;
    }
}
