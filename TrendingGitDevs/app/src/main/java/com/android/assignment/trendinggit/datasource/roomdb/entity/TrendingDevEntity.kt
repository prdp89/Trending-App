package com.android.assignment.trendinggit.datasource.roomdb.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "trending_dev"
)
data class TrendingDevEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var avatar: String,
    var name: String,

    @Embedded(prefix = "repo_")
    var repo: Repo,

    var url: String,
    var username: String
) {
    data class Repo(
        var description: String,
        var name: String,
        var url: String
    )
}