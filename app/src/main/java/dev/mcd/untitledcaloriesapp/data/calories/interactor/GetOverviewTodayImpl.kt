package dev.mcd.untitledcaloriesapp.data.calories.interactor

import dev.mcd.untitledcaloriesapp.domain.calories.entity.DayOverview
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntry
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntry.Result.CalorieEntry
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntry.Result.NoEntry
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetOverviewToday
import dev.mcd.untitledcaloriesapp.domain.common.time.DateTimeProvider
import javax.inject.Inject

class GetOverviewTodayImpl @Inject constructor(
    private val timeProvider: DateTimeProvider,
    private val getCalorieEntry: GetCalorieEntry,
) : GetOverviewToday {
    override suspend fun execute(): DayOverview {
        val date = timeProvider.date
        val entry = getCalorieEntry.execute(date)
        val limit = 1500

        val consumed = if (entry is CalorieEntry) {
            entry.calories
        } else 0

        return DayOverview(
            caloriesRemaining = limit - consumed,
            caloriesConsumed = consumed,
            calorieLimit = limit,
            hasAddedEntryToday = entry !is NoEntry
        )
    }
}
