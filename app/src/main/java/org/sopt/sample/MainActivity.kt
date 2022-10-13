package com.sopt.androidpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sopt.seminar2_test.home.GalleryFragment
import com.sopt.seminar2_test.home.SearchFragment
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 실습 코드
        val currentFragment = supportFragmentManager.findFragmentById(R.id.home_container)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.home_container, HomeFragment.newInstance())
                .commit()
        }

        onNavSelect()
//        setProfile()
    }

    private fun onNavSelect() {
        binding.bnvNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> replaceFragment(HomeFragment())
                R.id.menu_gallery -> replaceFragment(GalleryFragment())
                R.id.menu_search -> replaceFragment(SearchFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_container, fragment)
        transaction.commit()
    }

    //    private fun setProfile() {
//        binding.profileNameTv.text = "이름: " + intent.getStringExtra("id")
//        binding.profileMbtiTv.text = "MBTI: " + intent.getStringExtra("mbti")
//    }
}