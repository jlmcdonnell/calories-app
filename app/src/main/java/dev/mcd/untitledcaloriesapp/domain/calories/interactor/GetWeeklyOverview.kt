package dev.mcd.untitledcaloriesapp.domain.calories.interactor

import dev.mcd.untitledcaloriesapp.domain.calories.entity.WeekOverview

interface GetWeeklyOverview {
    suspend fun execute(): WeekOverview
}
