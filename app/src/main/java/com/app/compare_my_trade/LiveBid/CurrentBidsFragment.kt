package com.example.kotlin_project1.CurrentBid

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.Edit_vehicle
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.ui.postauthenticationui.ui.managebids.ManageBidsFragment
import com.app.compare_my_trade.utils.PreferenceUtils
import com.github.ybq.android.spinkit.SpinKitView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import okhttp3.internal.notify
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.util.HashMap


class CurrentBidsFragment : Fragment() {

    lateinit var recyclerview:RecyclerView

    var adapter: CurrentBidsAdapter? = null

    lateinit var progresstext: TextView
    lateinit var spinKitView: SpinKitView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_current_bids, container, false)



        recyclerview = view.findViewById(R.id.recyclerview1)


        recyclerview.layoutManager = LinearLayoutManager(activity)

        progresstext = view.findViewById(R.id.progressText)
        spinKitView = view.findViewById(R.id.progressBar)

        current_bids()




        return view
    }
    private fun current_bids() {

    }

    override fun onStart() {
        super.onStart()
        spinKitView.visibility = View.VISIBLE

        val URL = "http://motortraders.zydni.com/api/sellers/cars/list"

        val queue = Volley.newRequestQueue(activity)
        val model = ArrayList<CurrentBidsModel>()

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

                    if(vehicle_status.equals("available")){


                        try {

                            progresstext.visibility = View.GONE

                            var bid_price = data.getString("vehicle_price")
                            var brand_name = data.getString("brand_name")+" "+data.getString("model_name")
                            var body_type = data.getString("body_type")
                            var advertisement_id = data.getString("advertisement_id")
                            var front_image = data.getString("front_image")
                            var bid_count = data.getString("bid_count")

                            var product_id = data.getString("id")
                            var year = data.getString("model_year")



                            model.add(
                                CurrentBidsModel(
                                    front_image,
                                    brand_name,
                                    year,
                                    advertisement_id,
                                    bid_price,
                                    product_id,bid_count

                                )
                            )


                        } catch (e: JSONException) {

                        }
                    }

//                    Toast.makeText(activity,first_name.toString(),Toast.LENGTH_LONG).show()
//                    Log.i("jdhfisd",first_name.toString())

                }
                recyclerview.layoutManager = LinearLayoutManager(activity)


//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img_6, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))


                adapter = CurrentBidsAdapter(model)

                recyclerview.adapter = adapter

                recyclerview.setItemViewCacheSize(10000)
                Log.wtf("size_of_my_list", Integer.toString(model.size));
                adapter!!.notifyDataSetChanged()
                Log.d("debugMode", "The onCreateView method has been launched");

                adapter!!.setOnItemClickListener(object : CurrentBidsAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {

                        val mod: CurrentBidsModel = model.get(position)

                        var pid = mod.p_id
                        var price = mod.text6


//                        V_name = mod.text2
//                        V_body_type= mod.text3
//                        V_advertisement_id= mod.text4
//                        front_image= mod.image


                        val intent = Intent(activity, CarDetails::class.java).apply {
                            putExtra("v_id", pid)
                            putExtra("type", "current_bids")
                            putExtra("my_bid", price)
                            putExtra("bids", "LiveBids")
                        }
                        startActivity(intent)
                    }

                })

                adapter!!.setOnItemClickListener3(object :CurrentBidsAdapter.onItemClickListener3{
                    override fun onItemClick3(position: Int) {
                        var p_id = model.get(position).p_id
                        val intent = Intent(activity, Edit_vehicle::class.java).apply {
                            putExtra("p_id", p_id)
                        }
                        startActivity(intent)

                    }
                })
                adapter!!.setOnItemClickListener2(object :CurrentBidsAdapter.onItemClickListener2{
                    override fun onItemClick2(position: Int) {

                        var p_id = model.get(position).p_id

                        val dialog = Dialog(activity!!)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog!!.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
                        dialog.setContentView(R.layout.alert_dialog_box)
//                dialog.getWindow()!!.setBackgroundDrawableResource(R.drawable.currentbackground);

                        val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
                        val oktext = dialog.findViewById<TextView>(R.id.yestext)
                        val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
                        val cancel_text = dialog.findViewById<TextView>(R.id.no_text)
                        val title = dialog.findViewById<TextView>(R.id.title)
                        val massage = dialog.findViewById<TextView>(R.id.message)

                        title.setText("Alert")
                        cancel_text.text = "No"
                        oktext.text = "YES"
                        massage.setText("Are you sure want to delete the vehicle")

                        cancel.setOnClickListener {

                            dialog.dismiss()
                        }
                        ok.setOnClickListener {

                            dialog.dismiss()

                            val url = "http://motortraders.zydni.com/api/sellers/cars/delete/"+p_id

                            val queue = Volley.newRequestQueue(activity)


                            val request: StringRequest = object : StringRequest(
                                Method.DELETE, url,
                                Response.Listener { response ->


                                    model.removeAt(position)
                                    adapter!!.notifyItemRemoved(position)

                                    Toast.makeText(activity,"Deleted", Toast.LENGTH_LONG).show()

                                    val obj = ManageBidsFragment()
                                    





                                }, Response.ErrorListener { error ->

                                    Toast.makeText(activity,"Something went wrong", Toast.LENGTH_LONG).show()
                                }) {

                                @Throws(AuthFailureError::class)
                                override fun getHeaders(): Map<String, String> {
                                    val headers = HashMap<String, String>()
                                    headers.put("Accept","application/json")
                                    headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(it.context))

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

//                val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
//                val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//                dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
                        //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
                        // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
                        dialog.show()

                    }
                })


//                adapter!!.setOnItemClickListener2(object : CurrentBidsAdapter.onItemClickListener2 {
//                    override fun onItemClick2(position: Int) {
//
//                        adapter!!.notifyItemRemoved(position)
//                    }
//
//                })


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