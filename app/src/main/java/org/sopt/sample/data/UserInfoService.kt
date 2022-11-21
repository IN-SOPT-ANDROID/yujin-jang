package org.sopt.sample.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserInfoService {
    @GET("api/users")
    fun getUserInfo(@Query("page") page: Int): Call<ResponseUserInfoDTO>
}