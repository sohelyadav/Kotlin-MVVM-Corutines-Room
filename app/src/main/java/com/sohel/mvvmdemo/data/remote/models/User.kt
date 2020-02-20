package com.sohel.mvvmdemo.data.remote.models


import com.google.gson.annotations.SerializedName

data class User(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)