package com.sopt.androidpractice

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.LoginViewModel
import org.sopt.sample.R
import org.sopt.sample.data.ServicePool
import org.sopt.sample.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val authService = ServicePool.authService

    private val viewModel by viewModels<LoginViewModel>()

    // null이 들어가면 안 될 것 같아
    // null이면 버튼비활성화를 구현해놓으면 null 들어갈 일이 없을듯!!!
    private var id: String = ""
    private var password: String = ""
    private var mbti: String = ""

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
                viewModel.login(id, password)
                login()
            }
        }
    }

    private fun setUser() {
        id = binding.etId.text.toString()
        password = binding.etPassword.text.toString()
    }

    private fun login() {
        viewModel.successLogin.observe(this) { success ->
            if (success) {
                Log.d("LOGIN-RESPONSE/SUCCESS", "login 성공!!, ${viewModel.loginResult}")
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                Toast.makeText(this@LoginActivity, R.string.login_success, Toast.LENGTH_SHORT)
                    .show()
                intent.putExtra(R.string.id.toString(), id)
                intent.putExtra(R.string.name.toString(), mbti)
                startActivity(intent)
            } else {
                Log.d("LOGIN-RESPONSE/SUCCESS", "respone: ${viewModel.loginResult}")
            }
        }
    }

    private fun isLoginPattern(): Boolean {
        with(binding) {
            if (etId.text.length !in 6..20) {
                Snackbar.make(root, R.string.signup_id_input_error, Snackbar.LENGTH_SHORT)
                    .show()
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
