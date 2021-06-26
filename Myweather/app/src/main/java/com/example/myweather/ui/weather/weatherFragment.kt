package com.example.myweather.ui.weather

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.Database.DbManage
import com.example.myweather.R
import com.example.myweather.base.BaseFragment
import com.example.myweather.darw.Constant
import com.example.myweather.logic.model.weatherAdapter
import com.example.myweather.network.HttpUtil
import com.example.myweather.network.jhtemp
import com.example.myweather.network.url
import com.google.gson.Gson
import kotlinx.android.synthetic.main.now.*
import okhttp3.*
import java.io.IOException



class weatherFragment : BaseFragment() {
    lateinit var spn_city:Spinner
    lateinit var temp: TextView
    var sky:String = "null"
    lateinit var aqi:TextView
    lateinit var tx_sky:TextView
    var responseData:String="null"
    lateinit var futureList: List<jhtemp.ResultBean.FutureBean>
    lateinit var list_forecast:ListView
    //val editor = activity?.getSharedPreferences("city",Context.MODE_PRIVATE)?.edit()
    val citylist = DbManage.queryData("citise", "city")
    lateinit var city :String

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_weather, container, false)
               //初始化获得控件
        initcontrol(view)
        //数据传入适配器
        val spnCityAdapter =
            this.activity?.let { ArrayAdapter(it, R.layout.spn_item_city, citylist) }
        spn_city.adapter = spnCityAdapter

        //设置Spinner监听器
        var CityId :Int = 0
        spn_city!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                if (parent != null){
                    when(parent.id){
                        R.id.SPN_city -> {
                            CityId = position
                            SPN_city.setSelection(CityId)
                            city = citylist[position]
                            Constant.city = city
                            //editor?.putString("City",city)
                            //editor?.apply()
                            //Constant.city = citylist[position]
                            DbManage.deletetempList()

                            //开启子线程
                            try {
                                val tempUrl: String = url.getTemp_url(city)!!
                                HttpUtil.sendOkHttprequest(tempUrl, object : Callback {
                                    override fun onResponse(call: Call, response: Response) {
                                        responseData = response.body?.string()!!
                                        parseShowData(responseData)
                                    }
                                    override fun onFailure(call: Call, e: IOException) {

                                    }
                                })
                            } catch (e: SecurityException) {
                                e.printStackTrace()
                            }


                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        return view
    }

    private fun parseShowData(result: String) {

        try {

                //使用gson解析数据
                val gson=Gson()
                val jhTempBean: jhtemp = gson.fromJson(result, jhtemp::class.java)
                val jhResult: jhtemp.ResultBean? = jhTempBean.getResult()

                //        获取今日天气情况
                val jhRealtime: jhtemp.ResultBean.RealtimeBean? = jhResult?.getRealtime()
                sky= jhRealtime?.getInfo().toString()
            requireActivity().runOnUiThread {

                when  {
                    sky.contains("晴") -> img_bg.setImageResource(R.drawable.bg_clear_day)
                    sky.contains("多云") -> img_bg.setImageResource(R.drawable.bg_cloudy)
                    sky.contains("阴") -> img_bg.setImageResource(R.drawable.bg_cloudy)
                    sky.contains("雨") -> img_bg.setImageResource(R.drawable.bg_rain)
                }
                tx_sky.text  = sky

                temp.text=jhRealtime?.getTemperature()

                aqi.text = jhRealtime?.getaqi()

                // 获取未来三天的天气情况，加载到layout当中
                futureList = (jhResult?.getFuture() as List<jhtemp.ResultBean.FutureBean>?)!!

                //在数据库更新数据
                val i: Int = DbManage.updateInfoByCity(city, futureList.toString())
                if (i <= 0) {
                    //            更新数据库失败，说明没有这条城市信息，增加这个城市记录
                    DbManage.addCityInfo(city, futureList.toString())
                }

                val adapter = this.activity?.let { weatherAdapter(
                        it,
                        R.layout.forecast_item,
                        futureList
                )}
                list_forecast.adapter = adapter
            }
        }catch (e: SecurityException){
            e.printStackTrace()
        }
    }
    private fun initcontrol(view: View){
        list_forecast =  view.findViewById(R.id.List_forecast)
        spn_city = view.findViewById(R.id.SPN_city)
        temp=view.findViewById(R.id.currentTemp)
        tx_sky=view.findViewById(R.id.currentSky)
        aqi=view.findViewById(R.id.currentAQI)
    }

}


