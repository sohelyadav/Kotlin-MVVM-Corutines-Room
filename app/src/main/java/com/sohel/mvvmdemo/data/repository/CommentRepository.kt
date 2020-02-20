package com.sohel.mvvmdemo.data.repository

import androidx.lifecycle.LiveData
import com.sohel.mvvmdemo.data.remote.RetrofitInstance
import com.sohel.mvvmdemo.data.remote.models.CommentResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object CommentRepository {

    var job: CompletableJob? = null

    fun getComment(commentUrl: String): LiveData<List<CommentResponse>> {
        job = Job()
        return object : LiveData<List<CommentResponse>>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val response = RetrofitInstance.apiService.getComments(commentUrl)
                        withContext(Main) {
                            value = response
                            theJob.complete()
                        }
                    }

                }

            }
        }
    }

    fun cancelJobs() {
        job?.cancel()
    }

}




