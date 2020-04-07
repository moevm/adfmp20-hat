package com.example.hat

import android.content.Context
import com.example.hat.entity.Vocabular
import com.example.hat.util.UtilHat
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

/**
 * Tests for UtilHat class
 */
class UtilHatTest {

    @Test
    fun loadJSONFromAsset() {
        val comp = Mockito.mock(UtilHat.Companion::class.java)
        val context = Mockito.mock(Context::class.java)
        val fileName = "words.json"
        Mockito.`when`(comp.loadJSONFromAsset(context, fileName)).thenReturn(
            "{\n" +
                    "  \"easy\": [\n" +
                    "    \"one\"\n" +
                    "  ],\n" +
                    "  \"normal\": [\n" +
                    "    \"two\"\n" +
                    "  ],\n" +
                    "  \"hard\": [\n" +
                    "    \"three\"\n" +
                    "  ]\n" +
                    "}"
        )

        val actual = comp.loadJSONFromAsset(context, fileName)
        val expected = "{\n" +
                "  \"easy\": [\n" +
                "    \"one\"\n" +
                "  ],\n" +
                "  \"normal\": [\n" +
                "    \"two\"\n" +
                "  ],\n" +
                "  \"hard\": [\n" +
                "    \"three\"\n" +
                "  ]\n" +
                "}"
        assertEquals(expected, actual)
    }

    @Test
    fun getVocabularFromJsonTest() {
        val comp = Mockito.mock(UtilHat.Companion::class.java)
        val context = Mockito.mock(Context::class.java)
        val fileName = "words.json"
        Mockito.`when`(comp.getVocabular(context)).thenReturn(
            Vocabular(mutableSetOf(), mutableSetOf(), mutableSetOf())
        )

        val actual = comp.getVocabular(context)
        val expected = Vocabular(mutableSetOf(), mutableSetOf(), mutableSetOf())
        assertEquals(expected, actual)
    }
}