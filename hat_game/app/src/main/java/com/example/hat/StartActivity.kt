package com.example.hat

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.hat.entity.CurrentGameSettings
import com.example.hat.entity.GameSettings
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val gameSettings: GameSettings =
            intent.getSerializableExtra("gameSettings") as GameSettings
        updateTextFields(gameSettings)

        var currentGame =
            CurrentGameSettings(Pair(gameSettings.team1, 0), Pair(gameSettings.team2, 0), true)

        button_to_start.setOnClickListener {
            run {
                changeVisibilityForButtons(true)

                object : CountDownTimer(gameSettings.secondsPerStep?.times(1000) ?: 15, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        textViewTime.text = (millisUntilFinished / 1000).toString()
                    }

                    override fun onFinish() {
                        changeVisibilityForButtons(false)
                        currentGame.team1Active = currentGame.team1Active.not()
                        if (currentGame.team1Active) {
                            updateTeamField(gameSettings.team1)
                        } else {
                            updateTeamField(gameSettings.team2)
                        }
                        updateSecondsField(gameSettings.secondsPerStep)
                    }
                }.start()
            }
        }
    }

    private fun updateTextFields(gameSettings: GameSettings) {
        textViewNumWords.text = gameSettings.vocabular?.size.toString()
        updateTeamField(gameSettings.team1)
        updateSecondsField(gameSettings.secondsPerStep)
    }

    private fun updateSecondsField(seconds: Long?) {
        textViewTime.text = seconds.toString()
    }

    private fun updateTeamField(team: String?) {
        textViewComName.text = team
    }

    private fun changeVisibilityForButtons(isGameWillBeActive: Boolean) {
        if (isGameWillBeActive) {
            button_to_start.visibility = View.GONE
            textViewWord.visibility = View.VISIBLE
            button.visibility = View.VISIBLE
            button2.visibility = View.VISIBLE
        } else {
            button_to_start.visibility = View.VISIBLE
            textViewWord.visibility = View.GONE
            button.visibility = View.GONE
            button2.visibility = View.GONE
        }
    }
}
