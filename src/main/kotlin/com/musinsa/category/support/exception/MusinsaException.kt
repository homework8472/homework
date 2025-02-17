package com.musinsa.category.support.exception

import org.springframework.http.HttpStatus

sealed class MusinsaException(
    val code: Int,
    val httpStatus: HttpStatus,
    override val cause: Throwable? = null,
    override val message: String
) : RuntimeException(message, cause)

class BadRequestError(code: Int = 400, httpStatus: HttpStatus = HttpStatus.BAD_REQUEST, cause: Throwable? = null, message: String = "잘못 된 요청") : MusinsaException(code, httpStatus, cause, message)
class NotFoundError(code: Int = 404, httpStatus: HttpStatus = HttpStatus.NOT_FOUND, cause: Throwable? = null, message: String = "찾을 수 없습니다.") : MusinsaException(code, httpStatus, cause, message)
class EntityNotFoundError(code: Int = 404, httpStatus: HttpStatus = HttpStatus.NOT_FOUND, cause: Throwable? = null, message: String) : MusinsaException(code, httpStatus, cause, message)
class InternalServerError(code: Int = 500, httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR, cause: Throwable? = null, message: String = "알 수 없는 에러.") : MusinsaException(code, httpStatus, cause, message)
