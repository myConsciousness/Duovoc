package android.app.java.com.duovoc.framework.model.adapter;

import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.model.helper.DatabaseOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : DatabaseAdapter.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * SQLiteデータベースの接続に関わる汎用的な処理を定義したアダプタクラスです。
 * データベースの接続・切断は当該クラスを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see DatabaseOpenHelper
 * @since 1.0
 */
final public class DatabaseAdapter {

    /**
     * クラス名。
     */
    private static final String TAG = DatabaseAdapter.class.getSimpleName();

    /**
     * アプリケーションの情報。
     */
    private final Context context;

    /**
     * データベースを操作するヘルパークラスのオブジェクト。
     */
    private DatabaseOpenHelper databaseHelper;

    /**
     * SQLiteデータベースのオブジェクト。
     */
    private SQLiteDatabase database;

    /**
     * 当該クラスのコンストラクタです。
     *
     * @param context アプリケーションの情報。
     */
    public DatabaseAdapter(Context context) {
        this.context = context;
    }

    /**
     * データベースへ接続する処理を定義したメソッドです。
     * データベース接続を行う際は一番初めに当該メソッドを呼び出す必要があります。
     *
     * @see #close()
     */
    public void open() {
        final String methodName = "open";
        Logger.Info.write(TAG, methodName, "START");

        this.databaseHelper = new DatabaseOpenHelper(this.context);
        this.setDatabase(this.databaseHelper.getWritableDatabase());

        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * データベース接続を切る処理を定義したメソッドです。
     * メモリリークを防ぐために、
     * データベース接続を行った後には必ず当該メソッドを呼び出す必要があります。
     *
     * @see #open()
     */
    public void close() {
        final String methodName = "close";
        Logger.Info.write(TAG, methodName, "START");

        if (this.databaseHelper != null) {
            this.databaseHelper.close();
        }

        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * トランザクション処理の開始を通知する処理を定義したメソッドです。
     * トランザクション処理の前に必ず当該メソッドを呼び出す必要があります。
     * また、当該メソッドを呼び出すためには必ずデータベースへの接続が確立されている必要があります。
     *
     * @see #endTransaction()
     */
    public void beginTransaction() {
        final String methodName = "beginTransaction";
        Logger.Info.write(TAG, methodName, "START");

        this.database.beginTransaction();

        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * トランザクション処理が正常終了した際に行う処理を定義したメソッドです。
     * トランザクション処理の開始を通知した後に、
     * トランザクション処理が正常終了した際には当該メソッドを必ず呼び出す必要があります。
     * <p>
     * 異常終了する等で当該メソッドが呼び出されない場合はデータベースはロールバック処理を行います。
     * また、当該メソッドを呼び出すためには必ずデータベースへの接続が確立されている必要があります。
     */
    public void setTransactionSuccessful() {
        final String methodName = "setTransactionSuccessful";
        Logger.Info.write(TAG, methodName, "START");

        this.database.setTransactionSuccessful();

        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * トランザクション処理の終了を通知する処理を定義したメソッドです。
     * トランザクション処理の後には必ず当該メソッドを呼び出す必要があります。
     * また、当該メソッドを呼び出すためには必ずデータベースへの接続が確立されている必要があります。
     *
     * @see #beginTransaction()
     */
    public void endTransaction() {
        final String methodName = "endTransaction";
        Logger.Info.write(TAG, methodName, "START");

        this.database.endTransaction();

        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * データベースオブジェクトを返却するGetterメソッドです。
     *
     * @return データベースオブジェクト。
     */
    public SQLiteDatabase getDatabase() {
        final String methodName = "getDatabase";
        Logger.Info.write(TAG, methodName, "START");

        Logger.Info.write(TAG, methodName, "END");
        return this.database;
    }

    /**
     * データベースオブジェクトを設定するSetterメソッドです。
     * 当該Setterメソッドは他クラスで使用できません。
     *
     * @param database データベースオブジェクト。
     */
    private void setDatabase(SQLiteDatabase database) {
        final String methodName = "setDatabase";
        Logger.Info.write(TAG, methodName, "START");

        this.database = database;

        Logger.Info.write(TAG, methodName, "END");
    }
}
