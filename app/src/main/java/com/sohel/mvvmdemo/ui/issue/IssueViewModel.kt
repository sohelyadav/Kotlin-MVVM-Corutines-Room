package com.sohel.mvvmdemo.ui.issue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sohel.mvvmdemo.data.db.dao.IssuesDao
import com.sohel.mvvmdemo.data.db.entity.IssueResponse
import com.sohel.mvvmdemo.data.repository.IssueRepository


class IssueViewModel : ViewModel() {


    private val issueRepository = IssueRepository()

    private val _issueResponse: MutableLiveData<String> = MutableLiveData()

    val issueResponse: LiveData<List<IssueResponse>> = issueRepository.getIssue()


    fun cancelJobs(){
        issueRepository.cancelJobs()
    }
}

