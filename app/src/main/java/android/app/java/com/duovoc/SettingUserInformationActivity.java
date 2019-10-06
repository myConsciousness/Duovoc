package android.app.java.com.duovoc;

import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.Logger;
import android.view.Menu;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : SettingUserInformationActivity.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * ユーザ情報に関わる値の設定処理を行うアクティビティです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
final public class SettingUserInformationActivity extends BaseActivity {

    /**
     * クラス名。
     */
    private static final String TAG = SettingUserInformationActivity.class.getSimpleName();

    /**
     * 当該クラスのコンストラクタです。
     */
    public SettingUserInformationActivity() {
        super(R.layout.activity_setting_user_information);
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
