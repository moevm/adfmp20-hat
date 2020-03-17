package com.example.hat.util

import android.content.Context
import com.example.hat.entity.Vocabular
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class UtilHat {
    companion object {
        fun loadJSONFromAsset(context: Context): String? {
            var json: String? = null
            json = try {
                val `is`: InputStream = context.getAssets().open("words.json")
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
            return Gson().fromJson(UtilHat.loadJSONFromAsset(context), Vocabular::class.java)
        }
    }
}