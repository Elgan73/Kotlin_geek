package com.aisgorod.kotlin_geek.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aisgorod.kotlin_geek.App
import com.aisgorod.kotlin_geek.R
import com.aisgorod.kotlin_geek.presentation.LogoutDialog
import com.firebase.ui.auth.AuthUI

class MainActivity : AppCompatActivity(), LogoutDialog.LogoutListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        App.fragmentRouter.initRouter(supportFragmentManager, R.id.fragment_container_view)
    }

    override fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }

    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}