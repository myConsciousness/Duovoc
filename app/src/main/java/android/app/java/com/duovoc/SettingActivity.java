package android.app.java.com.duovoc;

import android.app.java.com.duovoc.framework.BaseActivity;
import android.app.java.com.duovoc.framework.Logger;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.widget.TextView;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : Setting.java
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
 * 以下の設定項目が現在定義されています。
 * <p>
 * 1, General
 * -> アプリケーション情報に関わる値の設定処理を行う項目です。
 * 2, User information
 * -> ユーザ情報に関わる値の設定処理を行う項目です。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
final public class SettingActivity extends BaseActivity {

    /**
     * クラス名。
     */
    private static final String TAG = SettingActivity.class.getSimpleName();

    /**
     * 当該クラスのコンストラクタです。
     */
    public SettingActivity() {
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

        final TextView textViewGeneral = this.findViewById(R.id.setting_general);
        final String title = this.getString(R.string.setting_general);

        SpannableString content = new SpannableString(title);
        content.setSpan(new UnderlineSpan(), 0, title.length(), 0);
        textViewGeneral.setText(content);

        Logger.Info.write(TAG, methodName, "END");
    }

    @Override
    protected void setListeners() {
        final String methodName = "setListeners";
        Logger.Info.write(TAG, methodName, "START");

//        final TextView textViewGeneral = this.findViewById(R.id.setting_general);
//        final TextView textViewUserInformation = this.findViewById(R.id.setting_user_information);
//
//        textViewGeneral.setOnClickListener(view -> {
//            // 総合設定画面へ遷移させる
//            super.startActivity(SettingGeneralActivity.class);
//        });
//
//        textViewUserInformation.setOnClickListener(view -> {
//            // ユーザ情報設定画面へ遷移させる
//            super.startActivity(SettingUserInformationActivity.class);
//        });

        Logger.Info.write(TAG, methodName, "END");
    }
}
