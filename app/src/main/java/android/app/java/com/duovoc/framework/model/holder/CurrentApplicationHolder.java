package android.app.java.com.duovoc.framework.model.holder;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : CurrentApplicationHolder.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 論理モデル名「カレントアプリケーション情報」を操作する際に使用するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see android.app.java.com.duovoc.model.CurrentApplicationInformation
 * @since 1.0
 */
final public class CurrentApplicationHolder {

    /**
     * コンフィグ名を格納するフィールドです。
     *
     * @see #getConfigName()
     * @see #setConfigName(String)
     */
    private String configName = "";

    /**
     * コンフィグ値を格納するフィールドです。
     *
     * @see #getConfigValue()
     * @see #configValue
     */
    private String configValue = "";

    /**
     * コンフィグ名を返却するGetterメソッドです。
     *
     * @return コンフィグ名。
     */
    public String getConfigName() {
        return this.configName;
    }

    /**
     * コンフィグ名を設定するSetterメソッドです。
     *
     * @param configName コンフィグ名。
     */
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    /**
     * コンフィグ値を返却するGetterメソッドです。
     *
     * @return コンフィグ値。
     */
    public String getConfigValue() {
        return this.configValue;
    }

    /**
     * コンフィグ値を設定するSetterメソッドです」。
     *
     * @param configValue コンフィグ値。
     */
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
