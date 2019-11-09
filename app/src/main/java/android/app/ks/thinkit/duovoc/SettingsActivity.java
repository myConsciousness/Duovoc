package android.app.ks.thinkit.duovoc;

import android.app.AlertDialog;
import android.app.ks.thinkit.duovoc.framework.Logger;
import android.app.ks.thinkit.duovoc.framework.PreferenceKey;
import android.app.ks.thinkit.duovoc.framework.model.CurrentApplicationInformation;
import android.app.ks.thinkit.duovoc.framework.model.holder.CurrentApplicationHolder;
import android.app.ks.thinkit.duovoc.model.CurrentUserInformation;
import android.app.ks.thinkit.duovoc.model.UserInformation;
import android.app.ks.thinkit.duovoc.model.property.CurrentUserColumnKey;
import android.app.ks.thinkit.duovoc.property.IntentExtraKey;
import android.app.ks.thinkit.duovoc.property.MessageID;
import android.app.ks.thinkit.duovoc.property.TransitionOriginalScreenId;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
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
public final class SettingsActivity extends DuovocBaseActivity {

    /**
     * クラス名。
     */
    private static final String TAG = SettingsActivity.class.getName();

    /**
     * ユーザ情報クリアダイアログのオブジェクト。
     */
    private AlertDialog.Builder clearUserInformationDialog;

    /**
     * 当該クラスのコンストラクタです。
     */
    public SettingsActivity() {
        super(R.layout.activity_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!BuildConfig.PAID) {
            super.onCreateOptionsMenu(menu);
            menu.findItem(R.id.menu_sync_button).setVisible(false);
            menu.findItem(R.id.menu_switch_language).setVisible(false);
            menu.findItem(R.id.menu_learn_on_duolingo).setVisible(false);
            menu.findItem(R.id.menu_setting_button).setVisible(false);
        }

        return true;
    }

    @Override
    protected void initializeView() {
        final String methodName = "initializeView";
        Logger.Info.write(TAG, methodName, "START");

        if (!BuildConfig.PAID) {
            this.initializeInterstitialAd();
        }

        super.displayBackButtonOnActionBar();

        this.initializeConnectWifiOnlySwitch();
        this.toggleSettingItems();

        Logger.Info.write(TAG, methodName, "END");
    }

    private void initializeConnectWifiOnlySwitch() {

        final CurrentApplicationInformation currentApplicationInformation = SettingsActivity.super.getCurrentApplicationInformation();
        currentApplicationInformation.selectByPrimaryKey(CurrentApplicationInformation.ConfigName.UsesWifiOnCommunicate);

        if (currentApplicationInformation.isEmpty()) {
            this.showInformationToast(MessageID.M00023);
            this.finish();
            return;
        }

        final Switch switchConnectWifiOnly = this.findViewById(R.id.setting_general_list_switch);
        switchConnectWifiOnly.setChecked("1".equals(currentApplicationInformation.getConfigValue()));
    }

    private void toggleSettingItems() {

        final String screenId = this.getIntent().getStringExtra(IntentExtraKey.ViewTransferId.getKeyName());

        if (TransitionOriginalScreenId.LoginActivity.getScreenName().equals(screenId)) {
            final LinearLayout linearLayoutScrollView = this.findViewById(R.id.layout_settings_scroll_view);
            final LinearLayout linearLayoutUserInformation = this.findViewById(R.id.setting_layout_user_information);
            linearLayoutScrollView.removeView(linearLayoutUserInformation);

            final View viewLine = this.findViewById(R.id.line_horizontal_center);
            linearLayoutScrollView.removeView(viewLine);
        }
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

        final String screenId = this.getIntent().getStringExtra(IntentExtraKey.ViewTransferId.getKeyName());

        if (!TransitionOriginalScreenId.LoginActivity.getScreenName().equals(screenId)) {
            final LinearLayout layoutRegisterUserInformation = this.findViewById(R.id.setting_layout_register_user_information);
            final LinearLayout layoutClearUserInformation = this.findViewById(R.id.setting_layout_clear_user_information);

            final CurrentUserInformation currentUserInformation = this.getCurrentUserInformation();
            currentUserInformation.selectAll();

            final String currentUserId = currentUserInformation.getModelInfo().get(0).getString(CurrentUserColumnKey.UserId);
            final UserInformation userInformation = this.getUserInformation();
            userInformation.selectByPrimaryKey(currentUserId);

            layoutRegisterUserInformation.setOnClickListener(view -> {
                if (userInformation.isEmpty()) {
                    super.showAuthenticationDialog(true);
                } else {
                    this.showInformationToast(MessageID.M00024);
                }
            });

            layoutClearUserInformation.setOnClickListener(view -> {

                if (!userInformation.isEmpty()) {
                    if (this.clearUserInformationDialog == null) {
                        this.clearUserInformationDialog = new AlertDialog.Builder(this);
                        this.clearUserInformationDialog.setMessage("Are you sure want to clear user information?");

                        this.clearUserInformationDialog.setPositiveButton("Clear", (dialogInterface, i) -> {
                            userInformation.deleteByPrimaryKey(currentUserId);
                            super.saveSharedPreference(PreferenceKey.SecretKey, "");

                            // 再検索
                            userInformation.selectByPrimaryKey(currentUserId);
                            this.showInformationToast(MessageID.M00025);
                        });

                        this.clearUserInformationDialog.setNegativeButton("Cancel", (dialogInterface, i) -> {
                        });
                    }

                    this.clearUserInformationDialog.show();

                } else {
                    this.showInformationToast(MessageID.M00026);
                }
            });
        }

        Logger.Info.write(TAG, methodName, "END");
    }

    @Override
    protected void onPostAuthentication() {
        /*
         * 認証処理後に画面部品の状態をリフレッシュするために再検索を行う。
         */
        final CurrentUserInformation currentUserInformation = this.getCurrentUserInformation();
        currentUserInformation.selectAll();

        final String currentUserId = currentUserInformation.getModelInfo().get(0).getString(CurrentUserColumnKey.UserId);
        final UserInformation userInformation = this.getUserInformation();
        userInformation.selectByPrimaryKey(currentUserId);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!BuildConfig.PAID) {
                // インターステイシャル広告を表示
                super.showInterstitialAd();
            }

            this.finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
