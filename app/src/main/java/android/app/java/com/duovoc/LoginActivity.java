package android.app.java.com.duovoc;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.communicate.HttpAsyncLogin;
import android.app.java.com.duovoc.communicate.HttpAsyncOverview;
import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.CipherHandler;
import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.MessageID;
import android.app.java.com.duovoc.framework.ModeType;
import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.PreferenceKey;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.model.CurrentUserInformation;
import android.app.java.com.duovoc.model.OverviewInformation;
import android.app.java.com.duovoc.model.UserInformation;
import android.app.java.com.duovoc.model.holder.UserHolder;
import android.app.java.com.duovoc.model.property.CurrentUserColumnKey;
import android.app.java.com.duovoc.model.property.OverviewColumnKey;
import android.app.java.com.duovoc.model.property.UserColumnKey;
import android.app.java.com.duovoc.property.IntentExtraKey;
import android.app.java.com.duovoc.property.TransitionOriginalScreenId;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

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
 * また、ユーザの認証処理を行う際に非同期処理を行います。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see BaseActivity
 * @see DuovocBaseActivity
 * @see HttpAsyncOverview
 * @since 1.0
 */
final public class LoginActivity extends DuovocBaseActivity {

    /**
     * クラス名。
     */
    private static final String TAG = LoginActivity.class.getSimpleName();

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

        final MenuItem syncButton = menu.findItem(R.id.menu_sync_button);
        final MenuItem switchLanguageButton = menu.findItem(R.id.menu_switch_language);
        syncButton.setVisible(false);
        switchLanguageButton.setVisible(false);

