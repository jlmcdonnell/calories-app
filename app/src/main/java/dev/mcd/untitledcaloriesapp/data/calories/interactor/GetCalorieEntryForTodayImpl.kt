package dev.mcd.untitledcaloriesapp.data.calories.interactor

import dev.mcd.untitledcaloriesapp.data.calories.api.CaloriesApi
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntryForToday
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntryForToday.Result.CalorieEntry
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntryForToday.Result.NoEntry
import java.time.LocalDate
import javax.inject.Inject

class GetCalorieEntryForTodayImpl @Inject constructor(
    private val caloriesApi: CaloriesApi,
) : GetCalorieEntryForToday {
    override suspend fun execute(): GetCalorieEntryForToday.Result {
        val date = LocalDate.now().toString()
        return caloriesApi.getEntry(date)
            .firstOrNull()
            ?.let {
                CalorieEntry(it.calories)
            } ?: NoEntry
    }
}
