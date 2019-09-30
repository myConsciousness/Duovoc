package android.app.java.com.duovoc.holder;

final public class OverviewSingleRow {

    private long id = 0L;

    private String overviewId = "";

    private String word = "";

    private String normalizedWord = "";

    private String lessonName = "";

    private String lastPracticed = "";

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setOverviewId(String overviewId) {
        this.overviewId = overviewId;
    }

    public String getOverviewId() {
        return this.overviewId;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }

    public void setNormalizedWord(String notmalizedWord) {
        this.normalizedWord = normalizedWord;
    }

    public String getNormalizedWord() {
        return this.normalizedWord;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonName() {
        return this.lessonName;
    }

    public void setLastPracticed(String lastPracticed) {
        this.lastPracticed = lastPracticed;
    }

    public String getLastPracticed() {
        return this.lastPracticed;
    }
}
