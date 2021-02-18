package dev.mcd.untitledcaloriesapp.data.calories.interactor

import dev.mcd.untitledcaloriesapp.data.calories.api.CaloriesApi
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntry
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntry.Result.CalorieEntry
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.SaveCalorieEntryForToday
import dev.mcd.untitledcaloriesapp.domain.common.time.DateTimeProvider
import javax.inject.Inject

class SaveCalorieEntryForTodayImpl @Inject constructor(
    private val caloriesApi: CaloriesApi,
    private val getCalorieEntry: GetCalorieEntry,
    private val timeProvider: DateTimeProvider,
) : SaveCalorieEntryForToday {

    override suspend fun execute(amount: Int) {
        val date = timeProvider.date
        val existing = getCalorieEntry.execute(date)

        if (existing is CalorieEntry) {
            caloriesApi.updateEntry(date, amount)
        } else {
            caloriesApi.createEntry(date, amount)
        }
    }
}
