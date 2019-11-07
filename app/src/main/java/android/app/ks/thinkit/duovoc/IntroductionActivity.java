package android.app.ks.thinkit.duovoc;

import android.app.ks.thinkit.duovoc.framework.model.adapter.DatabaseAdapter;
import android.os.Handler;
import android.view.Menu;

public final class IntroductionActivity extends DuovocBaseActivity {

    public IntroductionActivity() {
        super(R.layout.activity_introduction);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void initializeView() {

        // 初回起動時のデータベース作成処理
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
