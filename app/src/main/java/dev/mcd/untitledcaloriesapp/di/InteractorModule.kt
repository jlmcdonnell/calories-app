package dev.mcd.untitledcaloriesapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.untitledcaloriesapp.data.auth.interactor.GetAuthenticationStateImpl
import dev.mcd.untitledcaloriesapp.data.auth.interactor.LoginImpl
import dev.mcd.untitledcaloriesapp.data.auth.interactor.SignUpImpl
import dev.mcd.untitledcaloriesapp.data.calories.interactor.GetCalorieEntryImpl
import dev.mcd.untitledcaloriesapp.data.calories.interactor.GetOverviewTodayImpl
import dev.mcd.untitledcaloriesapp.data.calories.interactor.GetWeeklyOverviewImpl
import dev.mcd.untitledcaloriesapp.data.calories.interactor.SaveCalorieEntryForTodayImpl
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.GetAuthenticationState
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.Login
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.SignUp
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetCalorieEntry
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetOverviewToday
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.GetWeeklyOverview
import dev.mcd.untitledcaloriesapp.domain.calories.interactor.SaveCalorieEntryForToday

@Module
@InstallIn(SingletonComponent::class)
abstract class InteractorModule {

    /**
     * feature.auth
     */

    @Binds
    abstract fun isAuthenticated(impl: GetAuthenticationStateImpl): GetAuthenticationState

    @Binds
    abstract fun login(impl: LoginImpl): Login

    @Binds
    abstract fun signUp(impl: SignUpImpl): SignUp

    /**
     * feature.calories
     */

    @Binds
    abstract fun saveCalorieEntryForToday(impl: SaveCalorieEntryForTodayImpl): SaveCalorieEntryForToday

    @Binds
    abstract fun getCalorieEntry(impl: GetCalorieEntryImpl): GetCalorieEntry

    @Binds
    abstract fun getWeeklyOverview(impl: GetWeeklyOverviewImpl): GetWeeklyOverview

    @Binds
    abstract fun getOverviewToday(impl: GetOverviewTodayImpl): GetOverviewToday
}
