package dev.app.ks.thinkit.duovoc.model.holder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.app.ks.thinkit.duovoc.framework.model.holder.ModelAccessor;
import dev.app.ks.thinkit.duovoc.model.OverviewInformation;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : OverviewHolder.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 論理モデル名「概要情報」を操作する際に使用するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see OverviewInformation
 * @since 1.0
 */
public final class OverviewHolder extends ModelAccessor {

    /**
     * 単語情報の識別コードを格納するフィールドです。
     *
     * @see #getId()
     * @see #setId(String)
     */
    private String id = "";

    /**
     * ユーザに紐づくIDを格納するフィールドです。
     *
     * @see #getUserId()
     * @see #setUserId(String)
     */
    private String userId = "";

    /**
     * 言語名を格納するフィールドです。
     *
     * @see #getLanguageString()
     * @see #setLanguageString(String)
     */
    private String languageString = "";

    /**
     * 単語の言語区分を格納するフィールドです。
     *
     * @see #getLanguage()
     * @see #setLanguage(String)
     */
    private String language = "";

    /**
     * 学習時に使用している言語区分を格納するフィールドです。
     *
     * @see #getFromLanguage()
     * @see #setFromLanguage(String)
     */
    private String fromLanguage = "";

    /**
     * 当該項目に紐づく語彙素の識別IDを格納するフィールドです。
     *
     * @see #getLexemeId()
     * @see #setLexemeId(String)
     */
    private String lexemeId = "";

    /**
     * 語彙素の識別IDリストを格納するフィールドです。
     *
     * @see #getRelatedLexemes()
     * @see #setRelatedLexemes(List)
     */
    private List<String> relatedLexemes = new ArrayList<>();

    /**
     * 単語の学習度を格納するフィールドです。
     *
     * @see #getStrengthBars()
     * @see #setStrengthBars(int)
     */
    private int strengthBars = 0;

    /**
     * 不定詞を格納するフィールドです。
     *
     * @see #getInfinitive()
     * @see #setInfinitive(String)
     */
    private String infinitive = "";

    /**
     * 単語を格納するフィールドです。
     *
     * @see #getWordString()
     * @see #setWordString(String)
     */
    private String wordString = "";

    /**
     * 発音記号を除去した単語を格納するフィールドです。
     *
     * @see #getNormalizedString()
     * @see #setNormalizedString(String)
     */
    private String normalizedString = "";

    /**
     * 位置を格納するフィールドです。
     *
     * @see #getPos()
     * @see #setPos(String)
     */
    private String pos = "";

    /**
     * 最終学習時間（単位:ms）を格納するフィールドです。
     *
     * @see #getLastPracticedMs()
     * @see #setLastPracticedMs(Long)
     */
    private Long lastPracticedMs = 0L;

    /**
     * 単語が属するスキル名を格納するフィールドです。
     *
     * @see #getSkill()
     * @see #setSkill(String)
     */
    private String skill = "";

    /**
     * 最終学習時の日付と時刻を格納するフィールドです。
     *
     * @see #getLastPracticed()
     * @see #setLastPracticed(String)
     */
    private String lastPracticed = "";

    /**
     * 学習の強度を格納するフィールドです。
     *
     * @see #getStrength()
     * @see #setStrength(Double)
     */
    private Double strength = 0.0;

    /**
     * URLで指定されるレッスン名を格納するフィールドです。
     *
     * @see #getSkillUrlTitle()
     * @see #setSkillUrlTitle(String)
     */
    private String skillUrlTitle = "";

    /**
     * 単語の性別を格納するフィールドです。
     *
     * @see #getGender()
     * @see #setGender(String)
     */
    private String gender = "";

    /**
     * 単語情報の識別コードを返却するGetterメソッドです。
     *
     * @return 単語情報の識別コード
     */
    public String getId() {
        return this.id;
    }

    /**
     * 単語情報の識別コードを設定するSetterメソッドです。
     *
     * @param id 単語情報の識別コード。
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ユーザに紐づくIDを返却するGetterメソッドです。
     *
     * @return ユーザに紐づくID。
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * ユーザに紐づくIDを設定するSetterメソッドです。
     *
     * @param userId ユーザに紐づくID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 言語名を返却するGetterメソッドです。
     *
     * @return 言語名。
     */
    public String getLanguageString() {
        return this.languageString;
    }

