package com.example.hat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.hat.entity.CountDownTimerSettings
import com.example.hat.entity.CurrentGameSettings
import com.example.hat.entity.GameSettings
import com.example.hat.exception.NoActiveWordsException
import com.example.hat.exception.VocabularyIsEmptyException
import com.example.hat.services.GameService
import kotlinx.android.synthetic.main.activity_start.*
import java.util.*

class StartActivity : AppCompatActivity() {

    private val currentGame = GameService()
    private var gameSettings: GameSettings? = null
    private var isStepActive = false
    private var countDownTimer: CountDownTimer? = null
    private var countDownTimerSettings: CountDownTimerSettings = CountDownTimerSettings()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        gameSettings = intent.getSerializableExtra("gameSettings") as GameSettings
        updateTextFields(gameSettings ?: GameSettings())

        val currentGameSettings =
            CurrentGameSettings(
                Pair(gameSettings?.team1, 0),
                Pair(gameSettings?.team2, 0),
                true,
                gameSettings?.vocabular
            )

        currentGame.setGame(currentGameSettings)

        button_to_start.setOnClickListener {
            updateCountDownTimerSettings()
            run {
                changeVisibilityForButtons(true)
                countDownTimerSettings.remaining = gameSettings?.secondsPerStep?.times(1000) ?: 15
                try {
                    showWord()
                    isStepActive = true
                } catch (e: VocabularyIsEmptyException) {
                    isStepActive = false
                    finishGame(gameSettings)
                }
                countDownTimerSettings.isPaused = false
                createCountDownTimer(
                    gameSettings,
                    gameSettings?.secondsPerStep?.times(1000) ?: 15
                )
                countDownTimer?.start()
            }
        }

        button_guessed.setOnClickListener {
            currentGame.guessWord(textViewWord.text.toString().toLowerCase(Locale.ROOT))
            updateScore()
            updateWordsNumberField(gameSettings?.vocabular?.size)
            try {
                showWord()
            } catch (e: NoActiveWordsException) {
                updateSecondsField(0)
                isStepActive = false
                finishStep(gameSettings)
            } catch (e: VocabularyIsEmptyException) {
                updateSecondsField(0)
                isStepActive = false
                finishGame(gameSettings)
            } finally {
                updateScore()
            }
        }

        button_pas.setOnClickListener {
            currentGame.pasWord(textViewWord.text.toString().toLowerCase())
            try {
                showWord()
            } catch (e: NoActiveWordsException) {
                updateSecondsField(0)
                isStepActive = false
                finishStep(gameSettings)
            } catch (e: VocabularyIsEmptyException) {
                updateSecondsField(0)
                isStepActive = false
                finishGame(gameSettings)
            } finally {
                updateScore()
            }
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

    @SuppressLint("SetTextI18n")
    private fun updateSecondsField(seconds: Long?) {
        if (seconds != null) {
            if (seconds > 9) {
                textViewTime.text = seconds.toString()
            } else {
                textViewTime.text = "0$seconds"
            }
        }
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
        val word = currentGame.getRandomWordFromVocabulary()
        when {
            word.length > 16 -> textViewWord.textSize = 30F
            word.length > 13 -> textViewWord.textSize = 34F
            word.length > 11 -> textViewWord.textSize = 40F
            else -> textViewWord.textSize = 45F
        }
        textViewWord.text = word
    }

    private fun updateScore() {
        textViewScore.text = currentGame.getCurrentScore().toString()
    }

    @SuppressLint("SetTextI18n")
    private fun showPopUpChangeTeam(view: View, activeTeam: String) {
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_change_team, null)
        //Specify the length and width through constants
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        //Create a window with our parameters
        val popupWindow = PopupWindow(popupView, width, height)
        button_to_start.visibility = View.GONE
        textViewNameTime.visibility = View.GONE
        textViewTime.visibility = View.GONE
        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        //Initialize the elements of our window, install the handler
        val test2 = popupView.findViewById<TextView>(R.id.titleText)
        test2.text = "Смена хода! \n \nУгадывает: $activeTeam"

        val buttonOk =
            popupView.findViewById<Button>(R.id.popup_button_ok)
        buttonOk.setOnClickListener {
            popupWindow.dismiss()
            Toast.makeText(view.context, "Удачи, $activeTeam!", Toast.LENGTH_SHORT)
                .show()
            button_to_start.visibility = View.VISIBLE
            textViewNameTime.visibility = View.VISIBLE
            textViewTime.visibility = View.VISIBLE
        }
    }

