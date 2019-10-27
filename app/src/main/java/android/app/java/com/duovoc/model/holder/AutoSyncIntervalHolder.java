package android.app.java.com.duovoc.model.holder;

import android.app.java.com.duovoc.framework.model.holder.ModelAccessor;

import java.util.Objects;

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
 * @see android.app.java.com.duovoc.model.AutoSyncIntervalInformation
 * @since 1.0
 */
public final class AutoSyncIntervalHolder extends ModelAccessor {

    /**
     * 自動同期周期情報に紐づくユーザIDを格納するフィールドです。
     *
     * @see #getUserId()
     * @see #setUserId(String)
     */
    private String userId = "";

    /**
     * 概要情報の自動同期可否を格納するフィールドです。
     *
     * @see #getActivatedAutoSyncOverview()
     * @see #setActivatedAutoSyncOverview(String)
     */
    private String activatedAutoSyncOverview = "";

    /**
     * 概要情報の自動同期周期を格納するフィールドです。
     *
     * @see #getOverviewAutoSyncInterval()
     * @see #setOverviewAutoSyncInterval(int)
     */
    private int overviewAutoSyncInterval = 0;

    /**
     * ヒント情報の自動周期可否を格納するフィールドです。
     *
     * @see #getActivatedAutoSyncHint()
     * @see #setActivatedAutoSyncHint(String)
     */
    private String activatedAutoSyncHint = "";

    /**
     * ヒント情報の自動同期周期を格納するフィールドです。
     *
     * @see #getHintAutoSyncInterval()
     * @see #setHintAutoSyncInterval(int)
     */
    private int hintAutoSyncInterval = 0;

    /**
     * 自動同期周期情報に紐づくユーザIDを返却するGetterメソッドです。
     *
     * @return 自動同期周期情報に紐づくユーザID。
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 自動同期周期情報に紐づくユーザIDを設定するSetterメソッドです。
     *
     * @param userId 自動同期周期情報に紐づくユーザID。
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 概要情報の自動同期可否を返却するGetterメソッドです。
     *
     * @return 概要情報の自動同期可否。
     */
    public String getActivatedAutoSyncOverview() {
        return this.activatedAutoSyncOverview;
    }

    /**
     * 概要情報の自動同期可否を設定するSetterメソッドです。
     *
     * @param activatedAutoSyncOverview 概要情報の自動同期可否。
     */
    public void setActivatedAutoSyncOverview(String activatedAutoSyncOverview) {
        this.activatedAutoSyncOverview = activatedAutoSyncOverview;
    }

    /**
     * 概要情報の自動同期周期を返却するGetterメソッドです。
     *
     * @return 概要情報の自動同期周期。
     */
    public int getOverviewAutoSyncInterval() {
        return this.overviewAutoSyncInterval;
    }

    /**
     * 概要情報の自動同期周期を設定するSetterメソッドです。
     *
     * @param overview_auto_sync_interval 概要情報の自動同期周期。
     */
    public void setOverviewAutoSyncInterval(int overview_auto_sync_interval) {
        this.overviewAutoSyncInterval = overview_auto_sync_interval;
    }

    /**
     * ヒント情報の自動周期可否を返却するGetterメソッドです。
     *
     * @return ヒント情報の自動周期可否。
     */
    public String getActivatedAutoSyncHint() {
        return this.activatedAutoSyncHint;
    }

    /**
     * ヒント情報の自動周期可否を設定するSetterメソッドです。
     *
     * @param activatedAutoSyncHint ヒント情報の自動周期可否。
     */
    public void setActivatedAutoSyncHint(String activatedAutoSyncHint) {
        this.activatedAutoSyncHint = activatedAutoSyncHint;
    }

    /**
     * ヒント情報の自動同期周期を設定するGetterメソッドです。
     *
     * @return ヒント情報の自動同期周期。
     */
    public int getHintAutoSyncInterval() {
        return this.hintAutoSyncInterval;
    }

    /**
     * ヒント情報の自動同期周期を設定するSetterメソッドです。
     *
     * @param hintAutoSyncInterval ヒント情報の自動同期周期。
     */
    public void setHintAutoSyncInterval(int hintAutoSyncInterval) {
        this.hintAutoSyncInterval = hintAutoSyncInterval;
    }

    @Override
    public String toString() {
        return "AutoSyncIntervalHolder{" +
                "userId='" + this.userId + '\'' +
                ", activatedAutoSyncOverview='" + this.activatedAutoSyncOverview + '\'' +
                ", overview_auto_sync_interval=" + this.overviewAutoSyncInterval +
                ", activatedAutoSyncHint='" + this.activatedAutoSyncHint + '\'' +
                ", hintAutoSyncInterval=" + this.hintAutoSyncInterval +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        AutoSyncIntervalHolder that = (AutoSyncIntervalHolder) o;
        return this.getOverviewAutoSyncInterval() == that.getOverviewAutoSyncInterval() &&
                this.getHintAutoSyncInterval() == that.getHintAutoSyncInterval() &&
                Objects.equals(this.getUserId(), that.getUserId()) &&
                Objects.equals(this.getActivatedAutoSyncOverview(), that.getActivatedAutoSyncOverview()) &&
                Objects.equals(this.getActivatedAutoSyncHint(), that.getActivatedAutoSyncHint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.getUserId(),
                this.getActivatedAutoSyncOverview(),
                this.getOverviewAutoSyncInterval(),
                this.getActivatedAutoSyncHint(),
                this.getHintAutoSyncInterval());
    }
}
