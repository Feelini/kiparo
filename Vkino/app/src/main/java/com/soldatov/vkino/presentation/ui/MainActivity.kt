package com.soldatov.vkino.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        replaceHomeFragment()
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.home -> {
                    replaceHomeFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()
    }
}