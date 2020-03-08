package com.android.assignment.trendinggit.datasource.roomdb.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(
    tableName = "trending_repo"
)
data class TrendingRepoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var author: String,
    var avatar: String,
    var currentPeriodStars: Int,
    var description: String,
    var forks: Int,
    var language: String,
    var languageColor: String,
    var name: String,
    var stars: Int,
    var url: String
) {
    @Ignore
    @Json(name = "builtBy")
    var builtByList: List<BuiltBy>? = null

    @Entity(
        tableName = "trending_repo_dev"
    )
    data class BuiltBy(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var avatar: String,
        var href: String,
        var username: String
    )
}