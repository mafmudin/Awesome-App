package com.example.awesomeapp.base

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity: AppCompatActivity() {
    @SuppressLint("MissingPermission")

    fun connectionCheck():Boolean{
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED
    }

    open fun isInternetConnected(ctx: Context): Boolean {
        val connectivityMgr = ctx
            .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifi != null) {
            if (wifi.isConnected) {
                return true
            }
        }
        return mobile?.isConnected ?: false
    }
}