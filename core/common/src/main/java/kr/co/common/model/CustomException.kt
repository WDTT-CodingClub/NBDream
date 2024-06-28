package kr.co.common.model

import java.io.IOException

class CustomException(
    message: String? = null,
    val customError: CustomErrorType? = null,
    val throwable: Throwable? = null
) : IOException(message)

enum class CustomErrorType(val errorCode: Int) {
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    PAYMENT_REQUIRED(402),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    NOT_ACCEPTABLE(406),
    PROXY_AUTHENTICATION_REQUIRED(407),
    REQUEST_TIMEOUT(408),
    CONFLICT(409),
    GONE(410),
    LENGTH_REQUIRED(411),
    PRECONDITION_FAILED(412),
    REQUEST_ENTITY_TOO_LARGE(413),
    REQUEST_URI_TOO_LARGE(414),
    UNSUPPORTED_MEDIA_TYPE(415),
    RANGE_NOT_SATISFIABLE(416),
    EXPECTATION_FAILED(417),
    UNPROCESSABLE_ENTITY(422),
    LOCKED(423),
    FAILED_DEPENDENCY(424),
    UPGRADE_REQUIRED(426),
    PRECONDITION_REQUIRED(428),
    TOO_MANY_REQUESTS(429),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431),
    CONNECTION_CLOSED_WITHOUT_RESPONSE(444),
    UNAVAILABLE_FOR_LEGAL_REASONS(451),
    UNKNOWN(-1)
    ;

    companion object {
        val customError = entries.associateBy(CustomErrorType::errorCode)
    }
}