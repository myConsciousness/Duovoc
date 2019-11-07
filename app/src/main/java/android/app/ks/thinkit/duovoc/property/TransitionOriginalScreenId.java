package android.app.ks.thinkit.duovoc.property;

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
