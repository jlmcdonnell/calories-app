package dev.mcd.untitledcaloriesapp.presentation.createentry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetOverviewToday
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.SaveCalorieEntryForToday
import dev.mcd.untitledcaloriesapp.presentation.createentry.SaveEntryViewModel.Events.Dismiss
import dev.mcd.untitledcaloriesapp.presentation.createentry.SaveEntryViewModel.Events.ShowCaloriesToday
import dev.mcd.untitledcaloriesapp.presentation.createentry.SaveEntryViewModel.Events.ShowCreateError
import dev.mcd.untitledcaloriesapp.presentation.createentry.SaveEntryViewModel.Events.ShowNoCaloriesToday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SaveEntryViewModel @Inject constructor(
    private val saveCalorieEntryForToday: SaveCalorieEntryForToday,
    private val getOverviewToday: GetOverviewToday,
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
                val overview = withContext(Dispatchers.IO) {
                    getOverviewToday.execute()
                }
                if (overview.hasAddedEntryToday) {
                    events.postValue(ShowNoCaloriesToday)
                } else {
                    events.postValue(ShowCaloriesToday(overview.caloriesConsumed))
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
                    saveCalorieEntryForToday.execute(amount)
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
