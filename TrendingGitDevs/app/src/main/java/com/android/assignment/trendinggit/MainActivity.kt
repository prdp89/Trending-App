package com.android.assignment.trendinggit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.assignment.trendinggit.ui.home.HomeViewPagerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeViewPagerFragment.newInstance())
                .commitNow()
        }
    }
}