    /**
     * 言語名を設定するSetterメソッドです。
     *
     * @param languageString 言語名。
     */
    public void setLanguageString(String languageString) {
        this.languageString = languageString;
    }

    /**
     * 単語の言語区分を返却するGetterメソッドです。
     *
     * @return 単語の言語区分。
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     * 単語の言語区分を設定するSetterメソッドです。
     *
     * @param language 単語の言語区分。
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

    /**
     * 当該項目に紐づく語彙素の識別IDを返却するGetterメソッドです。
     *
     * @return 当該項目に紐づく語彙素の識別ID。
     */
    public String getLexemeId() {
        return this.lexemeId;
    }

    /**
     * 当該項目に紐づく語彙素の識別IDを設定するSetterメソッドです。
     *
     * @param lexemeId 当該項目に紐づく語彙素の識別ID。
     */
    public void setLexemeId(String lexemeId) {
        this.lexemeId = lexemeId;
    }

    /**
     * 語彙素の識別IDリストを返却するGetterメソッドです。
     *
     * @return 語彙素の識別IDリスト。
     */
    public List<String> getRelatedLexemes() {
        return this.relatedLexemes;
    }

    /**
     * 語彙素の識別IDリストを設定するSetterメソッドです。
     *
     * @param relatedLexemes 語彙素の識別IDリスト。
     */
    public void setRelatedLexemes(List<String> relatedLexemes) {
        this.relatedLexemes = relatedLexemes;
    }

    /**
     * 単語の学習度を返却するGetterメソッドです。
     *
     * @return 単語の学習度。
     */
    public int getStrengthBars() {
        return this.strengthBars;
    }

    /**
     * 単語の学習度を設定するSetterメソッドです。
     *
     * @param strengthBars 単語の学習度。
     */
    public void setStrengthBars(int strengthBars) {
        this.strengthBars = strengthBars;
    }

    /**
     * 不定詞を返却するGetterメソッドです。
     *
     * @return 不定詞。
     */
    public String getInfinitive() {
        return this.infinitive;
    }

    /**
     * 不定詞を設定するSetterメソッドです。
     *
     * @param infinitive 不定詞。
     */
    public void setInfinitive(String infinitive) {
        this.infinitive = infinitive;
    }

    /**
     * 単語を返却するGetterメソッドです。
     *
     * @return 単語。
     */
    public String getWordString() {
        return this.wordString;
    }

    /**
     * 単語を設定するSetterメソッドです。
     *
     * @param wordString 単語
     */
    public void setWordString(String wordString) {
        this.wordString = wordString;
    }

    /**
     * 発音記号を除去した単語を返却するGetterメソッドです。
     *
     * @return 発音記号を除去した単語。
     */
    public String getNormalizedString() {
        return this.normalizedString;
    }

    /**
     * 発音記号を除去した単語を設定するSetterメソッドです。
     *
     * @param normalizedString 発音記号を除去した単語。
     */
    public void setNormalizedString(String normalizedString) {
        this.normalizedString = normalizedString;
    }

    /**
     * 位置を返却するGetterメソッドです。
     *
     * @return 位置。
     */
    public String getPos() {
        return this.pos;
    }

    /**
     * 位置を設定するSetterメソッドです。
     *
     * @param pos 位置。
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     * 最終学習時間（単位:ms）を返却するGetterメソッドです。
     *
     * @return 最終学習時間（単位:ms）。
     */
    public Long getLastPracticedMs() {
        return this.lastPracticedMs;
    }

    /**
     * 最終学習時間（単位:ms）を設定するSetterメソッドです。
     *
     * @param lastPracticedMs 最終学習時間（単位:ms）。
     */
    public void setLastPracticedMs(Long lastPracticedMs) {
        this.lastPracticedMs = lastPracticedMs;
    }

    /**
     * 単語が属するスキル名を返却するGetterメソッドです。
     *
     * @return 単語が属するスキル名。
     */
    public String getSkill() {
        return this.skill;
    }

