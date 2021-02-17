package dev.mcd.untitledcaloriesapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.Login
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.SignUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthenticateViewModel @Inject constructor(
    private val signUp: SignUp,
    private val login: Login,
) : ViewModel() {

    sealed class Events {
        object ShowHome : Events()
        class ShowSignUpError(val cause: Throwable?) : Events()
        class ShowLoginError(val cause: Throwable?) : Events()
    }

    val events: Flow<Events>
        get() = eventsChannel.consumeAsFlow()

    private val eventsChannel = Channel<Events>(Channel.CONFLATED)

    fun onSignUp(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                runCatching {
                    signUp.execute(email, password)
                }.onSuccess {
                    eventsChannel.offer(Events.ShowHome)
                }.onFailure {
                    Timber.e(it, "signing up")
                    eventsChannel.offer(Events.ShowSignUpError(it))
                }
            }
        }
    }

    fun onLogin(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                runCatching {
                    login.execute(email, password)
                }.onSuccess {
                    eventsChannel.offer(Events.ShowHome)
                }.onFailure {
                    Timber.e(it, "logging in")
                    eventsChannel.offer(Events.ShowLoginError(it))
                }
            }
        }
    }
}
