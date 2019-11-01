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
    HTTP_GATEWAY_TIMEOUT(504),
    HTTP_GONE(410),
    HTTP_INTERNAL_ERROR(500),
    HTTP_LENGTH_REQUIRED(411),
    HTTP_MOVED_PERM(301),
    HTTP_MOVED_TEMP(302),
    HTTP_MULT_CHOICE(300),
    HTTP_NOT_ACCEPTABLE(406),
    HTTP_NOT_AUTHORITATIVE(203),
    HTTP_NOT_FOUND(404),
    HTTP_NOT_IMPLEMENTED(501),
    HTTP_NOT_MODIFIED(304),
    HTTP_NO_CONTENT(204),
    HTTP_OK(200),
    HTTP_PARTIAL(206),
    HTTP_PAYMENT_REQUIRED(402),
    HTTP_PRECON_FAILED(412),
    HTTP_PROXY_AUTH(407),
    HTTP_REQ_TOO_LONG(414),
    HTTP_RESET(205),
    HTTP_SEE_OTHER(303),
    HTTP_UNAUTHORIZED(401),
    HTTP_UNAVAILABLE(503),
    HTTP_UNSUPPORTED_TYPE(415),
    HTTP_USE_PROXY(305),
    HTTP_VERSION(505),
    DEFAULT(0);

    private int code;

    HttpStatusCode(int code) {
        this.code = code;
    }

    public static HttpStatusCode getStatusFromCode(final int code) {

        final HttpStatusCode[] httpStatusCodes = HttpStatusCode.values();

        for (HttpStatusCode httpStatusCode : httpStatusCodes) {
            if (httpStatusCode.code == code) {
                return httpStatusCode;
            }
        }

        return DEFAULT;
    }

    public String getStatusName() {
        return this.name();
    }

    public int getStatusCode() {
        return this.code;
    }
}