    /**
     * 単語が属するスキル名を設定するSetterメソッドです。
     *
     * @param skill 単語が属するスキル名。
     */
    public void setSkill(String skill) {
        this.skill = skill;
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

    /**
     * 学習の強度を返却するGetterメソッドです。
     *
     * @return 学習の強度。
     */
    public Double getStrength() {
        return this.strength;
    }

    /**
     * 学習の強度を設定するSetterメソッドです。
     *
     * @param strength 学習の強度。
     */
    public void setStrength(Double strength) {
        this.strength = strength;
    }

    /**
     * URLで指定されるレッスン名を返却するGetterメソッドです。
     *
     * @return URLで指定されるレッスン名。
     */
    public String getSkillUrlTitle() {
        return this.skillUrlTitle;
    }

    /**
     * URLで指定されるレッスン名を設定するSetterメソッドです。
     *
     * @param skillUrlTitle URLで指定されるレッスン名。
     */
    public void setSkillUrlTitle(String skillUrlTitle) {
        this.skillUrlTitle = skillUrlTitle;
    }

    /**
     * 単語の性別を返却するGetterメソッドです。
     *
     * @return 単語の性別。
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * 単語の性別を設定するSetterメソッドです。
     *
     * @param gender 単語の性別。
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "OverviewHolder{" +
                "id='" + this.id + '\'' +
                ", userId='" + this.userId + '\'' +
                ", languageString='" + this.languageString + '\'' +
                ", language='" + this.language + '\'' +
                ", fromLanguage='" + this.fromLanguage + '\'' +
                ", lexemeId='" + this.lexemeId + '\'' +
                ", relatedLexemes=" + this.relatedLexemes +
                ", strengthBars=" + this.strengthBars +
                ", infinitive='" + this.infinitive + '\'' +
                ", wordString='" + this.wordString + '\'' +
                ", normalizedString='" + this.normalizedString + '\'' +
                ", pos='" + this.pos + '\'' +
                ", lastPracticedMs=" + this.lastPracticedMs +
                ", skill='" + this.skill + '\'' +
                ", lastPracticed='" + this.lastPracticed + '\'' +
                ", strength=" + this.strength +
                ", skillUrlTitle='" + this.skillUrlTitle + '\'' +
                ", gender='" + this.gender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        OverviewHolder that = (OverviewHolder) o;
        return this.getStrengthBars() == that.getStrengthBars() &&
                Objects.equals(this.getId(), that.getId()) &&
                Objects.equals(this.getUserId(), that.getUserId()) &&
                Objects.equals(this.getLanguageString(), that.getLanguageString()) &&
                Objects.equals(this.getLanguage(), that.getLanguage()) &&
                Objects.equals(this.getFromLanguage(), that.getFromLanguage()) &&
                Objects.equals(this.getLexemeId(), that.getLexemeId()) &&
                Objects.equals(this.getRelatedLexemes(), that.getRelatedLexemes()) &&
                Objects.equals(this.getInfinitive(), that.getInfinitive()) &&
                Objects.equals(this.getWordString(), that.getWordString()) &&
                Objects.equals(this.getNormalizedString(), that.getNormalizedString()) &&
                Objects.equals(this.getPos(), that.getPos()) &&
                Objects.equals(this.getLastPracticedMs(), that.getLastPracticedMs()) &&
                Objects.equals(this.getSkill(), that.getSkill()) &&
                Objects.equals(this.getLastPracticed(), that.getLastPracticed()) &&
                Objects.equals(this.getStrength(), that.getStrength()) &&
                Objects.equals(this.getSkillUrlTitle(), that.getSkillUrlTitle()) &&
                Objects.equals(this.getGender(), that.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.getId(),
                this.getUserId(),
                this.getLanguageString(),
                this.getLanguage(),
                this.getFromLanguage(),
                this.getLexemeId(),
                this.getRelatedLexemes(),
                this.getStrengthBars(),
                this.getInfinitive(),
                this.getWordString(),
                this.getNormalizedString(),
                this.getPos(),
                this.getLastPracticedMs(),
                this.getSkill(),
                this.getLastPracticed(),
                this.getStrength(),
                this.getSkillUrlTitle(),
                this.getGender());
    }
}
