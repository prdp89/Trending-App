package com.android.assignment.trendinggit

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.android.assignment.trendinggit.di.DaggerAppComponent
import com.android.assignment.trendinggit.utils.isNight
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import java.io.PrintWriter
import java.io.StringWriter

class TrendingGitDevApplication : DaggerApplication() {

    companion object {

        private var mContext: Context? = null

        fun getContext(): Context? {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        //used to monitor DB
        Stetho.initializeWithDefaults(this)

        mContext = applicationContext

        val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)

        Thread.setDefaultUncaughtExceptionHandler { _, exception ->
            val sw = StringWriter()

            exception.printStackTrace(PrintWriter(sw))
            val exceptionAsString = sw.toString()

            Log.e("  ---->  %s", exceptionAsString)
            Log.e("uncaughtException", ": Exception ENDS")

            //TODO: log runtime exception to remote API or Save in local directory
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}