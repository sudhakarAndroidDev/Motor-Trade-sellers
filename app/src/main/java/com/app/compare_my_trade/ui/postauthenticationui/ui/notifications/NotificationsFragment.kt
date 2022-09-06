package com.app.compare_my_trade.ui.postauthenticationui.ui.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils
import com.example.kotlin_project1.CurrentBid.CurrentBidsAdapter
import com.example.kotlin_project1.CurrentBid.CurrentBidsModel
import com.github.ybq.android.spinkit.SpinKitView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class NotificationsFragment : Fragment() {

    lateinit var recyclerview1:RecyclerView
    lateinit var recyclerview2:RecyclerView

    lateinit var mark_all: TextView
    lateinit var total_cound: TextView

    lateinit var progresstext:TextView
    lateinit var spinKitView: SpinKitView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {
        val view= inflater.inflate(R.layout.fragment_notifications, container, false)

        recyclerview1 = view.findViewById(R.id.recyclerview_N_1)
        recyclerview2 = view.findViewById(R.id.recyclerview_N_2)
//        val recyclerview3 = view.findViewById<RecyclerView>(R.id.recyclerview_N_3)

        mark_all = view.findViewById(R.id.mark_all)
        total_cound = view.findViewById(R.id.total)


        progresstext = view.findViewById(R.id.progressText)
        spinKitView = view.findViewById(R.id.progressBar)

//        recyclerview3.layoutManager = LinearLayoutManager(activity)

        mark_all.setOnClickListener {

            MarkAll()
            val intent = Intent(activity, HomeActivity::class.java).apply {

                putExtra("nav","notification")
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)

        }





        return view
    }

    private fun MarkAll() {
        val URL = "http://motortraders.zydni.com/api/sellers/notification/mark-all-as-read"

        val queue = Volley.newRequestQueue(activity)


        val request: StringRequest = object : StringRequest(
            Method.POST, URL,
            Response.Listener { response ->

                latest()
                today()
                mark_all.visibility = View.GONE
                spinKitView.visibility = View.GONE
                mark_all.visibility = View.GONE
            }, Response.ErrorListener { error ->
                spinKitView.visibility = View.GONE


                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                    Toast.makeText(activity,"Check your internet connection", Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(activity))
                return headers
            }
        }
        request.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)

    }

    private fun today() {

        spinKitView.visibility = View.VISIBLE

        val URL = "http://motortraders.zydni.com/api/sellers/notification/list"

        val queue = Volley.newRequestQueue(activity)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                spinKitView.visibility = View.GONE



                progresstext.visibility = View.VISIBLE

                val res = JSONObject(response)

                var successfulBids = res.getJSONArray("data")
                var total = res.getString("total")

                if (total != "0"){
                    total_cound.text = "Notification ("+total+")"
                }


                recyclerview2.layoutManager = LinearLayoutManager(activity)

                val data1 = ArrayList<NotificationModel>()

//                Toast.makeText(activity,response.toString(),Toast.LENGTH_LONG).show()
//                Log.i("jdhfisd",response.toString())

                for (i in 0 until successfulBids.length()) {
                    val data: JSONObject = successfulBids.getJSONObject(i)



                    try {

                        progresstext.visibility = View.GONE

                        var id =data.getString("id")
                        var title =data.getString("title")
                        var notification_message =data.getString("notification_message")
                        var car_id =data.getString("car_id")
                        var is_read =data.getString("is_read")
                        var car_image = data.getString("car_image")


                        data1.add(NotificationModel(car_image, title,notification_message,id,car_id,is_read))


                    } catch (e: JSONException) {

                    }



                }


                val adapter2 = NotificationAdapter(data1)


                recyclerview2.adapter = adapter2



                adapter2.setOnItemClickListener(object : NotificationAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {

                        val mod: NotificationModel = data1.get(position)

                        var car_id = mod.car_id
                        var n_id = mod.id

                        val URL = "http://motortraders.zydni.com/api/sellers/notification/mark-as-read/"+n_id

                        val queue = Volley.newRequestQueue(activity)


                        val request: StringRequest = object : StringRequest(
                            Method.POST, URL,
                            Response.Listener { response ->



                                if (response.toString().equals("true")){
                                    val intent = Intent(activity, CarDetails::class.java).apply {

                                        putExtra("v_id",car_id)
                                    }
                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                    startActivity(intent)
                                    today()
                                    latest()
                                }

                            }, Response.ErrorListener { error ->
                                spinKitView.visibility = View.GONE


                                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                                    Toast.makeText(activity,"Check your internet connection", Toast.LENGTH_LONG).show()
                                }
                            }) {


                            @Throws(AuthFailureError::class)
                            override fun getHeaders(): Map<String, String> {
                                val headers = HashMap<String, String>()
                                headers.put("Accept", "application/json")
                                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(activity))
                                return headers
                            }




                        }
                        request.retryPolicy = DefaultRetryPolicy(
                            10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                        )
                        queue.add(request)


                    }

                })
            }, Response.ErrorListener { error ->
                spinKitView.visibility = View.GONE



                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                    Toast.makeText(activity,"Check your internet connection", Toast.LENGTH_LONG).show()
                }
            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(activity))

                return headers
            }



        }
        request.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)


    }

    private fun latest() {
        val URL = "http://motortraders.zydni.com/api/sellers/notification/list/5"

        val queue = Volley.newRequestQueue(activity)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                val res = JSONArray(response)



                recyclerview1.layoutManager = LinearLayoutManager(activity)

                val data1 = ArrayList<NotificationModel>()

//                Toast.makeText(activity,response.toString(),Toast.LENGTH_LONG).show()
//                Log.i("jdhfisd",response.toString())

                for (i in 0 until res.length()) {
                    val data: JSONObject = res.getJSONObject(i)

                    try {

                        var id =data.getString("id")
                        var title =data.getString("title")
                        var notification_message =data.getString("notification_message")
                        var car_id =data.getString("car_id")
                        var is_read =data.getString("is_read")
                        var car_image = data.getString("car_image")

                        data1.add(NotificationModel(car_image, title,notification_message,id,car_id,is_read))


                    } catch (e: JSONException) {

                    }



                }


                val adapter = NotificationAdapter(data1)


                recyclerview1.adapter = adapter



                adapter.setOnItemClickListener(object : NotificationAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {

                        val mod: NotificationModel = data1.get(position)

                        var car_id = mod.car_id
                        var n_id = mod.id

                        val URL = "http://motortraders.zydni.com/api/sellers/notification/mark-as-read/"+n_id

                        val queue = Volley.newRequestQueue(activity)


                        val request: StringRequest = object : StringRequest(
                            Method.POST, URL,
                            Response.Listener { response ->



                                if (response.toString().equals("true")){
                                    val intent = Intent(activity, CarDetails::class.java).apply {

                                        putExtra("v_id",car_id)

                                        }
                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                    startActivity(intent)
                                    today()
                                    latest()
                                }


                            }, Response.ErrorListener { error ->
                                spinKitView.visibility = View.GONE


                                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                                    Toast.makeText(activity,"Check your internet connection", Toast.LENGTH_LONG).show()
                                }
                            }) {


                            @Throws(AuthFailureError::class)
                            override fun getHeaders(): Map<String, String> {
                                val headers = HashMap<String, String>()
                                headers.put("Accept", "application/json")
                                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(activity))
                                return headers
                            }




                        }
                        request.retryPolicy = DefaultRetryPolicy(
                            10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                        )
                        queue.add(request)


                    }

                })
            }, Response.ErrorListener { error ->

                spinKitView.visibility = View.GONE


                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                    Toast.makeText(activity,"Check your internet connection", Toast.LENGTH_LONG).show()
                }

            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(activity))

                return headers
            }



        }
        request.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)


    }

    override fun onStart() {
        super.onStart()

        latest()
        today()

        val URL = "http://motortraders.zydni.com/api/sellers/notification/list"

        val queue = Volley.newRequestQueue(activity)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->



                val res = JSONObject(response)

                var successfulBids = res.getJSONArray("data")
                var total = res.getInt("total")

                val last_notification= 0
                try {
                    var last = 0

                    try {
                        for (i in 0 until successfulBids.length()) {
                            val data: JSONObject = successfulBids.getJSONObject(i)


                            var is_read =data.getInt("is_read")
                            var id =data.getString("id")
                            var title =data.getString("title")
                            var notification_message =data.getString("notification_message")
                            var car_id =data.getString("car_id")

                            if (is_read.equals(0)){
                                last++

                            }





                        }
                        if (last != 0){
                            mark_all.visibility = View.VISIBLE
                        }else{
                            mark_all.visibility = View.GONE
                        }
                    } catch (e: Exception) {

                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->




            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(activity))

                return headers
            }



        }
        request.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)

    }
}