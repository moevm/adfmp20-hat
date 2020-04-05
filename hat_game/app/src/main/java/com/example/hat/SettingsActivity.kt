package com.example.hat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.example.hat.entity.GameSettings
import com.example.hat.util.UtilHat
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initSpinner()

        button_to_players_names.setOnClickListener {
            val intent = Intent(this, PlayersNamesActivity::class.java)
            val gameSettings = GameSettings()
            gameSettings.secondsPerStep =
                spinner_time_for_step.selectedItem.toString().toLongOrNull()

            when {
                checkBoxEasy.isChecked -> gameSettings.vocabular =
                    UtilHat.getVocabular(applicationContext)?.easy
                checkBoxNorm.isChecked -> gameSettings.vocabular =
                    UtilHat.getVocabular(applicationContext)?.normal
                checkBoxHard.isChecked -> gameSettings.vocabular =
                    UtilHat.getVocabular(applicationContext)?.hard
            }

            intent.putExtra("gameSettings", gameSettings)
            startActivity(intent)
        }
    }

    fun initSpinner() {
        val items = arrayOf(15, 20, 30, 45, 60)
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        spinner_time_for_step.adapter = adapter
    }
}