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
import com.example.hat.entity.CurrentGameSettings
import com.example.hat.entity.GameSettings
import com.example.hat.exception.NoActiveWordsException
import com.example.hat.exception.VocabularyIsEmptyException
import com.example.hat.services.GameService
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    private val currentGame = GameService()

    private var isStepActive = true

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
                try {
                    showWord()
                    isStepActive = true
                } catch (e: VocabularyIsEmptyException) {
                    isStepActive = false
                    finishGame(gameSettings, it)
                }
                object : CountDownTimer(gameSettings.secondsPerStep?.times(1000) ?: 15, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        if (isStepActive) {
                            updateSecondsField(millisUntilFinished / 1000)
                        } else {
                            updateSecondsField(0)
                            this.cancel()
                        }
                    }

                    override fun onFinish() {
                        if (isStepActive) {
                            finishStep(gameSettings, it)
                        } else {
                            updateSecondsField(0)
                            this.cancel()
                        }
                    }
                }.start()
            }
        }

        button_guessed.setOnClickListener {
            currentGame.guessWord(textViewWord.text.toString().toLowerCase())
            updateScore()
            updateWordsNumberField(gameSettings.vocabular?.size)
            try {
                showWord()
            } catch (e: NoActiveWordsException) {
                updateSecondsField(0)
                isStepActive = false
                finishStep(gameSettings, it)
            } catch (e: VocabularyIsEmptyException) {
                updateSecondsField(0)
                isStepActive = false
                finishGame(gameSettings, it)
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
                finishStep(gameSettings, it)
            } catch (e: VocabularyIsEmptyException) {
                updateSecondsField(0)
                isStepActive = false
                finishGame(gameSettings, it)
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

    @SuppressLint("SetTextI18n")
    private fun showPopUpChangeTeam(view: View, activeTeam: String) {
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_change_team, null)
        //Specify the length and width through constants
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        //Make Inactive Items Outside Of PopupWindow
        val focusable = true
        //Create a window with our parameters
        val popupWindow = PopupWindow(popupView, width, height, focusable)
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
        }
        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener { _, _ ->
            //Close the window when clicked
            popupWindow.dismiss()
            true
        }
    }

    private fun finishStep(gameSettings: GameSettings, view: View) {
        changeVisibilityForButtons(false)
        currentGame.finishRound()
        if (currentGame.isTeam1Active()) {
            updateTeamField(gameSettings.team1)
            showPopUpChangeTeam(view, gameSettings.team1 ?: "")
        } else {
            updateTeamField(gameSettings.team2)
            showPopUpChangeTeam(view, gameSettings.team2 ?: "")
        }
        updateSecondsField(gameSettings.secondsPerStep)
        updateScore()
    }

    private fun finishGame(gameSettings: GameSettings, view: View) {
        currentGame.saveStatisticsToFile(applicationContext)
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_game_finished, null)
        //Specify the length and width through constants
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        //Make Inactive Items Outside Of PopupWindow
        val focusable = true
        //Create a window with our parameters
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        //Initialize the elements of our window, install the handler
        val test2 = popupView.findViewById<TextView>(R.id.finishText)
        test2.text =
            "Игра окончена! \n ${gameSettings.team1}: ${currentGame.getTeam1Score()}   ${gameSettings.team2}: ${currentGame.getTeam2Score()}"

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
        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener { _, _ ->
            //Close the window when clicked
            popupWindow.dismiss()
            true
        }
    }
}
