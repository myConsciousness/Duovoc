package android.app.java.com.duovoc.holder;

import java.util.ArrayList;
import java.util.List;

final public class OverviewHolder {

    /** 単語情報の識別コード */
    private String id = "";

    /** ユーザに紐づくID */
    private String userId = "";

    /** 学習時に使用している言語の文字列 */
    private String languageString = "";

    /** 単語の言語区分 */
    private String language = "";

    /** 学習時に使用している言語区分 */
    private String fromLanguage  = "";

    /** 語彙素の識別コード */
    private String lexemeId = "";

    /** 語彙素の識別コードリスト */
    private List<String> relatedLexemes = new ArrayList<>();

    /** 単語の学習度 */
    private int strengthBars = 0;

    /** 不定詞 */
    private String infinitive = "";

    /** 単語 */
    private String wordString = "";

    /** 学習している単語に対応する英単語 */
    private String englishWord = "";

    /** 発音記号を除去した単語 */
    private String normalizedString = "";

    /** 位置 */
    private String pos = "";

    /** 最終学習時間（ms） */
    private Long lastPracticedMs = 0L;

    /** 単語が属するスキル名 */
    private String skill = "";

    /** 最終学習時間 */
    private String lastPracticed = "";

    /** 強度 */
    private Double strength = 0.0;

    /** レッスンのタイトル */
    private String skillUrlTitle = "";

    /** 単語の性 */
    private String gender = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLanguageString() {
        return languageString;
    }

    public void setLanguageString(String languageString) {
        this.languageString = languageString;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFromLanguage() {
        return fromLanguage;
    }

    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    public String getLexemeId() {
        return lexemeId;
    }

    public void setLexemeId(String lexemeId) {
        this.lexemeId = lexemeId;
    }

    public List<String> getRelatedLexemes() {
        return relatedLexemes;
    }

    public void setRelatedLexemes(List<String> relatedLexemes) {
        this.relatedLexemes = relatedLexemes;
    }

    public int getStrengthBars() {
        return strengthBars;
    }

    public void setStrengthBars(int strengthBars) {
        this.strengthBars = strengthBars;
    }

    public String getInfinitive() {
        return infinitive;
    }

    public void setInfinitive(String infinitive) {
        this.infinitive = infinitive;
    }

    public String getWordString() {
        return wordString;
    }

    public void setWordString(String wordString) {
        this.wordString = wordString;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getNormalizedString() {
        return normalizedString;
    }

    public void setNormalizedString(String normalizedString) {
        this.normalizedString = normalizedString;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Long getLastPracticedMs() {
        return lastPracticedMs;
    }

    public void setLastPracticedMs(Long lastPracticedMs) {
        this.lastPracticedMs = lastPracticedMs;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getLastPracticed() {
        return lastPracticed;
    }

    public void setLastPracticed(String lastPracticed) {
        this.lastPracticed = lastPracticed;
    }

    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
        this.strength = strength;
    }

    public String getSkillUrlTitle() {
        return skillUrlTitle;
    }

    public void setSkillUrlTitle(String skillUrlTitle) {
        this.skillUrlTitle = skillUrlTitle;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "OverviewHolder{" +
                "id='" + id + '\'' +
                ", lexemeId='" + lexemeId + '\'' +
                ", relatedLexemes=" + relatedLexemes +
                ", strengthBars=" + strengthBars +
                ", infinitive='" + infinitive + '\'' +
                ", wordString='" + wordString + '\'' +
                ", englishWord='" + englishWord + '\'' +
                ", normalizedString='" + normalizedString + '\'' +
                ", pos='" + pos + '\'' +
                ", lastPracticedMs=" + lastPracticedMs +
                ", skill='" + skill + '\'' +
                ", lastPracticed='" + lastPracticed + '\'' +
                ", strength=" + strength +
                ", skillUrlTitle='" + skillUrlTitle + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
