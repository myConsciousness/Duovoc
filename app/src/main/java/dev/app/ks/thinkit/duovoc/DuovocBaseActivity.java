package dev.app.ks.thinkit.duovoc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.consent.DebugGeography;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.appcompat.app.ActionBar;
import dev.app.ks.thinkit.duovoc.communicate.HttpAsyncLogin;
import dev.app.ks.thinkit.duovoc.framework.BaseActivity;
import dev.app.ks.thinkit.duovoc.framework.CalendarHandler;
import dev.app.ks.thinkit.duovoc.framework.CipherHandler;
import dev.app.ks.thinkit.duovoc.framework.CommunicationChecker;
import dev.app.ks.thinkit.duovoc.framework.IPreferenceKey;
import dev.app.ks.thinkit.duovoc.framework.Logger;
import dev.app.ks.thinkit.duovoc.framework.ModeType;
import dev.app.ks.thinkit.duovoc.framework.ModelMap;
import dev.app.ks.thinkit.duovoc.framework.PreferenceKey;
import dev.app.ks.thinkit.duovoc.framework.StringChecker;
import dev.app.ks.thinkit.duovoc.framework.communicate.holder.HttpAsyncResults;
import dev.app.ks.thinkit.duovoc.model.AutoSyncIntervalInformation;
import dev.app.ks.thinkit.duovoc.model.CurrentUserInformation;
import dev.app.ks.thinkit.duovoc.model.OverviewInformation;
import dev.app.ks.thinkit.duovoc.model.OverviewRelatedLexemeInformation;
import dev.app.ks.thinkit.duovoc.model.OverviewTranslationInformation;
import dev.app.ks.thinkit.duovoc.model.SupportedLanguageInformation;
import dev.app.ks.thinkit.duovoc.model.UserInformation;
import dev.app.ks.thinkit.duovoc.model.UserMemoInformation;
import dev.app.ks.thinkit.duovoc.model.holder.UserHolder;
import dev.app.ks.thinkit.duovoc.model.property.CurrentUserColumnKey;
import dev.app.ks.thinkit.duovoc.model.property.UserColumnKey;
import dev.app.ks.thinkit.duovoc.property.IntentExtraKey;
import dev.app.ks.thinkit.duovoc.property.MessageID;
import dev.app.ks.thinkit.duovoc.property.SupportedLanguage;
import dev.app.ks.thinkit.duovoc.property.TransitionOriginalScreenId;

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
    private static final String TAG = DuovocBaseActivity.class.getName();

    /**
     * 画面のレイアウト。
     */
    private final int activityLayout;

    /**
     * 認証ダイアログのオブジェクト。
     */
    private AlertDialog authenticationDialog;

    /**
     * 言語学習における初回利用時ダイアログのオブジェクト。
     */
    private AlertDialog theFirstDayOfClassDialog;

    /**
     * GDPR同意フォームのオブジェクト。
     */
    private ConsentForm consentForm;

    /**
     * 当該基底クラスのコンストラクタ。
     * 当該基底クラスを継承した子クラスは必ず当該コンストラクタを実行する必要があります。
     *
     * @param activityLayout 出力する画面のレイアウト
     */
    protected DuovocBaseActivity(final int activityLayout) {
        super(activityLayout);

        this.activityLayout = activityLayout;
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
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        if (BuildConfig.PAID) {
            final MenuItem menuItemGetAdFree = menu.findItem(R.id.menu_buy_paid_version);
            menuItemGetAdFree.setVisible(false);
        } else {
            // TODO: 有料版リリース後に消す
            final MenuItem menuItemGetAdFree = menu.findItem(R.id.menu_buy_paid_version);
            menuItemGetAdFree.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        final int itemId = item.getItemId();

        if (itemId == R.id.menu_setting_button) {
            TransitionOriginalScreenId screenId = null;

            if (this.activityLayout == R.layout.activity_login) {
                screenId = TransitionOriginalScreenId.LoginActivity;
            } else if (this.activityLayout == R.layout.activity_overview) {
                screenId = TransitionOriginalScreenId.OverviewActivity;
            } else if (this.activityLayout == R.layout.activity_detail) {
                screenId = TransitionOriginalScreenId.DetailActivity;
            }

            assert screenId != null;

            final Map<String, String> extras = new HashMap<>();
            extras.put(IntentExtraKey.ViewTransferId.getKeyName(), screenId.getScreenName());

            this.startActivity(SettingsActivity.class, extras);

        } else if (itemId == R.id.menu_buy_paid_version) {
            // 有料版購入ページへ遷移させる
            final String URL_PURCHASE_PAID = "https://www.duolingo.com/skill/%s/Intro/1";
            final Uri parsedUrl = Uri.parse(URL_PURCHASE_PAID);

            super.startActivity(parsedUrl);

        } else if (itemId == android.R.id.home) {
            if (!BuildConfig.PAID) {
                // インターステイシャル広告を表示
                this.showInterstitialAd();
            }

            this.finish();
        }

        return true;
    }

    /**
     * アクションバーに戻るボタンを表示する処理を定義したメソッドです。
     * アクションバーの戻るボタンが押下された場合はアクティビティを終了し遷移前画面へ戻ります。
     */
    protected void displayBackButtonOnActionBar() {
        final ActionBar actionBar = this.getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
    }

    /**
     * インタースティシャル広告を初期化する処理を定義したメソッドです。
     */
    protected void initializeInterstitialAd() {
        final String unitId = this.isDebug()
                ? "ca-app-pub-3940256099942544/1033173712"
                : "ca-app-pub-7168775731316469/5038672098";

        final boolean isUserUnderage = Boolean.valueOf(this.getSharedPreference(PreferenceKey.AgeVerification));

        super.initializeInterstitialAd(String.valueOf(R.string.advertisement_app_id), unitId, isUserUnderage);
    }

    /**
     * インタースティシャル広告を表示する処理を定義したメソッドです。
     * インタースティシャル広告は当該メソッドがn回呼ばれた時点で出力されます。
     */
    protected void showInterstitialAd() {
        super.showInterstitialAd(3);
    }

    /**
     * EU一般データ保護規則からユーザに同意を求めるフォームを出力する処理を定義したメソッドです。
     * 出力対象外の国では当該メソッドはフォームを出力しません。
     */
    protected void checkGeneralDataProtectionRegulation(Context context) {

        final ConsentInformation consentInformation = ConsentInformation.getInstance(context);
        final String[] publisherIds = {"pub-7168775731316469"};

        ConsentInformation.getInstance(this).setDebugGeography(DebugGeography.DEBUG_GEOGRAPHY_EEA);

        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                if (ConsentInformation.getInstance(DuovocBaseActivity.this).isRequestLocationInEeaOrUnknown()) {
                    if (ConsentStatus.PERSONALIZED != consentStatus
                            && ConsentStatus.NON_PERSONALIZED != consentStatus
                            && ConsentStatus.UNKNOWN != consentStatus) {

                        // 同意情報をユーザから取得する必要があるので、Google標準の同意書を表示する
                        DuovocBaseActivity.this.consentForm = DuovocBaseActivity.this.makeConsentForm(context);
                        DuovocBaseActivity.this.consentForm.load();
                    }
                }
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                DuovocBaseActivity.this.finish();
            }
        });
    }

    /**
     * Google標準に準拠したEU一般データ保護規則の同意フォームを生成する処理を定義したメソッドです。
     * 一度ユーザが同意書に回答した場合であっても後に回答した内容を変更できるように
     * 当該同意フォームを表示する機能を実装する必要があります。
     * <p>
     * Remember to provide users with the option to Change or revoke consent.
     *
     * @param context アプリケーション情報。
     * @return Google標準に準拠したEU一般データ保護規則の同意フォーム。
     */
    private ConsentForm makeConsentForm(final Context context) {

        URL privacyUrl = null;

        try {
            privacyUrl = new URL("https://duovoc.flycricket.io/privacy.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        final ConsentForm form = new ConsentForm.Builder(context, privacyUrl).withListener(new ConsentFormListener() {
            @Override
            public void onConsentFormLoaded() {
                DuovocBaseActivity.this.consentForm.show();
            }

            @Override
            public void onConsentFormOpened() {
            }

            @Override
            public void onConsentFormClosed(ConsentStatus consentStatus, Boolean userPrefersAdFree) {

                if (userPrefersAdFree) {
                    // TODO: 有料版
                }

                if (consentStatus == ConsentStatus.PERSONALIZED) {
                    DuovocBaseActivity.super.saveSharedPreference(PreferenceKey.GeneralDataProtectionRegulation, ConsentStatus.PERSONALIZED.name());
                } else if (consentStatus == ConsentStatus.NON_PERSONALIZED) {
                    DuovocBaseActivity.super.saveSharedPreference(PreferenceKey.GeneralDataProtectionRegulation, ConsentStatus.NON_PERSONALIZED.name());
                } else {
                    DuovocBaseActivity.this.finish();
                }
            }

            @Override
            public void onConsentFormError(String errorDescription) {
                DuovocBaseActivity.this.finish();
            }
        })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .withAdFreeOption()
                .build();

        return form;
    }

    /**
     * バナー型の広告を画面へ出力する処理を定義したメソッドです。
     * バナー型広告を出力する場合は当該メソッドを実行する必要があります。
     *
     * @param layout バナー型広告のレイアウトID。
     */
    protected void displayBannerAdvertisement(final int layout) {
        super.displayBannerAdvertisement(layout, String.valueOf(R.string.advertisement_app_id));
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
                this.showInformationToast(MessageID.M00047);
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
            this.showInformationToast(MessageID.M00027);
            return;
        }

        if (!this.isActiveNetwork()) {
            this.showInformationToast(MessageID.M00028);
            return;
        }

        if (!this.isActiveWifiNetwork()) {
            this.showInformationToast(MessageID.M00029);
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

                DuovocBaseActivity.this.showSpinnerDialog("", "Please wait for a little...");
            }

            @Override
            protected void onPostExecute(final HttpAsyncResults httpAsyncResults) {
                super.onPostExecute(httpAsyncResults);

                final String methodName = "onPostExecute";
                Logger.Info.write(TAG, methodName, "START");

                if (!httpAsyncResults.isHttpStatusOk()) {
                    final List<String> additional = new ArrayList<>();
                    additional.add(httpAsyncResults.getHttpStatusCode().getStatusName());
                    DuovocBaseActivity.super.showInformationToast(MessageID.M00030, additional);
                    return;
                }

                final UserHolder userHolder = (UserHolder) httpAsyncResults.getModelAccessorList().get(0);

                if (!RESPONSE_CODE_OK.equals(userHolder.getResponse())) {
                    DuovocBaseActivity.this.dismissDialog();
                    DuovocBaseActivity.this.showInformationToast(MessageID.M00031);
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

                DuovocBaseActivity.this.showInformationToast(MessageID.M00032);

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
    protected final void showTheFirstDayOfClassDialog(final String learningLanguageCode) {

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
     * モデル「自動同期周期情報」から入力として渡された項目名に紐づく周期を取得し数値型として返却します。
     *
     * @param itemName 自動同期周期情報で管理されている周期の名前。
     * @return 同期周期。
     */
    protected final int getAutoSyncInterval(final AutoSyncIntervalInformation.ItemName itemName) {
        final AutoSyncIntervalInformation autoSyncIntervalInformation = this.getAutoSyncIntervalInformation();
        autoSyncIntervalInformation.selectByPrimaryKey(itemName);
        return autoSyncIntervalInformation.getInterval();
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
    protected final UserInformation getUserInformation() {
        return UserInformation.getInstance(this);
    }

    /**
     * 論理モデル名「カレントユーザ情報」のオブジェクトを返却します。
     * カレントユーザ情報はシングルトンオブジェクトです。
     *
     * @return カレントユーザ情報のモデルオブジェクト。
     * @see CurrentUserInformation
     */
    protected final CurrentUserInformation getCurrentUserInformation() {
        return CurrentUserInformation.getInstance(this);
    }

    /**
     * 論理モデル名「概要情報」のオブジェクトを返却します。
     * 概要情報はシングルトンオブジェクトです。
     *
     * @return 概要情報のモデルオブジェクト。
     * @see OverviewInformation
     */
    protected final OverviewInformation getOverviewInformation() {
        return OverviewInformation.getInstance(this);
    }

    /**
     * 論理モデル名「概要翻訳情報」のオブジェクトを返却します。
     * 概要翻訳情報はシングルトンオブジェクトです。
     *
     * @return 概要翻訳情報のモデルオブジェクト。
     * @see OverviewTranslationInformation
     */
    protected final OverviewTranslationInformation getOverviewTranslationInformation() {
        return OverviewTranslationInformation.getInstance(this);
    }

    /**
     * 論理モデル名「概要語彙素情報」のオブジェクトを返却します。
     * 概要語彙素情報はシングルトンオブジェクトです。
     *
     * @return 概要語彙素情報のモデルオブジェクト。
     * @see OverviewTranslationInformation
     */
    protected final OverviewRelatedLexemeInformation getOverviewRelatedLexemeInformation() {
        return OverviewRelatedLexemeInformation.getInstance(this);
    }

    /**
     * 論理モデル名「サポート言語情報」のオブジェクトを返却します。
     * サポート言語情報はシングルトンオブジェクトです。
     *
     * @return サポート言語情報のモデルオブジェクト。
     * @see SupportedLanguageInformation
     */
    protected final SupportedLanguageInformation getSupportedLanguageInformation() {
        return SupportedLanguageInformation.getInstance(this);
    }

    /**
     * 論理モデル名「ユーザメモ情報」のオブジェクトを返却します。
     * サポート言語情報はシングルトンオブジェクトです。
     *
     * @return ユーザメモ情報のモデルオブジェクト。
     * @see UserMemoInformation
     */
    protected final UserMemoInformation getUserMemoInformation() {
        return UserMemoInformation.getInstance(this);
    }

    /**
     * 論理モデル名「自動同期周期情報」のオブジェクトを返却します。
     * 自動同期周期情報はシングルトンオブジェクトです。
     *
     * @return 自動同期周期情報のモデルオブジェクト。
     * @see AutoSyncIntervalInformation
     */
    protected final AutoSyncIntervalInformation getAutoSyncIntervalInformation() {
        return AutoSyncIntervalInformation.getInstance(this);
    }
}
