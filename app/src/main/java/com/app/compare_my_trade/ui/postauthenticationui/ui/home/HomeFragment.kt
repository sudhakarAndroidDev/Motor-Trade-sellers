package com.app.compare_my_trade.ui.postauthenticationui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arindicatorview.ARIndicatorView
import com.example.kotlin_project1.CurrentBid.CurrentBidsAdapter
import com.example.kotlin_project1.CurrentBid.CurrentBidsModel
import com.example.kotlin_project1.WonBid.WonBidsAdapter
import com.example.kotlin_project1.WonBid.WonBidsModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.Intent as Intent1
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Build
import android.util.Log
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.*
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.SpinKitView
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.HashMap


class HomeFragment : Fragment() {

    lateinit var arIndicatorView: ARIndicatorView
    lateinit var profile:ImageView


    lateinit var recyclerview1:RecyclerView

    var adapter2: CurrentBidsAdapter? = null

    lateinit var recyclerview:RecyclerView

    lateinit var name :TextView
    lateinit var bid_count :TextView

    var adapter: WonBidsAdapter? = null

    var status:String? = null

    lateinit var spinKitView: SpinKitView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {



        val view= inflater.inflate(R.layout.fragment_home, container, false)

        res2()
        current_bids()
        acceptedbids()

        recyclerview = view.findViewById(R.id.recyclerview_accepted_bids)
        recyclerview1 = view.findViewById(R.id.recyclerview_accepted_bids2)

        name = view.findViewById(R.id.name)
        bid_count = view.findViewById(R.id.bid_count)

        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floating3)
        profile = view.findViewById<ImageView>(R.id.profile_photo)
        arIndicatorView = view.findViewById(R.id.ar_indicator)

        spinKitView =  view.findViewById(R.id.progressBar)

        profile.setOnClickListener {

//            findNavController().navigate(R.id.action_navigation_home_to_navigation_more2)
            val intent = Intent1(activity, HomeActivity::class.java).apply {
                putExtra("more","more")
            }
            startActivity(intent)

        }

        floatingActionButton.setOnClickListener {


            if (status != null) {
                if (status.equals("active")) {

                    val intent = Intent1(activity, Edit_vehicle::class.java).apply {
                        putExtra("add","add")
                    }
                    startActivity(intent)
                } else {
//                    AlertDialog.Builder(it.context)
//                        .setTitle("Warring")
//                        .setMessage("You need Subscription before continuing")
//                        .setNegativeButton(android.R.string.no, null)
//                        .setPositiveButton(
//                            android.R.string.yes,
//                            DialogInterface.OnClickListener { dialogInterface, i ->
//                                val intent =
//                                    android.content.Intent(it.context, PlanDetails::class.java)
//                                        .apply {
//                                        }
//                                startActivity(intent)
//                            }
//                        ).create().show()

                    val dialog = Dialog(it.context)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
                    dialog.setContentView(R.layout.alert_dialog_box)


                    val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
                    val oktext = dialog.findViewById<TextView>(R.id.yestext)
                    val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
                    val title = dialog.findViewById<TextView>(R.id.title)
                    val massage = dialog.findViewById<TextView>(R.id.message)

                    title.setText("Alert")
                    oktext.text = "Buy"
                    massage.setText("You don't have any active plan")

                    cancel.setOnClickListener {

                        dialog.dismiss()
                    }
                    ok.setOnClickListener {

                        val intent = android.content.Intent(it.context, PlanDetails::class.java)
                            .apply {
                            }
                        startActivity(intent)
                        dialog.dismiss()
                    }

//            val body = dialog.findViewById(R.id.body) as TextView
//            body.text = title
//            val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
//            val noBtn = dialog.findViewById(R.id.noBtn) as TextView
//            yesBtn.setOnClickListener {
//                dialog.dismiss()
//            }
//            noBtn.setOnClickListener { dialog.dismiss() }


                    //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
                    // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
                    dialog.show()

                }

            }
        }





