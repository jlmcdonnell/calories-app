package dev.mcd.untitledcaloriesapp.data.calories.api

import dev.mcd.untitledcaloriesapp.data.calories.api.serializer.CalorieEntry
import dev.mcd.untitledcaloriesapp.data.calories.api.serializer.CreateCalorieEntry
import dev.mcd.untitledcaloriesapp.data.calories.api.serializer.UpdateCalorieEntry
import dev.mcd.untitledcaloriesapp.data.common.extensions.asException
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.*
import javax.inject.Inject

interface CaloriesApi {
    fun getEntry(date: String): List<CalorieEntry>
    fun queryLatestEntries(limit: Int): List<CalorieEntry>
    fun createEntry(date: String, amount: Int)
    fun updateEntry(date: String, amount: Int)
}

class CaloriesApiImpl @Inject constructor(retrofit: Retrofit) : CaloriesApi {

    interface API {
        @POST("/rest/v1/calorie_entry")
        fun createEntry(@Body body: CreateCalorieEntry): Call<ResponseBody>

        @PATCH("/rest/v1/calorie_entry")
        fun updateEntry(@Body body: UpdateCalorieEntry, @Query("entry_date") query: String): Call<ResponseBody>

        @GET("/rest/v1/calorie_entry")
        fun getEntryForDate(@Query("entry_date") query: String): Call<List<CalorieEntry>>

        @GET("/rest/v1/calorie_entry")
        fun queryLatestEntries(@Query("limit") limit: String, @Query("order") order: String): Call<List<CalorieEntry>>
    }

    private val api = retrofit.create(API::class.java)

    override fun createEntry(date: String, amount: Int) {
        val body = CreateCalorieEntry(amount, date)
        api.createEntry(body).execute().apply {
            if (!isSuccessful) throw asException()
        }
    }

    override fun updateEntry(date: String, amount: Int) {
        val body = UpdateCalorieEntry(amount)
        val query = "eq.$date"
        api.updateEntry(body, query).execute().apply {
            if (!isSuccessful) {
                throw asException()
            }
        }
    }

    override fun getEntry(date: String): List<CalorieEntry> {
        api.getEntryForDate("eq.$date").execute().apply {
            if (isSuccessful) {
                return body()!!
            } else {
                throw asException()
            }
        }
    }

    override fun queryLatestEntries(limit: Int): List<CalorieEntry> {
        val orderQuery = "entry_date.desc"
        api.queryLatestEntries(limit.toString(), orderQuery).execute().apply {
            if (isSuccessful) {
                return body()!!
            } else {
                throw asException()
            }
        }
    }
}
