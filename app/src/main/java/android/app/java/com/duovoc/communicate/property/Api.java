package android.app.java.com.duovoc.communicate.property;

public enum Api {
    Login("https://www.duolingo.com/login"),
    Overview("https://www.duolingo.com/vocabulary/overview"),
    OverviewTranslation("https://d2.duolingo.com/words/hints/%s/%s");

    private String url;

    Api(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
