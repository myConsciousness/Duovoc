package android.app.java.com.duovoc.model.holder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : OverviewTranslationHolder.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 論理モデル名「概要翻訳情報」を操作する際に使用するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see android.app.java.com.duovoc.model.OverviewTranslationInformation
 * @since 1.0
 */
public final class OverviewTranslationHolder {

    /**
     * 概要情報に紐付く識別IDを格納するフィールドです。
     *
     * @see #getId()
     * @see #setId(String)
     */
    private String id = "";

    /**
     * ヒントリストを格納するフィールドです。
     *
     * @see #getHints()
     * @see #setHints(List)
     */
    private List<String> hints = new ArrayList<>();

    /**
     * 概要情報に紐付く識別IDを返却するGetterメソッドです。
     *
     * @return 概要情報に紐付く識別ID。
     */
    public String getId() {
        return this.id;
    }

    /**
     * 概要情報に紐付く識別IDを設定するSetterメソッドです。
     *
     * @param id 概要情報に紐付く識別ID。
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ヒントリストを返却するGetterメソッドです。
     *
     * @return ヒントリスト。
     */
    public List<String> getHints() {
        return this.hints;
    }

    /**
     * ヒントリストを設定するSetterメソッドです。
     *
     * @param hints ヒントリスト。
     */
    public void setHints(List<String> hints) {
        this.hints = hints;
    }

    @Override
    public String toString() {
        return "OverviewTranslationHolder{" +
                "id='" + this.id + '\'' +
                ", hints=" + this.hints +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        OverviewTranslationHolder that = (OverviewTranslationHolder) o;
        return Objects.equals(this.getId(), that.getId()) &&
                Objects.equals(this.getHints(), that.getHints());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getHints());
    }
}
