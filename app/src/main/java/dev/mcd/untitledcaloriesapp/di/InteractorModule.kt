package dev.mcd.untitledcaloriesapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.untitledcaloriesapp.data.auth.interactor.IsAuthenticatedImpl
import dev.mcd.untitledcaloriesapp.data.auth.interactor.LoginImpl
import dev.mcd.untitledcaloriesapp.data.auth.interactor.SignUpImpl
import dev.mcd.untitledcaloriesapp.data.calories.interactor.CreateCalorieEntryForTodayImpl
import dev.mcd.untitledcaloriesapp.data.calories.interactor.GetCalorieEntryForTodayImpl
import dev.mcd.untitledcaloriesapp.data.calories.interactor.GetWeeklyOverviewImpl
import dev.mcd.untitledcaloriesapp.data.prefs.interactor.GetAccessTokenImpl
import dev.mcd.untitledcaloriesapp.data.prefs.interactor.GetUserPrefsImpl
import dev.mcd.untitledcaloriesapp.data.prefs.interactor.SetUserCredentialsImpl
import dev.mcd.untitledcaloriesapp.data.prefs.interactor.SetUserPendingConfirmationImpl
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.IsAuthenticated
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.Login
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.SignUp
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.CreateCalorieEntryForToday
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntryForToday
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetWeeklyOverview
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.GetAccessToken
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.GetUserPrefs
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.SetUserCredentials
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.SetUserPendingConfirmation

@Module
@InstallIn(SingletonComponent::class)
abstract class InteractorModule {

    /**
     * feature.auth
     */

    @Binds
    abstract fun isAuthenticated(impl: IsAuthenticatedImpl): IsAuthenticated

    @Binds
    abstract fun login(impl: LoginImpl): Login

    @Binds
    abstract fun signUp(impl: SignUpImpl): SignUp

    /**
     * feature.calories
     */

    @Binds
    abstract fun createCalorieEntryForToday(impl: CreateCalorieEntryForTodayImpl): CreateCalorieEntryForToday

    @Binds
    abstract fun getCalorieEntryForToday(impl: GetCalorieEntryForTodayImpl): GetCalorieEntryForToday

    @Binds
    abstract fun getWeeklyOverview(impl: GetWeeklyOverviewImpl): GetWeeklyOverview

    /**
     * feature.prefs
     */

    @Binds
    abstract fun getUserPrefs(impl: GetUserPrefsImpl): GetUserPrefs

    @Binds
    abstract fun getAccessToken(impl: GetAccessTokenImpl): GetAccessToken

    @Binds
    abstract fun setUserCredentials(impl: SetUserCredentialsImpl): SetUserCredentials

    @Binds
    abstract fun setUserPendingConfirmation(impl: SetUserPendingConfirmationImpl): SetUserPendingConfirmation
}