        return view
    }

    private fun res2() {


        val URL = "http://motortraders.zydni.com/api/sellers/detail"

        val queue = Volley.newRequestQueue(activity)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            { response ->

                val res = JSONObject(response)



                try {
                    var avatar = res.getString("avatar")
                    var first_name = res.getString("first_name")
                    var subscription_status = res.getJSONObject("subscription_status")

                     status = subscription_status.getString("status")


                    name.setText(first_name)

                    if(activity !=null) {
                        Glide.with(this).load(avatar).fitCenter().into(profile)
                    }
                } catch (e: JSONException) {
                } catch (error: UnsupportedEncodingException) {
                }







//                Toast.makeText(requireContext(), avatar.toString(),Toast.LENGTH_LONG).show()
//                Log.i("jdhfisd", avatar.toString())



            }, { error ->



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

    private fun current_bids() {
    }

    private fun acceptedbids() {
        val URL = "http://motortraders.zydni.com/api/sellers/cars/list"

        val queue = Volley.newRequestQueue(activity)
        val model = ArrayList<WonBidsModel>()

        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                val res = JSONArray(response)

                if(activity !=null) {



                recyclerview.layoutManager = LinearLayoutManager(this.requireContext())



                for (i in 0 until res.length()) {
                    val data: JSONObject = res.getJSONObject(i)

                    var vehicle_status = data.getString("vehicle_status")

                    if(vehicle_status.equals("unavailable")){


                        try {


                            var bid_price = data.getString("vehicle_price")
                            var brand_name = data.getString("name")+" "+data.getString("brand_name")
                            var body_type = data.getString("body_type")
                            var advertisement_id = data.getString("advertisement_id")
                            var front_image = data.getString("front_image")
                            var product_id = data.getString("id")
                            var buyer = data.getJSONObject("buyer")
                            var buyer_name = buyer.getString("first_name")
                            var model_name = data.getString("model_name")+" "+ data.getString("model_year")
//                            var model_name = data.getString("model_name")

                            model.add(
                                WonBidsModel(
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

//                    Toast.makeText(activity,first_name.toString(),Toast.LENGTH_LONG).show()
//                    Log.i("jdhfisd",first_name.toString())

                }
                recyclerview.layoutManager = LinearLayoutManager(activity)


//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img_6, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))


                adapter = WonBidsAdapter(model)

                recyclerview.adapter = adapter

                recyclerview.setItemViewCacheSize(10000)
                Log.wtf("size_of_my_list", Integer.toString(model.size));
                adapter!!.notifyDataSetChanged()
                Log.d("debugMode", "The onCreateView method has been launched");

                recyclerview.setLayoutManager(
                    LinearLayoutManager(
                        activity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                )
                arIndicatorView.attachTo(recyclerview, true)

                adapter!!.setOnItemClickListener(object : WonBidsAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {

                        val mod: WonBidsModel = model.get(position)

                        var pid = mod.p_id
//                        var price = mod.text6


//                        V_name = mod.text2
//                        V_body_type= mod.text3
//                        V_advertisement_id= mod.text4
//                        front_image= mod.image


                        val intent = android.content.Intent(activity, CarDetails::class.java).apply {
                            putExtra("v_id", pid)
//                            putExtra("type", "current_bids")
//                            putExtra("my_bid", price)
                            this.putExtra("bids","AcceptedBids")
                        }
                        startActivity(intent)
                    }

                })
                }

            }, Response.ErrorListener { error ->


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

    override fun onStart() {
        super.onStart()
        val URL = "http://motortraders.zydni.com/api/sellers/cars/list"

        val queue = Volley.newRequestQueue(activity)
        val model = ArrayList<CurrentBidsModel>()

        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                spinKitView.visibility = View.GONE

                val res = JSONArray(response)

                if(activity !=null) {

                    recyclerview1.layoutManager = LinearLayoutManager(this.requireContext())


                    bid_count.text = res.length().toString()

                    for (i in 0 until res.length()) {
                        val data: JSONObject = res.getJSONObject(i)

                        var vehicle_status = data.getString("vehicle_status")

                        if (vehicle_status.equals("available")) {


                            try {


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
                    recyclerview1.layoutManager = LinearLayoutManager(activity)


//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img_6, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))


                    adapter2 = CurrentBidsAdapter(model)

                    recyclerview1.adapter = adapter2

                    recyclerview1.setItemViewCacheSize(10000)
                    Log.wtf("size_of_my_list", Integer.toString(model.size));
                    adapter2!!.notifyDataSetChanged()
                    Log.d("debugMode", "The onCreateView method has been launched");

                    adapter2!!.setOnItemClickListener(object :
                        CurrentBidsAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val mod: CurrentBidsModel = model.get(position)

                            var pid = mod.p_id
//                        var price = mod.text6


//                        V_name = mod.text2
//                        V_body_type= mod.text3
//                        V_advertisement_id= mod.text4
//                        front_image= mod.image


                            val intent =
                                android.content.Intent(activity, CarDetails::class.java).apply {
                                    putExtra("v_id", pid)
//                            putExtra("type", "current_bids")
//                            putExtra("my_bid", price)
                                    this.putExtra("bids", "LiveBids")
                                }
                            startActivity(intent)
                        }

                    })
                    adapter2!!.setOnItemClickListener3(object :CurrentBidsAdapter.onItemClickListener3{
                        override fun onItemClick3(position: Int) {
                            var p_id = model.get(position).p_id
                            val intent = android.content.Intent(activity, Edit_vehicle::class.java).apply {
                                putExtra("p_id", p_id)
                            }
                            startActivity(intent)

                        }
                    })
                    adapter2!!.setOnItemClickListener2(object :CurrentBidsAdapter.onItemClickListener2{
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

                                val queue = Volley.newRequestQueue(it.context)


                                val request: StringRequest = object : StringRequest(
                                    Method.DELETE, url,
                                    Response.Listener { response ->


                                        model.removeAt(position)
                                        adapter2!!.notifyItemRemoved(position)
                                        bid_count.text = (res.length() - 1).toString()
                                        Toast.makeText(activity,"Deleted", Toast.LENGTH_LONG).show()
//                                        val intent = Intent1(activity, HomeActivity::class.java).apply {
//                                        }
//                                        startActivity(intent)


                                    }, Response.ErrorListener { error ->

//                                        Log.i("ijhind", error.toString())
//                                        model.removeAt(position)
//                                        adapter2!!.notifyItemRemoved(position)

                                        Toast.makeText(activity,"Something went wrong", Toast.LENGTH_LONG).show()
//                                        try {
//
//                                            val charset: Charset = Charsets.UTF_8
//
//                                            val jsonObject = String(error.networkResponse.data, charset)
//                                            val data = JSONObject(jsonObject)
//
//                                            Log.i("ijhind", data.toString())
//                                        } catch (e: JSONException) {
//                                        } catch (error: UnsupportedEncodingException) {
//                                        }
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

//                adapter2!!.setOnItemClickListener2(object : CurrentBidsAdapter.onItemClickListener2 {
//                    override fun onItemClick2(position: Int) {
//
//
//                        adapter2!!.notifyItemRemoved(position)
//
//
//                    }
//                })

                    val myLinearLayoutManager = object : LinearLayoutManager(activity) {
                        override fun canScrollVertically(): Boolean {
                            return false
                        }
                    }

                    recyclerview1.layoutManager = myLinearLayoutManager
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