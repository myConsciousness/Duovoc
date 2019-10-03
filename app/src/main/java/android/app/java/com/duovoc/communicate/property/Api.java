package android.app.java.com.duovoc.communicate.property;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : Api.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * API情報を定義したEnumクラスです。
 * APIを処理中で使用する際には当該Enumクラスに定義された項目を使用します。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public enum Api {
    /**
     * ログイン認証APIを表す項目です。
     * 当該項目は以下のURLを文字列として保持します。
     * "https://www.duolingo.com/login"
     *
     * @see #getUrl()
     */
    Login("https://www.duolingo.com/login"),

    /**
     * 概要情報取得APIを表す項目です。
     * 当該項目は以下のURLを文字列として保持します。
     * "https://www.duolingo.com/vocabulary/overview"
     *
     * @see #getUrl()
     */
    Overview("https://www.duolingo.com/vocabulary/overview"),

    /**
     * ヒント情報取得APIを表す項目です。
     * 当該項目は以下のURLを文字列として保持します。
     * "https://d2.duolingo.com/words/hints/%s/%s"
     * <p>
     * 当該項目が保持するURL文字列に以下の値を設定する必要があります。
     * 1, 学習している言語の略
     * 2, レッスン名
     *
     * @see #getUrl()
     */
    OverviewTranslation("https://d2.duolingo.com/words/hints/%s/%s");

    /**
     * URLを格納するフィールドです。
     *
     * @see #getUrl()
     */
    private String url;

    /**
     * 当該Enumのコンストラクタです。
     *
     * @param url url。
     */
    Api(String url) {
        this.url = url;
    }

    /**
     * 当該項目のURL文字列を返却するGetterメソッドです。
     *
     * @return URL文字列。
     * @see #Login
     * @see #Overview
     * @see #OverviewTranslation
     */
    public String getUrl() {
        return this.url;
    }
}
