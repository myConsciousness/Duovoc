package dev.app.ks.thinkit.duovoc.framework.communicate.property;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : IRequestQuery.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * リクエスト時のクエリパラメータに関する基本的な規格を定義したインターフェースクラスです。
 * リクエスト時のクエリパラメータを定義するクラスは当該インターフェースを実装する必要があります。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public interface IRequestQuery {

    /**
     * クエリパラメータの名称を返却します。
     *
     * @return クエリパラメータの名称。
     */
    String getQueryName();
}
