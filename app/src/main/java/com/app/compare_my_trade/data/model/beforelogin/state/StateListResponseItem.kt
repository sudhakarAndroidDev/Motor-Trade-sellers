package com.app.compare_my_trade.data.model.beforelogin.state

data class StateListResponseItem(
    val country_id: String ="",
    val created_at: String="",
    val deleted_at: Any="",
    val id: Int=-1,
    val name: String="",
    val status: String="",
    val updated_at: String=""
)