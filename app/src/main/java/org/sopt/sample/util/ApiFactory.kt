package org.sopt.sample.util

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sopt.sample.util.ApiFactory.retrofit
import org.sopt.sample.util.ApiFactory.retrofit_reqres
import org.sopt.sample.data.service.AuthService
import org.sopt.sample.data.service.UserInfoService
import retrofit2.Retrofit

object ApiFactory {
    const val SOPT_BASE_URL: String = "http://3.39.169.52:3000/"
    const val REQRES_BASE_URL: String = "https://reqres.in/"

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SOPT_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    val retrofit_reqres by lazy {
        Retrofit.Builder()
            .baseUrl(REQRES_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    inline fun <reified T> create(retrofit: Retrofit): T = retrofit.create<T>(T::class.java)
}

object ServicePool {
    val authService = ApiFactory.create<AuthService>(retrofit)
    val userInfoService = ApiFactory.create<UserInfoService>(retrofit_reqres)
}
