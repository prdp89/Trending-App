package com.android.assignment.trendinggit.di

import com.android.assignment.trendinggit.TrendingGitDevApplication
import com.android.assignment.trendinggit.di.modules.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<TrendingGitDevApplication> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<TrendingGitDevApplication>
}