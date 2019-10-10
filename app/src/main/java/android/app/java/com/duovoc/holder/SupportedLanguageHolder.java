package android.app.java.com.duovoc.holder;

import android.app.java.com.duovoc.framework.model.holder.ModelAccessor;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : SupportedLanguageHolder.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 論理モデル名「サポート言語情報」を操作する際に使用するデータクラスです。
 * 当該データクラスの値の設定及び取得は、
 * GetterメソッドとSetterメソッドを介して行われます。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see android.app.java.com.duovoc.model.SupportedLanguageInformation
 * @since 1.0
 */
final public class SupportedLanguageHolder extends ModelAccessor {

    /**
     * 学習時に使用している言語区分を格納するフィールドです。
     *
     * @see #getFromLanguage()
     * @see #setFromLanguage(String)
     */
    private String fromLanguage = "";

    /**
     * 学習中の言語区分のリストを格納するフィールドです。
     *
     * @see #getLearningLanguageList()
     * @see #setLearningLanguageList(List)
     */
    private List<String> learningLanguageList = new ArrayList<>();

    /**
     * 学習時に使用している言語区分を返却するGetterメソッドです。
     *
     * @return 学習時に使用している言語区分。
     */
    public String getFromLanguage() {
        return this.fromLanguage;
    }

    /**
     * 学習時に使用している言語区分を設定するSetterメソッドです。
     *
     * @param fromLanguage 学習時に使用している言語区分。
     */
    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    /**
     * 学習中の言語区分のリストを返却するGetterメソッドです。
     *
     * @return 学習中の言語区分のリスト。
     */
    public List<String> getLearningLanguageList() {
        return this.learningLanguageList;
    }

    /**
     * 学習中の言語区分のリストを設定するSetterメソッドです。
     *
     * @param learningLanguageList 学習中の言語区分のリスト。
     */
    public void setLearningLanguageList(List<String> learningLanguageList) {
        this.learningLanguageList = learningLanguageList;
    }
}
