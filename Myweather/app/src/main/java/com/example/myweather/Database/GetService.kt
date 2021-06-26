package com.example.myweather.Database

import android.util.Log

class GetService {

    private val title:Array<String> = arrayOf("接下来的一周下沙将迎来连续的降雨,大家做好防潮准备！！","今天天气好热！！！","今天的太阳好大，希望最近几天快点下雨")
    private val date:Array<String> = arrayOf("2021-6-17","2021-6-16","2021-6-13")
    private val discuss:Array<String> = arrayOf("谢谢提醒，已经收好衣服了#已经关好窗le#忘记带伞了，希望下班不要下雨","是啊，太热了#现在热，过几天就会下雨吧","没办法，夏天到了#最近在毕业~呜呜呜")
    private val discusstime :Array<String> = arrayOf("2021-6-17#2021-6-17#2021-6-17","2021-6-16#2021-6-17","2021-6-13#2021-6-16")

    fun returnContent (): ArrayList<Topic>? {

    var contentList = ArrayList<Topic>()

    for (i in 0 until title.size){
        var huati=Topic()
        huati.topicContent = title[i]
        huati.topicDate = date[i]
        huati.discuss = discuss[i]
        huati.discussTime = discusstime[i]
        if (huati != null) {
            contentList?.add(huati)
        }
    }
    return contentList
    }
}