package com.bydhiva.dismaps.common

import com.bydhiva.dismaps.R
import retrofit2.HttpException
import java.io.IOException

class BadRequestNetworkException: Exception()
class ForbiddenNetworkException: Exception()
class NotFoundNetworkException: Exception()
class ConflictNetworkException: Exception()
class UnsupportedMediaNetworkException: Exception()
class TooManyRequestNetworkException: Exception()
class NetworkServerErrorException: Exception()
class NetworkServiceUnavailableException: Exception()
class UnexpectedNetworkException: Exception()
class ProvinceNotFoundException: Exception()

fun getExceptionByCode(code: Int) = when(code) {
    400 -> BadRequestNetworkException()
    403 -> ForbiddenNetworkException()
    404 -> NotFoundNetworkException()
    409 -> ConflictNetworkException()
    415 -> UnsupportedMediaNetworkException()
    429 -> TooManyRequestNetworkException()
    500 -> NetworkServerErrorException()
    503 -> NetworkServiceUnavailableException()
    else -> UnexpectedNetworkException()
}

fun Throwable.getExceptionMessageId() = when(this) {
    is BadRequestNetworkException -> R.string.bad_request_message
    is ForbiddenNetworkException -> R.string.forbidden_message
    is NotFoundNetworkException -> R.string.not_found_message
    is ConflictNetworkException -> R.string.conflict_message
    is UnsupportedMediaNetworkException -> R.string.unsupported_media_type_message
    is TooManyRequestNetworkException -> R.string.too_many_request_message
    is NetworkServerErrorException -> R.string.internal_server_error_message
    is NetworkServiceUnavailableException -> R.string.service_unavailable_message
    is IOException -> R.string.internet_error_message
    is HttpException -> R.string.http_error_message
    is ProvinceNotFoundException -> R.string.province_not_found
    else -> R.string.http_error_message
}