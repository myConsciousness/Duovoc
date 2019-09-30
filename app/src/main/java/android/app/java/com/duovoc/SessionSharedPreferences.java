package android.app.java.com.duovoc;

import android.app.Application;
import android.app.java.com.duovoc.framework.ModeType;

final public class SessionSharedPreferences extends Application {

    /** 処理モードを格納する共有情報。 */
    private ModeType modeType = ModeType.Offline;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ModeType getModeType() {
        return modeType;
    }

    public void setModeType(ModeType modeType) {
        this.modeType = modeType;
    }
}
