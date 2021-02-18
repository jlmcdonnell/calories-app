package dev.mcd.untitledcaloriesapp.data.calories.interactor

import dev.mcd.untitledcaloriesapp.data.calories.api.CaloriesApi
import dev.mcd.untitledcaloriesapp.domain.calories.entity.WeekOverview
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetWeeklyOverview
import java.time.DayOfWeek.FRIDAY
import java.time.DayOfWeek.MONDAY
import java.time.DayOfWeek.SATURDAY
import java.time.DayOfWeek.SUNDAY
import java.time.DayOfWeek.THURSDAY
import java.time.DayOfWeek.TUESDAY
import java.time.DayOfWeek.WEDNESDAY
import java.time.LocalDate
import javax.inject.Inject

class GetWeeklyOverviewImpl @Inject constructor(private val caloriesApi: CaloriesApi) : GetWeeklyOverview {

    override suspend fun execute(): WeekOverview {
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

        val dailyLimit = 1500 // TODO

        return WeekOverview(dailyLimit, entries)
    }
}
