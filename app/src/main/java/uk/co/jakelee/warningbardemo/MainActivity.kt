package uk.co.jakelee.warningbardemo

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNetworkListener()
    }

    private fun setupNetworkListener() {
        NetworkReceiver.networkReceiverListener = object : NetworkReceiver.NetworkReceiverListener {
            override fun onNetworkConnectionChanged(isConnected: Boolean) {
                toggleNoInternetBar(!isConnected)
            }
        }
    }

    private fun toggleNoInternetBar(display: Boolean) {
        if (display) {
            val enterAnim = AnimationUtils.loadAnimation(this, R.anim.enter_from_bottom)
            no_internet_bar.startAnimation(enterAnim)
        } else {
            val exitAnim = AnimationUtils.loadAnimation(this, R.anim.exit_to_bottom)
            no_internet_bar.startAnimation(exitAnim)
        }
        no_internet_bar.visibility = if (display) View.VISIBLE else View.GONE
    }

    private var networkReceiver: NetworkReceiver? = null

    override fun onStart() {
        super.onStart()
        networkReceiver = NetworkReceiver()
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        networkReceiver?.let { unregisterReceiver(it) }
    }
}
