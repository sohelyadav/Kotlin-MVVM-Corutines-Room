package com.sohel.mvvmdemo.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sohel.mvvmdemo.data.db.entity.IssueResponse

@Dao
interface IssuesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(issueResponseList : List<IssueResponse>)

    @Query("select * from issues")
    fun getIssues(): List<IssueResponse>
}