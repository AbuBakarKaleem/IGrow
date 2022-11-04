package com.app.igrow.data.remote

import com.app.igrow.IGrowApp
import com.app.igrow.utils.Utils.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

open class ConnectivityInterceptor : Interceptor {

    private val isConnected: Boolean
        get() {
            return isInternetAvailable(IGrowApp.getInstance())
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (!isConnected) {
            throw NoNetworkException()
        }
        return chain.proceed(originalRequest)
    }

    class NoNetworkException internal constructor() : IOException("NO INTERNET CONNECTION")
}