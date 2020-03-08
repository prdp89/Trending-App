package com.android.assignment.trendinggit.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import com.google.android.material.snackbar.Snackbar

class ApplicationUtils {
    companion object {

        fun isNetworkAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo?
            activeNetwork = cm.activeNetworkInfo
            return null != activeNetwork && activeNetwork.isConnected
        }

        fun showSnackBar(v: View?, snackBarText: String?) {
            if (v == null || snackBarText == null) {
                return
            }
            Snackbar.make(v, snackBarText, Snackbar.LENGTH_LONG).show()
        }
    }
}