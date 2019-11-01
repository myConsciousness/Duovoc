package android.app.java.com.duovoc.framework.communicate.property;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : RequestMethod.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * HTTPリクエストメソッドを定義したEnumクラスです。
 * <p>
 * Hypertext Transfer Protocol（ハイパーテキスト・トランスファー・プロトコル、略称 HTTP）とは、
 * HTMLなどのコンテンツの送受信に用いられる通信プロトコルです。
 * 主としてWorld Wide Webにおいて、WebブラウザとWebサーバとの間での転送に用いられます。
 * 日本標準仕様書ではハイパテキスト転送プロトコルとも呼ばれます。
 * <p>
 * HTTP/1.1 が RFC 7230 から RFC 7235 で規定されています（2019/11/01時点）。
 * かつては RFC 2616 が HTTP/1.1 を規定していたため、こちらもよく参照されています。
 * また、HTTP/2が RFC 7540 で規定されています。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public enum RequestMethod {

    /**
    * クライアントがサーバにデータを送信するためのメソッドです。
    * Webフォームや電子掲示板への投稿などで使用されます。
    *
    * @see RequestMethod#POST
    */
    Post(MethodName.POST),

    /**
    * 指定されたURIのリソースを取得するためのメソッドです。
    * HTTPの最も基本的な動作であり、HTTP/0.9では唯一のメソッドになります。
    *
    * @see RequestMethod#GET
    */
    Get(MethodName.GET),

    /**
    * GETと類似していますが、
    * HEADメソッドを使用されたリクエストに対してサーバはHTTPヘッダのみを返却します。
    * <p>
    * HEADメソッドを使用することでクライアントはWebページを取得せずに、
    * そのWebページが存在するかどうかを知ることが可能です。
    * 例えば、Webページのリンク先が生きているか、データを全て取得することなく検証することができます。
    *
    * @see RequestMethod#HEAD
    */
    Head(MethodName.HEAD),

    /**
    * 指定したURIにリソースを保存するためのメソッドです。
    * URIが指し示すリソースが存在しない場合は、サーバはそのURIにリソースを作成します。
    * クライアントからサーバへの画像のアップロードなどで使用されます。
    *
    * @see RequestMethod#PUT
    */
    Put(MethodName.PUT),

    /**
    * 指定したURIのリソースを削除するためのメソッドです。
    *
    * @see RequestMethod#DELETE
    */
    Delete(MethodName.DELETE),

    /**
    * TCPトンネルを接続するためのメソッドです。
    * 暗号化したメッセージをプロキシサーバを経由して転送する際に使用します。
    * <p>
    * 当初、ネットスケープコミュニケーションズによって考案されたものが、
    * IETFドラフトTunneling TCP based protocols through Web proxy serversとして公開されRFC 2817 に取り込まれました。
    * その後、RFC 7230 で定義が更新されています。
    *
    * @see RequestMethod#CONNECT
    */
    Connect(MethodName.CONNECT),

    /**
    * サーバを調査するためのメソッドです。
    * 例えば、サーバがサポートしているHTTPバージョンなどを取得することができます。
    *
    * @see RequestMethod#OPTIONS
    */
    Options(MethodName.OPTIONS),

    /**
    * サーバまでのネットワーク経路をチェックするためのメソッドです。
    * サーバは受け取ったメッセージのそれ自体をレスポンスのデータにコピーして応答します。
    * WindowsのTracertやUNIXのTracerouteとよく似た動作をします。
    *
    * @see RequestMethod#TRACE
    */
    Trace(MethodName.TRACE);

    /**
    * メソッド名を格納するためのフィールドです。
    *
    * @see MethodName
    */
    private MethodName methodName;

    /**
    * 当該Enumクラスのコンストラクタです。
    *
    * @param methodName リクエストメソッドの名称。
    */
    RequestMethod(MethodName methodName) {
        this.methodName = methodName;
    }

    /**
    * リクエストメソッドの名称を定義したインナーEnumクラスです。
    *
    * @see #Post
    * @see #Get
    * @see #Head
    * @see #Put
    * @see #Delete
    * @see #Connect
    * @see #Options
    * @see #Trace
    */
    private enum MethodName {
        POST,
        GET,
        HEAD,
        PUT,
        DELETE,
        CONNECT,
        OPTIONS,
        TRACE,
    }

    /**
    * リクエストメソッドの名称を返却します。
    *
    * @return リクエストメソッドの名称。
    * @see #Post
    * @see #Get
    * @see #Head
    * @see #Put
    * @see #Delete
    * @see #Connect
    * @see #Options
    * @see #Trace
    */
    public String getMethodName() {
        return this.methodName.name();
    }
}
