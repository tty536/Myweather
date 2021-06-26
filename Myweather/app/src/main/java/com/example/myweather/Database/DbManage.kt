package com.example.myweather.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.util.*

class DbManage {

companion object{

    var db: SQLiteDatabase? = null

    //创建数据库
    fun creatdb(context: Context){
        val dbHelper = DBhelper(context, "MyWeather.db", 5)
        db = dbHelper.writableDatabase//创建数据库
    }

    //获得数据返回数据列表
    fun queryData(tableName: String, columnName: String): List<String> {
        val cursor: Cursor = db?.query(tableName, null, null, null, null, null, null)!!
        val dataList: ArrayList<String> = ArrayList<String>()
        if (cursor.moveToFirst()) {
            do {
                var data = cursor.getString(cursor.getColumnIndex(columnName))
                dataList.add(data)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return dataList
    }

    fun queryAllCityName(): List<String>? {
        val cursor: Cursor = db?.query("Citise", null, null, null, null, null, null)!!
        val cityList: MutableList<String> = ArrayList()
        while (cursor.moveToNext()) {
            val city = cursor.getString(cursor.getColumnIndex("city"))
            cityList.add(city)
        }
        return cityList
    }

    /* 根据城市名称，替换信息内容*/
    fun updateInfoByCity(city: String, content: String?): Int {
        val values = ContentValues()
        values.put("content", content)
        return db?.update("Citise", values, "city=?", arrayOf(city))!!
    }

    fun addCityInfo(city: String?, content: String?): Long {
        val values = ContentValues()
        values.put("city", city)
        values.put("content", content)
        return db?.insert("Citise", null, values)!!
    }

    fun addtempList(date:String,top:Int,low:Int): Long {
        val values = ContentValues()
        values.put("date",date)
        values.put("top", top)
        values.put("low", low)
        return db?.insert("tempList", null, values)!!
    }

    fun deletetempList(){
        val sql:String = "delete  from tempList  "
        return db?.execSQL(sql)!!
    }

    fun queryTempList(columnName:String): List<String>? {
        val cursor: Cursor = db?.query("tempList", null, null, null, null, null, null)!!
        val list: MutableList<String> = ArrayList<String>()
        while (cursor.moveToNext()) {
            val date= cursor.getString(cursor.getColumnIndex(columnName))
            list.add(date)
        }
        return list
    }

}

}