package android.app.java.com.duovoc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.java.com.duovoc.communicate.HttpAsyncLogin;
import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.CalendarHandler;
import android.app.java.com.duovoc.framework.CipherHandler;
import android.app.java.com.duovoc.framework.CommunicationChecker;
import android.app.java.com.duovoc.framework.IPreferenceKey;
import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.MessageID;
import android.app.java.com.duovoc.framework.ModeType;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.PreferenceKey;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.framework.communicate.holder.HttpAsyncResults;
import android.app.java.com.duovoc.model.CurrentUserInformation;
import android.app.java.com.duovoc.model.OverviewInformation;
import android.app.java.com.duovoc.model.OverviewRelatedLexemeInformation;
import android.app.java.com.duovoc.model.OverviewTranslationInformation;
import android.app.java.com.duovoc.model.SupportedLanguageInformation;
import android.app.java.com.duovoc.model.UserInformation;
import android.app.java.com.duovoc.model.holder.UserHolder;
import android.app.java.com.duovoc.model.property.CurrentUserColumnKey;
import android.app.java.com.duovoc.model.property.UserColumnKey;
import android.app.java.com.duovoc.property.SupportedLanguage;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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
     * 言語学習における初回利用時ダイアログのオブジェクト。
     */
    private AlertDialog theFirstDayOfClassDialog;

    /**
     * 当該基底クラスのコンストラクタ。
     * 当該基底クラスを継承した子クラスは必ず当該コンストラクタを実行する必要があります。
     *
     * @param activityLayout 出力する画面のレイアウト
     */
    protected DuovocBaseActivity(final int activityLayout) {
        super(activityLayout);
    }

    @Override
    public void onPause() {
        super.onPause();

        /*
         * 初回時利用ダイアログが出力されている状態で
         * アクティビティがバックグラウンドへ移る状態を想定する。
         * 以上の状態で再度アクティビティを表示すると概要情報の同期化を行う処理が実行されるが、
         * その際に再度初回時利用ダイアログのインスタンスを生成するため、
         * バックグラウンド移行前のダイアログが消去できなくなってしまう。
         * そのため、当該イベントでバックグラウンド移行前に初回時利用ダイアログを消去する。
         */
        if (this.theFirstDayOfClassDialog != null) {
            this.theFirstDayOfClassDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final int itemId = item.getItemId();

        if (itemId == R.id.menu_setting_button) {
            this.startActivity(SettingsActivity.class);
        }

        return true;
    }

    /**
     * 認証ダイアログのオブジェクトを構築し画面上に出力します。
     * 当該メソッドから作成されたダイアログではユーザ情報登録の可否が必ずユーザ任意になります。
     *
     * @see #initializeAuthenticationDialog(View, boolean)
     * @see #setListenerAuthenticationDialog(View)
     */
    protected void showAuthenticationDialog() {
        this.showAuthenticationDialog(false);
    }

    /**
     * 認証ダイアログのオブジェクトを構築し画面上に出力します。
     *
     * @param registerRequired ユーザ情報の登録必須可否を表すフラグ。
     * @see #initializeAuthenticationDialog(View, boolean)
     * @see #setListenerAuthenticationDialog(View)
     */
    protected void showAuthenticationDialog(final boolean registerRequired) {

        final View viewDialog = this.getLayoutInflater().inflate(R.layout.login_dialog, null);
        this.initializeAuthenticationDialog(viewDialog, registerRequired);

        this.setListenerAuthenticationDialog(viewDialog);

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(viewDialog);
        this.authenticationDialog = dialogBuilder.create();

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
    private void initializeAuthenticationDialog(final View viewDialog, final boolean registerRequired) {

        if (registerRequired) {
            final CheckBox checkBoxRememberMe = viewDialog.findViewById(R.id.dialog_remember_me);
            checkBoxRememberMe.setChecked(true);
            checkBoxRememberMe.setClickable(false);
        }

        final CurrentUserInformation currentUserInformation = this.getCurrentUserInformation();
        currentUserInformation.selectAll();

        final ModelMap<CurrentUserColumnKey, Object> currentUserMap = currentUserInformation.getModelInfo().get(0);
        final String currentUserId = currentUserMap.getString(CurrentUserColumnKey.UserId);

        final UserInformation userInformation = this.getUserInformation();
        userInformation.selectByPrimaryKey(currentUserId);

        if (!userInformation.isEmpty()) {
            final String secretKey = this.getSharedPreference(PreferenceKey.SecretKey);

            if (StringChecker.isEffectiveString(secretKey)) {
                final ModelMap<UserColumnKey, Object> userMap = userInformation.getModelInfo().get(0);
                final String userName = userMap.getString(UserColumnKey.LoginName);
                final String password = userMap.getString(UserColumnKey.LoginPassword);

                final EditText editTextUserName = viewDialog.findViewById(R.id.dialog_user_name);
                final EditText editTextPassword = viewDialog.findViewById(R.id.dialog_password);
                editTextUserName.setText(CipherHandler.decrypt(userName, secretKey));
                editTextPassword.setText(CipherHandler.decrypt(password, secretKey));
            } else {
                // TODO: システムエラー
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

        final EditText editTextUserName = viewDialog.findViewById(R.id.dialog_user_name);
        final EditText editTextPassword = viewDialog.findViewById(R.id.dialog_password);
        final CheckBox checkBoxRememberMe = viewDialog.findViewById(R.id.dialog_remember_me);

        buttonSignIn.setOnClickListener(view -> {
            final String userName = editTextUserName.getText().toString();
            final String password = editTextPassword.getText().toString();

            this.authenticate(userName, password, checkBoxRememberMe.isChecked());
        });

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
     * @param userName            ユーザ名。
     * @param password            パスワード。
     * @param isCheckedRememberMe ユーザ情報の登録可否。
     * @see HttpAsyncLogin
     * @see CommunicationChecker#isOnline(Context)
     * @see CommunicationChecker#isWifiConnected(Context)
     */
    protected void authenticate(
            final String userName,
            final String password,
            final boolean isCheckedRememberMe) {

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

        @SuppressLint("StaticFieldLeak") final HttpAsyncLogin asyncLogin = new HttpAsyncLogin() {

            private static final String RESPONSE_CODE_OK = "OK";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                DuovocBaseActivity.this.onPreAuthentication();

                if (isCheckedRememberMe) {
                    // 過去に永続化されたユーザ情報を削除する。
                    final UserInformation userInformation = DuovocBaseActivity.this.getUserInformation();
                    userInformation.deleteAll();
                }

                DuovocBaseActivity.this.showSpinnerDialog("Certifying", "Waiting for response...");
            }

            @Override
            protected void onPostExecute(final HttpAsyncResults httpAsyncResults) {
                super.onPostExecute(httpAsyncResults);

                final String methodName = "onPostExecute";
                Logger.Info.write(TAG, methodName, "START");

                if (!httpAsyncResults.isHttpStatusOk()) {
                    // TODO: 通信エラー（メッセージ中にステータスとコードをバインドする）
                    DuovocBaseActivity.super.showInformationToast(MessageID.IJP00008);
                    return;
                }

                final UserHolder userHolder = (UserHolder) httpAsyncResults.getModelAccessorList().get(0);

                if (!RESPONSE_CODE_OK.equals(userHolder.getResponse())) {
                    DuovocBaseActivity.this.dismissDialog();
                    DuovocBaseActivity.this.showInformationToast(MessageID.IJP00003);
                    return;
                }

                if (isCheckedRememberMe) {
                    // 入力されたログイン情報はここで暗号化して設定する
                    final String secretKey = CipherHandler.generateSecretKey();
                    userHolder.setLoginName(CipherHandler.encrypt(userName, secretKey));
                    userHolder.setLoginPassword(CipherHandler.encrypt(password, secretKey));

                    final UserInformation userInformation = DuovocBaseActivity.this.getUserInformation();
                    userInformation.insert(userHolder);

                    /*
                     * 秘密鍵を共有情報へ保存する。
                     * 前回分の秘密鍵が存在する場合は値を上書きする。
                     */
                    DuovocBaseActivity.this.saveSharedPreference(PreferenceKey.SecretKey, secretKey);
                }

                // オンラインモードに設定
                DuovocBaseActivity.this.setModeType(ModeType.Online);
                DuovocBaseActivity.this.dismissDialog();

                if (DuovocBaseActivity.this.authenticationDialog != null) {
                    DuovocBaseActivity.this.authenticationDialog.dismiss();
                    DuovocBaseActivity.this.authenticationDialog = null;
                }

                // TODO: 完了メッセージ
                DuovocBaseActivity.this.showInformationToast(MessageID.IJP00008);

                DuovocBaseActivity.this.onPostAuthentication();
                DuovocBaseActivity.this.startActivityOnPostAuthentication(userHolder.getUserId());

                Logger.Info.write(TAG, methodName, "END");
            }
        };

        asyncLogin.execute(userName, password);
    }

    protected void onPreAuthentication() {
    }

    /**
     * 認証ダイアログが閉じる前に実行されるメソッドです。
     * 認証処理が完了した後に処理を定義する場合は当該メソッドをオーバーライドし処理を実装してください。
     */
    protected void onPostAuthentication() {
    }

    /**
     * 認証ダイアログが閉じる前、
     * onPostAuthenticationが実行された後に実行されるメソッドです。
     * 認証処理後に画面遷移を行う場合は当該メソッドをオーバーライドし処理を実装してください。
     */
    protected void startActivityOnPostAuthentication(final String userId) {
    }

    /**
     * 言語学習における初回利用時のダイアログを出力します。
     * 概要情報の同期化を行った際に1件も情報を取得できなかった場合、
     * または、学習言語を変更する際に一度もレッスンを完了していない言語が選択された場合に、
     * 当該メソッドが使用されることを想定して実装されています。
     *
     * @param learningLanguageCode 学習している言語の言語コード
     * @throws IllegalArgumentException サポートされていない言語コードを検知した際に発生します。
     */
    final protected void showTheFirstDayOfClassDialog(final String learningLanguageCode) {

        final SupportedLanguage supportedLanguage
                = SupportedLanguage.getSupportedLanguageFromCode(learningLanguageCode);

        final View viewDialog
                = this.getLayoutInflater().inflate(R.layout.dialog_the_first_day_of_class, null);

        this.initializeTheFirstDayOfClassDialog(viewDialog, supportedLanguage);
        this.setListenerTheFirstDayOfClassDialog(viewDialog, supportedLanguage);

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(viewDialog);

        this.theFirstDayOfClassDialog = dialogBuilder.create();
        this.theFirstDayOfClassDialog.setCanceledOnTouchOutside(false);
        this.theFirstDayOfClassDialog.show();
    }

    /**
     * 言語学習における初回利用時ダイアログの画面上に出力する情報を初期化します。
     *
     * @param viewDialog 言語学習における初回利用時ダイアログのオブジェクト。
     */
    private void initializeTheFirstDayOfClassDialog(final View viewDialog, final SupportedLanguage supportedLanguage) {

        final TextView textViewBasics = viewDialog.findViewById(R.id.new_to_learning_language);
        final TextView textViewPlacement = viewDialog.findViewById(R.id.already_know_some_learning_language);
        final String learningLanguage = supportedLanguage.getDisplayEnglishName();

        textViewBasics.setText(String.format("New to %s?", learningLanguage));
        textViewPlacement.setText(String.format("Already know some %s?", learningLanguage));
    }

    /**
     * 言語学習における初回利用時ダイアログの各部品にイベントをバインドします。
     *
     * @param viewDialog        言語学習における初回利用時ダイアログのオブジェクト。
     * @param supportedLanguage 学習言語に紐づくサポート言語情報。
     */
    private void setListenerTheFirstDayOfClassDialog(final View viewDialog, final SupportedLanguage supportedLanguage) {

        final LinearLayout linearLayoutBasics = viewDialog.findViewById(R.id.layout_new_to);
        final LinearLayout linearLayoutPlacement = viewDialog.findViewById(R.id.layout_already_know);
        final LinearLayout linearLayoutDismiss = viewDialog.findViewById(R.id.layout_dismiss);

        final String learningLanguageCode = supportedLanguage.getLanguageCode();

        linearLayoutBasics.setOnClickListener(view -> {
            if (this.isActiveNetwork()) {
                // 基本学習ページへ遷移させる
                final String BASE_URL_INTRO = "https://www.duolingo.com/skill/%s/Intro/1";
                final String URL_INTRO = String.format(BASE_URL_INTRO, learningLanguageCode);
                final Uri parsedUrl = Uri.parse(URL_INTRO);

                super.startActivity(parsedUrl);
            }
        });

        linearLayoutPlacement.setOnClickListener(view -> {
            if (this.isActiveNetwork()) {
                // 飛び級テストページへ遷移させる
                final String BASE_URL_PLACEMENT = "https://www.duolingo.com/placement/%s";
                final String URL_PLACEMENT = String.format(BASE_URL_PLACEMENT, learningLanguageCode);
                final Uri parsedUrl = Uri.parse(URL_PLACEMENT);

                super.startActivity(parsedUrl);
            }
        });

        linearLayoutDismiss.setOnClickListener(view ->
                this.theFirstDayOfClassDialog.dismiss()
        );
    }

    /**
     * 引数として渡された日時を現在のクライアント日時と比較し、
     * 経過期間を導出して数値型として返却します。
     * <p>
     * 当該メソッドは"yyyyMMddHHmmss"形式の入力のみに対応しています。
     * また、当該メソッドはモデルから取得した最終更新日時から経過期間を導出するために作成されたため、
     * 上記以外の目的のために使用する場合は入力として渡す日時の形式に注意してください。
     *
     * @param datetime "yyyyMMddHHmmss"形式の日時。
     * @return 入力された日時と現在のクライアント日時までの経過期間。
     */
    protected int getElapsedDay(final String datetime) {
        return CalendarHandler.getElapsedPeriodFromDatetime(CalendarHandler.getClientDatetime(), datetime);
    }

    /**
     * 論理モデル名「ユーザ情報」のオブジェクトを返却します。
     * ユーザ情報はシングルトンオブジェクトです。
     *
     * @return ユーザ情報のモデルオブジェクト。
     * @see UserInformation
     */
    final protected UserInformation getUserInformation() {
        return UserInformation.getInstance(this);
    }

    /**
     * 論理モデル名「カレントユーザ情報」のオブジェクトを返却します。
     * カレントユーザ情報はシングルトンオブジェクトです。
     *
     * @return カレントユーザ情報のモデルオブジェクト。
     * @see CurrentUserInformation
     */
    final protected CurrentUserInformation getCurrentUserInformation() {
        return CurrentUserInformation.getInstance(this);
    }

    /**
     * 論理モデル名「概要情報」のオブジェクトを返却します。
     * 概要情報はシングルトンオブジェクトです。
     *
     * @return 概要情報のモデルオブジェクト。
     * @see OverviewInformation
     */
    final protected OverviewInformation getOverviewInformation() {
        return OverviewInformation.getInstance(this);
    }

    /**
     * 論理モデル名「概要翻訳情報」のオブジェクトを返却します。
     * 概要翻訳情報はシングルトンオブジェクトです。
     *
     * @return 概要翻訳情報のモデルオブジェクト。
     * @see OverviewTranslationInformation
     */
    final protected OverviewTranslationInformation getOverviewTranslationInformation() {
        return OverviewTranslationInformation.getInstance(this);
    }

    /**
     * 論理モデル名「概要語彙素情報」のオブジェクトを返却します。
     * 概要語彙素情報はシングルトンオブジェクトです。
     *
     * @return 概要語彙素情報のモデルオブジェクト。
     * @see OverviewTranslationInformation
     */
    final protected OverviewRelatedLexemeInformation getOverviewRelatedLexemeInformation() {
        return OverviewRelatedLexemeInformation.getInstance(this);
    }

    /**
     * 論理モデル名「サポート言語情報」のオブジェクトを返却します。
     * サポート言語情報はシングルトンオブジェクトです。
     *
     * @return サポート言語情報のモデルオブジェクト。
     * @see SupportedLanguageInformation
     */
    final protected SupportedLanguageInformation getSupportedLanguageInformation() {
        return SupportedLanguageInformation.getInstance(this);
    }
}
