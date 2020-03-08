package com.android.assignment.trendinggit.datasource.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.assignment.trendinggit.datasource.roomdb.dao.TrendingDevDao
import com.android.assignment.trendinggit.datasource.roomdb.dao.TrendingRepoDao
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingDevEntity
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingRepoEntity

@Database(
    entities = [TrendingRepoEntity::class
        , TrendingRepoEntity.BuiltBy::class
        , TrendingDevEntity::class]
    , version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun trendingRepoDao(): TrendingRepoDao
    abstract fun trendingDevDao(): TrendingDevDao
}