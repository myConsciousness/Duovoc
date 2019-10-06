package android.app.java.com.duovoc;

import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.Logger;
import android.view.Menu;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : SettingGeneral.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * アプリケーション情報に関わる総合的な値を設定するアクティビティです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
final public class SettingGeneralActivity extends BaseActivity {

    /**
     * クラス名。
     */
    private static final String TAG = SettingGeneralActivity.class.getSimpleName();

    /**
     * 当該クラスのコンストラクタです。
     */
    public SettingGeneralActivity() {
        super(R.layout.activity_setting_general);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 設定画面ではメニューを無効化
        return true;
    }

    @Override
    protected void initializeView() {
        final String methodName = "initializeView";
        Logger.Info.write(TAG, methodName, "START");
        Logger.Info.write(TAG, methodName, "END");
    }

    @Override
    protected void setListeners() {
        final String methodName = "setListeners";
        Logger.Info.write(TAG, methodName, "START");
        Logger.Info.write(TAG, methodName, "END");
    }
}
