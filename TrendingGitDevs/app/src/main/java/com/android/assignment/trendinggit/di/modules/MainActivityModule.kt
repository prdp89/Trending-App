package com.android.assignment.trendinggit.di.modules

import com.android.assignment.trendinggit.ui.developers.DeveloperFragment
import com.android.assignment.trendinggit.ui.home.HomeViewPagerFragment
import com.android.assignment.trendinggit.ui.repository.RepositoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeViewPagerFragment

    @ContributesAndroidInjector
    abstract fun contributeDeveloperFragment(): DeveloperFragment

    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): RepositoryFragment
}