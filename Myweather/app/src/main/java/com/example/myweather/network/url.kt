package com.example.myweather.network

import android.util.Log

class url {
companion object{

    val KEY = "3845a1708923976fb2f68faa59a7c56e"

    var temp_url = "https://apis.juhe.cn/simpleWeather/query"

    var index_url = "https://apis.juhe.cn/simpleWeather/life"


    fun getTemp_url(city: String): String? {
        val Url:String=temp_url+"?city="+city+"&key="+KEY
        return Url
    }


    fun getIndex_url(city: String): String? {
        val Url:String=index_url+"?city="+city+"&key="+KEY
        return Url
    }
}

}