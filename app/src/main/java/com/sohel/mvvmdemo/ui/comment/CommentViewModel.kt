package com.sohel.mvvmdemo.ui.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sohel.mvvmdemo.data.remote.models.CommentResponse
import com.sohel.mvvmdemo.data.repository.CommentRepository

class CommentViewModel : ViewModel() {

    private val _commentUrl: MutableLiveData<String> = MutableLiveData()

    val commentResponse: LiveData<List<CommentResponse>> = Transformations
        .switchMap(_commentUrl) {
            CommentRepository.getComment(it)
        }

    fun getComment(commentUrl: String) {
        if (_commentUrl.value == commentUrl) {
            return
        }
        _commentUrl.value = commentUrl
    }

    fun cancelJobs() {
        CommentRepository.cancelJobs()
    }
}