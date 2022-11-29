package org.sopt.sample.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.login.LoginActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewmodel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignup.isEnabled = true
        clickSignUp()
    }

    private fun clickSignUp() {
        binding.btnSignup.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        viewmodel.signUp(
            binding.etId.text.toString(),
            binding.etPassword.text.toString(),
            binding.etName.text.toString()
        )
        viewmodel.successSignUp.observe(this) { success ->
            if (success) {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                intent.putExtra(R.string.id.toString(), binding.etId.text.toString())
                intent.putExtra(R.string.password.toString(), binding.etPassword.text.toString())
                setResult(AppCompatActivity.RESULT_OK, intent)
                finish()
            } else {
                Snackbar.make(binding.root, R.string.signup_failure, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}