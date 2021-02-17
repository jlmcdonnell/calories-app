package dev.mcd.untitledcaloriesapp.data.calories.interactor

import dev.mcd.untitledcaloriesapp.data.calories.api.CaloriesApi
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.CreateCalorieEntryForToday
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntryForToday
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntryForToday.Result.CalorieEntry
import java.time.LocalDate
import javax.inject.Inject

class CreateCalorieEntryForTodayImpl @Inject constructor(
    private val caloriesApi: CaloriesApi,
    private val getCalorieEntryForToday: GetCalorieEntryForToday,
) : CreateCalorieEntryForToday {

    override suspend fun execute(amount: Int) {
        val date = LocalDate.now().toString()
        val existing = getCalorieEntryForToday.execute()

        if (existing is CalorieEntry) {
            caloriesApi.updateEntry(date, amount)
        } else {
            caloriesApi.createEntry(date, amount)
        }
    }
}
