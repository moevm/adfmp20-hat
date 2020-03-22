package com.example.hat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.hat.entity.GameSettings
import com.example.hat.util.UtilHat
import kotlinx.android.synthetic.main.activity_mode.*


class ModeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode)
        button_to_add_word.setOnClickListener {
            val gameSettings: GameSettings =
                intent.getSerializableExtra("gameSettings") as GameSettings

            when {
                checkBoxEasy.isChecked -> gameSettings.vocabular =
                    UtilHat.getVocabular(applicationContext)?.easy
                checkBoxNorm.isChecked -> gameSettings.vocabular =
                    UtilHat.getVocabular(applicationContext)?.normal
                checkBoxHard.isChecked -> gameSettings.vocabular =
                    UtilHat.getVocabular(applicationContext)?.hard
            }
            Log.d("Jooooooo,.s", gameSettings.toString())
            val intent1 = Intent(this, AddWordActivity::class.java)
            intent1.putExtra("gameSettings", gameSettings)
            startActivity(intent1)
        }
    }
}
