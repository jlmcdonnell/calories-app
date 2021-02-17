package dev.mcd.untitledcaloriesapp.presentation.createentry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.CreateCalorieEntryForToday
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntryForToday
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntryForToday.Result.CalorieEntry
import dev.mcd.untitledcaloriesapp.presentation.createentry.CreateEntryViewModel.Events.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateEntryViewModel @Inject constructor(
    private val createCalorieEntryForToday: CreateCalorieEntryForToday,
    private val getCalorieEntryForToday: GetCalorieEntryForToday,
) : ViewModel() {

    sealed class Events {
        class ShowCaloriesToday(val calories: Int) : Events()
        object ShowNoCaloriesToday : Events()
        object Dismiss : Events()
        object ShowCreateError : Events()
    }

    lateinit var events: MutableLiveData<Events>

    fun start() {
        events = MutableLiveData()
        viewModelScope.launch {
            runCatching {
                val caloriesToday = withContext(Dispatchers.IO) {
                    getCalorieEntryForToday.execute()
                }
                if (caloriesToday is CalorieEntry) {
                    events.postValue(ShowCaloriesToday(caloriesToday.calories))
                } else {
                    events.postValue(ShowNoCaloriesToday)
                }
            }.onFailure {
                Timber.e(it, "retrieving calorie entry")
            }
        }
    }

    fun onCreateEntry(amount: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                runCatching {
                    createCalorieEntryForToday.execute(amount)
                }.onSuccess {
                    events.postValue(Dismiss)
                }.onFailure {
                    Timber.e(it, "creating entry")
                    events.postValue(ShowCreateError)
                }
            }
        }
    }
}
