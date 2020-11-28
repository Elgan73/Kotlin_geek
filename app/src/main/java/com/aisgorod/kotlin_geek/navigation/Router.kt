package com.aisgorod.kotlin_geek.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aisgorod.kotlin_geek.R

class Router {
    private lateinit var fragmentManager: FragmentManager
    private var containerId: Int = 0

    fun initRouter(fragmentManager: FragmentManager, containerId: Int) {
        this.fragmentManager = fragmentManager
        this.containerId = containerId
    }


    fun navigateTo(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .addToBackStack("FRAGMENT")
            .commit()
    }
}

