package com.example.hat

import android.app.ActionBar
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.TextView
import com.example.hat.entity.Statistics
import com.example.hat.util.UtilHat
import kotlinx.android.synthetic.main.activity_statistics.*


class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        addStatisticsInfo(UtilHat.loadStatistics(applicationContext))
    }

    private fun addStatisticsInfo(statistics: Statistics) {
        val layout = statistics_childLayout
        var index = 1
        statistics.statistics.toList().sortedBy { (_, value) -> (-1) * (value ?: 0) }.toMap()
            .forEach {
                val textEntry = TextView(this)
                textEntry.text = "${index}. ${it.key}: ${it.value}"
                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(0, 30, 0, 0)
                textEntry.layoutParams = params
                textEntry.textSize = 26F
                layout.addView(textEntry)
                index++
            }
    }
}
