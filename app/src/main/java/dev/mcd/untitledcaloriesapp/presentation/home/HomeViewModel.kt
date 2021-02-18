@file:Suppress("EXPERIMENTAL_API_USAGE")

package dev.mcd.untitledcaloriesapp.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.untitledcaloriesapp.domain.auth.entity.AuthenticationState.Authenticated
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.GetAuthenticationState
import dev.mcd.untitledcaloriesapp.domain.calories.entity.DayOverview
import dev.mcd.untitledcaloriesapp.domain.calories.entity.WeekOverview
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetOverviewToday
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetWeeklyOverview
import dev.mcd.untitledcaloriesapp.presentation.home.HomeViewModel.Events.DisplayDayOverview
import dev.mcd.untitledcaloriesapp.presentation.home.HomeViewModel.Events.DisplayWeekOverview
import dev.mcd.untitledcaloriesapp.presentation.home.HomeViewModel.Events.NavigateAuthentication
import dev.mcd.untitledcaloriesapp.presentation.home.HomeViewModel.Events.NavigateCreateEntry
import dev.mcd.untitledcaloriesapp.presentation.home.HomeViewModel.Events.ShowError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAuthenticationState: GetAuthenticationState,
    private val getWeeklyOverview: GetWeeklyOverview,
    private val getOverviewToday: GetOverviewToday,
) : ViewModel() {

    sealed class Events {
        class DisplayWeekOverview(val overview: WeekOverview) : Events()
        class DisplayDayOverview(val overview: DayOverview) : Events()
        object NavigateAuthentication : Events()
        object NavigateCreateEntry : Events()
        object ShowError : Events()
    }

    lateinit var events: MutableLiveData<Events>

    fun start() {
        events = MutableLiveData()
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    if (getAuthenticationState.execute() != Authenticated) {
                        events.postValue(NavigateAuthentication)
                    } else {
                        val overview = getWeeklyOverview.execute()
                        events.postValue(DisplayWeekOverview(overview))
                    }

                    val overview = getOverviewToday.execute()
                    events.postValue(DisplayDayOverview(overview))
                }
            }.onFailure {
                Timber.e(it, "loading data")
                events.postValue(ShowError)
            }
        }
    }

    fun onAddEntryClicked() {
        events.postValue(NavigateCreateEntry)
    }
}
