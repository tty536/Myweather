package com.example.myweather.Database

import android.app.DownloadManager
import java.io.Serializable

class Topic: Serializable {

    private val serialVersionUID = 1L
     val QUREY = "qurey"
     val INSERT = "insert"
     val UPDATE  = "update"
    private var topicID:Int = 0
     var topicContent :String = ""
     var topicDate :String = ""
     var discuss :String = ""
     var discussTime :String = ""
     var state :String = ""


}