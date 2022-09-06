package com.app.compare_my_trade.network.clientbuilder

import androidx.lifecycle.MutableLiveData
import com.app.compare_my_trade.network.model.BaseResponse
import com.app.compare_my_trade.network.model.Errors
import com.app.compare_my_trade.network.model.Resource
import com.app.compare_my_trade.utills.Singleton
import com.app.compare_my_trade.utills.Singleton.StatusCode
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException



open class BaseRepository{
    private val noNetworkMsg = "No internet connection or network failure"
    fun <T> getCallback(responseData: MutableLiveData<Resource<BaseResponse<T>>>): Callback<BaseResponse<T>> {
        return object : Callback<BaseResponse<T>> {
            override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
                if (t is IOException)
                    responseData.value = Resource.failure("Server time out", null)
                else
                    responseData.value = Resource.failure(t.message!!, null)
            }
            override fun onResponse(call: Call<BaseResponse<T>>, response: Response<BaseResponse<T>>) {
                if (response.isSuccessful && response.body() != null) {
                    responseData.value = Resource.success(response.body()!!)
                } else {
                    StatusCode=response.code().toString()
                    val errorResponse = getErrorResponse<T>(response.errorBody()!!)
                    responseData.value = Resource.error(response.code().toString(),errorResponse)
                }
            }
        }
    }

    fun <T> getListCallbacks(responseData: MutableLiveData<List<T>>): Callback<List<T>> {
        return object : Callback<List<T>> {

            override fun onResponse(call: Call<List<T>>, response: Response<List<T>>) {
                if (response.isSuccessful && response.body() != null) {
                    responseData.value = response.body()
                } else {
                    responseData.value = null
                }
            }

            override fun onFailure(call: Call<List<T>>, t: Throwable) {
                responseData.value = null
            }

        }
    }


    fun <T> getLoginCallback(responseData: MutableLiveData<Resource<T>>): Callback<T> {
        return object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                val msg = if (t is IOException) noNetworkMsg else t.message!!
                responseData.value = Resource.failure(msg, null)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful && response.body() != null) {
                    responseData.value = Resource.success(response.body()!!)
                } else if (response.errorBody() != null) {
                    val errorResponse = getLoginErrorResponse<T>(response.errorBody()!!)
                    responseData.value = Resource.error(errorResponse.message, errorResponse.data)
                }

            }

        }
    }
    fun <T> getLoginErrorResponse(responseBody: ResponseBody): BaseResponse<T> {
        var truckErrorResponse: BaseResponse<T>
        try {
            val jObjError = JSONObject(responseBody.string())
            val listPathObject = jObjError.getJSONObject("errors")
            val iter = listPathObject.keys() //This should be the iterator you want.
            val key = iter.next()
            val error=listPathObject.getJSONArray(key)
            truckErrorResponse = BaseResponse(
                message =error[0].toString(),
                data = null,
                success = true
            )
        } catch (e: JSONException) {
            truckErrorResponse = BaseResponse(
                success = false,
                data = null,
                message = "unable to get the response")
        }
        return truckErrorResponse
    }
    fun <T> getErrorResponse(responseBody: ResponseBody): BaseResponse<T> {
        val truckErrorResponse: BaseResponse<T> = try {
            val jObjError = JSONObject(responseBody.string())
            BaseResponse(
                success = true,
                message = jObjError.getString("message"),
                data = null
            )
        } catch (e: JSONException) {
            BaseResponse(
                success = false,
                message = "unable to get the response",
                data = null)
        }
        return truckErrorResponse
    }

    fun createPlainTextRequestBody(data: String?): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), data ?: "")
    }
}