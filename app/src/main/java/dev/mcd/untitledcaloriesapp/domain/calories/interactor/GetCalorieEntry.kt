package dev.mcd.untitledcaloriesapp.domain.calories.interactor

import dev.mcd.untitledcaloriesapp.domain.common.time.DateString

interface GetCalorieEntry {
    sealed class Result {
        object NoEntry : Result()
        class CalorieEntry(val calories: Int) : Result()
    }

    suspend fun execute(date: DateString): Result
}