        return true;
    }

    @Override
    protected void initializeView() {
        final String methodName = "initializeView";
        Logger.Info.write(TAG, methodName, "START");

        super.setTitle(R.string.title_activity_login);

        final UserInformation userInformation = UserInformation.getInstance(this);
        userInformation.selectAll();

        final ModelMap<UserColumnKey, Object> modelMap = userInformation.getModelInfo();

        if (!modelMap.isEmpty()) {

            final String secretKey = super.getSharedPreference(PreferenceKey.SecretKey);

            if (StringChecker.isEffectiveString(secretKey)) {
                final EditText editTextUserName = this.findViewById(R.id.userName);
                final EditText editTextPassword = this.findViewById(R.id.loginPassword);
                final String userName = modelMap.getString(UserColumnKey.LoginName);
                final String password = modelMap.getString(UserColumnKey.LoginPassword);

                editTextUserName.setText(CipherHandler.decrypt(userName, secretKey));
                editTextPassword.setText(CipherHandler.decrypt(password, secretKey));

            } else {
                /** TODO: メッセージ出力 */
                super.showInformationToast(MessageID.IJP00008);
            }
        }

        Logger.Info.write(TAG, methodName, "END");
    }

    @Override
    protected void setListeners() {
        final String methodName = "setListeners";
        Logger.Info.write(TAG, methodName, "START");

        final Button buttonSignIn = this.findViewById(R.id.signin);
        final Button buttonOffline = this.findViewById(R.id.offlineMode);
        final TextView textViewSignUp = this.findViewById(R.id.signup);
        final TextView textViewForgotPassword = this.findViewById(R.id.login_forgot_password);

        buttonSignIn.setOnClickListener(view -> {

            final EditText editTextUserName = this.findViewById(R.id.userName);
            final EditText editTextPassword = this.findViewById(R.id.loginPassword);

            LoginActivity.this.signIn(editTextUserName.getText().toString(), editTextPassword.getText().toString());
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

    /**
     * ログイン画面のユーザ認証処理を定義したメソッドです。
     * 当該メソッドはログイン画面でサインインボタンが押下された場合に処理を行います。
     * <p>
     * 同期化処理はバックグラウンド上で行い、
     * 処理中はキャンセル不可なプログレスダイアログを画面上に出力します。
     * <p>
     * 認証処理に成功した場合は概要画面へ遷移します。
     * また、チェックボックスが選択されていた場合は、
     * 認証処理成功時に以下のユーザ情報をモデルへ保存します。
     * 1, ユーザID
     * 2, ログイン時ユーザ名
     * 3, ログイン時パスワード（暗号化）
     * <p>
     * 以下の場合は認証処理を行うことができません。
     * 1, ネットワーク接続が行われていない場合。
     * 2, Wifi接続時のみ同期化処理を行う設定にしている際にWifi接続が行われていない場合。
     * <p>
     * 上記の2パターンの何れの場合も対応したメッセージを出力して当該メソッド処理を終了します。
     */
    private void signIn(final String userName, final String password) {
        final String methodName = "signIn";
        Logger.Info.write(TAG, methodName, "START");

        if (!StringChecker.isEffectiveString(userName)
                || !StringChecker.isEffectiveString(password)) {
            super.showInformationToast(MessageID.IJP00002);
            return;
        }

        if (!super.isActiveNetwork()) {
            this.showInformationToast(MessageID.IJP00006);
            return;
        }

        if (!super.isActiveWifiNetwork()) {
            this.showInformationToast(MessageID.IJP00007);
            return;
        }

        super.setCookie();

        final CheckBox checkBoxStoreSignInInfo = this.findViewById(R.id.storeSignInInfo);

        @SuppressLint("StaticFieldLeak") final HttpAsyncLogin asyncLogin = new HttpAsyncLogin() {

            private static final String RESPONSE_CODE_OK = "OK";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                if (checkBoxStoreSignInInfo.isChecked()) {
                    final UserInformation userInformation
                            = LoginActivity.super.getUserInformation();

                    // 過去に永続化されたユーザ情報を削除する。
                    userInformation.clear();
                }

                LoginActivity.super.showSpinnerDialog("Certifying", "Waiting for response...");
            }

            @Override
            protected void onPostExecute(final UserHolder userHolder) {
                super.onPostExecute(userHolder);

                final String methodName = "onPostExecute";
                Logger.Info.write(TAG, methodName, "START");

                if (!RESPONSE_CODE_OK.equals(userHolder.getResponse())) {
                    LoginActivity.super.dismissDialog();
                    LoginActivity.super.showInformationToast(MessageID.IJP00003);
                    Logger.Debug.write(TAG, methodName, "レスポンスコード = (%s)", userHolder.getResponse());
                    return;
                }

                if (checkBoxStoreSignInInfo.isChecked()) {
                    // 入力されたログイン情報はここで暗号化して設定する
                    final String secretKey = CipherHandler.generateSecretKey();
                    userHolder.setLoginName(CipherHandler.encrypt(userName, secretKey));
                    userHolder.setLoginPassword(CipherHandler.encrypt(password, secretKey));

                    final UserInformation userInformation
                            = LoginActivity.super.getUserInformation();

                    if (!userInformation.insert(userHolder)) {
                        // should not be happened
                        LoginActivity.super.dismissDialog();
                        LoginActivity.super.showInformationToast(MessageID.IJP00004);
                        Logger.Error.write(TAG, methodName, "ユーザ情報 : (%s)", userHolder.toString());
                        return;
                    }

                    /*
                     * 秘密鍵を共有情報へ保存する。
                     * 前回分の秘密鍵が存在する場合は値を上書きする。
                     */
                    LoginActivity.super.saveSharedPreference(PreferenceKey.SecretKey, secretKey);
                }

                // オンラインモードに設定
                LoginActivity.super.setModeType(ModeType.Online);

                final Map<String, String> extras = new HashMap<>();
                extras.put(IntentExtraKey.UserId.getKeyName(), userHolder.getUserId());
                extras.put(IntentExtraKey.ViewTransferId.getKeyName(), TransitionOriginalScreenId.LoginActivity.getScreenName());

                Logger.Info.write(TAG, methodName, "END");
                LoginActivity.super.dismissDialog();
                LoginActivity.super.startActivity(OverviewActivity.class, extras);
            }
        };

        asyncLogin.execute(userName, password);
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

        if (currentUserInformation.getModelInfo().isEmpty()) {
            /** TODO: メッセージID */
            // カレントユーザ情報が存在しない場合は、オフラインモードでの起動を抑止する。
            super.showInformationToast(MessageID.IJP00008);
            return;
        }

        final ModelMap<CurrentUserColumnKey, Object> modelMap = currentUserInformation.getModelInfo();
        final String currentUserId = modelMap.getString(CurrentUserColumnKey.UserId);
        final String currentLanguage = modelMap.getString(CurrentUserColumnKey.Language);
        final String currentFromLanguage = modelMap.getString(CurrentUserColumnKey.FromLanguage);

        if (this.isNotSynchronized(currentUserId, currentLanguage, currentFromLanguage)) {
            /*
             * 一覧画面でリストに表示する情報が存在しない場合は、
             * 一覧画面への遷移を抑止する。
             */
            super.showInformationToast(MessageID.IJP00005);
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
