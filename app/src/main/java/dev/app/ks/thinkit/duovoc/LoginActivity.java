package dev.app.ks.thinkit.duovoc;

import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import dev.app.ks.thinkit.duovoc.framework.BaseActivity;
import dev.app.ks.thinkit.duovoc.framework.CipherHandler;
import dev.app.ks.thinkit.duovoc.framework.Logger;
import dev.app.ks.thinkit.duovoc.framework.ModeType;
import dev.app.ks.thinkit.duovoc.framework.ModelList;
import dev.app.ks.thinkit.duovoc.framework.ModelMap;
import dev.app.ks.thinkit.duovoc.framework.PreferenceKey;
import dev.app.ks.thinkit.duovoc.framework.StringChecker;
import dev.app.ks.thinkit.duovoc.model.CurrentUserInformation;
import dev.app.ks.thinkit.duovoc.model.OverviewInformation;
import dev.app.ks.thinkit.duovoc.model.UserInformation;
import dev.app.ks.thinkit.duovoc.model.property.CurrentUserColumnKey;
import dev.app.ks.thinkit.duovoc.model.property.OverviewColumnKey;
import dev.app.ks.thinkit.duovoc.model.property.UserColumnKey;
import dev.app.ks.thinkit.duovoc.property.IntentExtraKey;
import dev.app.ks.thinkit.duovoc.property.MessageID;
import dev.app.ks.thinkit.duovoc.property.TransitionOriginalScreenId;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : LoginActivity.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * ログイン画面の表示処理を行うアクティビティです。
 * 当該画面では主に以下の機能を提供します。
 * <p>
 * 1, ログイン
 * 入力されたユーザ情報を基に認証APIを実行し、
 * 認証された場合はクッキーを保存し概要画面へ遷移させます。
 * <p>
 * 2, オフライン起動
 * オフラインモードで概要画面へ遷移させます。
 * オフラインモードでは全ての同期化処理が抑制されます。
 * アプリケーションの初回起動時には当該モードでの起動は抑制されます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see BaseActivity
 * @see DuovocBaseActivity
 * @see #authenticate(String, String, boolean)
 * @since 1.0
 */
public final class LoginActivity extends DuovocBaseActivity {

    /**
     * クラス名。
     */
    private static final String TAG = LoginActivity.class.getName();

