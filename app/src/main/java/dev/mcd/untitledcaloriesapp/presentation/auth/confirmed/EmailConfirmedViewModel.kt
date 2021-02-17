package dev.mcd.untitledcaloriesapp.presentation.auth.confirmed

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import javax.inject.Inject

@HiltViewModel
class EmailConfirmedViewModel @Inject constructor() : ViewModel() {

    sealed class Events {
        object ShowHome : Events()
    }

    val events: Flow<Events>
        get() = eventsChannel.consumeAsFlow()

    private val eventsChannel = Channel<Events>(1)

    fun onGetStartedClicked() {
        eventsChannel.offer(Events.ShowHome)
    }
}
