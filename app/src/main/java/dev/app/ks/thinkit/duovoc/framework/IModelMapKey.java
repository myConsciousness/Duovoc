package dev.app.ks.thinkit.duovoc.framework;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : IModelMapKey.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * テーブルのカラムに紐付くカラムを操作する処理を定義するインターフェースです。
 * カラム情報を操作する処理を定義する場合は当該インターフェースを必ず実装する必要があります。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public interface IModelMapKey {

    /**
     * テーブルのカラムに紐付く物理キー名を取得する処理を定義するメソッドです。
     *
     * @return テーブルのカラムに紐付く物理キー名。
     */
    String getKeyName();
}
