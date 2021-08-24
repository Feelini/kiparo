package com.soldatov.covid.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.soldatov.covid.R
import androidx.lifecycle.ViewModelProvider
import com.soldatov.covid.domain.models.CovidInfo
import com.soldatov.covid.presentation.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private var viewModel: MainActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModelFactory = ViewModelFactory(application)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        viewModel!!.getCovidInfo()?.observe(this) { covidInfo ->
            show(covidInfo)
        }
        viewModel?.fetchCovidInfo()
    }

    fun show(covidInfo: CovidInfo){
        Log.DEBUG
    }
}