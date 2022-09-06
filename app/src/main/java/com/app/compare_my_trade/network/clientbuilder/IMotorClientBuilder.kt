package com.app.compare_my_trade.network.clientbuilder

import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface IMotorClientBuilder {
    fun getClient(): OkHttpClient
    fun getBuilder(): Retrofit.Builder
    fun getRetrofit(): Retrofit
}