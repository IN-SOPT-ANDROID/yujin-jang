package org.sopt.sample.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.util.ServicePool.authService
import org.sopt.sample.data.entity.request.RequestLoginDTO
import org.sopt.sample.data.entity.response.ResponseLoginDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _loginResult: MutableLiveData<ResponseLoginDTO> = MutableLiveData()
    val loginResult: LiveData<ResponseLoginDTO> = _loginResult

    private val _successLogin = MutableLiveData<Boolean>()
    val successLogin: LiveData<Boolean> = _successLogin

    fun login(email: String, password: String) {
        authService.login(
            RequestLoginDTO(email, password)
        ).enqueue(object : Callback<ResponseLoginDTO> {
            override fun onResponse(
                call: Call<ResponseLoginDTO>,
                response: Response<ResponseLoginDTO>
            ) {
                if (response.code() == 200) {
                    _successLogin.value = true
                    _loginResult.value = response.body()
                } else {
                    _successLogin.value = false
                }
            }

            override fun onFailure(call: Call<ResponseLoginDTO>, t: Throwable) {
            }
        })
    }
}
