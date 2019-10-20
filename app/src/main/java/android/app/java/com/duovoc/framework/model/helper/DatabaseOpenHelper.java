package android.app.java.com.duovoc.framework.model.helper;

import android.app.java.com.duovoc.framework.CommonConstants;
import android.app.java.com.duovoc.framework.FileHandler;
import android.app.java.com.duovoc.framework.Logger;
import android.app.java.com.duovoc.framework.StringHandler;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.Arrays;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : DatabaseOpenHelper.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * データベースへ接続する機能を定義したヘルパークラスです。
 * 当該クラスの実行時に以下の処理が実行されます。
 * <p>
 * 1, onCreate
 * 当該クラス実行時に指定されたデータベースが存在しない場合に実行されます。
 * 当該メソッドではデータベースの作成を行い、
 * assetファイル内に配置されたSQL文を実行して各テーブルの作成及び初期データの挿入を行います。
 * <p>
 * 2, onUpgrade
 * 当該クラス実行時に指定されたバージョンとデータベースのバージョンが異なっていた場合に実行されます。
 * 当該メソッドには新しいバージョンのデータベースに必要なテーブルの作成処理やデータの挿入処理を定義します。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
final public class DatabaseOpenHelper extends SQLiteOpenHelper {

    /**
     * クラス名。
     */
    private static final String TAG = DatabaseOpenHelper.class.getCanonicalName();

    /**
     * データベース名。
     */
    private static final String DATABASE_NAME = "duovoc_debug.db";

    /**
     * データベースのバージョン。
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * assetファイル内に存在するcreate文を配置したファイル名。
     */
    private static final String ASSET_FILE_CREATE_QUERY = "create";

    /**
     * assetファイル内に存在するinsert文を配置したファイル名。
     */
    private static final String ASSET_FILE_INSERT_QUERY = "insert";

    /**
     * assetファイル内に存在するtrigger生成文を配置したファイル名。
     */
    private static final String ASSET_FILE_TRIGGER_QUERY = "trigger";

    /**
     * アプリケーションの情報。
     */
    private Context context;

    /**
     * 当該クラスのコンストラクタです。
     *
     * @param context アプリケーションの情報。
     */
    public DatabaseOpenHelper(final Context context) {
        super(context, DATABASE_NAME, new CursorFactoryForDebug(), DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        final String methodName = "onCreate";
        Logger.Info.write(TAG, methodName, "START");

        this.createTable(database);
        this.initializeTable(database);

        Logger.Info.write(TAG, methodName, "END");
    }

    @Override
    public void onUpgrade(
            final SQLiteDatabase database,
            final int oldVersion,
            final int newVersion) {

        final String methodName = "onUpgrade";
        Logger.Info.write(TAG, methodName, "START");
        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * テーブルの作成処理を定義したメソッドです。
     * 当該メソッドはonCreateメソッドで使用されることを想定しています。
     *
     * @param database データベースオブジェクト。
     */
    private void createTable(final SQLiteDatabase database) {
        final String methodName = "createTable";
        Logger.Info.write(TAG, methodName, "START");

        this.performQuery(database, ASSET_FILE_CREATE_QUERY);

        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * 作成したテーブルに初期値を挿入する処理を定義したメソッドです。
     * 既にテーブルが作成されていない状態で呼び出された場合は必ず実行時に失敗します。
     * また、当該メソッドはonCreateメソッドで使用されることを想定しています。
     *
     * @param database データベースオブジェクト。
     */
    private void initializeTable(final SQLiteDatabase database) {
        final String methodName = "initializeTable";
        Logger.Info.write(TAG, methodName, "START");

        this.performQuery(database, ASSET_FILE_TRIGGER_QUERY);
        this.performQuery(database, ASSET_FILE_INSERT_QUERY);

        Logger.Info.write(TAG, methodName, "END");
    }

    /**
     * assetファイル内に存在するSQLファイルからSQLステートメントを取得し、
     * 取得したSQLステートメントを実行する処理を定義したメソッドです。
     *
     * @param fileName SQLファイルが存在するファイルの名前。
     * @param database データベースオブジェクト。
     */
    private void performQuery(final SQLiteDatabase database, final String fileName) {
        final String methodName = "performQuery";
        Logger.Info.write(TAG, methodName, "START");

        final AssetManager assetManager = this.context.getResources().getAssets();

        try {
            final String[] files = assetManager.list(fileName);

            if (files != null) {
                for (String file : files) {
                    final String fileDirectory = StringHandler.concatSequence(CommonConstants.CHAR_SEPARATOR_SLASH, fileName, file);
                    final String content = FileHandler.read(assetManager.open(fileDirectory));
                    final String[] sqlList = StringHandler.split(content, CommonConstants.CHAR_SEPARATOR_SLASH);
                    
                    Arrays.stream(sqlList).forEach(database::execSQL);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.Info.write(TAG, methodName, "END");
    }
}
