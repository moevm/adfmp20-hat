package com.example.hat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        button_to_players_names.setOnClickListener {
            val intent = Intent(this, PlayersNamesActivity::class.java)
            startActivity(intent)
        }
    }
}
