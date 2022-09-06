package com.app.compare_my_trade.ui.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.compare_my_trade.utills.NetworkUtils.isNetworkConnected

class NetworkChangeReceiver(private var connectivityReceiverListener: INetworkConnection?=null) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (!isNetworkConnected(context) && connectivityReceiverListener != null) {
                connectivityReceiverListener?.onNetworkConnectionChanged(false)
        }
    }
}