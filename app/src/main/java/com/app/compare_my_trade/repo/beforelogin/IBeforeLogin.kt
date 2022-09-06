package com.app.compare_my_trade.repo.beforelogin

import com.app.compare_my_trade.data.model.beforelogin.login.LoginResponse
import com.app.compare_my_trade.data.model.beforelogin.register.CreateAccountResponse
import com.app.compare_my_trade.data.model.beforelogin.state.StateListResponseItem
import com.app.compare_my_trade.network.model.BaseResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface IBeforeLogin {

    @Multipart
    @POST("api/buyers/login")
    fun getLoginResponse( @Part("email")email: RequestBody , @Part("password")password :RequestBody):Call<BaseResponse<LoginResponse>>

    @Multipart
    @POST("api/buyers/register")
    fun getCreateAccountResponse(
        @Part("first_name")firstName:RequestBody,
        @Part("last_name")lastName:RequestBody,
        @Part("email")email: RequestBody,
        @Part("business_phone")phone:RequestBody,
        @Part("password")password: RequestBody,
        @Part("address_line")addressLine:RequestBody,
        @Part("location_id")locationID:RequestBody,
        @Part("postal_code")postalCode:RequestBody
    ):Call<BaseResponse<CreateAccountResponse>>

    @GET("api/common/states")
    fun getCreateStateListResponse():Call<List<StateListResponseItem>>
}