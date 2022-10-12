package com.sopt.androidpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.sopt.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.profileNameTv.text = "이름: " + intent.getStringExtra("id")
        binding.profileMbtiTv.text = "MBTI: " + intent.getStringExtra("mbti")
    }
}