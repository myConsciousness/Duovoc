package dev.app.ks.thinkit.duovoc.holder;

import java.util.Objects;

import dev.app.ks.thinkit.duovoc.adapter.OverviewTranslationAdapter;

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
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see OverviewTranslationAdapter
 * @since 1.0
 */
public final class HintSingleRow {

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
     * ヒントを返却するGetterメソッドです。
     *
     * @return ヒント。
     */
    public String getHint() {
        return this.hint;
    }

    /**
     * ヒントを設定するSetterメソッドです。
     *
     * @param hint ヒント。
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public String toString() {
        return "HintSingleRow{" +
                "id=" + this.id +
                ", hint='" + this.hint + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        HintSingleRow that = (HintSingleRow) o;
        return this.getId() == that.getId() &&
                Objects.equals(this.getHint(), that.getHint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getHint());
    }
}
