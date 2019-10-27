package android.app.java.com.duovoc.model.holder;

import android.app.java.com.duovoc.framework.model.holder.ModelAccessor;

import java.util.Objects;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : SwitchLanguageHolder.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 学習情報変更APIの実行時に使用するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public final class SwitchLanguageHolder extends ModelAccessor {

    /**
     * 過去のレッスン受講有無を格納するフィールドです。
     *
     * @see #isFirstTime()
     * @see #setFirstTime(boolean)
     */
    private boolean firstTime = false;

    /**
     * 過去のレッスンの受講有無を返却するGetterメソッドです。
     *
     * @return 過去にレッスンを受けたことがない場合は {@code true}, それ以外は{@code false}
     */
    public boolean isFirstTime() {
        return this.firstTime;
    }

    /**
     * 過去のレッスンの受講有無を設定するSetterメソッドです。
     *
     * @param firstTime 過去のレッスンの受講有無
     */
    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    @Override
    public String toString() {
        return "SwitchLanguageHolder{" +
                "firstTime=" + this.firstTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        SwitchLanguageHolder that = (SwitchLanguageHolder) o;
        return this.isFirstTime() == that.isFirstTime();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.isFirstTime());
    }
}
