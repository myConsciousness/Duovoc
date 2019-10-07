package android.app.java.com.duovoc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.java.com.duovoc.communicate.HttpAsyncLogin;
import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.CipherHandler;
import android.app.java.com.duovoc.framework.CommunicationChecker;
import android.app.java.com.duovoc.framework.IPreferenceKey;
import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.MessageID;
import android.app.java.com.duovoc.framework.ModeType;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.PreferenceKey;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.holder.UserHolder;
import android.app.java.com.duovoc.model.CurrentUserInformation;
import android.app.java.com.duovoc.model.OverviewInformation;
import android.app.java.com.duovoc.model.OverviewTranslationInformation;
import android.app.java.com.duovoc.model.UserInformation;
import android.app.java.com.duovoc.model.property.UserColumnKey;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : DuovocBaseActivity.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * アプリ名「Duovoc」の基本的な振る舞いを定義した基底クラスです。
 * 各アクティビティは当該基底クラスを継承し、
 * 必要に応じて抽象メソッドを実装する必要があります。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see BaseActivity
 * @see BaseActivity#initializeView()
 * @see BaseActivity#setListeners()
 * @since 1.0
 */
public abstract class DuovocBaseActivity extends BaseActivity {

    /**
     * クラス名。
     */
    private static final String TAG = DuovocBaseActivity.class.getSimpleName();

    /**
     * 認証ダイアログのオブジェクト。
     */
    private AlertDialog authenticationDialog;

    /**
     * 当該基底クラスのコンストラクタ。
     * 当該基底クラスを継承した子クラスは必ず当該コンストラクタを実行する必要があります。
     *
     * @param activityLayout 出力する画面のレイアウト
     */
    protected DuovocBaseActivity(final int activityLayout) {
        super(activityLayout);
    }

    /**
     * 認証ダイアログオブジェクトを構築し画面上に出力します。
     *
     * @see #initializeAuthenticationDialog(View)
     * @see #setListenerAuthenticationDialog(View)
     */
    protected void buildAuthenticationDialog() {

        final View viewDialog = this.getLayoutInflater().inflate(R.layout.login_dialog, null);
        this.initializeAuthenticationDialog(viewDialog);

        if (this.authenticationDialog == null) {

            this.setListenerAuthenticationDialog(viewDialog);

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setView(viewDialog);
            this.authenticationDialog = dialogBuilder.create();
        }

        this.authenticationDialog.show();
    }

    /**
     * 認証ダイアログの画面上に出力する情報を初期化します。
     *
     * @param viewDialog 認証ダイアログのオブジェクト。
     * @see UserInformation
     * @see UserInformation#selectAll()
     * @see #getSharedPreference(IPreferenceKey)
     */
    private void initializeAuthenticationDialog(final View viewDialog) {

        final UserInformation userInformation = this.getUserInformation(this);

        userInformation.selectAll();
        final ModelMap<UserColumnKey, Object> modelMap = userInformation.getModelInfo();

        if (!modelMap.isEmpty()) {

            final String secretKey = this.getSharedPreference(PreferenceKey.SecretKey);

            if (StringChecker.isEffectiveString(secretKey)) {
                final String userName = modelMap.getString(UserColumnKey.LoginName);
                final String password = modelMap.getString(UserColumnKey.LoginPassword);

                final EditText editTextUserName = viewDialog.findViewById(R.id.dialog_user_name);
                final EditText editTextPassword = viewDialog.findViewById(R.id.dialog_password);
                editTextUserName.setText(CipherHandler.decrypt(userName, secretKey));
                editTextPassword.setText(CipherHandler.decrypt(password, secretKey));

            } else {
                /** TODO: メッセージ出力 */
                this.showInformationToast(MessageID.IJP00008);
            }
        }
    }

    /**
     * 認証ダイアログの各部品にイベントをバインドします。
     * 当該処理は認証ダイアログを初回起動した時に実行されます。
     *
     * @param viewDialog 認証ダイアログのオブジェクト。
     */
    private void setListenerAuthenticationDialog(final View viewDialog) {

        final Button buttonSignIn = viewDialog.findViewById(R.id.dialog_button_signin);
        final TextView textViewForgotPassword = viewDialog.findViewById(R.id.dialog_forgot_password);

        buttonSignIn.setOnClickListener(view -> this.authentication(viewDialog));

        textViewForgotPassword.setOnClickListener(view -> {

            if (this.isActiveNetwork()) {
                // パスワード再設定画面へ遷移させる
                final String URL_FORGOT_PASSWORD = "https://www.duolingo.com/forgot_password";
                final Uri parsedUrl = Uri.parse(URL_FORGOT_PASSWORD);

                final Intent intent = new Intent(Intent.ACTION_VIEW, parsedUrl);
                this.startActivity(intent);
            }
        });
    }

