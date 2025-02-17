package com.musinsa.category.support

import com.musinsa.category.support.exception.BadRequestError
import com.musinsa.category.support.exception.InternalServerError
import com.musinsa.category.support.exception.MusinsaException
import com.musinsa.category.support.exception.NotFoundError
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(MusinsaException::class)
    fun handleException(e: MusinsaException, response: HttpServletResponse): ErrorResponse {
        logger.error(e) { e.message }
        return e.toErrorResponse().also {
            response.status = it.code
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun badRequestError(e: Exception, response: HttpServletResponse): ErrorResponse {
        logger.error(e) { e.message }
        return BadRequestError().toErrorResponse().also {
            response.status = it.code
        }
    }


    @ExceptionHandler(NoResourceFoundException::class)
    fun notFoundError(e: Exception, response: HttpServletResponse): ErrorResponse {
        logger.error(e) { e.message }
        return NotFoundError().toErrorResponse().also {
            response.status = it.code
        }
    }

    @ExceptionHandler(Exception::class)
    fun internalServerError(e: Exception, response: HttpServletResponse): ErrorResponse {
        logger.error(e) { e.message }
        return InternalServerError().toErrorResponse().also {
            response.status = it.code
        }
    }
}

fun MusinsaException.toErrorResponse() = ErrorResponse(code, message)
data class ErrorResponse(val code: Int, val message: String)
