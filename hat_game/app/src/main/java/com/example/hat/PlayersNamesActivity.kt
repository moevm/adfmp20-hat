package com.example.hat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_players_names.*

class PlayersNamesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players_names)

        button_to_mode.setOnClickListener {
            val intent = Intent(this, ModeActivity::class.java)
            startActivity(intent)
        }
    }
}
