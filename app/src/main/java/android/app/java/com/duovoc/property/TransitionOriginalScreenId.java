package android.app.java.com.duovoc.property;

public enum TransitionOriginalScreenId {
    DuovocBaseActivity,
    LoginActivity,
    OverviewActivity,
    DetailActivity,
    SettingsActivity;

    public String getScreenName() {
        return this.name();
    }
}
