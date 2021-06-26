package com.example.myweather.darw

import android.content.Context

object DensityUtils {
    private var screenW = 0
    private var screenH = 0
    private var screenDensity = 0f
    fun getScreenW(context: Context): Int {
        if (screenW == 0) {
            initScreen(context)
        }
        return screenW
    }

    fun getScreenH(context: Context): Int {
        if (screenH == 0) {
            initScreen(context)
        }
        return screenH
    }

    fun getScreenDensity(context: Context): Float {
        if (screenDensity == 0f) {
            initScreen(context)
        }
        return screenDensity
    }

    private fun initScreen(context: Context) {
        val metric = context.resources.displayMetrics
        screenW = metric.widthPixels
        screenH = metric.heightPixels
        screenDensity = metric.density
    }

    fun dp2px(context: Context, dpValue: Float): Int {
        return (dpValue * getScreenDensity(context) + 0.5f).toInt()
    }

    fun px2dp(context: Context, pxValue: Float): Int {
        return (pxValue / getScreenDensity(context) + 0.5f).toInt()
    }
}
