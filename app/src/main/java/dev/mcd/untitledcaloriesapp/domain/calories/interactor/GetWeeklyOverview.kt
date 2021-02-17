package dev.mcd.untitledcaloriesapp.domain.calories.interactor

import dev.mcd.untitledcaloriesapp.domain.calories.entity.WeeklyOverview

interface GetWeeklyOverview {
    suspend fun execute(): WeeklyOverview
}
