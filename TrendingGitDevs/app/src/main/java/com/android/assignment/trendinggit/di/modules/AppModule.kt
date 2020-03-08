package com.android.assignment.trendinggit.di.modules

import android.app.Application
import android.content.Context
import com.android.assignment.trendinggit.TrendingGitDevApplication
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule

@Module(
    includes = [AndroidSupportInjectionModule::class
        , ActivityBuildersModule::class
        , NetworkModule::class
        , DatabaseModule::class]
)
abstract class AppModule {

    @Binds
    abstract fun provideApplicationContext(app: Application): Context

    @Binds
    abstract fun provideApplication(app: TrendingGitDevApplication): Application
}