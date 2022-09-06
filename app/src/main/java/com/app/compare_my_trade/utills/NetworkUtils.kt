package com.app.compare_my_trade.utills

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import com.app.compare_my_trade.utills.Singleton.connectionCheck
import java.lang.Exception

object NetworkUtils {

    fun isNetworkConnected(context: Context?): Boolean {

        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try{
            NetworkRequest.Builder()
            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    connectionCheck = true
                }
                override fun onLost(network: Network) {
                    connectionCheck = false
                }
            }
            )}catch (e:Exception){
                Log.e("Network Utils","crash $e")
            }
            return connectionCheck
        } else {
            val activeNetwork =
                connectivityManager.activeNetworkInfo
            activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }
}