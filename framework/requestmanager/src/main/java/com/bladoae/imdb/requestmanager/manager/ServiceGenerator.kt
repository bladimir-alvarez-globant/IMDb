package com.bladoae.imdb.requestmanager.manager

import com.bladoae.imdb.base.common.API_RESPONSE_CONTENT_NOT_FOUND
import com.bladoae.imdb.base.common.API_RESPONSE_CONTENT_UNAUTHORIZED
import com.bladoae.imdb.requestmanager.ApiService
import com.bladoae.imdb.requestmanager.utils.ApiResponse
import com.bladoae.imdb.requestmanager.utils.GenericErrorApiResponse
import com.bladoae.imdb.requestmanager.utils.NetworkConnectivity
import com.bladoae.imdb.requestmanager.utils.SuccessApiResponse
import com.squareup.moshi.Moshi
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val timeoutRead = 60   //In seconds
private const val timeoutConnect = 60   //In seconds

class ServiceGenerator(
    private val baseUrl: String,
    private val networkConnectivity: NetworkConnectivity,
    private val moshi: Moshi
) {

    private lateinit var retrofit: Retrofit

    init {
        setupRetrofit()
    }

    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder().apply {
        connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
        readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)
    }

    private fun setupRetrofit() {
        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
    }

    fun createService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    suspend fun <Data> processCallWithError(responseCall: suspend () -> Response<Data>): ApiResponse<Data?> {
        if (!networkConnectivity.isConnected()) {
            return GenericErrorApiResponse(
                500,
                this.networkConnectivity.getNetworkInfo().toString()
            )
        }
        var response: Response<Data>? = null
        return try {
            response = responseCall.invoke()
            when {
                response.isSuccessful -> {
                    SuccessApiResponse(response.body())
                }
                response.code() == API_RESPONSE_CONTENT_NOT_FOUND -> {
                    GenericErrorApiResponse(API_RESPONSE_CONTENT_NOT_FOUND, "")
                }
                response.code() == API_RESPONSE_CONTENT_UNAUTHORIZED -> {
                    GenericErrorApiResponse(API_RESPONSE_CONTENT_UNAUTHORIZED, "")
                }
                else -> {
                    GenericErrorApiResponse(-2, "")
                }
            }
        } catch (npe: NullPointerException) {
            GenericErrorApiResponse(-2, npe.message)
        } catch (ex: Exception) {
            GenericErrorApiResponse(-2, ex.message)
        }
    }

}