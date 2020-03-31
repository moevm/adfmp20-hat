package com.example.hat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.hat.entity.GameSettings
import kotlinx.android.synthetic.main.activity_players_names.*

class PlayersNamesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players_names)

        button_to_mode.setOnClickListener {
            val gameSettings: GameSettings =
                intent.getSerializableExtra("gameSettings") as GameSettings
            gameSettings.team1 = textView_player_name.text.toString()
            gameSettings.team2 = textView_player_name2.text.toString()
            val intent1 = Intent(this, AddWordActivity::class.java)
            intent1.putExtra("gameSettings", gameSettings)
            startActivity(intent1)
        }
    }
}
