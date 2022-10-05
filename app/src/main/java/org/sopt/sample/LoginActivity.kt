package com.sopt.androidpractice


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(){

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
        with(binding){
            loginBtn.setOnClickListener {
                if (idInput.text.length < 6 || idInput.text.length > 10) {
                    Snackbar.make(root, "아이디가 잘못되었습니다!", Snackbar.LENGTH_SHORT).show()
                }
                else if (passwordInput.text.length < 8 || passwordInput.text.length > 12) {
                    Snackbar.make(root, "비밀번호가 잘못되었습니다!", Snackbar.LENGTH_SHORT).show()
                }
                else {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    Toast.makeText(this@LoginActivity, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                    intent.putExtra("id", id)
                    intent.putExtra("mbti", mbti)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setResultSignUp() {
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK){
                id = result.data?.getStringExtra("id")?: ""
                password = result.data?.getStringExtra("password")?:""
                mbti = result.data?.getStringExtra("mbti")?:""
                binding.idInput.setText(id)
                binding.passwordInput.setText(password)
            }
        }
    }

    private fun clickSignUp() {
        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }
}