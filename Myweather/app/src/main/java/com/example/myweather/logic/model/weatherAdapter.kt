package com.example.myweather.logic.model

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.myweather.Database.DbManage
import com.example.myweather.R
import com.example.myweather.darw.Constant
import com.example.myweather.network.jhtemp

class weatherAdapter(activity: Activity, val resourceid:Int, data:List<jhtemp.ResultBean.FutureBean>):
        ArrayAdapter<jhtemp.ResultBean.FutureBean>(activity,resourceid,data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        //对converView的重利用
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceid, parent, false)
        } else {
            view = convertView
        }

        val data: TextView = view.findViewById(R.id.dateInfo)
        val skyname: TextView = view.findViewById(R.id.skyInfo)
        val skyimg: ImageView = view.findViewById(R.id.skyIcon)
        val toptemp: TextView = view.findViewById(R.id.temperatureInfo)

        val weather: jhtemp.ResultBean.FutureBean? = getItem(position)

        if (weather != null) {
            data.text = weather.date
            skyname.text = weather.weather
            when {
                weather.weather!!.contains("晴") -> skyimg.setImageResource(R.drawable.ic_clear_day)
                weather.weather!!.contains("小雨") -> skyimg.setImageResource(R.drawable.ic_light_rain)
                weather.weather!!.contains("雷阵雨") -> skyimg.setImageResource(R.drawable.ic_thunder_shower)
                weather.weather!!.contains("阴") -> skyimg.setImageResource(R.drawable.ic_cloudy)
                weather.weather!!.contains("大雨") -> skyimg.setImageResource(R.drawable.ic_storm_rain)
                weather.weather!!.contains("多云") -> skyimg.setImageResource(R.drawable.ic_cloudy)
                weather.weather!!.contains("中雨") -> skyimg.setImageResource(R.drawable.ic_storm_rain)
                weather.weather!!.contains("阵雨") -> skyimg.setImageResource(R.drawable.ic_storm_rain)
            }
            toptemp.text = weather.temperature

            val tempList: List<String> = weather.temperature?.split("/")!!
            var lowtemp: Int = tempList[0].toInt()
            var toptemp: Int = tempList[1].substring(0, tempList[1].length - 1).toInt()
            DbManage.addtempList(weather.date.toString(),toptemp,lowtemp)
            Log.d("Temp",toptemp.toString())
        }
        return view
    }
}