package android.app.java.com.duovoc.framework.communicate.property;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : HttpStatusCode.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * HTTPステータスコードを定義したEnumクラスです。
 * HTTPステータスコードは、HTTPにおいてWebサーバからのレスポンスの意味を表現する3桁の数字からなるコードです。
 * RFC 7231等によって定義され、IANAがHTTP Status Code Registryとして管理されています。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public enum HttpStatusCode {
    /**
    * 受理。
    * リクエストは受理されたが、処理は完了していない場合に返却されます。
    * <p>
    * 例えば、PUTメソッドでリソースを作成するリクエストを行ったときに
    * サーバがリクエストを受理したもののリソースの作成が完了していない場合に返却されます。
    * <p>
    * バッチ処理向けです。
    */
    HTTP_ACCEPTED(202),

    /**
    * 不正なゲートウェイ。
    * ゲートウェイ・プロキシサーバが不正なリクエストを受け取り、
    * 当該リクエストを拒否した場合に返却されます。
    */
    HTTP_BAD_GATEWAY(502),

    /**
    * 許可されていないメソッド。
    * 許可されていないリクエストメソッドを使用しようした場合に返却されます。
    * <p>
    * 例えば、POSTメソッドの使用が許されていない場所でPOSTメソッドを使用した場合に返却されます。
    */
    HTTP_BAD_METHOD(405),

    /**
    * リクエストが不正である。
    * 定義されていないメソッドを使うなど、
    * クライアントからの異常なリクエストが送信された場合に返却されます。
    */
    HTTP_BAD_REQUEST(400),

    /**
    * リクエストタイムアウト。
    * リクエストが時間以内に処理を完了することができなかった場合に返却されます。
    */
    HTTP_CLIENT_TIMEOUT(408),

    /**
    * 競合。
    * 送信されたリクエストに対して現在のリソースに異常が存在する場合に返却されます。
    * <p>
    * 例えば、パーミッションの設定が読み取り専用などに設定されているリソースに対して変更リクエストを送信した場合、
    * または存在しないコレクションの下にリソースの作成リクエストを送信した場合に返却されます。
    */
    HTTP_CONFLICT(409),

    /**
    * 作成。
    * リクエストされた処理が完了された場合に返却されます。
    * 新たに作成されたリソースのURIが返される。
    */
    HTTP_CREATED(201),

    /**
    * ペイロードが大きすぎる。
    * リクエストエンティティがサーバの許容範囲を超えている場合に返却されます。
    * <p>
    * 例えば、アップローダの上限を超えたデータを送信しようとした場合に返却されます。
    */
    HTTP_ENTITY_TOO_LARGE(413),

    /**
    * 禁止されている。
    * リソースにアクセスすることを拒否された場合に返却されます。
    * <p>
    * 例えば、リソースに対するアクセス権がない場合やホストがアクセス禁止処分を受けた場合などに返却されます。
    */
    HTTP_FORBIDDEN(403),

    /**
    * ゲートウェイタイムアウト。
    * ゲートウェイ・プロキシサーバがURIから推測されるサーバからの適切なレスポンスを受信できずタイムアウトした場合に返却されます。
    */
    HTTP_GATEWAY_TIMEOUT(504),

    /**
    * 消滅した。
    * リクエストされたリソースが恒久的に移動または消滅した場合に返却されます。
    * 404 Not Foundと類似しているが、410 Http Goneの場合はリソースが二度と復活しない場合に使われます。
    * ただし、このコードは特別に設定しないと提示できないため、
    * リソースが消滅しても404コードが返却される場合が多いです。
    */
    HTTP_GONE(410),

    /**
    * サーバ内部エラー。
    * リクエストされたデータの処理中にサーバ内部でエラーが発生した場合に返却されます。
    * <p>
    * 例えば、CGIとして動作させているプログラムに文法エラーが存在する、
    * または、設定に誤りが存在する等の場合に返却されます。
    */
    HTTP_INTERNAL_ERROR(500),

    /**
    * 長さが必要。
    * Content-Lengthヘッダが必須にもかかわらず、
    * Content-Lengthヘッダが存在しないリクエストに対してサーバがアクセスを拒否した場合に返却されます。
    */
    HTTP_LENGTH_REQUIRED(411),

    /**
    * 恒久的に移動した。
    * リクエストしたリソースが恒久的に移動されているときに返却される。
    * Location:ヘッダに移動先のURLが示されている。
    * <p>
    * ウェブサイトが移転した場合などに用いる。
    * ウェブサイト移転の特殊な場合として、HTTPからHTTPSへの移転も該当する。
    * <p>
    * その他、ファイルではなくディレクトリに対応するURLの末尾に/を書かずにアクセスした場合に返却される。
    * 例えば、https://www.w3.org/TR
    */
    HTTP_MOVED_PERM(301),

    /**
    * 発見した。
    * リクエストしたリソースが一時的に移動されているときに返される。
    * Location:ヘッダに移動先のURLが示されている。
    * <p>
    * 元々はMoved Temporarily（一時的に移動した）で、
    * 本来はリクエストしたリソースが一時的にそのURLに存在せず、
    * 別のURLにある場合に使用するステータスコードであった。
    * <p>
    * しかし、例えば掲示板やWikiなどで投稿後にウェブブラウザに対して
    * 他のURLに転送させたいときにもこのコードが使用されるようになったため、
    * 302コードはFoundになり、新たに303コードと307コードが作成された。
    *
    * @see #HTTP_SEE_OTHER
    */
    HTTP_MOVED_TEMP(302),

    /**
    * 複数の選択。
    * リクエストしたリソースが複数存在しユーザやユーザーエージェントに選択肢を提示する場合に返却される。
    * <p>
    * 例えば、W3Cの https://www.w3.org/TR/xhtml11/DTD/xhtml11.html
    */
    HTTP_MULT_CHOICE(300),

    /**
    * 受理できない。
    * Accept関連のヘッダに受理できない内容が含まれている場合に返却される。
    * <p>
    * 以下返却例。
    * 1, サーバは英語か日本語しか受け付けられないが、リクエストのAccept-Language:ヘッダにzh（中国語）しか含まれていなかった。
    * 2, サーバはapplication/pdfを送信したかったが、リクエストのAccept:ヘッダにapplication/pdfが含まれていなかった。
    * 3, サーバはUTF-8の文章を送信したかったが、リクエストのAccept-Charset:ヘッダには、UTF-8が含まれていなかった。
    */
    HTTP_NOT_ACCEPTABLE(406),

    /**
    * 信頼できない情報。
    * オリジナルのデータではなく、ローカルやプロキシ等からの情報である場合に返却される。
    */
    HTTP_NOT_AUTHORITATIVE(203),

    /**
    * 未検出。
    * リクエストに対してリソースを発見できなかった場合に返却される。
    * リソースを発見できなかった場合に以外にも、
    * リソースへのアクセス権がない場合などにも使用される。
    */
    HTTP_NOT_FOUND(404),

    /**
    * 実装されていない。
    * 実装されていないリクエストメソッドをサーバが検知した場合に返却される。
    * <p>
    * 例えば、WebDAVが実装されていないサーバに対してWebDAVで使用するメソッド（MOVEやCOPY）を使用した場合に返却される。
    */
    HTTP_NOT_IMPLEMENTED(501),

    /**
    * 未更新。
    * リクエストしたリソースが更新されていない場合に返却される。
    * <p>
    * 例えば、 If-Modified-Since:ヘッダを使用したリクエストを行い、
    * そのヘッダに示された時間以降に更新がなかった場合に返却される。
    */
    HTTP_NOT_MODIFIED(304),

    /**
    * 内容なし。
    * リクエストを受理したが返却すべきレスポンスエンティティが存在しない場合に返却される。
    * <p>
    * 例えば、POSTメソッドでフォームの内容を送信したがウェブブラウザの画面を更新しない場合に返却される。
    */
    HTTP_NO_CONTENT(204),

    /**
    * 正常。
    * 送信されたリクエストは成功し、レスポンスと共にリクエストに応じた情報が返却される。
    * 送信されたリクエストを正常に処理できた場合は、ほとんどのサーバが当該ステータスコードを返却している。
    */
    HTTP_OK(200),

    /**
    * 部分的内容。
    * 部分的GETリクエストをサーバが検知した場合に返却される。
    * <p>
    * 例えば、ダウンロードツール等で分割ダウンロードを行った場合やレジュームを行った場合に返却される。
    */
    HTTP_PARTIAL(206),

    /**
    * 支払いが必要である。
    * 現在は実装されておらず、将来のために予約されているとされるステータスコード（2019/11/01時点）。
    * Web Payments HTTP APIで利用が検討されていたものの、
    * Web Payments HTTP APIそのものの開発が中止されたため採用には至っていない。
    */
    HTTP_PAYMENT_REQUIRED(402),

    /**
    * 前提条件で失敗した。
    * 前提条件が真偽値における偽だった場合に返却される。
    * <p>
    * 例えば、リクエストのIf-Unmodified-Since:ヘッダに設定された時刻より後に更新があった場合に返却される。
    */
    HTTP_PRECON_FAILED(412),

    /**
    * プロキシ認証が必要である。
    * プロキシの認証が必要な場合に返却される。
    */
    HTTP_PROXY_AUTH(407),

    /**
    * URIが大きすぎる。
    * URIが長過ぎるためサーバが処理を拒否した場合に返却される。
    * <p>
    * 例えば、画像データのような大きなデータをGETメソッドで送ろうとし、
    * URIが何10kBにもなった場合に返す（上限値はサーバに依存する）。
    * <p>
    * RFC 2616以前では、Request-URI Too Long（リクエストURIが大きすぎる）と定義されていた。
    */
    HTTP_REQ_TOO_LONG(414),

    /**
    * 内容のリセット。
    * リクエストを受理しユーザエージェントの画面をリセットする場合に返却される。
    * <p>
    * 例えば、POSTメソッドでフォームの内容を送信した後に、
    * ウェブブラウザの画面を初期状態に戻す場合に返却される。
    */
    HTTP_RESET(205),

    /**
    * 他を参照せよ。
    * リクエストに対するレスポンスが他のURLに存在する場合に返却される。
    * <p>
    * Location:ヘッダに移動先のURLが示されている。
    * Location:ヘッダで指示されるリソースに対してGETでリクエストしなければならない
    * <p>
    * リクエストしたリソースは確かにそのURLにあるが、
    * 他のリソースをもってレスポンスとするような場合に使用する。
    *
    * @see #HTTP_MOVED_TEMP
    */
    HTTP_SEE_OTHER(303),

    /**
    * 認証が必要である。
    * Basic認証やDigest認証などを行うときに返却される。
    * 主なウェブブラウザは、レスポンスヘッダーWWW-Authenticateで処理可能な認証方式が指定されていれば認証ダイアログを表示する。
    */
    HTTP_UNAUTHORIZED(401),

    /**
    * サービス利用不可。
    * サービスが一時的に高トラフィックなどによる過負荷やメンテナンスで使用不可能である場合に返却される。
    */
    HTTP_UNAVAILABLE(503),

    /**
    * サポートされていないメディアタイプ。
    * リクエストで指定されたメディアタイプがサーバでサポートされていない場合に返却する。
    */
    HTTP_UNSUPPORTED_TYPE(415),

    /**
    * プロキシを使用せよ。
    * レスポンスのLocation:ヘッダに示されるプロキシを使用してリクエストを行わなければならない場合に返却される。
    */
    HTTP_USE_PROXY(305),

    /**
    * サポートされていないHTTPバージョン。
    * リクエストがサポートされていないHTTPバージョンである場合に返却される。
    */
    HTTP_VERSION_NOT_SUPPORTED(505),

    /**
    * 当該Enumクラスでのデフォルトステータスコードを表す値です。
    * アプリケーションの製造時に当該ステータスコードが発生した場合は、
    * 適宜エラーに対応したステータスコードを当該クラスへ追加してください。
    * そのため、プロダクトのランタイムにおいて当該ステータスコードが発生することはありません。
    */
    DEFAULT(0);

    /**
    * HTTPステータスのコード値を格納するフィールドです。
    */
    private int code;

    /**
    * 当該Enumクラスのコンストラクタです。
    *
    * @param code HTTPステータスのコード値。
    */
    HttpStatusCode(int code) {
        this.code = code;
    }

    /**
    * 入力されたコード値から当該Enumクラスに定義されたHTTPステータスを特定し返却する処理を定義したメソッドです。
    * コード値に紐づくHTTPステータスが存在しない場合はDEFAULを返却します。
    * アプリケーションの開発時にDEFAULTが返却された場合は適宜エラーに対応したHTTPステータスを当該クラスに追加してください。
    *
    * @param code HTTPステータスコードに紐づくコード値。
    * @see #HTTP_ACCEPTED
    * @see #HTTP_BAD_GATEWAY
    * @see #HTTP_BAD_METHOD
    * @see #HTTP_BAD_REQUEST
    * @see #HTTP_CLIENT_TIMEOUT
    * @see #HTTP_CONFLICT
    * @see #HTTP_CREATED
    * @see #HTTP_ENTITY_TOO_LARGE
    * @see #HTTP_FORBIDDEN
    * @see #HTTP_GATEWAY_TIMEOUT
    * @see #HTTP_GONE
    * @see #HTTP_INTERNAL_ERROR
    * @see #HTTP_LENGTH_REQUIRED
    * @see #HTTP_MOVED_PERM
    * @see #HTTP_MOVED_TEMP
    * @see #HTTP_MULT_CHOICE
    * @see #HTTP_NOT_ACCEPTABLE
    * @see #HTTP_NOT_AUTHORITATIVE
    * @see #HTTP_NOT_FOUND
    * @see #HTTP_NOT_IMPLEMENTED,
    * @see #HTTP_NOT_MODIFIED
    * @see #HTTP_NO_CONTENT
    * @see #HTTP_OK
    * @see #HTTP_PARTIAL
    * @see #HTTP_PAYMENT_REQUIRED
    * @see #HTTP_PRECON_FAILED
    * @see #HTTP_PROXY_AUTH
    * @see #HTTP_REQ_TOO_LONG
    * @see #HTTP_RESET
    * @see #HTTP_SEE_OTHER
    * @see #HTTP_UNAUTHORIZED
    * @see #HTTP_UNAVAILABLE
    * @see #HTTP_UNSUPPORTED_TYPE
    * @see #HTTP_USE_PROXY
    * @see #HTTP_VERSION_NOT_SUPPORTED
    */
    public static HttpStatusCode getStatusFromCode(final int code) {

        final HttpStatusCode[] httpStatusCodes = HttpStatusCode.values();

        for (HttpStatusCode httpStatusCode : httpStatusCodes) {
            if (httpStatusCode.code == code) {
                return httpStatusCode;
            }
        }

        return DEFAULT;
    }

    /**
    * 当該HTTPステータスの名称を返却します。
    *
    * @return HTTPステータスコードの名称。
    * @see #HTTP_ACCEPTED
    * @see #HTTP_BAD_GATEWAY
    * @see #HTTP_BAD_METHOD
    * @see #HTTP_BAD_REQUEST
    * @see #HTTP_CLIENT_TIMEOUT
    * @see #HTTP_CONFLICT
    * @see #HTTP_CREATED
    * @see #HTTP_ENTITY_TOO_LARGE
    * @see #HTTP_FORBIDDEN
    * @see #HTTP_GATEWAY_TIMEOUT
    * @see #HTTP_GONE
    * @see #HTTP_INTERNAL_ERROR
    * @see #HTTP_LENGTH_REQUIRED
    * @see #HTTP_MOVED_PERM
    * @see #HTTP_MOVED_TEMP
    * @see #HTTP_MULT_CHOICE
    * @see #HTTP_NOT_ACCEPTABLE
    * @see #HTTP_NOT_AUTHORITATIVE
    * @see #HTTP_NOT_FOUND
    * @see #HTTP_NOT_IMPLEMENTED,
    * @see #HTTP_NOT_MODIFIED
    * @see #HTTP_NO_CONTENT
    * @see #HTTP_OK
    * @see #HTTP_PARTIAL
    * @see #HTTP_PAYMENT_REQUIRED
    * @see #HTTP_PRECON_FAILED
    * @see #HTTP_PROXY_AUTH
    * @see #HTTP_REQ_TOO_LONG
    * @see #HTTP_RESET
    * @see #HTTP_SEE_OTHER
    * @see #HTTP_UNAUTHORIZED
    * @see #HTTP_UNAVAILABLE
    * @see #HTTP_UNSUPPORTED_TYPE
    * @see #HTTP_USE_PROXY
    * @see #HTTP_VERSION_NOT_SUPPORTED
    */
    public String getStatusName() {
        return this.name();
    }

    /**
    * 当該HTTPステータスのコード値を返却します。
    *
    * @return HTTPステータスのコード値。
    * @see #HTTP_ACCEPTED
    * @see #HTTP_BAD_GATEWAY
    * @see #HTTP_BAD_METHOD
    * @see #HTTP_BAD_REQUEST
    * @see #HTTP_CLIENT_TIMEOUT
    * @see #HTTP_CONFLICT
    * @see #HTTP_CREATED
    * @see #HTTP_ENTITY_TOO_LARGE
    * @see #HTTP_FORBIDDEN
    * @see #HTTP_GATEWAY_TIMEOUT
    * @see #HTTP_GONE
    * @see #HTTP_INTERNAL_ERROR
    * @see #HTTP_LENGTH_REQUIRED
    * @see #HTTP_MOVED_PERM
    * @see #HTTP_MOVED_TEMP
    * @see #HTTP_MULT_CHOICE
    * @see #HTTP_NOT_ACCEPTABLE
    * @see #HTTP_NOT_AUTHORITATIVE
    * @see #HTTP_NOT_FOUND
    * @see #HTTP_NOT_IMPLEMENTED,
    * @see #HTTP_NOT_MODIFIED
    * @see #HTTP_NO_CONTENT
    * @see #HTTP_OK
    * @see #HTTP_PARTIAL
    * @see #HTTP_PAYMENT_REQUIRED
    * @see #HTTP_PRECON_FAILED
    * @see #HTTP_PROXY_AUTH
    * @see #HTTP_REQ_TOO_LONG
    * @see #HTTP_RESET
    * @see #HTTP_SEE_OTHER
    * @see #HTTP_UNAUTHORIZED
    * @see #HTTP_UNAVAILABLE
    * @see #HTTP_UNSUPPORTED_TYPE
    * @see #HTTP_USE_PROXY
    * @see #HTTP_VERSION_NOT_SUPPORTED
    */
    public int getStatusCode() {
        return this.code;
    }
}
