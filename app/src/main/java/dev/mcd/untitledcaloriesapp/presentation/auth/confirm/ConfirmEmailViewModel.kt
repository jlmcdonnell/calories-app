package dev.mcd.untitledcaloriesapp.presentation.auth.confirm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import javax.inject.Inject

@HiltViewModel
class ConfirmEmailViewModel @Inject constructor() : ViewModel() {

    sealed class Events

    val events: Flow<Events>
        get() = eventsChannel.consumeAsFlow()

    private val eventsChannel = Channel<Events>(Channel.CONFLATED)
}
