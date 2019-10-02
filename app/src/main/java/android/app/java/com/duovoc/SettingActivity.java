package android.app.java.com.duovoc;

import android.app.java.com.duovoc.framework.BaseActivity;
import android.view.Menu;

final public class SettingActivity extends BaseActivity {

    private static final String TAG = SettingActivity.class.getSimpleName();

    public SettingActivity() {
        super(R.layout.activity_setting);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 設定画面ではメニューを無効化
        return true;
    }

    @Override
    protected void initializeView() {
    }

    @Override
    protected void setListeners() {
    }
}
