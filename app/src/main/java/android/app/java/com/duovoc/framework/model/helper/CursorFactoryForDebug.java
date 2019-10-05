package android.app.java.com.duovoc.framework.model.helper;

import android.app.java.com.duovoc.framework.Logger;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : CursorFactoryForDebug.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 実行するクエリのデバッグを行う機能を定義したファクトリクラスです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
final public class CursorFactoryForDebug implements SQLiteDatabase.CursorFactory {

    /**
     * クラス名。
     */
    private static final String TAG = CursorFactoryForDebug.class.getSimpleName();

    @Override
    public Cursor newCursor(
            final SQLiteDatabase database,
            final SQLiteCursorDriver masterQuery,
            final String editTable,
            final SQLiteQuery query) {
        final String methodName = "newCursor";
        Logger.Info.write(TAG, methodName, "START");

        Logger.Debug.write(TAG, methodName, query.toString());

        Logger.Info.write(TAG, methodName, "END");
        return new SQLiteCursor(masterQuery, editTable, query);
    }
}
