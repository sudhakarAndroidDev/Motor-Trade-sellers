package com.example.kotlin_project1.DeclinedBid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.R
import com.app.compare_my_trade.utils.PreferenceUtils
import com.example.kotlin_project1.CurrentBid.CurrentBidsAdapter
import com.example.kotlin_project1.WonBid.WonBidsAdapter
import com.example.kotlin_project1.WonBid.WonBidsModel
import com.github.ybq.android.spinkit.SpinKitView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap


class DeclinedBidsFragment : Fragment() {

    lateinit var recyclerview:RecyclerView

    var adapter: DeclinedBidsAdapter? = null

    lateinit var progresstext: TextView
    lateinit var spinKitView: SpinKitView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_declined_bids, container, false)

        recyclerview = view.findViewById(R.id.recyclerview3)

        progresstext = view.findViewById(R.id.progressText)
        spinKitView = view.findViewById(R.id.progressBar)


        DeclinedBids()

        return view
    }

    private fun DeclinedBids() {

        spinKitView.visibility = View.VISIBLE

        val URL = "http://motortraders.zydni.com/api/sellers/cars/list"

        val queue = Volley.newRequestQueue(activity)
        val model = ArrayList<DeclinedBidsModel>()

        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                spinKitView.visibility = View.GONE


                    progresstext.visibility = View.VISIBLE


                val res = JSONArray(response)


                recyclerview.layoutManager = LinearLayoutManager(activity)



                for (i in 0 until res.length()) {
                    val data: JSONObject = res.getJSONObject(i)

                    var vehicle_status = data.getString("vehicle_status")



                    if(vehicle_status.equals("sold")){


                        try {
                            progresstext.visibility = View.GONE

                            var bid_price = data.getString("vehicle_price")
                            var brand_name = data.getString("name")+" "+data.getString("brand_name")
                            var body_type = data.getString("body_type")
                            var advertisement_id = data.getString("advertisement_id")
                            var front_image = data.getString("front_image")
                            var product_id = data.getString("id")
                            var buyer = data.getString("buyer")
                            var buyer_name = "-"

                            if (buyer != "null"){
                                var buyer_object = JSONObject(buyer)
                                buyer_name = buyer_object!!.getString("first_name")
                            }else{
                                buyer_name = "-"
                            }

                            var model_name = data.getString("model_name")+" "+ data.getString("model_year")


//                            Toast.makeText(activity,bid_price.toString(),Toast.LENGTH_LONG).show()
//                            Log.i("jdhfisd",bid_price.toString())

                            model.add(
                                DeclinedBidsModel(
                                    front_image,
                                    brand_name,
                                    model_name,
                                    advertisement_id,
                                    bid_price,
                                    product_id,
                                    buyer_name

                                )
                            )



                        } catch (e: JSONException) {

                        }
                    }



                }
                recyclerview.layoutManager = LinearLayoutManager(activity)


//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img_6, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))


                adapter = DeclinedBidsAdapter(model)

                recyclerview.adapter = adapter

                recyclerview.setItemViewCacheSize(10000)
                Log.wtf("size_of_my_list", Integer.toString(model.size));
                adapter!!.notifyDataSetChanged()
                Log.d("debugMode", "The onCreateView method has been launched");

                adapter!!.setOnItemClickListener(object : DeclinedBidsAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {

                        val mod: DeclinedBidsModel = model.get(position)

                        var pid = mod.p_id
//                        var price = mod.text6


//                        V_name = mod.text2
//                        V_body_type= mod.text3
//                        V_advertisement_id= mod.text4
//                        front_image= mod.image


                        val intent = Intent(activity, CarDetails::class.java).apply {
                            putExtra("v_id", pid)
                            putExtra("type", "current_bids")
//                            putExtra("my_bid", price)
                            this.putExtra("bids","SoldVehicles")
                        }
                        startActivity(intent)
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
                headers.put("Accept", "application/json")
                headers.put("Authorization", "Bearer "+ PreferenceUtils.getTokan(activity))

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

}