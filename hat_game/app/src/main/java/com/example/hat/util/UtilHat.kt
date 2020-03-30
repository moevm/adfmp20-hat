package com.example.hat.util

import android.content.Context
import android.util.Log
import com.example.hat.entity.Statistics
import com.example.hat.entity.Vocabular
import com.google.gson.Gson
import java.io.*
import java.nio.charset.Charset

class UtilHat {
    companion object {

        private const val WORDS_FILE_NAME = "words.json"
        const val STATISTICS_FILE_NAME = "statistics.json"

        fun loadJSONFromAsset(context: Context, fileName: String): String? {
            val json: String?
            json = try {
                val `is`: InputStream = context.getAssets().open(fileName)
                val size: Int = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                String(buffer, Charset.defaultCharset())
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }
            return json
        }

        fun getVocabular(context: Context): Vocabular? {
            return Gson().fromJson(
                loadJSONFromAsset(context, WORDS_FILE_NAME),
                Vocabular::class.java
            )
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
    }
}