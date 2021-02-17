package dev.mcd.untitledcaloriesapp.data.calories.interactor

import dev.mcd.untitledcaloriesapp.data.calories.api.CaloriesApi
import dev.mcd.untitledcaloriesapp.domain.calories.entity.WeeklyOverview
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetWeeklyOverview
import java.time.DayOfWeek.*
import java.time.LocalDate
import javax.inject.Inject

class GetWeeklyOverviewImpl @Inject constructor(private val caloriesApi: CaloriesApi) : GetWeeklyOverview {

    override suspend fun execute(): WeeklyOverview {
        val now = LocalDate.now()

        val last7Entries = caloriesApi.queryLatestEntries(7)

        val entries = listOf(
            now.with(MONDAY),
            now.with(TUESDAY),
            now.with(WEDNESDAY),
            now.with(THURSDAY),
            now.with(FRIDAY),
            now.with(SATURDAY),
            now.with(SUNDAY),
        ).map { date ->
            last7Entries.firstOrNull {
                LocalDate.parse(it.entryDate) == date
            }?.calories ?: 0
        }

        return WeeklyOverview(entries)
    }
}
