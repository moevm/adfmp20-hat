package com.example.hat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.hat.entity.GameSettings
import kotlinx.android.synthetic.main.activity_add_word.*
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val gameSettings: GameSettings = intent.getSerializableExtra("gameSettings") as GameSettings
        updateTextFields(gameSettings)
    }

    private fun updateTextFields(gameSettings: GameSettings) {
        textViewTime.text = gameSettings.secondsPerStep.toString()
        textViewNumWords.text = gameSettings.vocabular?.size.toString()
        textViewComName.text = gameSettings.team1
    }
}
