package com.sopt.androidpractice


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

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
        with(binding) {
            btnLogin.setOnClickListener {
                if (idInput.text.length !in 6..10) {
                    Snackbar.make(root, R.string.id_input_error, Snackbar.LENGTH_SHORT).show()
                } else if (etPassword.text.length !in 8..12) {
                    Snackbar.make(root, R.string.password_input_error, Snackbar.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    Toast.makeText(this@LoginActivity, R.string.login_success, Toast.LENGTH_SHORT)
                        .show()
                    intent.putExtra(R.string.id.toString(), id)
                    intent.putExtra(R.string.mbti.toString(), mbti)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setResultSignUp() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    id = result.data?.getStringExtra(R.string.id.toString()) ?: ""
                    password = result.data?.getStringExtra(R.string.password.toString()) ?: ""
                    mbti = result.data?.getStringExtra(R.string.mbti.toString()) ?: ""
                    binding.idInput.setText(id)
                    binding.etPassword.setText(password)
                    Snackbar.make(binding.root, R.string.signup_success, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun clickSignUp() {
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }
}