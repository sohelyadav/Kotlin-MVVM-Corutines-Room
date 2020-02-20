package com.sohel.mvvmdemo

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.sohel.mvvmdemo.helpers.SharedPrefs


class ApplicationController : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: ApplicationController? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        SharedPrefs.initSharedPrefs(this)
    }
}