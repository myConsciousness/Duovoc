package android.app.java.com.duovoc;

import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.MessageID;
import android.app.java.com.duovoc.framework.model.CurrentApplicationInformation;
import android.app.java.com.duovoc.framework.model.holder.CurrentApplicationHolder;
import android.app.java.com.duovoc.model.CurrentUserInformation;
import android.app.java.com.duovoc.model.UserInformation;
import android.app.java.com.duovoc.model.property.CurrentUserColumnKey;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.Switch;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : Settings.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 設定項目を出力するアクティビティです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
final public class SettingsActivity extends DuovocBaseActivity {

    /**
     * クラス名。
     */
    private static final String TAG = SettingsActivity.class.getSimpleName();

    /**
     * 当該クラスのコンストラクタです。
     */
    public SettingsActivity() {
        super(R.layout.activity_setting);
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

        this.initializeConnectWifiOnlySwitch();

        Logger.Info.write(TAG, methodName, "END");
    }

    private void initializeConnectWifiOnlySwitch() {

        final CurrentApplicationInformation currentApplicationInformation = SettingsActivity.super.getCurrentApplicationInformation();
        currentApplicationInformation.selectByPrimaryKey(CurrentApplicationInformation.ConfigName.UsesWifiOnCommunicate);

        if (currentApplicationInformation.isEmpty()) {
            // TODO: 業務エラー
            return;
        }

        final Switch switchConnectWifiOnly = this.findViewById(R.id.setting_general_list_switch);
        switchConnectWifiOnly.setChecked("1".equals(currentApplicationInformation.getConfigValue()));
    }

    @Override
    protected void setListeners() {
        final String methodName = "setListeners";
        Logger.Info.write(TAG, methodName, "START");

        final Switch switchConnectWifiOnly = this.findViewById(R.id.setting_general_list_switch);

        switchConnectWifiOnly.setOnCheckedChangeListener((compoundButton, isChecked) -> {

            final CurrentApplicationHolder currentApplicationHolder = new CurrentApplicationHolder();
            currentApplicationHolder.setConfigName(CurrentApplicationInformation.ConfigName.UsesWifiOnCommunicate);
            currentApplicationHolder.setConfigValue(isChecked ? "1" : "0");

            final CurrentApplicationInformation currentApplicationInformation = SettingsActivity.super.getCurrentApplicationInformation();
            currentApplicationInformation.replace(currentApplicationHolder);
        });

        final LinearLayout layoutRegisterUserInformation = this.findViewById(R.id.setting_layout_register_user_information);
        final LinearLayout layoutClearUserInformation = this.findViewById(R.id.setting_layout_clear_user_information);

        final CurrentUserInformation currentUserInformation = this.getCurrentUserInformation();
        currentUserInformation.selectAll();

        final String currentUserId = currentUserInformation.getModelInfo().get(0).getString(CurrentUserColumnKey.UserId);
        final UserInformation userInformation = this.getUserInformation();
        userInformation.selectByPrimaryKey(currentUserId);

        layoutRegisterUserInformation.setOnClickListener(view -> {
            if (userInformation.isEmpty()) {

            } else {
                this.showInformationToast(MessageID.IJP00001);
            }
        });

        layoutClearUserInformation.setOnClickListener(view -> {
            if (!userInformation.isEmpty()) {

            } else {
                this.showInformationToast(MessageID.IJP00001);
            }
        });

        Logger.Info.write(TAG, methodName, "END");
    }
}
