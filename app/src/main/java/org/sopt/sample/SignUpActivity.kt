package com.sopt.androidpractice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickSignUp()
    }

    private fun clickSignUp(){
        with(binding){
            signUpBtn.setOnClickListener {
                if (idInput.length() == 0 || passwordInput.length() == 0 || mbtiInput.length() == 0) {
                    Snackbar.make(root, "입력하지 않은 정보가 있습니다.", Snackbar.LENGTH_SHORT).show()
                }
                else if (idInput.text.length < 6 || idInput.text.length > 10) {
                    Snackbar.make(root, "아이디는 6 ~ 10글자로 설정해야 합니다.", Snackbar.LENGTH_SHORT).show()
                }
                else if (passwordInput.text.length < 8 || passwordInput.text.length > 12) {
                    Snackbar.make(root, "비밀번호는 8 ~ 12글자로 설정해야 합니다.", Snackbar.LENGTH_SHORT).show()
                }
                else {
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    intent.putExtra("id", idInput.text.toString())
                    intent.putExtra("password", passwordInput.text.toString())
                    intent.putExtra("mbti", mbtiInput.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }
}