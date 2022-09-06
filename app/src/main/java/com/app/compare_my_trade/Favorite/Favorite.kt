package com.app.compare_my_trade.Favorite

import android.R.attr
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.example.kotlin_project1.Favorite.FavoriteAdapter
import com.example.kotlin_project1.Favorite.FavoriteModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

import android.R.attr.tag
import com.android.volley.*


class Favorite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)


        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview4)


        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<FavoriteModel>()


//        data.add(FavoriteModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden"))
//        data.add(FavoriteModel(R.drawable.img_6, "12 feb 2005","BMW 7 series (2005)","73OLD Seden"))
//        data.add(FavoriteModel(R.drawable.img_6, "12 feb 2005","BMW 7 series (2005)","73OLD Seden"))
//        data.add(FavoriteModel(R.drawable.img_6, "12 feb 2005","BMW 7 series (2005)","73OLD Seden"))


        val adapter = FavoriteAdapter(data)


        recyclerview.adapter = adapter


        getdata()

    }

    private fun getdata() {

        val requestQueue = Volley.newRequestQueue(this)
        val URL = "http://motortraders.zydni.com/api/buyers/favourites"
//        val jsonBody = JSONObject()
//
//            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, URL, jsonBody,
//                { response ->
//                    try {
//                        Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
//                        Log.i("iuwhdiw",response.toString())
//
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                },
//                { error ->
//                    Log.i("iuwhdiw",error.toString())
//                }
//            )
//            jsonObjectRequest.setRetryPolicy(
//                DefaultRetryPolicy(
//                    0,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//                )
//            )
//            requestQueue.add(jsonObjectRequest)


        val req: JsonObjectRequest = object : JsonObjectRequest(
            Method.GET, URL,
            null, Response.Listener<JSONObject> {
                fun onResponse(response: JSONObject) {

                    Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
                    Log.i("iuwhdiw",response.toString())
                }
            }, Response.ErrorListener {
                fun onErrorResponse(error: VolleyError) {
                    VolleyLog.d(tag.toString(), "Error: " + error.message)
                    Log.e(tag.toString(), "Site Info Error: " + error.message)
                    Toast.makeText(
                        this.getApplicationContext(),
                        error.message, Toast.LENGTH_SHORT
                    ).show()


                }
            }) {
            /**
             * Passing some request headers
             */
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
               // headers.put("Content-Type", "application/json")

                val auth = "310|y0OnPUcBgE40R92f0jTn6MyY1qYIim27zcjbuDP1"

              //  headers.put("Authorization","310|y0OnPUcBgE40R92f0jTn6MyY1qYIim27zcjbuDP1")

//                headers.put("Bearer","TOKEN")
//                headers.put("TOKEN","310|y0OnPUcBgE40R92f0jTn6MyY1qYIim27zcjbuDP1")
               headers["Authorization"] = "310|y0OnPUcBgE40R92f0jTn6MyY1qYIim27zcjbuDP1"
//                headers["Bearer"] = "TOKEN"
//                headers["TOKEN"] = "310|y0OnPUcBgE40R92f0jTn6MyY1qYIim27zcjbuDP1"
                return headers
            }
        }
        requestQueue.add(req)

    }
}