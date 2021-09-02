package com.soldatov.covid.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soldatov.covid.R
import com.soldatov.covid.presentation.infolist.InfoFragment
import com.soldatov.covid.presentation.map.MapFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavView)
        setupUI()
    }

    private fun setupUI() {
        replaceMapFragment()
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.map -> {
                    replaceMapFragment()
                    true
                }
                R.id.info -> {
                    replaceInfoFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceInfoFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, InfoFragment())
            .commit()
    }

    private fun replaceMapFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MapFragment())
            .commit()
    }
}
