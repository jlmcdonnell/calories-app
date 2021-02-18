package dev.mcd.untitledcaloriesapp.domain.calories.entity

data class DayOverview(
    val caloriesConsumed: Int,
    val caloriesRemaining: Int,
    val calorieLimit: Int,
    val hasAddedEntryToday: Boolean,
)
