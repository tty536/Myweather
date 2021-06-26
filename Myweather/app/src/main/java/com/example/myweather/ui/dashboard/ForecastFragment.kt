package com.example.myweather.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myweather.Database.DbManage
import com.example.myweather.R
import com.example.myweather.darw.Constant
import com.example.myweather.darw.TrendView
import kotlinx.android.synthetic.*

class ForecastFragment : Fragment() {
    private var mtrendView: TrendView? = null
    private lateinit var tv_City:TextView
   // val sp = activity?.getSharedPreferences("city",Context.MODE_PRIVATE)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_forecast, container, false)
        tv_City = root.findViewById(R.id.Tv_city)
        mtrendView = root.findViewById(R.id.trendView)


        tv_City.text = Constant.city //sp?.getString("City","SP存储出错！")
        //Log.d("城市信息", sp?.getString("City","SP存储出错！").toString())
                //Constant.city
        mtrendView?.dateList = DbManage.queryTempList("date")
        mtrendView?.toptempList = DbManage.queryTempList("top")
        mtrendView?.lowtempList  =DbManage.queryTempList("low")
                //设置动画插值器
        mtrendView?.setInterpolator( DecelerateInterpolator())

        mtrendView?.setOnClickListener(){
            mtrendView?.startAnim(2500)
        }

        mtrendView?.startAnim(2500)
        return root
    }
}