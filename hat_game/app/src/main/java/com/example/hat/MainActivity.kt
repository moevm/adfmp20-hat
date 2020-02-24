package com.example.hat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun click_button_new_game(view: View) {
        val intent = Intent(this@MainActivity, Activity_2::class.java)
        startActivity(intent)
    }

    fun click_button_rules(view: View) {
        val intent = Intent(this@MainActivity, Activity_3_rules::class.java)
        startActivity(intent)
    }
}
