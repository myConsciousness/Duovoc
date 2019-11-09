package dev.app.ks.thinkit.duovoc.model.property;

import android.content.ContentValues;
import android.database.Cursor;

import dev.app.ks.thinkit.duovoc.framework.CalendarHandler;
import dev.app.ks.thinkit.duovoc.framework.IModelMapKey;
import dev.app.ks.thinkit.duovoc.framework.ModelMap;
import dev.app.ks.thinkit.duovoc.framework.model.CursorHandler;
import dev.app.ks.thinkit.duovoc.model.holder.AutoSyncIntervalHolder;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : AutoSyncIntervalColumnKey.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 論理モデル名「自動同期周期情報」のカラム項目を操作する処理を定義したEnumクラスです。
 * 概要情報を操作する際には当該Enumクラスを使用します。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public enum AutoSyncIntervalColumnKey implements IModelMapKey {

    /**
     * 物理カラム名「item_name」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<AutoSyncIntervalColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, AutoSyncIntervalHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, AutoSyncIntervalHolder)
     * @see Key#item_name
     */
    ItemName(Key.item_name) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<AutoSyncIntervalColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getIntegerOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, AutoSyncIntervalHolder autoSyncIntervalHolder) {
            contentValues.put(this.getKeyName(), autoSyncIntervalHolder.getItemName());
        }
    },

    /**
     * 物理カラム名「sync_interval」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<AutoSyncIntervalColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, AutoSyncIntervalHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, AutoSyncIntervalHolder)
     * @see Key#sync_interval
     */
    SyncInterval(Key.sync_interval) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<AutoSyncIntervalColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getIntegerOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, AutoSyncIntervalHolder autoSyncIntervalHolder) {
            contentValues.put(this.getKeyName(), autoSyncIntervalHolder.getSyncInterval());
        }
    },

    /**
     * 物理カラム名「modified_datetime」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<AutoSyncIntervalColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, AutoSyncIntervalHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, AutoSyncIntervalHolder)
     * @see Key#modified_datetime
     */
    ModifiedDatetime(Key.modified_datetime) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<AutoSyncIntervalColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, AutoSyncIntervalHolder autoSyncIntervalHolder) {
            final String currentClientDatetime = CalendarHandler.getClientDatetime();
            contentValues.put(this.getKeyName(), currentClientDatetime);
        }
    };

    /**
     * カラムの物理名を格納するフィールドです。
     *
     * @see #getKeyName()
     * @see Key#item_name
     * @see Key#sync_interval
     * @see Key#modified_datetime
     */
    private Key key;

    /**
     * 当該Enumのコンストラクタです。
     *
     * @see #getKeyName()
     * @see Key#item_name
     * @see Key#sync_interval
     * @see Key#modified_datetime
     */
    AutoSyncIntervalColumnKey(Key key) {
        this.key = key;
    }

    /**
     * 当該項目に紐付くカラムの物理名を文字列として返却します。
     *
     * @return カラムの物理名。
     */
    public String getKeyName() {
        return this.key.name();
    }

    /**
     * モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * 当該Enumクラスの項目は当該抽象メソッドを必ず実装する必要があります。
     *
     * @param cursor   カーソルオブジェクト。
     * @param modelMap 値を設定するモデルマップ。
     */
    public abstract void setModelMap(Cursor cursor, ModelMap<AutoSyncIntervalColumnKey, Object> modelMap);

    /**
     * モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     * 当該Enumクラスの項目は当該抽象メソッドを必ず実装する必要があります。
     *
     * @param contentValues          挿入情報を保持するオブジェクト。
     * @param autoSyncIntervalHolder 自動同期周期情報を保持するオブジェクト。
     */
    public abstract void setContentValues(ContentValues contentValues, AutoSyncIntervalHolder autoSyncIntervalHolder);

    /**
     * 論理モデル名「自動同期周期情報」のカラムに紐付く物理名を定義したEnumクラスです。
     * 概要情報のカラムに紐付く物理名は必ず当該Enumクラスへ定義する必要があります。
     *
     * @see #getKeyName()
     */
    private enum Key {
        item_name,
        sync_interval,
        modified_datetime,
    }
}
