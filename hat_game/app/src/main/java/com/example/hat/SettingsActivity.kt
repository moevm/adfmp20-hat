package com.example.hat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.example.hat.entity.GameSettings
import com.example.hat.entity.Vocabular
import com.example.hat.util.UtilHat
import kotlinx.android.synthetic.main.activity_settings.*
import java.lang.Exception


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initSpinnerForSeconds()

        val vocabular: Vocabular? = UtilHat.getVocabular(applicationContext)
        val minVocabularSize = arrayOf(
            vocabular?.easy?.size,
            vocabular?.normal?.size,
            vocabular?.hard?.size
        ).minBy { it ?: 0 }
        try {
            initSpinnerWords(minVocabularSize)
        } catch (e: Exception) {

        }
        button_to_players_names.setOnClickListener {
            val intent = Intent(this, PlayersNamesActivity::class.java)
            val gameSettings = GameSettings()
            gameSettings.secondsPerStep =
                spinner_time_for_step.selectedItem.toString().toLongOrNull()

            gameSettings.wordsInVocabular =
                spinner_words_in_vocabular.selectedItem.toString().toLongOrNull()

            var vocabularForSettings: MutableSet<String> = mutableSetOf()
            when {
                checkBoxEasy.isChecked -> vocabularForSettings =
                    vocabular?.easy ?: mutableSetOf()
                checkBoxNorm.isChecked -> vocabularForSettings =
                    vocabular?.normal ?: mutableSetOf()
                checkBoxHard.isChecked -> vocabularForSettings =
                    vocabular?.hard ?: mutableSetOf()
            }

            gameSettings.vocabular =
                vocabularForSettings.shuffled().take(gameSettings.wordsInVocabular?.toInt() ?: 0).toMutableSet()

            intent.putExtra("gameSettings", gameSettings)
            startActivity(intent)
        }
    }

    fun initSpinnerForSeconds() {
        val items = arrayOf(15, 20, 30, 45, 60)
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        spinner_time_for_step.adapter = adapter
    }

    fun initSpinnerWords(minSize: Int?) {
        val items = arrayListOf(5)
        if (minSize != null) {
            if (minSize > 10) {
                items.add(10)
            }
            if (minSize > 15) {
                items.add(15)
            }
            if (minSize > 30) {
                items.add(30)
            }
            if (minSize > 60) {
                items.add(60)
            }
        }
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        spinner_words_in_vocabular.adapter = adapter
    }
}