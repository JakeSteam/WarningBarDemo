package uk.co.jakelee.warningbardemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, arg1: Intent) {
        if (networkReceiverListener != null) {
            networkReceiverListener!!.onNetworkConnectionChanged(hasInternet(context))
        }
    }

    private fun hasInternet(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        connectivityManager.isActiveNetworkMetered
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
        return false
    }

    interface NetworkReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var networkReceiverListener: NetworkReceiverListener? = null
    }
}