    /**
     * 入力情報を基に非同期の認証処理を実行します。
     * 以下のエラーが発生した場合はエラーメッセージを出力して当該メソッドの処理を終了します。
     * 1, 入力エラー（必須チェック）
     * 2, 接続エラー（ネットワーク接続不備）
     * 3, 認証エラー（ログイン情報の入力誤り）
     * <p>
     * 認証に成功した場合はメッセージを出力しダイアログを閉じます。
     *
     * @param viewDialog 認証ダイアログのオブジェクト。
     * @see HttpAsyncLogin
     * @see CommunicationChecker#isOnline(Context)
     * @see CommunicationChecker#isWifiConnected(Context)
     */
    private void authentication(final View viewDialog) {

        final EditText editTextUserName = viewDialog.findViewById(R.id.dialog_user_name);
        final EditText editTextPassword = viewDialog.findViewById(R.id.dialog_password);
        final String userName = editTextUserName.getText().toString();
        final String password = editTextPassword.getText().toString();

        if (!StringChecker.isEffectiveString(userName)
                || !StringChecker.isEffectiveString(password)) {
            /** TODO: メッセージID */
            this.showInformationToast(MessageID.IJP00001);
            return;
        }

        if (!this.isActiveNetwork()) {
            this.showInformationToast(MessageID.IJP00006);
            return;
        }

        if (!this.isActiveWifiNetwork()) {
            this.showInformationToast(MessageID.IJP00007);
            return;
        }

        this.setCookie();

        final CheckBox checkBoxStoreSignInInfo = viewDialog.findViewById(R.id.dialog_remember_me);

        @SuppressLint("StaticFieldLeak") final HttpAsyncLogin asyncLogin = new HttpAsyncLogin() {

            private static final String RESPONSE_CODE_OK = "OK";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                if (checkBoxStoreSignInInfo.isChecked()) {

                    final UserInformation userInformation
                            = DuovocBaseActivity.this.getUserInformation(DuovocBaseActivity.this);

                    // 過去に永続化されたユーザ情報を削除する。
                    userInformation.clear();
                }

                DuovocBaseActivity.this.showSpinnerDialog("Certifying", "Waiting for response...");
            }

            @Override
            protected void onPostExecute(final UserHolder userHolder) {
                super.onPostExecute(userHolder);

                final String methodName = "onPostExecute";
                Logger.Info.write(TAG, methodName, "START");

                try {

                    if (!RESPONSE_CODE_OK.equals(userHolder.getResponse())) {
                        DuovocBaseActivity.this.showInformationToast(MessageID.IJP00003);
                        Logger.Debug.write(TAG, methodName, "レスポンスコード = (%s)", userHolder.getResponse());
                        return;
                    }

                    if (checkBoxStoreSignInInfo.isChecked()) {

                        // 入力されたログイン情報はここで暗号化して設定する
                        final String secretKey = CipherHandler.generateSecretKey();
                        userHolder.setLoginName(CipherHandler.encrypt(userName, secretKey));
                        userHolder.setLoginPassword(CipherHandler.encrypt(password, secretKey));

                        final UserInformation userInformation
                                = DuovocBaseActivity.this.getUserInformation(DuovocBaseActivity.this);

                        if (!userInformation.insert(userHolder)) {
                            // should not be happened
                            DuovocBaseActivity.this.showInformationToast(MessageID.IJP00004);
                            Logger.Error.write(TAG, methodName, "ユーザ情報 : (%s)", userHolder.toString());
                            return;
                        }

                        /*
                         * 秘密鍵を共有情報へ保存する。
                         * 前回分の秘密鍵が存在する場合は値を上書きする。
                         */
                        DuovocBaseActivity.this.saveSharedPreference(PreferenceKey.SecretKey, secretKey);
                    }
                } finally {
                    DuovocBaseActivity.this.dismissDialog();
                }

                // オンラインモードに設定
                DuovocBaseActivity.this.setModeType(ModeType.Online);

                // TODO: 完了メッセージ
                DuovocBaseActivity.this.showInformationToast(MessageID.IJP00008);
                DuovocBaseActivity.this.authenticationDialog.dismiss();

                Logger.Info.write(TAG, methodName, "END");
            }
        };

        asyncLogin.execute(userName, password);
    }

    /**
     * 論理モデル名「ユーザ情報」のオブジェクトを返却します。
     * ユーザ情報はシングルトンオブジェクトです。
     *
     * @return ユーザ情報のモデルオブジェクト。
     * @see UserInformation
     */
    final protected UserInformation getUserInformation(final Context context) {
        return UserInformation.getInstance(context);
    }

    /**
     * 論理モデル名「カレントユーザ情報」のオブジェクトを返却します。
     * カレントユーザ情報はシングルトンオブジェクトです。
     *
     * @return カレントユーザ情報のモデルオブジェクト。
     * @see CurrentUserInformation
     */
    final protected CurrentUserInformation getCurrentUserInformation(final Context context) {
        return CurrentUserInformation.getInstance(context);
    }

    /**
     * 論理モデル名「概要情報」のオブジェクトを返却します。
     * 概要情報はシングルトンオブジェクトです。
     *
     * @return 概要情報のモデルオブジェクト。
     * @see OverviewInformation
     */
    final protected OverviewInformation getOverviewInformation(final Context context) {
        return OverviewInformation.getInstance(context);
    }

    /**
     * 論理モデル名「概要翻訳情報」のオブジェクトを返却します。
     * 概要翻訳情報はシングルトンオブジェクトです。
     *
     * @return 概要翻訳情報のモデルオブジェクト。
     * @see OverviewTranslationInformation
     */
    final protected OverviewTranslationInformation getOverviewTranslationInformation(final Context context) {
        return OverviewTranslationInformation.getInstance(context);
    }
}