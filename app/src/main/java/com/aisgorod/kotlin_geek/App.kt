package com.aisgorod.kotlin_geek

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.aisgorod.kotlin_geek.navigation.Router

class App: Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
        var fragmentRouter = Router()
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}