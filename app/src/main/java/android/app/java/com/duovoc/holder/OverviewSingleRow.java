package android.app.java.com.duovoc.holder;

final public class OverviewSingleRow {

    private long id = 0L;

    private String overviewId = "";

    private String word = "";

    private String lessonName = "";

    private String lastPracticed = "";

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOverviewId() {
        return this.overviewId;
    }

    public void setOverviewId(String overviewId) {
        this.overviewId = overviewId;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLessonName() {
        return this.lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLastPracticed() {
        return this.lastPracticed;
    }

    public void setLastPracticed(String lastPracticed) {
        this.lastPracticed = lastPracticed;
    }
}
