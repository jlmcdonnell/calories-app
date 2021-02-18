package dev.mcd.untitledcaloriesapp.domain.calories.interactor

import dev.mcd.untitledcaloriesapp.domain.calories.entity.DayOverview

interface GetOverviewToday {
    suspend fun execute(): DayOverview
}
