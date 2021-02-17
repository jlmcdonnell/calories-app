package dev.mcd.untitledcaloriesapp.domain.calories.interactor

interface CreateCalorieEntryForToday {
    suspend fun execute(amount: Int)
}
