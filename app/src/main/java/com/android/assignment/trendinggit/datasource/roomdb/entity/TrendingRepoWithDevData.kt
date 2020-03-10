package com.android.assignment.trendinggit.datasource.roomdb.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TrendingRepoWithDevData(

    @Embedded
    var mTrendingRepoEntity: TrendingRepoEntity? = null,

    @Relation(
        entity = TrendingRepoEntity.BuiltBy::class,
        entityColumn = "mTrendingRepoId",
        parentColumn = "id"
    )
    var mTrendingRepoDevList: List<TrendingRepoEntity.BuiltBy>? = null
)