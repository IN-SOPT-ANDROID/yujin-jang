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
    private val _successSignUp = MutableLiveData<Boolean>()
    val successSignUp: LiveData<Boolean> = _successSignUp

    private val _checkId = MutableLiveData<Boolean>()
    val checkId: LiveData<Boolean> = _checkId

    private val _checkPassword = MutableLiveData<Boolean>()
    val checkPassword: LiveData<Boolean> = _checkPassword

    private val idPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,10}\$")
    private val passwordPattern =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#\$%^&*()+|=])[A-Za-z\\d~!@#\$%^&*()+|=]{6,12}\$")

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

    fun checkPattern(text: String, type: Int) {
        when (type) {
            ID -> _checkId.value = (text.length in 6..10 && idPattern.matches(text)) || text.isEmpty()
            PASSWORD -> _checkPassword.value = (text.length in 6..12 && passwordPattern.matches(text)) || text.isEmpty()
        }
    }

    companion object {
        private val ID = 0
        private val PASSWORD = 1
    }
}
