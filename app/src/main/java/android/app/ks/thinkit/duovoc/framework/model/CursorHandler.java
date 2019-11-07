package android.app.ks.thinkit.duovoc.framework.model;

import android.database.Cursor;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : CursorHandler.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * カーソルオブジェクトを操作する汎用的な機能を定義したユーティリティクラスです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public final class CursorHandler {

    /**
     * 当該クラスのコンストラクタです。
     * 当該クラスはインスタンス生成を必要としないため修飾子をprivate指定しています。
     */
    private CursorHandler() {
    }

    /**
     * カーソルオブジェクトから入力情報として渡されたキー名に紐付く値を取得し返却します。
     * キー名に紐付く値が存在しない場合は例外が発生します。
     *
     * @param cursor  カーソルオブジェクト。
     * @param keyName カラムに紐付くキー名。
     * @return キー名に紐付く値。
     * @throws IllegalArgumentException キー名に紐付く値が存在しない場合。
     */
    public static String getStringOrThrow(final Cursor cursor, final String keyName) {
        final int index = cursor.getColumnIndexOrThrow(keyName);
        return cursor.getString(index);
    }

    /**
     * カーソルオブジェクトから入力情報として渡されたキー名に紐付く値を取得し返却します。
     * キー名に紐付く値が存在しない場合は例外が発生します。
     *
     * @param cursor  カーソルオブジェクト。
     * @param keyName カラムに紐付くキー名。
     * @return キー名に紐付く値。
     * @throws IllegalArgumentException キー名に紐付く値が存在しない場合。
     */
    public static int getIntegerOrThrow(final Cursor cursor, final String keyName) {
        final int index = cursor.getColumnIndexOrThrow(keyName);
        return cursor.getInt(index);
    }

    /**
     * カーソルオブジェクトから入力情報として渡されたキー名に紐付く値を取得し返却します。
     * キー名に紐付く値が存在しない場合は例外が発生します。
     *
     * @param cursor  カーソルオブジェクト。
     * @param keyName カラムに紐付くキー名。
     * @return キー名に紐付く値。
     * @throws IllegalArgumentException キー名に紐付く値が存在しない場合。
     */
    public static Double getDoubleIfNotEmpty(final Cursor cursor, final String keyName) {
        final int index = cursor.getColumnIndexOrThrow(keyName);
        return cursor.getDouble(index);
    }

    /**
     * カーソルオブジェクトから入力情報として渡されたキー名に紐付く値を取得し返却します。
     * キー名に紐付く値が存在しない場合は例外が発生します。
     *
     * @param cursor  カーソルオブジェクト。
     * @param keyName カラムに紐付くキー名。
     * @return キー名に紐付く値。
     * @throws IllegalArgumentException キー名に紐付く値が存在しない場合。
     */
    public static Long getLongOrThrow(final Cursor cursor, final String keyName) {
        final int index = cursor.getColumnIndexOrThrow(keyName);
        return cursor.getLong(index);
    }
}
