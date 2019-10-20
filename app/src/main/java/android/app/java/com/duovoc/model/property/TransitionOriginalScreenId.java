package android.app.java.com.duovoc.model.property;

public enum TransitionOriginalScreenId {
    LoginActivity,
    OverviewActivity,
    DetailActivity,
    SettingsActivity;

    public String getScreenName() {
        return this.name();
    }
}
