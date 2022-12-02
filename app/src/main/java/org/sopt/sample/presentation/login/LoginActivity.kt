package org.sopt.sample.presentation.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.presentation.main.MainActivity
import org.sopt.sample.presentation.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val viewModel by viewModels<LoginViewModel>()

    // null이 들어가면 안 될 것 같아
    // null이면 버튼비활성화를 구현해놓으면 null 들어갈 일이 없을듯!!!
    private var id: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickLogin()
        clickSignUp()
        setResultSignUp()
    }

    private fun clickLogin() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etId.text.toString(), binding.etPassword.text.toString())
            onSuccessLogin()
        }
    }

    private fun onSuccessLogin() {
        viewModel.successLogin.observe(this) { success ->
            if (success) {
                Toast.makeText(this@LoginActivity, R.string.login_success, Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(this, MainActivity::class.java))
            }
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
