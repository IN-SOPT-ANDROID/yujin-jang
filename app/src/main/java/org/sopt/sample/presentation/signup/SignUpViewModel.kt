package org.sopt.sample.presentation.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.entity.request.RequestSignUpDTO
import org.sopt.sample.data.entity.response.ResponseSignUpDTO
import org.sopt.sample.util.ServicePool.authService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    private val _signUpResult: MutableLiveData<ResponseSignUpDTO> = MutableLiveData()
    var signUpResult: LiveData<ResponseSignUpDTO> = _signUpResult

    private val _successSignUp = MutableLiveData<Boolean>()
    val successSignUp: LiveData<Boolean> = _successSignUp

    fun signUp(id: String, password: String, name: String) {
        authService.signup(
            RequestSignUpDTO(id, password, name)
        ).enqueue(object : Callback<ResponseSignUpDTO> {
            override fun onResponse(
                call: Call<ResponseSignUpDTO>,
                response: Response<ResponseSignUpDTO>
            ) {
                if (response.code() == 201 && response.isSuccessful) {
                    _successSignUp.value = true
                    _signUpResult.value = response.body()
                } else {
                    _successSignUp.value = false
                    Log.d("SignUP 실패", response.message())
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDTO>, t: Throwable) {
                _successSignUp.value = false
                Log.d("SignUP 서버 통신 실패", t.message.toString())
            }
        })
    }
}
