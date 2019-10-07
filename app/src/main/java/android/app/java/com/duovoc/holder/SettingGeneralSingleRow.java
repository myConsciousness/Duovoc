package android.app.java.com.duovoc.holder;

import android.app.java.com.duovoc.adapter.SettingGeneralAdapter;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : SettingGeneralSingleRow.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 設定画面における総合設定リストの単一行を表現するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see SettingGeneralAdapter
 * @since 1.0
 */
final public class SettingGeneralSingleRow {

    /**
     * 当該項目に紐付くIDを格納するフィールドです。
     *
     * @see #getId()
     * @see #setId(long)
     */
    private long id = 0L;

    /**
     * 設定項目のタイトルを格納するフィールドです。
     *
     * @see #getTitle()
     * @see #setTitle(String)
     */
    private String title = "";

    /**
     * 設定項目のサマリを格納するフィールドです。
     *
     * @see #getSummary()
     * @see #setSummary(String)
     */
    private String summary = "";

    /**
     * 当該項目に紐付くIDを返却するGetterメソッドです。
     *
     * @return 当該項目に紐付くID。
     */
    public long getId() {
        return this.id;
    }

    /**
     * 当該項目に紐付くIDを設定するSetterメソッドです。
     *
     * @param id 当該項目に紐付くID。
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 設定項目のタイトルを返却するGetterメソッドです。
     *
     * @return 設定項目のタイトル。
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 設定項目のタイトルを設定するSetterメソッドです。
     *
     * @param title 設定項目のタイトル。
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 設定項目のサマリを返却するGetterメソッドです。
     *
     * @return 設定項目のサマリ。
     */
    public String getSummary() {
        return this.summary;
    }

    /**
     * 設定項目のサマリを設定するSetterメソッドです。
     *
     * @param summary 設定項目のサマリ。
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }
}
