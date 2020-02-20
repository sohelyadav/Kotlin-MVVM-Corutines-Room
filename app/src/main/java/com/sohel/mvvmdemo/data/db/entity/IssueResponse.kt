package com.sohel.mvvmdemo.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "issues")
data class IssueResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @SerializedName("comments_url")
    val commentsUrl: String,
    val number: Int,
    val title: String,
    val comments: Int,
    val body: String
)