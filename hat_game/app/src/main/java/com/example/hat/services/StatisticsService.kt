package com.example.hat.services

import android.content.Context
import android.util.Log
import com.example.hat.entity.Statistics
import com.google.gson.Gson
import java.io.*

class StatisticsService {
    companion object {
        const val STATISTICS_FILE_NAME = "statistics.json"
    }

    fun saveStatistics(statistics: Statistics, context: Context) {
        try {
            val outputStreamWriter = OutputStreamWriter(
                context.openFileOutput(
                    STATISTICS_FILE_NAME,
                    Context.MODE_PRIVATE
                )
            )
            outputStreamWriter.write(Gson().toJson(statistics))
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
    }

    fun loadStatistics(context: Context): Statistics {
        var fis: FileInputStream? = null
        try {
            fis = context.openFileInput(STATISTICS_FILE_NAME)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            val sb = StringBuilder()
            var text: String?
            while (br.readLine().also { text = it } != null) {
                sb.append(text).append("\n")
            }
            return Gson().fromJson(sb.toString(), Statistics::class.java)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return Statistics(hashMapOf())
    }
}