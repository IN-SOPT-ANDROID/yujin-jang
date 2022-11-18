package org.sopt.sample.data

import retrofit2.Call
import retrofit2.http.GET

interface UserInfoService {
    @GET("api/users?page=2")
    fun getUserInfo(): Call<ResponseUserInfoDTO>
}
