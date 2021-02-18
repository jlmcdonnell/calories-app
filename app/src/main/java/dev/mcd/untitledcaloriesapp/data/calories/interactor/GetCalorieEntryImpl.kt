package dev.mcd.untitledcaloriesapp.data.calories.interactor

import dev.mcd.untitledcaloriesapp.data.calories.api.CaloriesApi
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntry
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntry.Result.CalorieEntry
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntry.Result.NoEntry
import dev.mcd.untitledcaloriesapp.domain.common.time.DateString
import javax.inject.Inject

class GetCalorieEntryImpl @Inject constructor(
    private val caloriesApi: CaloriesApi,
) : GetCalorieEntry {
    override suspend fun execute(date: DateString): GetCalorieEntry.Result {
        return caloriesApi.getEntry(date)
            .firstOrNull()
            ?.let {
                CalorieEntry(it.calories)
            } ?: NoEntry
    }
}