    /**
     * 当該クラスのコンストラクタです。
     * ログイン画面のレイアウトを適用するために基底クラスへログイン画面のレイアウトを渡します。
     */
    public LoginActivity() {
        super(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.findItem(R.id.menu_sync_button).setVisible(false);
        menu.findItem(R.id.menu_switch_language).setVisible(false);
        menu.findItem(R.id.menu_learn_on_duolingo).setVisible(false);

        return true;
    }

    @Override
    protected void initializeView() {
        final String methodName = "initializeView";
        Logger.Info.write(TAG, methodName, "START");

        if (!BuildConfig.PAID) {
            super.displayBannerAdvertisement(R.id.loginAdViewTop);
            super.displayBannerAdvertisement(R.id.loginAdViewBottom);
        } else {
            this.removeBannerAdvertisement(
                    R.id.layout_login_scroll_view,
                    R.id.loginAdViewTop,
                    R.id.loginAdViewBottom);
        }

        final CurrentUserInformation currentUserInformation = this.getCurrentUserInformation();
        currentUserInformation.selectAll();

        if (!currentUserInformation.isEmpty()) {
            final ModelMap<CurrentUserColumnKey, Object> currentUserMap = currentUserInformation.getModelInfo().get(0);
            final String currentUserId = currentUserMap.getString(CurrentUserColumnKey.UserId);

            final UserInformation userInformation = this.getUserInformation();
            userInformation.selectByPrimaryKey(currentUserId);

            if (!userInformation.isEmpty()) {
                final String secretKey = super.getSharedPreference(PreferenceKey.SecretKey);

                if (StringChecker.isEffectiveString(secretKey)) {
                    final EditText editTextUserName = this.findViewById(R.id.userName);
                    final EditText editTextPassword = this.findViewById(R.id.loginPassword);

                    final ModelMap<UserColumnKey, Object> userMap = userInformation.getModelInfo().get(0);
                    final String userName = userMap.getString(UserColumnKey.LoginName);
                    final String password = userMap.getString(UserColumnKey.LoginPassword);

                    editTextUserName.setText(CipherHandler.decrypt(userName, secretKey));
                    editTextPassword.setText(CipherHandler.decrypt(password, secretKey));
                } else {
                    this.showInformationToast(MessageID.M00001);
                    return;
                }
            }

            Logger.Info.write(TAG, methodName, "END");
        }
    }

    @Override
    protected void setListeners() {
        final String methodName = "setListeners";
        Logger.Info.write(TAG, methodName, "START");

        final Button buttonSignIn = this.findViewById(R.id.signin);
        final Button buttonOffline = this.findViewById(R.id.offlineMode);
        final TextView textViewSignUp = this.findViewById(R.id.signup);
        final TextView textViewForgotPassword = this.findViewById(R.id.login_forgot_password);

        final EditText editTextUserName = this.findViewById(R.id.userName);
        final EditText editTextPassword = this.findViewById(R.id.loginPassword);

        buttonSignIn.setOnClickListener(view -> {
            final String userName = editTextUserName.getText().toString();
            final String password = editTextPassword.getText().toString();
            final CheckBox checkBoxRememberMe = this.findViewById(R.id.storeSignInInfo);

            LoginActivity.super.authenticate(userName, password, checkBoxRememberMe.isChecked());
        });

        buttonOffline.setOnClickListener(view -> this.offline());

        textViewSignUp.setOnClickListener(view -> {
            if (super.isActiveNetwork()) {
                // アカウント登録をさせるためにDuolingoホームページへ遷移させる
                final String URL_DUOLINGO = "https://www.duolingo.com/";
                final Uri parsedUrl = Uri.parse(URL_DUOLINGO);

                super.startActivityOnBrowser(parsedUrl);
            }
        });

        textViewForgotPassword.setOnClickListener(view -> {
            if (this.isActiveNetwork()) {
                // パスワード再設定画面へ遷移させる
                final String URL_FORGOT_PASSWORD = "https://www.duolingo.com/forgot_password";
                final Uri parsedUrl = Uri.parse(URL_FORGOT_PASSWORD);

                super.startActivityOnBrowser(parsedUrl);
            }
        });

        Logger.Info.write(TAG, methodName, "END");
    }

    @Override
    protected void startActivityOnPostAuthentication(final String userId) {
        final Map<String, String> extras = new HashMap<>();
        extras.put(IntentExtraKey.UserId.getKeyName(), userId);
        extras.put(IntentExtraKey.ViewTransferId.getKeyName(), TransitionOriginalScreenId.LoginActivity.getScreenName());

        super.startActivity(OverviewActivity.class, extras);
    }

    @Override
    public void onStart() {
        super.onStart();

        final TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);

        if (telephonyManager != null
                && "us".equals(telephonyManager.getSimCountryIso())
                && !StringChecker.isEffectiveString(super.getSharedPreference(PreferenceKey.AgeVerification))) {
            super.showCoppaConsentForm();
        }

        if (!BuildConfig.PAID) {
            if (!(telephonyManager != null
                    && "us".equals(telephonyManager.getSimCountryIso()))) {
                super.checkGeneralDataProtectionRegulation(this);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /*
             * イントロ画面へ遷移することを抑止するため、
             * 一覧画面から「戻る」ボタンが押下された場合は、
             * ホーム画面へ戻す処理を定義する。
             */
            final Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(homeIntent);
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * ログイン画面でオフラインボタンが押下された場合の処理を定義したメソッドです。
     * オフラインモードで概要画面を起動します。
     * <p>
     * 但し、カレントユーザ情報が存在しない場合は、
     * 一度もログイン後に同期化処理を行っていないため概要画面への遷移を抑止します。
     * <p>
     * また、概要画面へ遷移後に出力する情報が存在しない場合も、
     * 概要画面への遷移を抑止します。
     */
    private void offline() {
        final String methodName = "offline";
        Logger.Info.write(TAG, methodName, "START");

        final CurrentUserInformation currentUserInformation = this.getCurrentUserInformation();
        currentUserInformation.selectAll();

        if (currentUserInformation.isEmpty()) {
            // カレントユーザ情報が存在しない場合は、オフラインモードでの起動を抑止する。
            super.showInformationToast(MessageID.M00002);
            return;
        }

        final ModelMap<CurrentUserColumnKey, Object> modelMap = currentUserInformation.getModelInfo().get(0);
        final String currentUserId = modelMap.getString(CurrentUserColumnKey.UserId);
        final String currentLanguage = modelMap.getString(CurrentUserColumnKey.Language);
        final String currentFromLanguage = modelMap.getString(CurrentUserColumnKey.FromLanguage);

        if (this.isNotSynchronized(currentUserId, currentLanguage, currentFromLanguage)) {
            // 一覧画面でリストに表示する情報が存在しない場合は一覧画面への遷移を抑止する。
            super.showInformationToast(MessageID.M00003);
            return;
        }

        // オフラインモードに設定
        super.setModeType(ModeType.Offline);

        final Map<String, String> extras = new HashMap<>();
        extras.put(IntentExtraKey.UserId.getKeyName(), currentUserId);
        extras.put(IntentExtraKey.ViewTransferId.getKeyName(), TransitionOriginalScreenId.LoginActivity.getScreenName());

        Logger.Info.write(TAG, methodName, "END");
        super.startActivity(OverviewActivity.class, extras);
    }

    /**
     * ユーザが一度でも概要画面で同期化を行ったことがあるかどうかを判定する処理を定義したメソッドです。
     * カレントユーザ情報に紐付く概要情報が存在しない場合は未同期の状態とみなします。
     *
     * @param userId       カレントユーザ情報のユーザID。
     * @param language     カレントユーザ情報の言語区分。
     * @param fromLanguage カレントユーザ情報の学習時に使用している言語区分。
     * @return 同期済みの場合は {@code true}、それ以外は{@code false}
     */
    private boolean isNotSynchronized(final String userId, final String language, final String fromLanguage) {
        final String methodName = "isNotSynchronized";
        Logger.Info.write(TAG, methodName, "START");

        final OverviewInformation overviewInformation = OverviewInformation.getInstance(this);
        overviewInformation.selectByCurrentUserInformation(userId, language, fromLanguage);

        final ModelList<ModelMap<OverviewColumnKey, Object>> modelMaps = overviewInformation.getModelInfo();

        Logger.Info.write(TAG, methodName, "END");
        return modelMaps.isEmpty();
    }
}
