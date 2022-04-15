package ir.sanags.interviewTask.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import java.io.IOException


fun Context.hasNetwork(): Boolean {
    val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connMgr.activeNetwork?.let { network ->
            connMgr.getNetworkCapabilities(network)?.let { capabilities ->
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        } ?: false
    } else {
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        networkInfo?.isConnected == true
    }
}


class NoConnectivityException : IOException() {
    override val message: String
        get() = "اتصال به اینترنت برقرار نمی باشد"
}