package dev.app.ks.thinkit.duovoc.framework;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import dev.app.ks.thinkit.duovoc.BuildConfig;
import dev.app.ks.thinkit.duovoc.SessionSharedPreferences;
import dev.app.ks.thinkit.duovoc.framework.model.CurrentApplicationInformation;
import dev.app.ks.thinkit.duovoc.framework.model.MasterMessageInformation;
import dev.app.ks.thinkit.duovoc.property.MessageID;
import dev.app.ks.thinkit.duovoc.property.MessageLanguageKind;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : BaseActivity.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
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
     * クラス名。
     */
    private static final String TAG = BaseActivity.class.getName();

    /**
     * 画面レイアウトのID。
     */
    private final int activityLayout;

    /**
     * プログレスダイアログを操作するオブジェクト。
     */
    private ProgressDialogHandler progressDialogHandler;

    /**
     * 共有情報へアクセスするためのオブジェクト。
     */
    private SharedPreferences sharedPreferences;

    /**
     * セッション共有情報へアクセスするためのオブジェクト。
     */
    private SessionSharedPreferences sessionSharedPreferences;

    /**
     * インテースティシャル広告のオブジェクト。
     */
    private InterstitialAd mInterstitialAd;

    /**
     * 当該基底クラスのコンストラクタ。
     * 当該基底クラスを継承した子クラスは必ず当該コンストラクタを実行する必要があります。
     *
     * @param activityLayout 出力する画面のレイアウト
     */
    protected BaseActivity(final int activityLayout) {
        this.activityLayout = activityLayout;
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
        this.setContentView(this.activityLayout);

        final String methodName = "onCreate";
        Logger.Info.write(TAG, methodName, "START");

        // Activityが生成されてからインスタンスを取得する
        this.sharedPreferences = this.getSharedPreferences(this.getDefaultSharedPreferencesName(this), MODE_PRIVATE);
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
     * @see MasterMessageInformation#searchDisplayMessage(MessageID, MessageLanguageKind)
     * @see MasterMessageInformation#getMessage()
     */
    protected final void showInformationToast(final MessageID messageId) {
        this.showInformationToast(messageId, new ArrayList<>());
    }

    /**
     * 引数として渡されたメッセージIDを基にメッセージ付きのトーストを下部に表示します。
     * メッセージIDからメッセージへの変換は当該メソッド内で行われます。
     *
     * @param messageId  出力メッセージに紐づくユニークな値
     * @param additional メッセージに付加する情報
     * @see MasterMessageInformation#searchDisplayMessage(MessageID, MessageLanguageKind)
     * @see MasterMessageInformation#getMessage()
     */
    protected final void showInformationToast(final MessageID messageId, final List<String> additional) {

        final MasterMessageInformation masterMessageInformation = this.getMasterMessageInformation();
        masterMessageInformation.searchDisplayMessage(messageId, MessageLanguageKind.English);

        Toast.makeText(this, String.format(masterMessageInformation.getMessage(), additional), Toast.LENGTH_LONG).show();
    }

    /**
     * 引数として渡されたタイトルとメッセージを基にプログレスダイアログを表示します。
     * ダイアログの表示が不要になった際には必ず{@code dismissDialog()}を実行してください。
     *
     * @param title   ダイアログに出力する題名
     * @param message ダイアログに出力するメッセージ
     * @see #dismissDialog()
     */
    protected final void showSpinnerDialog(final String title, final String message) {
        this.progressDialogHandler.showSpinnerDialog(title, message);
    }

    /**
     * 表示されているダイアログの消去処理を行います。
     * ダイアログの表示を行った後には当該メソッドが必ず実行されるようにしてください。
     *
     * @see #showSpinnerDialog(String, String)
     */
    protected final void dismissDialog() {
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
    protected final void saveSharedPreference(final IPreferenceKey key, final String value) {
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
    protected final String getSharedPreference(final IPreferenceKey key) {
        return this.sharedPreferences.getString(key.getKeyName(), "");
    }

    /**
     * 入力情報を基にカレントアプリケーション情報を参照し、
     * 入力に紐付くコンフィグ値を返却します。
     *
     * @param config 取得対象の値に紐付くコンフィグ名
     * @return 検索処理が正常終了した場合はコンフィグ名に紐付くコンフィグ値。
     * @see CurrentApplicationInformation
     * @see CurrentApplicationInformation#selectByPrimaryKey(CurrentApplicationInformation.ConfigName)
     */
    protected final String getConfigValue(final CurrentApplicationInformation.ConfigName config) {
        final CurrentApplicationInformation currentApplicationInformation = this.getCurrentApplicationInformation();
        currentApplicationInformation.selectByPrimaryKey(config);
        return currentApplicationInformation.getConfigValue();
    }

    /**
     * インタースティシャル広告を初期化する処理を定義したメソッドです。
     *
     * @param appId          AdMobで登録されているアプリID。
     * @param unitId         AdMobで登録されている広告のUnit。
     * @param isUserUnderage ユーザの未成年可否。
     */
    protected void initializeInterstitialAd(final String appId, final String unitId, final boolean isUserUnderage) {

        MobileAds.initialize(this, appId);
        final String consentResult = this.getSharedPreference(PreferenceKey.GeneralDataProtectionRegulation);

        final Bundle extras = new Bundle();
        if (ConsentStatus.NON_PERSONALIZED.name().equals(consentResult)) {
            extras.putString("npa", "1");
        }

        this.mInterstitialAd = new InterstitialAd(this);
        this.mInterstitialAd.setAdUnitId(unitId);

        this.mInterstitialAd.loadAd(new AdRequest.Builder()
                .tagForChildDirectedTreatment(isUserUnderage)
                .setMaxAdContentRating(isUserUnderage ? "G" : "MA")
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build());

        this.mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // 新しい広告を読み込む
                BaseActivity.this.mInterstitialAd.loadAd(new AdRequest.Builder()
                        .tagForChildDirectedTreatment(isUserUnderage)
                        .setMaxAdContentRating(isUserUnderage ? "G" : "MA")
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build());
            }
        });
    }

    /**
     * インタースティシャル広告を表示する処理を定義したメソッドです。
     * インタースティシャル広告は当該メソッドがn回呼ばれた時点で出力されます。
     */
    protected void showInterstitialAd(final int threshold) {

        final String countTransfer = this.getSharedPreference(PreferenceKey.CountTransferForInterstitial);

        if (StringChecker.isEffectiveString(countTransfer)) {
            int countTransferForInterstitial = Integer.parseInt(countTransfer);

            if (countTransferForInterstitial > threshold && this.mInterstitialAd.isLoaded()) {
                this.saveSharedPreference(PreferenceKey.CountTransferForInterstitial, "1");
                this.mInterstitialAd.show();
            } else {
                countTransferForInterstitial++;
                this.saveSharedPreference(PreferenceKey.CountTransferForInterstitial, String.valueOf(countTransferForInterstitial));
            }
        } else {
            this.saveSharedPreference(PreferenceKey.CountTransferForInterstitial, "1");
        }
    }

    /**
     * バナー型の広告を画面へ出力する処理を定義したメソッドです。
     * バナー型広告を出力する場合は当該メソッドを実行する必要があります。
     *
     * @param layout バナー型広告のレイアウトID。
     */
    protected void displayBannerAdvertisement(final int layout, final String appId) {

        MobileAds.initialize(this, appId);
        final String consentResult = this.getSharedPreference(PreferenceKey.GeneralDataProtectionRegulation);

        final Bundle extras = new Bundle();
        if (ConsentStatus.NON_PERSONALIZED.name().equals(consentResult)) {
            extras.putString("npa", "1");
        }

        final boolean isUserUnderage = Boolean.valueOf(this.getSharedPreference(PreferenceKey.AgeVerification));

        final AdRequest adRequest = new AdRequest
                .Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .tagForChildDirectedTreatment(isUserUnderage)
                .setMaxAdContentRating(isUserUnderage ? "G" : "MA")
                .build();

        final AdView adView = this.findViewById(layout);
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdClosed() {
            }
        });
    }

    protected void removeBannerAdvertisement(final int parentLayout, final int bannerLayout) {
        final LinearLayout linearLayoutScrollView = this.findViewById(parentLayout);
        final AdView adView = this.findViewById(bannerLayout);
        linearLayoutScrollView.removeView(adView);
    }

    protected void removeBannerAdvertisement(final int parentLayout, final int topBannerLayout, final int bottomBannerLayout) {
        final LinearLayout linearLayoutScrollView = this.findViewById(parentLayout);
        final AdView adViewTop = this.findViewById(topBannerLayout);
        final AdView adViewBottom = this.findViewById(bottomBannerLayout);

        linearLayoutScrollView.removeView(adViewTop);
        linearLayoutScrollView.removeView(adViewBottom);
    }

    protected void restartApplication() {
        final Intent intent = this.getPackageManager().getLaunchIntentForPackage(this.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }

    /**
     * アプリケーションがデバッグモードで起動しているか判定します。
     * リリース版の場合は常にfalseが返却されます。
     *
     * @return 開発時には {@code true}、リリース時には{@code false}
     */
    protected boolean isDebug() {
        return BuildConfig.DEBUG;
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
    protected final boolean convertToBoolean(final String value) {
        final String TRUE = "1";
        return TRUE.equals(value);
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
    protected final boolean isOnlineMode() {
        return this.sessionSharedPreferences.getModeType() == ModeType.Online;
    }

    /**
     * セッション共有情報へ処理モードを設定します。
     *
     * @see #isOnlineMode()
     */
    protected final void setModeType(final ModeType modeType) {
        this.sessionSharedPreferences.setModeType(modeType);
    }

    /**
     * ネットワークの接続状態の判定処理を行います。
     *
     * @return 有効なネットワーク状態の場合は {@code true}、それ以外は{@code false}
     * @see CommunicationChecker#isOnline(Context)
     */
    protected final boolean isActiveNetwork() {
        return CommunicationChecker.isOnline(this);
    }

    /**
     * Wifiネットワークの接続状態の判定処理を行います。
     *
     * @return 有効なネットワーク状態の場合は {@code true}、それ以外は{@code false}
     * @see CommunicationChecker#isWifiConnected(Context)
     */
    protected final boolean isActiveWifiNetwork() {

        final String configValue = this.getConfigValue(CurrentApplicationInformation.ConfigName.UsesWifiOnCommunicate);
        final boolean convertedConfigValue = this.convertToBoolean(configValue);

        return !convertedConfigValue
                || CommunicationChecker.isWifiConnected(this);
    }

    /**
     * 入力情報をクリップボードへコピーする処理を実行します。
     * システム情報からクリップボードを取得できなかった場合は{@code false}を返却します。
     *
     * @param context アプリケーション情報。
     * @param text    コピー対象の文字列。
     * @return コピー処理が正常終了した場合は {@code true}、それ以外は{@code false}
     */
    protected final boolean copyToClipboard(final Context context, final String text) {
        return this.copyToClipboard(context, "", text);
    }

    /**
     * 入力情報をクリップボードへコピーする処理を実行します。
     * システム情報からクリップボードを取得できなかった場合は{@code false}を返却します。
     *
     * @param context アプリケーション情報。
     * @param label   コピー対象の文字列に関する説明。
     * @param text    コピー対象の文字列。
     * @return コピー処理が正常終了した場合は {@code true}、それ以外は{@code false}
     */
    protected final boolean copyToClipboard(final Context context, final String label, final String text) {

        final ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboardManager == null) {
            return false;
        }

        clipboardManager.setPrimaryClip(ClipData.newPlainText(label, text));

        return true;
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
     * 現在動作しているアクティビティをバックグラウンドへ移行させ、
     * 指定されたアクティビティを起動する処理を実行します。
     *
     * @param toClass 起動するクラスオブジェクト。
     * @see #startActivity(Class, Map)
     */
    protected final void startActivity(final Class toClass) {
        this.startActivity(toClass, new HashMap<>());
    }

    /**
     * 現在動作しているアクティビティをバックグラウンドへ移行させ、
     * 指定されたアクティビティを起動する処理を実行します。
     * 次画面へ渡す値が連想配列として渡された場合、
     * 次画面へ遷移する前にインテントへ引き継ぎ情報を設定します。
     *
     * @param toClass 起動するクラスオブジェクト。
     * @param extras  引き継ぎ情報。
     * @see #startActivity(Class)
     */
    protected final void startActivity(final Class toClass, final Map<String, String> extras) {

        final Intent intent = new Intent(this, toClass);

        if (!extras.isEmpty()) {
            final Set<Map.Entry<String, String>> entries = extras.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }

        try {
            this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // should not be happened
            e.printStackTrace();
        }
    }

    /**
     * 入力として渡されたURIをもとに画面を表示します。
     *
     * @param uri 表示するページのURI。
     */
    protected final void startActivity(final Uri uri) {

        try {
            this.startActivity(this.getBrowserIntent(uri));
        } catch (ActivityNotFoundException e) {
            // should not be happened
            e.printStackTrace();
        }
    }

    /**
     * 入力として渡されたURIをブラウザで表示します。
     * デフォルトブラウザが設定されている場合はデフォルトブラウザを起動し、
     * デフォルトブラウザが設定されていない場合はユーザにブラウザを指定させるダイアログを表示します。
     *
     * @param uri 表示するページのURI。
     */
    protected final void startActivityOnBrowser(final Uri uri) {

        try {
            this.startActivity(this.getBrowserIntent(uri));
        } catch (ActivityNotFoundException e) {
            // should not be happened
            e.printStackTrace();
        }
    }

    /**
     * ページ表示に使用するブラウザを判定し対応するインテントを返却します。
     * デフォルトブラウザが設定されている場合はデフォルトブラウザを起動し、
     * デフォルトブラウザが設定されていない場合はユーザにブラウザを指定させるダイアログを表示します。
     *
     * @param uri 表示するページのURI。
     * @return ブラウザに対応するインテント。
     */
    private Intent getBrowserIntent(final Uri uri) {

        // HTTPS通信に対応したデフォルトブラウザを取得する
        final Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"));
        final ResolveInfo defaultResInfo = this.getPackageManager().resolveActivity(browser, PackageManager.MATCH_DEFAULT_ONLY);

        // デフォルトブラウザが存在する場合
        if (defaultResInfo != null) {
            final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage(defaultResInfo.activityInfo.packageName);

            return intent;
        }

        // デフォルトブラウザが存在しない場合はユーザに選択させる
        final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        final List<ResolveInfo> resolveInfoList = this.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        List<Intent> intentList = new ArrayList<>();

        for (ResolveInfo resolveInfo : resolveInfoList) {
            final Intent targeted = new Intent(intent);
            final String packageName = resolveInfo.activityInfo.packageName;

            if (this.getPackageName().equals(packageName)) {
                // 自分のアプリを選択から外す
                continue;
            }

            targeted.setPackage(packageName);
            intentList.add(targeted);
        }

        final Intent chooser = Intent.createChooser(new Intent(), "Open in browser");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[0]));

        return chooser;
    }

    /**
     * 画面の回転仕様をユーザの端末設定に準拠させます。
     * ユーザの端末で回転処理がロックされている場合は回転処理は発生しません。
     *
     * @see #setRequestedOrientation(int)
     * @see #setRequestedOrientationLocked()
     */
    protected final void setRequestedOrientationUnlocked() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
    }

    /**
     * 画面の回転処理を抑止します。
     * 当該メソッドが実行された場合、
     * ロックを解除するメソッドが実行されるまでアプリケーション内での回転処理は抑止されます。
     *
     * @see #setRequestedOrientation(int)
     * @see #setRequestedOrientationUnlocked()
     */
    protected final void setRequestedOrientationLocked() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    /**
     * 論理モデル名「カレントアプリケーション情報」のオブジェクトを返却します。
     * カレントアプリケーション情報はシングルトンオブジェクトです。
     *
     * @return カレントアプリケーション情報のモデルオブジェクト。
     * @see CurrentApplicationInformation
     */
    protected final CurrentApplicationInformation getCurrentApplicationInformation() {
        return CurrentApplicationInformation.getInstance(this);
    }

    /**
     * 論理モデル名「マスタメッセージ情報」のオブジェクトを返却します。
     * マスタメッセージ情報はシングルトンオブジェクトです。
     *
     * @return マスタメッセージ情報のモデルオブジェクト。
     * @see MasterMessageInformation
     */
    protected final MasterMessageInformation getMasterMessageInformation() {
        return MasterMessageInformation.getInstance(this);
    }
}
