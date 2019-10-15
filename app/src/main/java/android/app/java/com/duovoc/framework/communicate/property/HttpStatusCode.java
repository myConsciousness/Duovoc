package android.app.java.com.duovoc.framework.communicate.property;

public enum HttpStatusCode {
    HTTP_ACCEPTED(202),
    HTTP_BAD_GATEWAY(502),
    HTTP_BAD_METHOD(405),
    HTTP_BAD_REQUEST(400),
    HTTP_CLIENT_TIMEOUT(408),
    HTTP_CONFLICT(409),
    HTTP_CREATED(201),
    HTTP_ENTITY_TOO_LARGE(413),
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
    HTTP_VERSION(505);

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

        return HTTP_NOT_FOUND;
    }

    public String getStatusName() {
        return this.name();
    }

    public int getStatusCode() {
        return this.code;
    }
}