    private fun finishStep(gameSettings: GameSettings?) {
        changeVisibilityForButtons(false)
        currentGame.finishRound()
        if (currentGame.isTeam1Active()) {
            updateTeamField(gameSettings?.team1)
            showPopUpChangeTeam(
                findViewById<View>(android.R.id.content).rootView,
                gameSettings?.team1 ?: ""
            )
        } else {
            updateTeamField(gameSettings?.team2)
            showPopUpChangeTeam(
                findViewById<View>(android.R.id.content).rootView,
                gameSettings?.team2 ?: ""
            )
        }
        updateSecondsField(gameSettings?.secondsPerStep)
        updateScore()
    }

    private fun finishGame(gameSettings: GameSettings?) {
        currentGame.saveStatisticsToFile(applicationContext)
        val inflater =
            findViewById<View>(android.R.id.content).rootView.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_game_finished, null)
        //Specify the length and width through constants
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        //Create a window with our parameters
        val popupWindow = PopupWindow(popupView, width, height)
        //Set the location of the window on the screen
        popupWindow.showAtLocation(
            findViewById<View>(android.R.id.content).rootView,
            Gravity.CENTER,
            0,
            0
        )
        //Initialize the elements of our window, install the handler
        val test2 = popupView.findViewById<TextView>(R.id.finishText)
        test2.text =
            "Игра окончена! \n ${gameSettings?.team1}: ${currentGame.getTeam1Score()}   ${gameSettings?.team2}: ${currentGame.getTeam2Score()}"

        val buttonMainMenu =
            popupView.findViewById<Button>(R.id.popup_button_menu)
        buttonMainMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val buttonStatistics =
            popupView.findViewById<Button>(R.id.popup_button_statistics)
        buttonStatistics.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createCountDownTimer(gameSettings: GameSettings?, millisInFuture: Long) {
        this.countDownTimer = object : CountDownTimer(millisInFuture + 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownTimerSettings.currentValue = countDownTimerSettings.currentValue + 1000
                countDownTimerSettings.remaining = countDownTimerSettings.remaining - 1000
                if (isStepActive && !countDownTimerSettings.isPaused) {
                    updateSecondsField(millisUntilFinished / 1000)
                } else {
                    updateSecondsField(0)
                    this.cancel()
                }
            }

            override fun onFinish() {
                if (isStepActive && !countDownTimerSettings.isPaused) {
                    finishStep(gameSettings)
                } else {
                    updateSecondsField(0)
                    this.cancel()
                }
                countDownTimerSettings.isPaused = true
            }
        }
    }

    private fun updateCountDownTimerSettings() {
        countDownTimerSettings = CountDownTimerSettings()
        countDownTimerSettings.remaining = gameSettings?.secondsPerStep?.times(1000) ?: 15
    }

    private fun pauseTimer() {
        isStepActive = false
        countDownTimer?.cancel()
        countDownTimerSettings.isPaused = true
        isStepActive = true
    }

    override fun onPause() {
        super.onPause()
        pauseTimer()
    }

    override fun onResume() {
        super.onResume()
        if (isStepActive) {
            countDownTimerSettings.isPaused = false
            createCountDownTimer(
                gameSettings,
                countDownTimerSettings.remaining
            )
            changeVisibilityForButtons(true)
            countDownTimer?.start()
        }
    }
}