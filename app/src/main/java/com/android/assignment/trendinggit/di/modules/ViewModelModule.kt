package com.android.assignment.trendinggit.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.assignment.trendinggit.di.TrendingGitDevsViewModelFactory
import com.android.assignment.trendinggit.di.ViewModelKey
import com.android.assignment.trendinggit.ui.developers.TrendingDevViewModel
import com.android.assignment.trendinggit.ui.repository.RepositoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: TrendingGitDevsViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RepositoryViewModel::class)
    abstract fun bindRepositoryViewModel(repositoryViewModel: RepositoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrendingDevViewModel::class)
    abstract fun bindTrendingDevViewModel(trendingDevViewModel: TrendingDevViewModel): ViewModel
}