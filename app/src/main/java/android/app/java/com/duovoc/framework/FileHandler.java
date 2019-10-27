package android.app.java.com.duovoc.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : FileHandler.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * ファイルを操作する汎用的な機能を定義したユーティリティクラスです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public final class FileHandler {

    /**
     * 当該クラスのコンストラクタです。
     * 当該クラスはインスタンス生成を必要としないため修飾子をprivate指定しています。
     */
    private FileHandler() {
    }

    /**
     * ファイルから文字列を読み込み、
     * 読み込んだ値を結合した結果を文字列として返却します。
     *
     * @param inputStream バイト入力ストリーム。
     * @return ファイルから読み込んだ文字列。
     * @throws IOException ファイル読み込みにおいて異常終了した際に発生します。
     */
    public static String read(final InputStream inputStream) throws IOException {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str).append("\n");
            }
        }

        return sb.toString();
    }

}
