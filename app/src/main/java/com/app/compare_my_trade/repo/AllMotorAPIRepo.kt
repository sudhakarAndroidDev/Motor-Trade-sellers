package com.app.compare_my_trade.repo

import com.app.compare_my_trade.network.clientbuilder.MotorClientBuilder
import com.app.compare_my_trade.repo.beforelogin.IBeforeLogin

class AllMotorAPIRepo(private val motorClientBuilder: MotorClientBuilder) {

    fun getBeforeLoginControllerRepo():IBeforeLogin{
        return motorClientBuilder.getRetrofit().create(IBeforeLogin::class.java)
    }

}