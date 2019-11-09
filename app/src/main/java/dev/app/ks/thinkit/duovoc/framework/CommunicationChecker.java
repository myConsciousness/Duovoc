package dev.app.ks.thinkit.duovoc.framework;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : CommunicationChecker.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/10/19
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * ネットワーク接続に関する汎用的な判定処理を定義したユーティリティクラスです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public final class CommunicationChecker {

    /**
     * 当該クラスのコンストラクタ。
     * 当該クラスはインスタンス生成が不要なため修飾子をprivateで指定しています。
     */
    private CommunicationChecker() {
    }

    /**
     * 端末がネットワークに接続されているかを判定します。
     *
     * @param context アプリケーション情報。
     * @return ネットワーク接続がある場合は {@code true}、それ以外は{@code false}
     */
    public static boolean isOnline(final Context context) {

        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 端末がWi-Fiに接続されているかを判定します。
     *
     * @param context アプリケーション情報。
     * @return Wi-Fi接続がある場合は{@code true}、それ以外は{@code false}
     */
    public static boolean isWifiConnected(final Context context) {

        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            return false;
        }

        return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
