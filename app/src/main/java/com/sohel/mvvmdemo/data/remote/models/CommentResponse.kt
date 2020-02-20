package com.sohel.mvvmdemo.data.remote.models


import com.google.gson.annotations.SerializedName

data class CommentResponse(
    val user: User,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val body: String
)