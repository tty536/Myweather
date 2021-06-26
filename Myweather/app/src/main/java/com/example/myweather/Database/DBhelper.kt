package com.example.myweather.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBhelper( context:Context, name: String, version:Int):

        SQLiteOpenHelper(context,name,null,version){

    private val createCity = "Create table  if not exists Citise ("+
            "id integer primary key autoincrement,"+
            "city varchar(10) unique not null,"+
            "content text not null)"

    private val createTempList = "Create table if not exists tempList( "+
            "date String not null,"+
            "top int not null,"+
            "low int not null)"


    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(createCity)
        db.execSQL(createTempList)
Log.d("创建数据表","Ture")
        val data = ContentValues().apply {
            put("city","武汉")
            put("content","00")
        }
        val data1 = ContentValues().apply {
            put("city","合肥")
            put("content","00")
        }
        val data2 = ContentValues().apply {
            put("city","北京")
            put("content","00")
        }
        val data3 = ContentValues().apply {
            put("city","杭州")
            put("content","00")
        }
        db.insert("Citise",null,data)
        db.insert("Citise",null,data2)
        db.insert("Citise",null,data1)
        db.insert("Citise",null,data3)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
       if (oldVersion<=1){

       }
        if(oldVersion<=2){
            db.execSQL("drop table if exists Citise")
            onCreate(db)
        }
        if (oldVersion<=4){
            db.execSQL("drop table if exists tempList ")
            db.execSQL(createTempList)
        }

    }
}