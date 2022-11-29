package org.sopt.sample.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.login.LoginActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickSignUp()
        setCheckId()
        setCheckPassword()
    }

    private fun setCheckId() {
        binding.etId.doAfterTextChanged { text ->
            viewModel.checkPattern(text.toString(), 0)
        }
        viewModel.checkId.observe(this) { check ->
            with(binding) {
                if (check) {
                    tvIdErrorMsg.visibility = View.GONE
                    etId.backgroundTintList =
                        ContextCompat.getColorStateList(applicationContext, R.color.teal_200)
                } else {
                    tvIdErrorMsg.visibility = View.VISIBLE
                    etId.backgroundTintList =
                        ContextCompat.getColorStateList(applicationContext, R.color.red)
                }
            }
            setLoginBtn()
        }
    }

    private fun setCheckPassword() {
        binding.etPassword.doAfterTextChanged { text ->
            viewModel.checkPattern(text.toString(), 1)
        }
        viewModel.checkPassword.observe(this) { check ->
            with(binding) {
                if (check) {
                    tvPasswordErrorMsg.visibility = View.GONE
                    etPassword.backgroundTintList =
                        ContextCompat.getColorStateList(applicationContext, R.color.teal_200)
                } else {
                    tvPasswordErrorMsg.visibility = View.VISIBLE
                    etPassword.backgroundTintList =
                        ContextCompat.getColorStateList(applicationContext, R.color.red)
                }
            }
            setLoginBtn()
        }
    }

    private fun clickSignUp() {
        binding.btnSignup.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        viewModel.signUp(
            binding.etId.text.toString(),
            binding.etPassword.text.toString(),
            binding.etName.text.toString()
        )
        viewModel.successSignUp.observe(this) { success ->
            if (success) {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                intent.putExtra(R.string.id.toString(), binding.etId.text.toString())
                intent.putExtra(R.string.password.toString(), binding.etPassword.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Snackbar.make(binding.root, R.string.signup_failure, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setLoginBtn() {
        binding.btnSignup.isEnabled =
            viewModel.checkId.value == true && viewModel.checkPassword.value == true
    }
}
