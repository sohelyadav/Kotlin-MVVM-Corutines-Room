package com.sohel.mvvmdemo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.sohel.mvvmdemo.ApplicationController
import com.sohel.mvvmdemo.data.db.AppDatabase
import com.sohel.mvvmdemo.data.db.dao.IssuesDao
import com.sohel.mvvmdemo.data.db.entity.IssueResponse
import com.sohel.mvvmdemo.data.remote.RetrofitInstance
import com.sohel.mvvmdemo.helpers.SharedPrefs
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.threeten.bp.LocalDate

class IssueRepository {

    private var mIssuesDao: IssuesDao? = null

    var job: CompletableJob? = null

    fun getIssue(): LiveData<List<IssueResponse>> {
        val lastFetchTime =
            LocalDate.parse(SharedPrefs.read(SharedPrefs.SAVE_TIME, LocalDate.now().toString()))
        Log.e("lastFetchTime", lastFetchTime.toString())

        job = Job()
        return object : LiveData<List<IssueResponse>>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val response =
                            if (isFetchCurrentNeeded(lastFetchTime)) {
                                Log.e("Issue Repository:::", "fetching from DB")
                                getDataFromDB()
                            } else {
                                Log.e("Issue Repository:::", "fetching from Server")
                                RetrofitInstance.apiService.getIssues()
                            }
                        saveDataToDB(response)
                        withContext(Main) {
                            value = response
                            theJob.complete()
                        }
                    }
                }
            }
        }
    }


    private fun isFetchCurrentNeeded(lastFetchTime: LocalDate): Boolean {
        val twentyFourHoursAgo = LocalDate.now().minusDays(1)
        return lastFetchTime.isAfter(twentyFourHoursAgo) || lastFetchTime.isEqual(twentyFourHoursAgo)
    }

    fun saveDataToDB(issueList: List<IssueResponse>) {
        GlobalScope.launch(IO) {
            val db: AppDatabase = AppDatabase.invoke(ApplicationController.applicationContext())
            mIssuesDao = db.issueDao()
            mIssuesDao!!.upsert(issueList)
            SharedPrefs.write(SharedPrefs.SAVE_TIME, LocalDate.now().toString())
        }
    }

    suspend fun getDataFromDB(): List<IssueResponse> {
        val db: AppDatabase = AppDatabase.invoke(ApplicationController.applicationContext())
        mIssuesDao = db.issueDao()
        return withContext(IO) {
            return@withContext mIssuesDao!!.getIssues()
        }
    }

    fun cancelJobs() {
        job?.cancel()
    }

}




