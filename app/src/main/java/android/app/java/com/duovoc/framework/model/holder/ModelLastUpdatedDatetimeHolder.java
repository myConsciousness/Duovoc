package android.app.java.com.duovoc.framework.model.holder;

import android.app.java.com.duovoc.framework.model.ModelLastUpdatedDatetimeInformation;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : ModelLastUpdatedDatetimeHolder.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 論理モデル名「モデル最終更新日時情報」を操作する際に使用するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see ModelLastUpdatedDatetimeInformation
 * @since 1.0
 */
public final class ModelLastUpdatedDatetimeHolder extends ModelAccessor {

    /**
     * モデル物理名を格納するフィールドです。
     *
     * @see #getModelPhysicalName()
     * @see #setModelPhysicalName(String)
     */
    private String modelPhysicalName = "";

    /**
     * 最終更新日時を格納するフィールドです。
     *
     * @see #getLastUpdatedDatetime()
     * @see #setLastUpdatedDatetime(String)
     */
    private String lastUpdatedDatetime = "";

    /**
     * 最終更新日付を格納するフィールドです。
     *
     * @see #getLastUpdatedDate()
     * @see #setLastUpdatedDate(String)
     */
    private String lastUpdatedDate = "";

    /**
     * 最終更新時間を格納するフィールドです。
     *
     * @see #getLastUpdatedTime()
     * @see #setLastUpdatedTime(String)
     */
    private String lastUpdatedTime = "";

    /**
     * モデル物理名を返却するGetterメソッドです。
     *
     * @return モデル物理名。
     */
    public String getModelPhysicalName() {
        return this.modelPhysicalName;
    }

    /**
     * モデル物理名を設定するSetterメソッドです。
     *
     * @param modelPhysicalName モデル物理名。
     */
    public void setModelPhysicalName(String modelPhysicalName) {
        this.modelPhysicalName = modelPhysicalName;
    }

    /**
     * 最終更新日時を返却するGetterメソッドです。
     *
     * @return 最終更新日時
     */
    public String getLastUpdatedDatetime() {
        return this.lastUpdatedDatetime;
    }

    /**
     * 最終更新日時を設定するSetterメソッドです。
     *
     * @param lastUpdatedDatetime 最終更新日時。
     */
    public void setLastUpdatedDatetime(String lastUpdatedDatetime) {
        this.lastUpdatedDatetime = lastUpdatedDatetime;
    }

    /**
     * 最終更新日付を返却するGetterメソッドです。
     *
     * @return 最終更新日付。
     */
    public String getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    /**
     * 最終更新日付を設定するSetterメソッドです。
     */
    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    /**
     * 最終更新時間を返却するGetterメソッドです。
     *
     * @return 最終更新時間。
     */
    public String getLastUpdatedTime() {
        return this.lastUpdatedTime;
    }

    /**
     * 最終更新時間を設定するSetterメソッドです。
     *
     * @param lastUpdatedTime 最終更新時間。
     */
    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
