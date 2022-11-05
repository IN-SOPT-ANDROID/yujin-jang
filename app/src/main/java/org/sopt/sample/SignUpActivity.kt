package com.sopt.androidpractice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickSignUp()
    }

    private fun clickSignUp() {
        with(binding) {
            btnSignup.setOnClickListener {
                if (etId.text.isNullOrBlank() || etPassword.text.isNullOrBlank() || etMbti.text.isNullOrBlank()) {
                    Snackbar.make(root, R.string.no_input_error, Snackbar.LENGTH_SHORT).show()
                } else if (etId.text.length < 6 || etId.text.length > 10) {
                    Snackbar.make(root, R.string.signup_id_input_error, Snackbar.LENGTH_SHORT)
                        .show()
                } else if (etPassword.text.length < 8 || etPassword.text.length > 12) {
                    Snackbar.make(root, R.string.signup_password_input_error, Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    intent.putExtra(R.string.id.toString(), etId.text.toString())
                    intent.putExtra(R.string.password.toString(), etPassword.text.toString())
                    intent.putExtra(R.string.mbti.toString(), etMbti.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }
}