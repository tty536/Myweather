package com.example.myweather.network

import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL

public class Httputils {
companion object{
    fun getJsonContent(path: String?): String? {
        val baos = ByteArrayOutputStream()
        try {
            val url = URL(path)
            val conn = url.openConnection() as HttpURLConnection
            val `is` = conn.inputStream
            var hasRead = 0
            val buf = ByteArray(1024)
            while (`is`.read(buf).also { hasRead = it } != -1) {
                baos.write(buf, 0, hasRead)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return baos.toString()
    }
}

}