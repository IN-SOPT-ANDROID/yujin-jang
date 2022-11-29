package org.sopt.sample.data.service

import org.sopt.sample.data.entity.request.RequestLoginDTO
import org.sopt.sample.data.entity.request.RequestSignUpDTO
import org.sopt.sample.data.entity.response.ResponseLoginDTO
import org.sopt.sample.data.entity.response.ResponseSignUpDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/user/signin")
    fun login(
        @Body request: RequestLoginDTO
    ): Call<ResponseLoginDTO>

    @POST("api/user/signup")
    fun signup(
        @Body request: RequestSignUpDTO
    ): Call<ResponseSignUpDTO>
}
