package com.sohel.mvvmdemo.helpers

import android.content.Context
import android.content.SharedPreferences

object SharedPrefs {
    private lateinit var mSharedPref: SharedPreferences

    private const val PREFS_NAME = "params"

    const val SAVE_TIME = "save_time"

    fun initSharedPrefs(context: Context){
        mSharedPref= context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun read(key:String, value: String?):String?{
        return mSharedPref.getString(key, value)
    }

    fun read(key:String, value:Int):Int?{
        return mSharedPref.getInt(key,value)
    }

    fun read(key:String, value:Boolean):Boolean?{
        return mSharedPref.getBoolean(key, value)
    }

    fun read(key:String, value:Float):Float?{
        return mSharedPref.getFloat(key,value)
    }

    fun write(key:String, value:String){
        val prefsEditor: SharedPreferences.Editor= mSharedPref.edit()
        with(prefsEditor){
            putString(key,value)
            commit()
        }
    }

    fun write(key:String, value:Int){
        val prefsEditor:SharedPreferences.Editor= mSharedPref.edit()
        with(prefsEditor){
            putInt(key,value)
            commit()
        }
    }

    fun write(key:String, value:Boolean){
        val prefsEditor:SharedPreferences.Editor= mSharedPref.edit()
        with(prefsEditor){
            putBoolean(key, value)
            commit()
        }
    }

    fun write(key:String, value: Float){
        val prefsEditor:SharedPreferences.Editor= mSharedPref.edit()
        with(prefsEditor){
            putFloat(key,value)
            commit()
        }
    }
}