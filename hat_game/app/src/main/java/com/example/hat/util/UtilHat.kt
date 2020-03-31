package com.example.hat.util

import android.content.Context
import com.example.hat.entity.Vocabular
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class UtilHat {
    companion object {

        private const val WORDS_FILE_NAME = "words.json"

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
    }
}