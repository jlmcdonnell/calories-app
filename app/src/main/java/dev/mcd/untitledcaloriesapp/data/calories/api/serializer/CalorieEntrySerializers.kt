package dev.mcd.untitledcaloriesapp.data.calories.api.serializer

import com.squareup.moshi.Json

data class CreateCalorieEntry(
    @field:Json(name = "calories")
    val calories: Int,
    @field:Json(name = "entry_date")
    val entryDate: String,
)

data class UpdateCalorieEntry(
    @field:Json(name = "calories")
    val calories: Int,
)

data class CalorieEntry(
    @field:Json(name = "calories")
    val calories: Int,
    @field:Json(name = "entry_date")
    val entryDate: String,
)
