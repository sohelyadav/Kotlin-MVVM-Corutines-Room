package com.sohel.mvvmdemo.data.remote

import com.sohel.mvvmdemo.data.remote.models.CommentResponse
import com.sohel.mvvmdemo.data.db.entity.IssueResponse
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiService {

    @GET("issues")
    suspend fun getIssues(): List<IssueResponse>

    @GET
   suspend fun getComments(@Url commentUrl: String ): List<CommentResponse>

}