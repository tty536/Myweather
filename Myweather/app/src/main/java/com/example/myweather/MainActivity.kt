package com.example.myweather

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //获得底部导航栏
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        //对应的页面
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

    }

}