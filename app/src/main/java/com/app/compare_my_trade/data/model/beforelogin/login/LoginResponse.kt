package com.app.compare_my_trade.data.model.beforelogin.login

data class LoginResponse(
    val access_token: String,
    val token_type: String,
    val user: User
)