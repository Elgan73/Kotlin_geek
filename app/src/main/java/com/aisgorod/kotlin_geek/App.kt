package com.aisgorod.kotlin_geek

import android.annotation.SuppressLint
import androidx.multidex.MultiDexApplication
import com.aisgorod.kotlin_geek.di.DependencyGraph
import com.aisgorod.kotlin_geek.navigation.Router
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(DependencyGraph.modules)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragmentRouter = Router()
    }

}