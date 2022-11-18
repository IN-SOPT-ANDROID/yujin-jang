package org.sopt.sample.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.sopt.sample.data.ApiFactory.retrofit
import org.sopt.sample.data.ApiFactory.retrofit_reqres
import retrofit2.Retrofit

object ApiFactory {
    const val SOPT_BASE_URL: String = "http://3.39.169.52:3000/"
    const val REQRES_BASE_URL: String = "https://reqres.in/"

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SOPT_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val retrofit_reqres by lazy {
        Retrofit.Builder()
            .baseUrl(REQRES_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(retrofit: Retrofit): T = retrofit.create<T>(T::class.java)
}

object ServicePool {
    val authService = ApiFactory.create<AuthService>(retrofit)
    val userInfoService = ApiFactory.create<UserInfoService>(retrofit_reqres)
}
