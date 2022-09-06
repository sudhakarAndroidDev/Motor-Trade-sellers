package com.app.compare_my_trade.network.clientbuilder
import com.app.compare_my_trade.utills.Constants
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent
import java.io.IOException

class ApiInterceptor : KoinComponent,
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // try the request
        val newRequest = request.newBuilder()
            .header(Constants.ACCEPT_KEY, Constants.APPLICATION_JSON)
            .build()
        return chain.proceed(newRequest)
    }


}