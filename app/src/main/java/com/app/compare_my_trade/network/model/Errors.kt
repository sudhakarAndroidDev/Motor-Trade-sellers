package com.app.compare_my_trade.network.model

import com.google.gson.annotations.SerializedName

data class Errors(@SerializedName("message")
                  val message: String)