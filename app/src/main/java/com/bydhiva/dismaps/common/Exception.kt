package com.bydhiva.dismaps.common

import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.base.LibraryModule
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

fun Throwable.getExceptionMessage() = when(this) {
    is BadRequestNetworkException -> LibraryModule.application.getString(R.string.bad_request_message)
    is ForbiddenNetworkException -> LibraryModule.application.getString(R.string.forbidden_message)
    is NotFoundNetworkException -> LibraryModule.application.getString(R.string.not_found_message)
    is ConflictNetworkException -> LibraryModule.application.getString(R.string.conflict_message)
    is UnsupportedMediaNetworkException -> LibraryModule.application.getString(R.string.unsupported_media_type_message)
    is TooManyRequestNetworkException -> LibraryModule.application.getString(R.string.too_many_request_message)
    is NetworkServerErrorException -> LibraryModule.application.getString(R.string.internal_server_error_message)
    is NetworkServiceUnavailableException -> LibraryModule.application.getString(R.string.service_unavailable_message)
    is IOException -> LibraryModule.application.getString(R.string.internet_error_message)
    is HttpException -> LibraryModule.application.getString(R.string.http_error_message)
    is ProvinceNotFoundException -> LibraryModule.application.getString(R.string.http_error_message)
    else -> LibraryModule.application.getString(R.string.http_error_message)
}