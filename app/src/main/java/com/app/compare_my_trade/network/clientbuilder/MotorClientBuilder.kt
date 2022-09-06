package com.app.compare_my_trade.network.clientbuilder

import com.app.compare_my_trade.BuildConfig
import com.app.compare_my_trade.data.local.ISharedPreferenceService
import com.app.compare_my_trade.data.local.SharedPreferenceImp
import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.*


class MotorClientBuilder(private val interceptor: ApiInterceptor, private val sharedPreferences: SharedPreferenceImp) : KoinComponent,IMotorClientBuilder {
    override fun getClient() = createClientAuth()

    override fun getBuilder() = builder

    override fun getRetrofit()= retrofit

    private val API_BASE_URL = "http://motor.yenjoy.in/"
    private val CONNECT_TIMEOUT = 15
    private val READ_TIMEOUT = 60
    private val WRITE_TIMEOUT = 60


    private val httpLoggingInterceptor = HttpLoggingInterceptor()

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private val builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))



    private fun createClientAuth(): OkHttpClient {
        //ADD DISPATCHER WITH MAX REQUEST TO 1
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        val client: OkHttpClient = OkHttpClient.Builder()
            //.dispatcher(dispatcher )
           // .authenticator(authenticator)
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            //.addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(interceptor)
            .build()
        return client
    }

    private val retrofit: Retrofit = builder.client(createClientAuth()).build()

    init {
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
    }

}