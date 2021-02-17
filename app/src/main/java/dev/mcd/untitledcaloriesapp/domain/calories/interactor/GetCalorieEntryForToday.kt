package dev.mcd.untitledcaloriesapp.domain.calories.interactor

interface GetCalorieEntryForToday {
    sealed class Result {
        object NoEntry : Result()
        class CalorieEntry(val calories: Int) : Result()
    }

    suspend fun execute(): Result
}
