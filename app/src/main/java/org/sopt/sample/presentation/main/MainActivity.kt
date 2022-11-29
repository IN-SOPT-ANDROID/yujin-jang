package org.sopt.sample.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.presentation.gallery.GalleryFragment
import org.sopt.sample.presentation.home.HomeFragment
import org.sopt.sample.presentation.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.home_container)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.home_container, HomeFragment.newInstance())
                .commit()
        }

        onNavSelect()
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
}
