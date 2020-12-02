package com.aisgorod.kotlin_geek.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.aisgorod.kotlin_geek.App
import com.aisgorod.kotlin_geek.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_note.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        App.fragmentRouter.initRouter(supportFragmentManager, R.id.fragment_container_view)
    }
}