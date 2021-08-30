package com.soldatov.jokeabout.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.soldatov.jokeabout.R
import com.soldatov.jokeabout.domain.models.Data
import com.soldatov.jokeabout.presentation.utils.UseCaseProvider

class JokeAboutActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var save: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joke_about)

        val saveDataUseCase = UseCaseProvider.provideSaveDataUseCase(this)

        radioGroup = findViewById(R.id.radioGroup)
        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_name)
        save = findViewById(R.id.confirm_button)

        save.setOnClickListener {
            val data = Data(getTimeInSec(), firstName.text.toString(), lastName.text.toString())
            saveDataUseCase.execute(data)
        }
    }

    private fun getTimeInSec(): Int {
        val radioButtonID: Int = radioGroup.checkedRadioButtonId
        val radioButton: View = radioGroup.findViewById(radioButtonID)

        return when (radioGroup.indexOfChild(radioButton)) {
            0 -> 30
            1 -> 60
            else -> 3600
        }
    }
}