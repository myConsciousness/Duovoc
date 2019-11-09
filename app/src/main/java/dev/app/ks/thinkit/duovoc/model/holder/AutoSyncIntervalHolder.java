package dev.app.ks.thinkit.duovoc.model.holder;

import java.util.Objects;

import dev.app.ks.thinkit.duovoc.framework.model.holder.ModelAccessor;
import dev.app.ks.thinkit.duovoc.model.AutoSyncIntervalInformation;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : .java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 論理モデル名「自動同期周期情報」を操作する際に使用するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see AutoSyncIntervalInformation
 * @since 1.0
 */
public final class AutoSyncIntervalHolder extends ModelAccessor {

    /**
     * 項目名を格納するフィールドです。
     *
     * @see #getItemName()
     * @see #setItemName(String)
     */
    private String itemName = "";

    /**
     * 同期周期を格納するフィールドです。
     *
     * @see #getSyncInterval()
     * @see #setSyncInterval(int)
     */
    private int syncInterval = 0;

    /**
     * 項目名を返却するGetterメソッドです。
     *
     * @return 項目名。
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * 項目名を設定するSetterメソッドです。
     *
     * @param itemName 項目名。
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 同期周期を返却するGetterメソッドです。
     *
     * @return 同期周期。
     */
    public int getSyncInterval() {
        return this.syncInterval;
    }

    /**
     * 同期周期を設定するSetterメソッドです。
     *
     * @param syncInterval 同期周期。
     */
    public void setSyncInterval(int syncInterval) {
        this.syncInterval = syncInterval;
    }

    @Override
    public String toString() {
        return "AutoSyncIntervalHolder{" +
                "itemName='" + this.itemName + '\'' +
                ", syncInterval=" + this.syncInterval +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        AutoSyncIntervalHolder that = (AutoSyncIntervalHolder) o;
        return this.getSyncInterval() == that.getSyncInterval() &&
                Objects.equals(this.getItemName(), that.getItemName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getItemName(), this.getSyncInterval());
    }
}
