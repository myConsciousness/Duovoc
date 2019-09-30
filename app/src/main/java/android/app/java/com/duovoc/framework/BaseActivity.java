package android.app.java.com.duovoc.framework;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.java.com.duovoc.R;
import android.app.java.com.duovoc.SessionSharedPreferences;
import android.app.java.com.duovoc.communicate.HttpAsyncLogin;
import android.app.java.com.duovoc.holder.UserHolder;
import android.app.java.com.duovoc.model.CurrentApplicationInformation;
import android.app.java.com.duovoc.model.MasterMessageInformation;
import android.app.java.com.duovoc.model.UserInformation;
import android.app.java.com.duovoc.model.property.UserColumnKey;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : BaseActivity.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 *
 * アクティビティの基本的な振る舞いを定義した基底クラスです。
 * 各アクティビティは当該基底クラスを継承し、
 * 必要に応じて抽象メソッドを実装する必要があります。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see #initializeView()
 * @see #setListeners()
 * @since 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 定数 : クラス名を保持する。
     */
    private static final String TAG = BaseActivity.class.getSimpleName();

    /**
     * 定数 : 画面レイアウトのIDを保持する。
     */
    private final int activityLayout;

    /**
     * 定数 : モデル「ユーザ情報」のオブジェクトを保持する。
     */
    private final UserInformation userInformation;

    /**
     * 定数 : マスタモデル「メッセージ情報マスタ」のオブジェクトを保持する。
     */
    private final MasterMessageInformation masterMessageInformation;

    /**
     * 定数 : モデル「カレントアプリケーション情報」のオブジェクトを保持する。
     */
    private final CurrentApplicationInformation currentApplicationInformation;

    /**
     * 定数 : プログレスダイアログを操作するオブジェクトを保持する。
     */
    private final ProgressDialogHandler progressDialogHandler;

    /**
     * 変数 : 共有情報へアクセスするためのオブジェクトを保持する。
     */
    private SharedPreferences sharedPreferences;

    /**
     * 変数 : セッション共有情報へアクセスするためのオブジェクトを保持する。
     */
    private SessionSharedPreferences sessionSharedPreferences;

    /**
     * 変数 : サインインダイアログのオブジェクトを保持する。
     */
    private AlertDialog signinDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final int itemId = item.getItemId();

        if (itemId == R.id.menuSettingButton) {

        }

        return true;
    }

    /**
     * 当該基底クラスのコンストラクタ。
     * 当該基底クラスを継承した子クラスは必ず当該コンストラクタを実行する必要があります。
     *
     * @param activityLayout 出力する画面のレイアウト
     */
    protected BaseActivity(final int activityLayout) {
        this.activityLayout = activityLayout;
        this.userInformation = UserInformation.getInstance(this);
        this.masterMessageInformation = MasterMessageInformation.getInstance(this);
        this.currentApplicationInformation = CurrentApplicationInformation.getInstance(this);
        this.progressDialogHandler = new ProgressDialogHandler(this);
    }

    /**
     * 画面表示の初期化を行う抽象メソッド。
     * 当該基底クラスを継承した子クラスが画面の初期化を行いたい場合、
     * 当該メソッドをオーバーライドして実装する必要があります。
     * 実装された当該抽象メソッドは当該基底クラスのonCreateメソッドで実行されます。
     *
     * @see #onCreate(Bundle)
     */
    protected abstract void initializeView();

    /**
     * リスナーの設定処理を行う抽象メソッド。
     * 当該基底クラスを継承した子クラスがリスナーの設定処理を行いたい場合、
     * 当該メソッドをオーバーライドして実装する必要があります。
     * 実装された当該抽象メソッドは当該来てクラスのonCreateメソッドで実行されます。
     *
     * @see #onCreate(Bundle)
     */
    protected abstract void setListeners();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.activityLayout);

        final String methodName = "onCreate";
        Logger.Info.write(TAG, methodName, "START");

        // Activityが生成されてからインスタンスを取得する
        this.sharedPreferences = getSharedPreferences(this.getDefaultSharedPreferencesName(this), MODE_PRIVATE);
        this.sessionSharedPreferences = (SessionSharedPreferences) this.getApplication();

        this.initializeView();
        this.setListeners();

        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * 共有情報のデフォルトファイル名を返却します。
     *
     * @param context アプリケーション情報。
     * @return デフォルトファイル名。
     */
    private String getDefaultSharedPreferencesName(final Context context) {
        return context.getPackageName() + "_preferences";
    }

    /**
     * 引数として渡されたメッセージIDを基にメッセージ付きのトーストを下部に表示します。
     * メッセージIDからメッセージへの変換は当該メソッド内で行われます。
     *
     * @param messageId 出力メッセージに紐づくユニークな値
     * @see MasterMessageInformation#searchMasterByPrimaryKey(String)
     * @see MasterMessageInformation#getMessage()
     */
    final protected void showInformationToast(final MessageID messageId) {

        this.masterMessageInformation.searchMasterByPrimaryKey(messageId.getMessageId());
        Toast.makeText(this, masterMessageInformation.getMessage(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 引数として渡されたタイトルとメッセージを基にプログレスダイアログを表示します。
     * ダイアログの表示が不要になった際には必ず{@code dismissDialog()}を実行してください。
     *
     * @param title   ダイアログに出力する題名
     * @param message ダイアログに出力するメッセージ
     * @see #dismissDialog()
     */
    final protected void showSpinnerDialog(final String title, final String message) {
        this.progressDialogHandler.showSpinnerDialog(title, message);
    }

    /**
     * 表示されているダイアログの消去処理を行います。
     * ダイアログの表示を行った後には当該メソッドが必ず実行されるようにしてください。
     *
     * @see #showSpinnerDialog(String, String)
     */
    final protected void dismissDialog() {
        this.progressDialogHandler.dismissDialog();
    }

    /**
     * キーを基に共有情報の保存処理を行います。
     * 保存情報は外部の定義体内で管理されます。
     *
     * @param key   保存する値に紐付くキー。
     * @param value 保存する値。
     * @see IPreferenceKey
     */
    final protected void saveSharedPreference(final IPreferenceKey key, final String value) {
        final SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key.getKeyName(), value).apply();
    }

    /**
     * キーを基に共有情報から値を取得し返却します。
     * キーに紐付く情報が存在しない場合は空文字列を返却します。
     *
     * @param key 保存情報に紐付くキー。
     * @see IPreferenceKey
     */
    final protected String getSharedPreference(final IPreferenceKey key) {
        return this.sharedPreferences.getString(key.getKeyName(), "");
    }

    /**
     * 入力情報を基にカレントアプリケーション情報を参照し、
     * 入力に紐付くコンフィグ値を返却します。
     * 検索処理においてエラーが発生した場合は空文字列を返却します。
     *
     * @param config 取得対象の値に紐付くコンフィグ名
     * @return 検索処理が正常終了した場合はコンフィグ名に紐付くコンフィグ値、それ以外は空文字列。
     * @see CurrentApplicationInformation
     * @see CurrentApplicationInformation#selectByPrimaryKey(String)
     */
    final protected String getConfigValue(final CurrentApplicationInformation.ConfigName config) {

        if (!this.currentApplicationInformation.selectByPrimaryKey(config.getConfigName())) {
            return "";
        }

        return this.currentApplicationInformation.getConfigValue();
    }

    /**
     * 文字列を真偽値へ変換します。
     * 変換対象は以下の値です。
     * <p>
     * 1, "1" : {@code true}
     * 2, 上記以外 : {@code false}
     *
     * @param value 変換対象の値
     * @return 入力値が"1"の場合は{@code true}, それ以外は{@code false}。
     */
    final protected boolean convertToBoolean(final String value) {
        return "1".equals(value);
    }

    /**
     * セッション共有情報から処理モードを参照し、
     * アプリケーションの処理モードを判定します。
     * 処理モードは初期値でオフラインモードに設定されているため、
     * 必要に応じて適宜オンラインモードへ変更する必要があります。
     *
     * @return オンラインモードの場合は {@code true}、それ以外の場合は{@code false}
     * @see #sessionSharedPreferences
     */
    final protected boolean isOnlineMode() {
        return this.sessionSharedPreferences.getModeType() == ModeType.Online;
    }

    /**
     * セッション共有情報へ処理モードを設定します。
     *
     * @see #isOnlineMode()
     */
    final protected void setModeType(final ModeType modeType) {
        this.sessionSharedPreferences.setModeType(modeType);
    }

    /**
     * クッキー情報を設定します。
     *
     * @see CookieManager
     * @see CookieManager#setCookiePolicy(CookiePolicy)
     * @see CookieManager#setDefault(CookieHandler)
     */
    protected void setCookie() {
        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
    }

    /**
     * サインインダイアログオブジェクトを構築し画面上に出力します。
     *
     * @see #initializeViewSigninDialog(View)
     * @see #setListenerSigninDialog(View)
     */
    protected void buildSigninDialog() {

        final View viewDialog = getLayoutInflater().inflate(R.layout.login_dialog, null);
        this.initializeViewSigninDialog(viewDialog);

        if (this.signinDialog == null) {

            this.setListenerSigninDialog(viewDialog);

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setView(viewDialog);
            this.signinDialog = dialogBuilder.create();
        }

        this.signinDialog.show();
    }

    /**
     * サインインダイアログの画面上に出力する情報を初期化します。
     *
     * @param viewDialog サインインダイアログのオブジェクト。
     * @see UserInformation
     * @see UserInformation#selectAll()
     * @see #getSharedPreference(IPreferenceKey)
     */
    private void initializeViewSigninDialog(final View viewDialog) {

        this.userInformation.selectAll();
        final ModelMap<UserColumnKey, Object> modelMap = this.userInformation.getModelInfo();

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
     * サインインダイアログの各部品にイベントをバインドします。
     * 当該処理はサインダイアログを初回起動した時に実行されます。
     *
     * @param viewDialog サインインダイアログのオブジェクト。
     */
    private void setListenerSigninDialog(final View viewDialog) {

        final Button buttonSignIn = viewDialog.findViewById(R.id.dialog_button_signin);
        final TextView textViewForgotPassword = viewDialog.findViewById(R.id.dialog_forgot_password);

        buttonSignIn.setOnClickListener(view -> this.signin(viewDialog));

        textViewForgotPassword.setOnClickListener(view -> {

            // パスワード再設定画面へ遷移させる
            final String URL_FORGOT_PASSWORD = "https://www.duolingo.com/forgot_password";
            final Uri parsedUrl = Uri.parse(URL_FORGOT_PASSWORD);

            final Intent intent = new Intent(Intent.ACTION_VIEW, parsedUrl);
            startActivity(intent);
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
     * @param viewDialog サインインダイアログのオブジェクト。
     * @see HttpAsyncLogin
     * @see CommunicationChecker#isOnline(Context)
     * @see CommunicationChecker#isWifiConnected(Context)
     */
    private void signin(final View viewDialog) {

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

        final String configValue = this.getConfigValue(CurrentApplicationInformation.ConfigName.UsesWifiOnCommunicate);
        final boolean convertedConfigValue = this.convertToBoolean(configValue);

        if (!CommunicationChecker.isOnline(this)
                || (convertedConfigValue && !CommunicationChecker.isWifiConnected(this))) {
            /** TODO: メッセージID */
            this.showInformationToast(MessageID.IJP00002);
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
                    // 過去に永続化されたユーザ情報を削除する。
                    BaseActivity.this.userInformation.clear();
                }

                BaseActivity.this.showSpinnerDialog("Authorizing", "Waiting for response...");
            }

            @Override
            protected void onPostExecute(final UserHolder userHolder) {
                super.onPostExecute(userHolder);

                final String methodName = "onPostExecute";
                Logger.Info.write(TAG, methodName, "START");

                try {

                    if (!RESPONSE_CODE_OK.equals(userHolder.getResponse())) {
                        BaseActivity.this.showInformationToast(MessageID.IJP00003);
                        Logger.Debug.write(TAG, methodName, "レスポンスコード = (%s)", userHolder.getResponse());
                        return;
                    }

                    if (checkBoxStoreSignInInfo.isChecked()) {

                        // 入力されたログイン情報はここで暗号化して設定する
                        final String secretKey = CipherHandler.generateSecretKey();
                        userHolder.setLoginName(CipherHandler.encrypt(userName, secretKey));
                        userHolder.setLoginPassword(CipherHandler.encrypt(password, secretKey));

                        if (!BaseActivity.this.userInformation.insert(userHolder)) {
                            // should not be happened
                            BaseActivity.this.showInformationToast(MessageID.IJP00004);
                            Logger.Error.write(TAG, methodName, "ユーザ情報 : (%s)", userHolder.toString());
                            return;
                        }

                        /*
                         * 秘密鍵を共有情報へ保存する。
                         * 前回分の秘密鍵が存在する場合は値を上書きする。
                         */
                        BaseActivity.this.saveSharedPreference(PreferenceKey.SecretKey, secretKey);
                    }
                } finally {
                    BaseActivity.this.dismissDialog();
                }

                // オンラインモードに設定
                BaseActivity.this.setModeType(ModeType.Online);

                // TODO: 完了メッセージ
                BaseActivity.this.showInformationToast(MessageID.IJP00008);
                BaseActivity.this.signinDialog.dismiss();
                Logger.Info.write(TAG, methodName, "END");
            }
        };

        asyncLogin.execute(userName, password);
    }
}
