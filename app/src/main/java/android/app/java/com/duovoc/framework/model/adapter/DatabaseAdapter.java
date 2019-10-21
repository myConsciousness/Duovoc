package android.app.java.com.duovoc.framework.model.adapter;

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
        this.databaseHelper = new DatabaseOpenHelper(this.context);
        this.setDatabase(this.databaseHelper.getWritableDatabase());
    }

    /**
     * データベース接続を切る処理を定義したメソッドです。
     * メモリリークを防ぐために、
     * データベース接続を行った後には必ず当該メソッドを呼び出す必要があります。
     *
     * @see #open()
     */
    public void close() {
        if (this.databaseHelper != null) {
            this.databaseHelper.close();
        }
    }

    /**
     * トランザクション処理の開始を通知する処理を定義したメソッドです。
     * トランザクション処理の前に必ず当該メソッドを呼び出す必要があります。
     * また、当該メソッドを呼び出すためには必ずデータベースへの接続が確立されている必要があります。
     *
     * @see #endTransaction()
     */
    public void beginTransaction() {
        this.database.beginTransaction();
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
        this.database.setTransactionSuccessful();
    }

    /**
     * トランザクション処理の終了を通知する処理を定義したメソッドです。
     * トランザクション処理の後には必ず当該メソッドを呼び出す必要があります。
     * また、当該メソッドを呼び出すためには必ずデータベースへの接続が確立されている必要があります。
     *
     * @see #beginTransaction()
     */
    public void endTransaction() {
        this.database.endTransaction();
    }

    /**
     * データベースオブジェクトを返却するGetterメソッドです。
     *
     * @return データベースオブジェクト。
     */
    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    /**
     * データベースオブジェクトを設定するSetterメソッドです。
     * 当該Setterメソッドは他クラスで使用できません。
     *
     * @param database データベースオブジェクト。
     */
    private void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }
}
