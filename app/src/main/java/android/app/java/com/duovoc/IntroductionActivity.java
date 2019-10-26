package android.app.java.com.duovoc;

import android.os.Handler;

public final class IntroductionActivity extends DuovocBaseActivity {

    public IntroductionActivity() {
        super(R.layout.activity_introduction);
    }

    @Override
    protected void initializeView() {
        new Handler().postDelayed(() -> {
            IntroductionActivity.this.startActivity(LoginActivity.class);
            IntroductionActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            IntroductionActivity.this.finish();
        }, 1500);
    }

    @Override
    protected void setListeners() {
    }
}
