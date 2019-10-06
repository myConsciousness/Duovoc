package android.app.java.com.duovoc;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.communicate.HttpAsyncLogin;
import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.CipherHandler;
import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.MessageID;
import android.app.java.com.duovoc.framework.ModeType;
import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.PreferenceKey;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.holder.UserHolder;
import android.app.java.com.duovoc.model.CurrentUserInformation;
import android.app.java.com.duovoc.model.OverviewInformation;
import android.app.java.com.duovoc.model.UserInformation;
import android.app.java.com.duovoc.model.property.CurrentUserColumnKey;
import android.app.java.com.duovoc.model.property.OverviewColumnKey;
import android.app.java.com.duovoc.model.property.UserColumnKey;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

final public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private final UserInformation userInformation = UserInformation.getInstance(this);
    private final CurrentUserInformation currentUserInformation = CurrentUserInformation.getInstance(this);

    public LoginActivity() {
        super(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        final MenuItem syncButton = menu.findItem(R.id.menuRefreshButton);
        syncButton.setVisible(false);

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

        final Button buttonSignin = this.findViewById(R.id.signin);
        final Button buttonOffline = this.findViewById(R.id.offlineMode);
        final TextView textViewSignup = this.findViewById(R.id.signup);
        final TextView textViewForgotPassword = this.findViewById(R.id.login_forgot_password);

        buttonSignin.setOnClickListener(view -> {

            final EditText editTextUserName = this.findViewById(R.id.userName);
            final EditText editTextPassword = this.findViewById(R.id.loginPassword);

            LoginActivity.this.signIn(editTextUserName.getText().toString(), editTextPassword.getText().toString());
        });

        buttonOffline.setOnClickListener(view -> this.offline());

        textViewSignup.setOnClickListener(view -> {

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
                    // 過去に永続化されたユーザ情報を削除する。
                    LoginActivity.this.userInformation.clear();
                }

                LoginActivity.super.showSpinnerDialog("Authorizing", "Waiting for response...");
            }

            @Override
            protected void onPostExecute(final UserHolder userHolder) {
                super.onPostExecute(userHolder);

                final String methodName = "onPostExecute";
                Logger.Info.write(TAG, methodName, "START");

                try {

                    if (!RESPONSE_CODE_OK.equals(userHolder.getResponse())) {
                        LoginActivity.super.showInformationToast(MessageID.IJP00003);
                        Logger.Debug.write(TAG, methodName, "レスポンスコード = (%s)", userHolder.getResponse());
                        return;
                    }

                    if (checkBoxStoreSignInInfo.isChecked()) {

                        // 入力されたログイン情報はここで暗号化して設定する
                        final String secretKey = CipherHandler.generateSecretKey();
                        userHolder.setLoginName(CipherHandler.encrypt(userName, secretKey));
                        userHolder.setLoginPassword(CipherHandler.encrypt(password, secretKey));

                        if (!LoginActivity.this.userInformation.insert(userHolder)) {
                            // should not be happened
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
                } finally {
                    LoginActivity.super.dismissDialog();
                }

                // オンラインモードに設定
                LoginActivity.super.setModeType(ModeType.Online);

                final Map<String, String> extras = new HashMap<>();
                extras.put(UserColumnKey.UserId.getKeyName(), userHolder.getUserId());

                Logger.Info.write(TAG, methodName, "END");
                LoginActivity.super.startActivity(ListViewActivity.class, extras);
            }
        };

        asyncLogin.execute(userName, password);
    }

    private void offline() {
        final String methodName = "offline";
        Logger.Info.write(TAG, methodName, "START");

        if (!this.currentUserInformation.selectAll()) {
            /** TODO: メッセージID */
            /*
             * カレントユーザ情報が存在しない場合は、
             * オフラインモードでの起動を抑止する。
             */
            super.showInformationToast(MessageID.IJP00008);
            return;
        }

        final ModelMap<CurrentUserColumnKey, Object> modelMap = this.currentUserInformation.getModelInfo();
        final String currentUserId = modelMap.getString(CurrentUserColumnKey.UserId);
        final String currentLanguage = modelMap.getString(CurrentUserColumnKey.Language);
        final String currentFromLanguage = modelMap.getString(CurrentUserColumnKey.FromLanguage);

        if (!this.isAlreadySynced(currentUserId, currentLanguage, currentFromLanguage)) {

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
        extras.put(UserColumnKey.UserId.getKeyName(), currentUserId);

        Logger.Info.write(TAG, methodName, "END");
        super.startActivity(ListViewActivity.class, extras);
    }

    private boolean isAlreadySynced(final String userId, final String language, final String fromLanguage) {
        final String methodName = "isAlreadySynced";
        Logger.Info.write(TAG, methodName, "START");

        final OverviewInformation overviewInformation = OverviewInformation.getInstance(this);
        overviewInformation.selectByCurrentUserInformation(userId, language, fromLanguage);

        final ModelList<ModelMap<OverviewColumnKey, Object>> modelMaps = overviewInformation.getModelInfo();

        Logger.Info.write(TAG, methodName, "END");
        return !modelMaps.isEmpty();
    }


}
