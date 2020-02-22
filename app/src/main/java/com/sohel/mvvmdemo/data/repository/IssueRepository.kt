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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class IssueRepository {

    companion object {
        @JvmField
        val TAG: String = IssueRepository::class.java.simpleName
    }

    private var mIssuesDao: IssuesDao? = null
    val db: AppDatabase = AppDatabase.invoke(ApplicationController.applicationContext())

    var job: CompletableJob? = null
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

    fun getIssue(): LiveData<List<IssueResponse>> {
        val lastFetchTime = (SharedPrefs.read(SharedPrefs.SAVE_TIME, Date().toString()))!!
        Log.e(TAG, "last fetch date::: $lastFetchTime")
        job = Job()
        return object : LiveData<List<IssueResponse>>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val response =
                            if (isFetchCurrentNeeded(lastFetchTime)) {
                                Log.e(TAG, "fetching from DB")
                                getDataFromDB()
                            } else {
                                Log.e(TAG, "fetching from Server")
                                fetchDataFromRemote()
                            }
                        withContext(Main) {
                            value = response
                            theJob.complete()
                        }
                    }
                }
            }
        }
    }

    private suspend fun fetchDataFromRemote(): List<IssueResponse> {
        val serverResponse = RetrofitInstance.apiService.getIssues()
        saveDataToDB(serverResponse)
        return serverResponse
    }

    private fun isFetchCurrentNeeded(lastFetchTime: String): Boolean {
        try {
            val oldDate: Date = dateFormat.parse(lastFetchTime)!!
            val currentDate = Date()
            val diff: Long = currentDate.time - oldDate.time
            val diffHours = diff / (60 * 60 * 1000)
            Log.e(TAG, "hours difference:::$diffHours")
            if (diffHours < 24) {
                return true
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }


    private fun saveDataToDB(issueList: List<IssueResponse>) {
        GlobalScope.launch(IO) {
            mIssuesDao = db.issueDao()
            mIssuesDao!!.upsert(issueList)
            SharedPrefs.write(SharedPrefs.SAVE_TIME, dateFormat.format(Date()))
        }
    }

    suspend fun getDataFromDB(): List<IssueResponse> {
        mIssuesDao = db.issueDao()
        return withContext(IO) {
            return@withContext mIssuesDao!!.getIssues()
        }
    }

    fun cancelJobs() {
        job?.cancel()
    }

}




