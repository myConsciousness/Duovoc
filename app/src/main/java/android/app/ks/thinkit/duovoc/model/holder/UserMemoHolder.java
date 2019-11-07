package android.app.ks.thinkit.duovoc.model.holder;

import android.app.ks.thinkit.duovoc.framework.model.holder.ModelAccessor;

import java.util.Objects;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : UserMemoHolder.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/10/26
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 論理モデル名「ユーザメモ情報」を操作する際に使用するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see android.app.ks.thinkit.duovoc.model.UserMemoInformation
 * @since 1.0
 */
public final class UserMemoHolder extends ModelAccessor {

    /**
     * メモ情報に紐付くユーザIDを格納するフィールド。
     *
     * @see #getUserId
     * @see #setUserId(String)
     */
    private String userId = "";

    /**
     * メモ情報に紐付く概要IDを格納するフィールド。
     *
     * @see #getOverviewId
     * @see #setOverviewId(String)
     */
    private String overviewId = "";

    /**
     * メモを格納するフィールド。
     *
     * @see #getMemo
     * @see #setMemo(String)
     */
    private String memo = "";

    /**
     * メモ情報に紐付くユーザIDを返却するGetterメソッドです。
     *
     * @return メモ情報に紐付くユーザID。
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * メモ情報に紐付くユーザIDを設定するSetterメソッドです。
     *
     * @param userId メモ情報に紐付くユーザID。
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * メモ情報に紐付く概要IDを返却するGetterメソッドです。
     *
     * @return メモ情報に紐付く概要ID。
     */
    public String getOverviewId() {
        return this.overviewId;
    }

    /**
     * メモ情報に紐付く概要IDを設定するSetterメソッドです。
     *
     * @param overviewId メモ情報に紐付く概要ID。
     */
    public void setOverviewId(String overviewId) {
        this.overviewId = overviewId;
    }

    /**
     * メモを返却するGetterメソッドです。
     *
     * @return メモ。
     */
    public String getMemo() {
        return this.memo;
    }

    /**
     * メモを設定するSetterメソッドです。
     *
     * @param memo メモ。
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "UserMemoHolder{" +
                "userId='" + this.userId + '\'' +
                ", overviewId='" + this.overviewId + '\'' +
                ", memo='" + this.memo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        UserMemoHolder that = (UserMemoHolder) o;
        return Objects.equals(this.getUserId(), that.getUserId()) &&
                Objects.equals(this.getOverviewId(), that.getOverviewId()) &&
                Objects.equals(this.getMemo(), that.getMemo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId(), this.getOverviewId(), this.getMemo());
    }
}
