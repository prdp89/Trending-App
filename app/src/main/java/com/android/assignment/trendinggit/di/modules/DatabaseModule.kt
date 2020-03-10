package com.android.assignment.trendinggit.di.modules

import android.app.Application
import androidx.room.Room
import com.android.assignment.trendinggit.datasource.roomdb.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database =
        Room.databaseBuilder(app, Database::class.java, "git_db")
            .build()
}