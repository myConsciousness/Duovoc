package android.app.java.com.duovoc;

import android.app.java.com.duovoc.framework.model.adapter.DatabaseAdapter;
import android.os.Handler;

public final class IntroductionActivity extends DuovocBaseActivity {

    public IntroductionActivity() {
        super(R.layout.activity_introduction);
    }

    @Override
    protected void initializeView() {

        // 初回起動時のデータベース作成用
        final DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        databaseAdapter.initializeDatabase();

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
