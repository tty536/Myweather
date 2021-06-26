package com.example.myweather.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.myweather.Database.DbManage

class UniteApp : Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }
    override fun onCreate(){
        DbManage.creatdb(this)
        super.onCreate()
        context=applicationContext
    }
}