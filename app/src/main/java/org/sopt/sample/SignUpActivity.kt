package com.sopt.androidpractice

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.data.RequestSignUpDTO
import org.sopt.sample.data.ResponseSignUpDTO
import org.sopt.sample.data.ServicePool
import org.sopt.sample.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val authService = ServicePool.authService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickSignUp()
        setClickable()
    }

    private fun clickSignUp() {
        binding.btnSignup.setOnClickListener {
            if (isSignUpPattern()) {
                signUp()
            }
        }
    }

    private fun signUp() {
        authService.signup(
            RequestSignUpDTO(
                binding.etId.text.toString(),
                binding.etPassword.text.toString(),
                binding.etName.text.toString()
            )
        ).enqueue(object : Callback<ResponseSignUpDTO> {
            override fun onResponse(
                call: Call<ResponseSignUpDTO>,
                response: Response<ResponseSignUpDTO>
            ) {
                if (response.code() == 201) {
                    Log.d("SIGNUP-RESPONSE/SUCCESS", "signup 성공!! , $response")
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    intent.putExtra(R.string.id.toString(), binding.etId.text.toString())
                    intent.putExtra(
                        R.string.password.toString(),
                        binding.etPassword.text.toString()
                    )
                    intent.putExtra(R.string.name.toString(), binding.etName.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    Log.d(
                        "SIGNUP-RESPONSE/SUCCESS",
                        "respone: $response"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDTO>, t: Throwable) {
                Log.d("SIGNUP-RESPONSE/FAILURE", "signup 실패 ㅠㅠ, ${t.message}")
                Snackbar.make(
                    binding.root,
                    R.string.signup_failure,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun isSignUpPattern(): Boolean {
        with(binding) {
            if (etId.text.isNullOrBlank() || etPassword.text.isNullOrBlank() || etName.text.isNullOrBlank()) {
                Snackbar.make(root, R.string.no_input_error, Snackbar.LENGTH_SHORT).show()
                return false
            } else if (etId.text.length !in 6..20) {
                Snackbar.make(root, R.string.signup_id_input_error, Snackbar.LENGTH_SHORT).show()
                return false
            } else if (etPassword.text.length !in 8..12) {
                Snackbar.make(root, R.string.signup_password_input_error, Snackbar.LENGTH_SHORT)
                    .show()
                return false
            } else return true
        }
    }

    private fun setClickable() {
        val signUpTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                with(binding) {
                    btnSignup.isEnabled =
                        etId.text.isNotEmpty() && etPassword.text.isNotEmpty() && etName.text.isNotEmpty()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        with(binding) {
            etId.addTextChangedListener(signUpTextWatcher)
            etPassword.addTextChangedListener(signUpTextWatcher)
            etName.addTextChangedListener(signUpTextWatcher)
        }
    }
}