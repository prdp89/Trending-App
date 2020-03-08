package com.android.assignment.trendinggit

import android.content.Context
import android.util.Log
import com.android.assignment.trendinggit.di.DaggerAppComponent
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