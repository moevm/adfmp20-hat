package com.example.hat

import android.content.Context
import com.example.hat.entity.Statistics
import com.example.hat.services.GameService
import com.example.hat.services.StatisticsService
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times

/**
 * Tests for StatisticsService class
 */
class StatisticsServiceUnitTest {

    @Test
    fun loadAndSaveStatisticsTest() {
        val context = Mockito.mock(Context::class.java)
        val gameService = Mockito.mock(GameService::class.java)
        val statisticsService = Mockito.mock(StatisticsService::class.java)
        Mockito.`when`(statisticsService.loadStatistics(context))
            .thenReturn(Statistics(hashMapOf()))
        gameService?.saveStatisticsToFile(context)
        Mockito.verify(gameService, times(1)).saveStatisticsToFile(context)
    }
}