package com.android.assignment.trendinggit.datasource.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingRepoEntity
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingRepoWithDevData

@Dao
interface TrendingRepoDao {
    @Query("SELECT * FROM trending_repo")
    suspend fun loadTrendingRepos(): List<TrendingRepoEntity>

    @Transaction
    @Query("SELECT * FROM trending_repo where id=:id")
    suspend fun loadTrendingRepoWithDevData(id: Int): TrendingRepoWithDevData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrendingRepo(trendingRepoEntity: TrendingRepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrendingRepoList(trendingRepoEntityList: List<TrendingRepoEntity>)

    @Query("DELETE FROM trending_repo")
    fun deleteOldRepos()

    @Dao
    interface TrendingRepoDevDao {
        @Query("SELECT * FROM trending_repo_dev")
        fun loadTrendingRepoDev(): LiveData<List<TrendingRepoEntity.BuiltBy>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertTrendingRepoDev(trendingRepoEntity: TrendingRepoEntity.BuiltBy)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertTrendingRepoDevList(trendingRepoEntityList: List<TrendingRepoEntity.BuiltBy>)

        @Query("DELETE FROM trending_repo_dev")
        suspend fun deleteOldRepoDev()
    }
}