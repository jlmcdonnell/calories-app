package dev.mcd.untitledcaloriesapp.domain.calories.interactor

interface SaveCalorieEntryForToday {
    suspend fun execute(amount: Int)
}
