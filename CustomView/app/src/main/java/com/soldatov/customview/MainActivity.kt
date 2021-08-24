package com.soldatov.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val equalizer: EqualizerView = findViewById(R.id.equalizer)
        val equalizerValues: TextView = findViewById(R.id.equalizerValues)

        equalizer.setEqualizerDataChangedListener(object: EqualizerView.OnEqualizerDataChangedListener{
            override fun onEqualizerDataChanged(values: ArrayList<Int>) {
                equalizerValues.text = mapListToString(values = values)
            }
        })

        equalizerValues.text = mapListToString(values = equalizer.getColumnsValues())
    }

    private fun mapListToString(values: ArrayList<Int>): String{
        var result = ""
        for (value in values){
            result += "$value, "
        }
        return result.substring(0, result.length - 2)
    }
}