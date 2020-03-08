package com.android.assignment.trendinggit.datasource.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingDevEntity

@Dao
interface TrendingDevDao {
    @Query("SELECT * FROM trending_dev")
    fun loadTrendingDevs(): List<TrendingDevEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrendingDevs(trendingDevEntity: TrendingDevEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrendingDevList(trendingDevEntityList: List<TrendingDevEntity>)

    @Query("DELETE FROM trending_dev")
    fun deleteOldDevs()
}