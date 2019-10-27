package android.app.java.com.duovoc.model.property;

import android.app.java.com.duovoc.framework.CalendarHandler;
import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.CursorHandler;
import android.app.java.com.duovoc.model.holder.AutoSyncIntervalHolder;
import android.content.ContentValues;
import android.database.Cursor;

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
     * 物理カラム名「user_id」を表す項目です。
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
     * @see Key#user_id
     */
    UserId(Key.user_id) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<AutoSyncIntervalColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, AutoSyncIntervalHolder autoSyncIntervalHolder) {
            contentValues.put(this.getKeyName(), autoSyncIntervalHolder.getUserId());
        }
    },

    /**
     * 物理カラム名「activated_auto_sync_overview」を表す項目です。
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
     * @see Key#user_id
     */
    ActivatedAutoSyncOverview(Key.activated_auto_sync_overview) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<AutoSyncIntervalColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, AutoSyncIntervalHolder autoSyncIntervalHolder) {
            contentValues.put(this.getKeyName(), autoSyncIntervalHolder.getActivatedAutoSyncOverview());
        }
    },

    /**
     * 物理カラム名「overview_auto_sync_interval」を表す項目です。
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
     * @see Key#user_id
     */
    OverviewAutoSyncInterval(Key.overview_auto_sync_interval) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<AutoSyncIntervalColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getIntegerOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, AutoSyncIntervalHolder autoSyncIntervalHolder) {
            contentValues.put(this.getKeyName(), autoSyncIntervalHolder.getOverviewAutoSyncInterval());
        }
    },

    /**
     * 物理カラム名「activated_auto_sync_hint」を表す項目です。
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
     * @see Key#user_id
     */
    ActivatedAutoSyncHint(Key.activated_auto_sync_hint) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<AutoSyncIntervalColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, AutoSyncIntervalHolder autoSyncIntervalHolder) {
            contentValues.put(this.getKeyName(), autoSyncIntervalHolder.getActivatedAutoSyncHint());
        }
    },

    /**
     * 物理カラム名「hint_auto_sync_interval」を表す項目です。
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
     * @see Key#user_id
     */
    HintAutoSyncInterval(Key.hint_auto_sync_interval) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<AutoSyncIntervalColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getIntegerOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, AutoSyncIntervalHolder autoSyncIntervalHolder) {
            contentValues.put(this.getKeyName(), autoSyncIntervalHolder.getHintAutoSyncInterval());
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
     * @see Key#user_id
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
     * @see Key#user_id
     * @see Key#activated_auto_sync_overview
     * @see Key#overview_auto_sync_interval
     * @see Key#activated_auto_sync_hint
     * @see Key#hint_auto_sync_interval
     * @see Key#modified_datetime
     */
    private Key key;

    /**
     * 当該Enumのコンストラクタです。
     *
     * @see #getKeyName()
     * @see Key#user_id
     * @see Key#activated_auto_sync_overview
     * @see Key#overview_auto_sync_interval
     * @see Key#activated_auto_sync_hint
     * @see Key#hint_auto_sync_interval
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
        user_id,
        activated_auto_sync_overview,
        overview_auto_sync_interval,
        activated_auto_sync_hint,
        hint_auto_sync_interval,
        modified_datetime,
    }
}
