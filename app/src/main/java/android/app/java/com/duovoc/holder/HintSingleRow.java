package android.app.java.com.duovoc.holder;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : HintSingleRow.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 詳細画面におけるヒントリストの単一行を表現するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます*
 *
 * @author Kato Shinya
 * @version 1.0
 * @see android.app.java.com.duovoc.adapter.OverviewTranslationAdapter
 * @since 1.0
 */
final public class HintSingleRow {

    /**
     * 当該項目に紐付くIDを格納するフィールドです。
     *
     * @see #getId()
     * @see #setId(long)
     */
    private long id = 0L;

    /**
     * ヒントを格納するフィールドです。
     *
     * @see #getHint()
     * @see #setHint(String)
     */
    private String hint = "";

    /**
     * 当該項目に紐付くIDを返却するGetterメソッドです。
     *
     * @return 当該項目に紐付くID。
     */
    public long getId() {
        return id;
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
     * ヒントを返却するGetterメソッドです。
     *
     * @return ヒント。
     */
    public String getHint() {
        return hint;
    }

    /**
     * ヒントを設定するSetterメソッドです。
     *
     * @param hint ヒント。
     */
    public void setHint(String hint) {
        this.hint = hint;
    }
}
