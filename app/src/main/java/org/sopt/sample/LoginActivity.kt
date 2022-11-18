package com.sopt.androidpractice


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.data.RequestLoginDTO
import org.sopt.sample.data.ResponseLoginDTO
import org.sopt.sample.data.ServicePool
import org.sopt.sample.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val authService = ServicePool.authService
    private var id: String? = null
    private var password: String? = null
    private var mbti: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setResultSignUp()
        clickLogin()
        clickSignUp()
    }

    private fun clickLogin() {
        binding.btnLogin.setOnClickListener {
            if (isLoginPattern()) {
                setUser()
                login()
            }
        }
    }

    private fun setUser() {
        id = binding.etId.text.toString()
        password = binding.etPassword.text.toString()
    }

    private fun login() {
        authService.login(
            RequestLoginDTO(binding.etId.text.toString(), binding.etPassword.text.toString())
        ).enqueue(object : Callback<ResponseLoginDTO> {
            override fun onResponse(
                call: Call<ResponseLoginDTO>, response: Response<ResponseLoginDTO>
            ) {
                if (response.code() == 200) {
                    Log.d("LOGIN-RESPONSE/SUCCESS", "login 성공!!, $response")
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    Toast.makeText(
                        this@LoginActivity, R.string.login_success, Toast.LENGTH_SHORT
                    ).show()
                    intent.putExtra(R.string.id.toString(), id)
                    intent.putExtra(R.string.name.toString(), mbti)
                    startActivity(intent)
                } else {
                    Log.d(
                        "LOGIN-RESPONSE/SUCCESS", "respone: $response, ${binding.etId.text}, ${binding.etPassword.text}"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseLoginDTO>, t: Throwable) {
                Log.d("LOGIN-RESPONSE/FAILURE", "login 실패 ㅠㅠ, ${t.message}")
                Snackbar.make(binding.root, R.string.login_failure, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun isLoginPattern(): Boolean {
        with(binding) {
            if (etId.text.length !in 6..20) {
                Snackbar.make(root, R.string.signup_id_input_error, Snackbar.LENGTH_SHORT).show()
                return false
            }
            if (etPassword.text.length !in 8..12) {
                Snackbar.make(root, R.string.signup_password_input_error, Snackbar.LENGTH_SHORT)
                    .show()
                return false
            } else return true
        }
    }

    private fun clickSignUp() {
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun setResultSignUp() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    id = result.data?.getStringExtra(R.string.id.toString()) ?: ""
                    password = result.data?.getStringExtra(R.string.password.toString()) ?: ""
                    mbti = result.data?.getStringExtra(R.string.name.toString()) ?: ""
                    Snackbar.make(binding.root, R.string.signup_success, Snackbar.LENGTH_SHORT)
                        .show()
                }
                with(binding) {
                    etId.setText(id)
                    etPassword.setText(password)
                }
            }
    }
}