@file:Suppress("EXPERIMENTAL_API_USAGE")

package dev.mcd.untitledcaloriesapp.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.IsAuthenticated
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.IsAuthenticated.Result.NotAuthenticated
import dev.mcd.untitledcaloriesapp.domain.calories.entity.WeeklyOverview
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetWeeklyOverview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val isAuthenticated: IsAuthenticated,
    private val getWeeklyOverview: GetWeeklyOverview,
) : ViewModel() {

    sealed class Events {
        class ShowOverview(val overview: WeeklyOverview) : Events()
        object ShowAuthentication : Events()
        object ShowCreateEntry : Events()
    }

    lateinit var events: MutableLiveData<Events>

    fun start() {
        events = MutableLiveData()
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    if (isAuthenticated.execute() == NotAuthenticated) {
                        events.postValue(Events.ShowAuthentication)
                    } else {
                        val overview = getWeeklyOverview.execute()
                        events.postValue(Events.ShowOverview(overview))
                    }
                }
            }.onFailure {
                Timber.e(it, "loading data")
            }
        }
    }

    fun onAddEntryClicked() {
        events.postValue(Events.ShowCreateEntry)
    }
}
