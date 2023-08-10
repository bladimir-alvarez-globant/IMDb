package com.bladoae.imdb.requestmanager.utils

import com.squareup.moshi.Json

sealed class ApiResponse<out Data>

data class ErrorApiResponse(
    @Json(name = "errorCode") private val errorCodePrivate: Int? = null,
    @Json(name = "message") private val messagePrivate: String? = null,
    @Json(name = "errors") val errorsList: List<Errors>? = null
) : ApiResponse<Nothing>() {

    val errors: List<Errors> = if (errorsList.isNullOrEmpty()) {
        listOf(Errors(errorCodePrivate ?: -1, "", messagePrivate ?: ""))
    } else {
        errorsList
    }

    val errorCode = errorCodePrivate ?: errors.first().code
    val message = messagePrivate ?: errors.first().errorDetails
    val field = errors.first().field
}

data class SuccessApiResponse<out Data>(val body: Data) : ApiResponse<Data>()

data class GenericErrorApiResponse(val statusCode: Int, val message: String? = "") :
    ApiResponse<Nothing>()

data class Errors(
    val code: Int,
    val field: String,
    val errorDetails: String
)