package android.app.java.com.duovoc.framework;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : CommonConstants.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/10/19
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 汎用的な文字列型の値を定義した定数クラスです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public final class CommonConstants {

    /**
     * char型のスラッシュ。
     */
    public static final char CHAR_SEPARATOR_SLASH = '/';

    /**
     * char型のピリオド。
     */
    public static final char CHAR_SEPARATOR_PERIOD = ',';

    /**
     * String型のピリオド。
     */
    public static final String STRING_SEPARATOR_PERIOD = ",";

    /**
     * String型のシステム情報から取得した改行コード。
     */
    public static final String SYSTEM_BR = System.getProperty("line.separator");

    /**
     * ISO基準の日時形式。
     */
    public static final String FORMAT_ISO_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * 当該クラスのコンストラクタです。
     * 当該クラスはインスタンス生成を必要としないため修飾子をprivate指定しています。
     */
    private CommonConstants() {
    }
}
