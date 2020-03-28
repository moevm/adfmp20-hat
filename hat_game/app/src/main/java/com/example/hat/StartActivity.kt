package com.example.hat

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.hat.entity.CurrentGameSettings
import com.example.hat.entity.GameSettings
import com.example.hat.services.GameService
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    private val currentGame = GameService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val gameSettings: GameSettings =
            intent.getSerializableExtra("gameSettings") as GameSettings
        updateTextFields(gameSettings)

        val currentGameSettings =
            CurrentGameSettings(
                Pair(gameSettings.team1, 0),
                Pair(gameSettings.team2, 0),
                true,
                gameSettings.vocabular
            )

        currentGame.setGame(currentGameSettings)

        button_to_start.setOnClickListener {
            run {
                changeVisibilityForButtons(true)
                showWord()
                object : CountDownTimer(gameSettings.secondsPerStep?.times(1000) ?: 15, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        textViewTime.text = (millisUntilFinished / 1000).toString()
                    }

                    override fun onFinish() {
                        changeVisibilityForButtons(false)
                        currentGame.finishRound()
                        if (currentGame.isTeam1Active()) {
                            updateTeamField(gameSettings.team1)
                        } else {
                            updateTeamField(gameSettings.team2)
                        }
                        updateSecondsField(gameSettings.secondsPerStep)
                        updateScore()
                    }
                }.start()
            }
        }

        button_guessed.setOnClickListener{
            currentGame.guessWord(textViewWord.text.toString().toLowerCase())
            showWord()
            updateScore()
            updateWordsNumberField(gameSettings.vocabular?.size)
        }

        button_pas.setOnClickListener {
            currentGame.pasWord(textViewWord.text.toString().toLowerCase())
            showWord()
            updateScore()
        }
    }

    private fun updateTextFields(gameSettings: GameSettings) {
        updateWordsNumberField(gameSettings.vocabular?.size)
        updateTeamField(gameSettings.team1)
        updateSecondsField(gameSettings.secondsPerStep)
    }

    private fun updateWordsNumberField(number: Int?) {
        textViewNumWords.text = number.toString()
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
            button_guessed.visibility = View.VISIBLE
            button_pas.visibility = View.VISIBLE
        } else {
            button_to_start.visibility = View.VISIBLE
            textViewWord.visibility = View.GONE
            button_guessed.visibility = View.GONE
            button_pas.visibility = View.GONE
        }
    }

    private fun showWord() {
        textViewWord.text = currentGame.getRandomWordFromVocabulary()
    }

    private fun updateScore() {
        textViewScore.text = currentGame.getCurrentScore().toString()
    }
}
