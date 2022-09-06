package com.app.compare_my_trade.data.model.beforelogin.register

data class CreateAccountResponse(
    val address_line: String,
    val avatar: String,
    val business_phone: String,
    val created_at: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val location_id: String,
    val postal_code: String,
    val updated_at: String
)