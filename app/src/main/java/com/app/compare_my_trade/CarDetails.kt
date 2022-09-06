package com.app.compare_my_trade

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.ListOfBiding.ListofBidingModel
import com.app.compare_my_trade.car_images.CarImage
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils
import com.arindicatorview.ARIndicatorView
import com.bumptech.glide.Glide
import com.example.kotlin_project1.WonBid.ListOfBidingAdapter
import com.github.ybq.android.spinkit.SpinKitView
import org.json.JSONException
import org.json.JSONObject

class CarDetails : AppCompatActivity() {

    lateinit var Bids:String
    lateinit var linearLayout: LinearLayout
    lateinit var linear1: LinearLayout
    lateinit var linearLayout2: LinearLayout
    lateinit var  textView1: TextView
    lateinit var  textView2: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var textView3: TextView
    lateinit var back: TextView


    lateinit var arIndicatorView: ARIndicatorView
    lateinit var recyclerview:RecyclerView



    var v_id: String? = null
    lateinit var bidId:String


    lateinit var v_name:TextView
    lateinit var v_body_type:TextView
    lateinit var v_advertisement_id:TextView
    lateinit var v_status:TextView
    lateinit var v_location:TextView
    lateinit var v_fuel_type:TextView
    lateinit var v_transmission:TextView
    lateinit var v_odometer:TextView
    lateinit var v_drive_type:TextView
    lateinit var v_make:TextView
    lateinit var v_body_type_table:TextView
    lateinit var v_model:TextView
    lateinit var v_model_des:TextView
    lateinit var v_transmission_table:TextView
    lateinit var v_drive_type_table:TextView
    lateinit var v_fuel_type_table:TextView
    lateinit var v_service_log:TextView
    lateinit var v_current_odometer:TextView
    lateinit var v_VIN:TextView
    lateinit var v_price_table:TextView
    lateinit var v_status_table:TextView
    lateinit var buyer_name:TextView
    lateinit var buyer_number:TextView
    lateinit var v_price:TextView
    lateinit var v_registration_no:TextView
    lateinit var update_bids:TextView
    lateinit var mybidprice:TextView
    lateinit var post_date:TextView
    lateinit var accept_avatar:ImageView
    lateinit var accept_name:TextView
    lateinit var accept_number:TextView
    lateinit var accept_email:TextView
    lateinit var accept_address_line:TextView


    lateinit var Make:TextView
    lateinit var body_type_table:TextView
    lateinit var Model:TextView
    lateinit var model_des:TextView
    lateinit var model_year:TextView
    lateinit var transmission_table:TextView
    lateinit var drive_type_table:TextView
    lateinit var fuel_type_table:TextView
    lateinit var service_log:TextView
    lateinit var current_odometer:TextView
    lateinit var VIN:TextView
    lateinit var price_table:TextView
    lateinit var status_table:TextView
    lateinit var registration_no:TextView
    lateinit var bidCount:TextView
    lateinit var accepted_bid_amount:TextView
    lateinit var owners_manual:TextView

    lateinit var change:Button


    var V_name : String? = null
    var V_body_type: String? = null
    var V_fuel_type: String? = null
    var V_transmission: String? = null
    var V_odometer: String? = null
    var V_drive_type: String? = null
    var V_make: String? = null
    var V_model_year: String? = null
    var V_model: String? = null
    var V_model_des: String? = null
    var V_service_log: String? = null
    var V_VIN: String? = null
    var V_price: String? = null
    var V_registration_no: String? = null
    var V_advertisement_id: String? = null


    var front_image: String? = null
    var rear_image: String? = null
    var interior_image: String? = null
    var cargo_area_image: String? = null
    var engine_bay_image: String? = null
    var roof_image: String? = null
    var wheels_image: String? = null
    var keys_image: String? = null
    var left_side_image: String? = null
    var owners_Manual_Side: String? = null

    var My_bids: String? = null


     var bid_id: String? = null
    var accepted_bid_id: String? = null

    var bidding_type :String? = null

    var buyerName:String? = null
    var buyerNumber:String? = null
    var buyerAvatar:String? = null
    var buyerbusinessname:String? = null
    var buyerbusinessemail:String? = null
    var buyerABN:String? = null
    var buyerpincode:String? = null
    var buyer_address_line:String? = null
    var buyer_email:String? = null
    var accept_bid_price: String? = null


    lateinit var spinKitView: SpinKitView

    lateinit var load: LinearLayout
    lateinit var relativeLayout: RelativeLayout

    lateinit var bidNow:LinearLayout

    lateinit var back_type:String

    lateinit var finil_bidType:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)





        Bids = intent.getStringExtra("bids").toString()
        v_id = intent.getStringExtra("v_id").toString()
        back_type = intent.getStringExtra("type").toString()

        Log.i("id", v_id!!)

        if (intent.getStringExtra("n_id").toString() != null){
            val URL = "http://motortraders.zydni.com/api/sellers/notification/mark-as-read/"+intent.getStringExtra("n_id").toString()

            val queue = Volley.newRequestQueue(this)


            val request: StringRequest = object : StringRequest(
                Method.POST, URL,
                Response.Listener { response ->



                    if (response.toString().equals("true")){

                    }

                }, Response.ErrorListener { error ->

                }) {


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Accept", "application/json")
                    headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@CarDetails))
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


        spinKitView = findViewById<SpinKitView>(R.id.progressBar)
        load = findViewById(R.id.linear3)
        relativeLayout = findViewById(R.id.scr)




        spinKitView.visibility = View.VISIBLE
//        Toast.makeText(this,v_id,Toast.LENGTH_LONG).show()


        bidNow =  findViewById<LinearLayout>(R.id.bid_now_layout)

        val edit =  findViewById<ImageView>(R.id.edit)
        val delete =  findViewById<ImageView>(R.id.delete)
        change = findViewById(R.id.change_buttom)
        val show_details= findViewById<ImageView>(R.id.show_details)

        recyclerview =  findViewById(R.id.view_image)
        arIndicatorView =  findViewById(R.id.ar_indicator)

        linearLayout = findViewById(R.id.linear111)
        linearLayout2 = findViewById(R.id.linear222)
        textView1 = findViewById(R.id.vehicle_status)
        textView2 = findViewById(R.id.vehicle_status_table)
        textView3 = findViewById(R.id.text3)
        back = findViewById(R.id.back)
        recyclerView = findViewById(R.id.list_of_biding_recyclerview)
        linear1 = findViewById(R.id.linear1)
        bidCount = findViewById(R.id.bid_count)
        accepted_bid_amount = findViewById(R.id.accepted_bid_amount)
        owners_manual = findViewById(R.id.owners_manual)

        v_name = findViewById(R.id.v_name)
        v_body_type= findViewById(R.id.body_type)
        v_advertisement_id= findViewById(R.id.advertisement_id)
        v_status= findViewById(R.id.vehicle_status)
//        v_location= findViewById(R.id.location)
        v_fuel_type= findViewById(R.id.fuel_type)
        v_transmission= findViewById(R.id.transmission)
        v_odometer= findViewById(R.id.odometer)
        v_drive_type= findViewById(R.id.drive_type)
        v_make= findViewById(R.id.make)
        v_body_type_table= findViewById(R.id.body_type_table)
        v_model= findViewById(R.id.model)
        v_model_des= findViewById(R.id.model_des)
        v_transmission_table= findViewById(R.id.transmission_table)
        v_drive_type_table= findViewById(R.id.drive_type_table)
        v_fuel_type_table= findViewById(R.id.fuel_type_table)
        v_service_log= findViewById(R.id.service_log)
        v_current_odometer= findViewById(R.id.current_odometer)
        v_VIN= findViewById(R.id.vehicle_VIN)
        v_price_table= findViewById(R.id.vehicle_price_table)
        v_status_table= findViewById(R.id.vehicle_status_table)
        buyer_name= findViewById(R.id.buyer_name)
        buyer_number= findViewById(R.id.buyer_number)
        post_date =  findViewById(R.id.post_at)
        v_price= findViewById(R.id.vehicle_price)
        v_registration_no = findViewById(R.id.Registration)
//        mybidprice =  findViewById(R.id.my_bid_price)
        accept_avatar = findViewById(R.id.accept_avatar)
        accept_name = findViewById(R.id.buyer_name)
        accept_number = findViewById(R.id.buyer_number)
        accept_email = findViewById(R.id.buyer_email)
        accept_address_line= findViewById(R.id.buyer_address_line)



        Make= findViewById(R.id.Make)
        body_type_table= findViewById(R.id.Body_type_table)
        Model= findViewById(R.id.Model)
        model_des= findViewById(R.id.Model_des)
        transmission_table= findViewById(R.id.Transmission_table)
        drive_type_table= findViewById(R.id.Drive_type_table)
        fuel_type_table= findViewById(R.id.Fuel_type_table)
        service_log= findViewById(R.id.Service_log)
        current_odometer= findViewById(R.id.Current_odometer)
        VIN= findViewById(R.id.Vehicle_VIN)
        price_table= findViewById(R.id.Vehicle_price_table)
        status_table= findViewById(R.id.Vehicle_status_table)
        registration_no = findViewById(R.id.registration)
        model_year = findViewById(R.id.model_year)
        data()

        if (Bids.equals("SoldVehicles")){
            delete.visibility = View.GONE
        }


//        if (Bids.equals("LiveBids"))
//        {
//            linearLayout2.setBackgroundResource(R.drawable.vector68)
//
//        }else if (Bids.equals("AcceptedBids"))
//        {
//            linearLayout.visibility = View.VISIBLE
//            linearLayout2.setBackgroundResource(R.drawable.vector63)
//
//            textView3.setText("See Buyer Detail")
//        }else if (Bids.equals("SoldVehicles")){
//            linearLayout.visibility = View.VISIBLE
//            linearLayout2.setBackgroundResource(R.drawable.vector62)
//            textView3.setText("See Buyer Detail")
//            textView2.setTextColor(color1)
//            change.visibility = View.GONE
//            recyclerView.visibility = View.GONE
//            linear1.visibility =  View.GONE
//        }


        back.setOnClickListener {

            if (back_type.equals("accepted") || back_type.equals("sold")){

                val intent = Intent(this@CarDetails, HomeActivity::class.java).apply {


                }
                startActivity(intent)
            }else{
                super.onBackPressed()
            }

        }



        show_details.setOnClickListener {

            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
            dialog.setContentView(R.layout.submit)

            val textView = dialog.findViewById<TextView>(R.id.close)


            val buyer_name = dialog.findViewById<TextView>(R.id.buyer_name)
            val buyer_number= dialog.findViewById<TextView>(R.id.buyer_number)
            val buyer_business_name = dialog.findViewById<TextView>(R.id.buyer_business_name)
            val buyer_business_email = dialog.findViewById<TextView>(R.id.buyer_business_email)
            val buyer_addess = dialog.findViewById<TextView>(R.id.buyer_address_line)
            val buyer_pinCode = dialog.findViewById<TextView>(R.id.buyer_pin_code)
            val buyer_profile = dialog.findViewById<ImageView>(R.id.buyer_profile)

            buyer_name.text = "Name: "+buyerName
            if (buyerNumber.equals("null")){
                buyer_number.text = "Phone: "+"-"
            }else{
                buyer_number.text = "Phone: "+buyerNumber
            }

            if (buyerbusinessname.equals("null")){
                buyer_business_name.text = "-"
            }else{
                buyer_business_name.text = buyerbusinessname
            }
            if (buyerbusinessemail.equals("null")){
                buyer_business_email.text = "-"
            }else{
                buyer_business_email.text = buyerbusinessemail
            }

            if (buyer_address_line.equals("null")){
                buyer_addess.text = "-"
            }else{
                buyer_addess.text = buyer_address_line
            }
            if (buyerpincode.equals("null")){
                buyer_pinCode.text ="-"
            }else{
                buyer_pinCode.text =buyerpincode
            }

            Glide.with(this).load(buyerAvatar).fitCenter().into(buyer_profile)

            textView.setOnClickListener {

                dialog.dismiss()
            }
            dialog.setCancelable(false)
//            val body = dialog.findViewById(R.id.body) as TextView
//            body.text = title
//            val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
//            val noBtn = dialog.findViewById(R.id.noBtn) as TextView
//            yesBtn.setOnClickListener {
//                dialog.dismiss()
//            }
//            noBtn.setOnClickListener { dialog.dismiss() }

            val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
            val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
            // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.show()


        }

        edit.setOnClickListener {

            val intent = Intent(this, Edit_vehicle::class.java).apply {
                putExtra("p_id", v_id)
                putExtra("V_name",V_name)
                putExtra("V_body_type",V_body_type)
                putExtra("V_fuel_type",V_fuel_type)
                putExtra("V_transmission",V_transmission)
                putExtra("V_odometer",V_odometer)
                putExtra("V_drive_type",V_drive_type)
                putExtra("V_make",V_make)
                putExtra("V_model_year",V_model_year)
                putExtra("V_model",V_model)
                putExtra("V_model_des",V_model_des)
                putExtra("V_service_log",V_service_log)
                putExtra("V_VIN",V_VIN)
                putExtra("V_price",V_price)
                putExtra("V_registration_no",V_registration_no)
                putExtra("front_image",front_image)
                putExtra("rear_image",rear_image)
                putExtra("interior_image",interior_image)
                putExtra("cargo_area_image",cargo_area_image)
                putExtra("engine_bay_image",engine_bay_image)
                putExtra("roof_image",roof_image)
                putExtra("wheels_image",wheels_image)
                putExtra("keys_image",keys_image)
                putExtra("left_side_image",left_side_image)
                putExtra("owners_Manual_Side",owners_Manual_Side)




            }
            startActivity(intent)
        }

        delete.setOnClickListener {
            val url = "http://motortraders.zydni.com/api/sellers/cars/delete/" +v_id

            val queue = Volley.newRequestQueue(it.context)


            val request: StringRequest = object : StringRequest(
                Method.DELETE, url,
                Response.Listener { response ->


                    Toast.makeText(it.context, "Deleted", Toast.LENGTH_LONG).show()
                    val intent = Intent(it.context, HomeActivity::class.java).apply {
                    }
                    it.context.startActivity(intent)


                }, Response.ErrorListener { error ->
                    Toast.makeText(it.context, "Deleted", Toast.LENGTH_LONG).show()
                    val intent = Intent(it.context, HomeActivity::class.java).apply {
                    }
                    it.context.startActivity(intent)

                }) {

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Accept", "application/json")
                    headers.put("Authorization", "Bearer " + PreferenceUtils.getTokan(it.context))

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

//
//        imageView.setOnClickListener {
//
//            val intent = Intent(this, CarImage::class.java).apply {
//
//            }
//            startActivity(intent)
//        }



        bidNow.setOnClickListener {

            if (Bids.equals("LiveBids"))
            {
                val intent = Intent(this, Edit_vehicle::class.java).apply {
                    putExtra("p_id", v_id)
                    putExtra("V_name",V_name)
                    putExtra("V_body_type",V_body_type)
                    putExtra("V_fuel_type",V_fuel_type)
                    putExtra("V_transmission",V_transmission)
                    putExtra("V_odometer",V_odometer)
                    putExtra("V_drive_type",V_drive_type)
                    putExtra("V_make",V_make)
                    putExtra("V_model_year",V_model_year)
                    putExtra("V_model",V_model)
                    putExtra("V_model_des",V_model_des)
                    putExtra("V_service_log",V_service_log)
                    putExtra("V_VIN",V_VIN)
                    putExtra("V_price",V_price)
                    putExtra("V_registration_no",V_registration_no)
                    putExtra("front_image",front_image)
                    putExtra("rear_image",rear_image)
                    putExtra("interior_image",interior_image)
                    putExtra("cargo_area_image",cargo_area_image)
                    putExtra("engine_bay_image",engine_bay_image)
                    putExtra("roof_image",roof_image)
                    putExtra("wheels_image",wheels_image)
                    putExtra("keys_image",keys_image)
                    putExtra("left_side_image",left_side_image)
                    putExtra("owners_Manual_Side",owners_Manual_Side)
                }
                startActivity(intent)

            }else if (Bids.equals("AcceptedBids")|| finil_bidType.equals("accepted"))
            {
//                val dialog = Dialog(this)
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//                dialog.setContentView(R.layout.submit)
//
//                val textView = dialog.findViewById<TextView>(R.id.close)
//                val buyer_name = dialog.findViewById<TextView>(R.id.buyer_name)
//                val buyer_number= dialog.findViewById<TextView>(R.id.buyer_number)
//                val buyer_business_name = dialog.findViewById<TextView>(R.id.buyer_business_name)
//                val buyer_business_email = dialog.findViewById<TextView>(R.id.buyer_business_email)
//                val buyer_ABN = dialog.findViewById<TextView>(R.id.buyer_ABN)
//                val buyer_pinCode = dialog.findViewById<TextView>(R.id.buyer_pin_code)
//                val buyer_profile = dialog.findViewById<ImageView>(R.id.buyer_profile)
//
//                buyer_name.text = buyerName
//                buyer_number.text = buyerNumber
//                buyer_business_name.text = buyerbusinessname
//                buyer_business_email.text = buyerbusinessemail
//                buyer_ABN.text = buyerABN
//                buyer_pinCode.text =buyerpincode
//                Glide.with(this).load(buyerAvatar).fitCenter().into(buyer_profile)
//
//                textView.setOnClickListener {
//
//                    dialog.dismiss()
//                }
//                dialog.setCancelable(false)
////            val body = dialog.findViewById(R.id.body) as TextView
////            body.text = title
////            val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
////            val noBtn = dialog.findViewById(R.id.noBtn) as TextView
////            yesBtn.setOnClickListener {
////                dialog.dismiss()
////            }
////            noBtn.setOnClickListener { dialog.dismiss() }
//
//                val width = (resources.displayMetrics.widthPixels * 1.00).toInt()
//                val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//                dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//                //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
//                // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
//                dialog.show()



                val dialog = Dialog(it.context)
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
                massage.setText("Are you sure want to sold the bid")

                cancel.setOnClickListener {

                    dialog.dismiss()
                }
                ok.setOnClickListener {

                    dialog.dismiss()

                    val URL = "http://motortraders.zydni.com/api/sellers/bid/sold/" +accepted_bid_id
                    val queue = Volley.newRequestQueue(it.context)
                    val request: StringRequest = object : StringRequest(
                        Method.POST, URL,
                        Response.Listener { response ->

                            var res = response.toString()

                            if (res.equals("true")) {

                                Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
                                val intent = Intent(this, CarDetails::class.java).apply {


                                    putExtra("bids","SoldVehicles")
                                    putExtra("type","sold")
                                    putExtra("v_id",v_id)

                                }
                                startActivity(intent)
                            }

                        }, Response.ErrorListener { error ->
                            spinKitView.visibility = View.GONE
                            if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                                Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                            }
                        }) {
                        @Throws(AuthFailureError::class)
                        override fun getHeaders(): Map<String, String> {
                            val headers = HashMap<String, String>()
                            headers.put("Accept", "application/json")
                            headers.put(
                                "Authorization",
                                "Bearer "+ PreferenceUtils.getTokan(this@CarDetails)
                            )

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



            }else if (Bids.equals("SoldVehicles") || finil_bidType.equals("sold") ){
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
                dialog.setContentView(R.layout.submit)

                val textView = dialog.findViewById<TextView>(R.id.close)


                val buyer_name = dialog.findViewById<TextView>(R.id.buyer_name)
                val buyer_number= dialog.findViewById<TextView>(R.id.buyer_number)
                val buyer_business_name = dialog.findViewById<TextView>(R.id.buyer_business_name)
                val buyer_business_email = dialog.findViewById<TextView>(R.id.buyer_business_email)
                val buyer_addess = dialog.findViewById<TextView>(R.id.buyer_address_line)
                val buyer_pinCode = dialog.findViewById<TextView>(R.id.buyer_pin_code)
                val buyer_profile = dialog.findViewById<ImageView>(R.id.buyer_profile)

                buyer_name.text = "Name: "+buyerName
                if (buyerNumber.equals("null")){
                    buyer_number.text = "Phone: "+"-"
                }else{
                    buyer_number.text = "Phone: "+buyerNumber
                }

                if (buyerbusinessname.equals("null")){
                    buyer_business_name.text = "-"
                }else{
                    buyer_business_name.text = buyerbusinessname
                }
                if (buyerbusinessemail.equals("null")){
                    buyer_business_email.text = "-"
                }else{
                    buyer_business_email.text = buyerbusinessemail
                }

                if (buyer_address_line.equals("null")){
                    buyer_addess.text = "-"
                }else{
                    buyer_addess.text = buyer_address_line
                }
                if (buyerpincode.equals("null")){
                    buyer_pinCode.text ="-"
                }else{
                    buyer_pinCode.text =buyerpincode
                }

                Glide.with(this).load(buyerAvatar).fitCenter().into(buyer_profile)

                textView.setOnClickListener {

                    dialog.dismiss()
                }
                dialog.setCancelable(false)
//            val body = dialog.findViewById(R.id.body) as TextView
//            body.text = title
//            val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
//            val noBtn = dialog.findViewById(R.id.noBtn) as TextView
//            yesBtn.setOnClickListener {
//                dialog.dismiss()
//            }
//            noBtn.setOnClickListener { dialog.dismiss() }

                val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
                val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
                dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
                //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
                // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.show()



            }

        }

        change.setOnClickListener {

            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
            dialog.setContentView(R.layout.activity_change)

            val textView = dialog.findViewById<TextView>(R.id.close)

            textView.setOnClickListener {

                dialog.dismiss()
            }
            dialog.setCancelable(false)
//            val body = dialog.findViewById(R.id.body) as TextView
//            body.text = title
//            val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
//            val noBtn = dialog.findViewById(R.id.noBtn) as TextView
//            yesBtn.setOnClickListener {
//                dialog.dismiss()
//            }
//            noBtn.setOnClickListener { dialog.dismiss() }

            val width = (resources.displayMetrics.widthPixels * 1.00).toInt()
            val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
            // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.show()

            var sold = dialog.findViewById<RadioButton>(R.id.radioButton3)

            var update_status = dialog.findViewById<LinearLayout>(R.id.update_status)


            update_status.setOnClickListener {

                if (sold.isChecked) {


                    val URL = "http://motortraders.zydni.com/api/sellers/bid/sold/" +accepted_bid_id
                    val queue = Volley.newRequestQueue(it.context)
                    val request: StringRequest = object : StringRequest(
                        Method.POST, URL,
                        Response.Listener { response ->

                            var res = response.toString()

                            if (res.equals("true")) {

                                dialog.dismiss()
                                Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
                            }

                        }, Response.ErrorListener { error ->

                        }) {
                        @Throws(AuthFailureError::class)
                        override fun getHeaders(): Map<String, String> {
                            val headers = HashMap<String, String>()
                            headers.put("Accept", "application/json")
                            headers.put(
                                "Authorization",
                                "Bearer "+ PreferenceUtils.getTokan(this@CarDetails)
                            )

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
        }
    }


    private fun data() {

        Log.i("ekfkfsw",v_id+"    "+PreferenceUtils.getTokan(this@CarDetails))
        val URL = "http://motortraders.zydni.com/api/sellers/cars/show/"+v_id
        Log.i("gyhujnln",v_id.toString())

        val queue = Volley.newRequestQueue(this)


        val request: StringRequest = object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                spinKitView.visibility = View.GONE
                relativeLayout.visibility = View.VISIBLE
                load.visibility = View.VISIBLE

                val res = JSONObject(response)
                val hiddenSeller: JSONObject
                var vehicleDetail: JSONObject
                vehicleDetail = res.getJSONObject("vehicleDetail")
//                hiddenSeller = res.getJSONObject("hiddenSeller")







                var V_id = vehicleDetail.getString("id")
                V_name = vehicleDetail.getString("name")
                V_body_type= vehicleDetail.getString("body_type")
                V_advertisement_id= vehicleDetail.getString("advertisement_id")
                var V_status= vehicleDetail.getString("vehicle_status")
                var V_location= vehicleDetail.getString("name")
                V_fuel_type= vehicleDetail.getString("fuel_type")
                V_transmission= vehicleDetail.getString("transmission")
                V_odometer= vehicleDetail.getString("odometer_mileage")
                V_drive_type= vehicleDetail.getString("drive_type")
                V_make= vehicleDetail.getString("brand_name")
                V_model_year = vehicleDetail.getString("model_year")
                V_model= vehicleDetail.getString("model_name")
                V_model_des= vehicleDetail.getString("model_description")
                V_service_log = vehicleDetail.getString("service_log_book")
                V_VIN= vehicleDetail.getString("vehicle_vin")
//                var Post_name= hiddenSeller.getString("name")
//                var Post_number= hiddenSeller.getString("business_phone")
                var Post_date= vehicleDetail.getString("published_at")
                V_price= vehicleDetail.getString("vehicle_price")
                V_registration_no = vehicleDetail.getString("vehicle_registration_number")
                var bid_count = vehicleDetail.getString("bid_count")



                val color1 = ContextCompat.getColor(this, R.color.Red)








                v_name.text =  V_make+" "+V_model
                v_body_type.text= V_model_year
                v_advertisement_id.text= "Ad ID - "+V_advertisement_id

//                v_location.text= V_location
                v_fuel_type.text= V_fuel_type
                v_transmission.text= V_transmission
                v_odometer.text= V_odometer+"km"
                v_drive_type.text= V_drive_type
                v_make.text= V_make
                model_year.text = V_model_year
                v_body_type_table.text= V_body_type
                v_model.text= V_model
                v_model_des.text= V_model_des
                v_transmission_table.text=  V_transmission
                v_drive_type_table.text= V_drive_type
                v_fuel_type_table.text= V_fuel_type
                if (V_service_log.equals("null")){
                    v_service_log.text= "-"
                }else{
                    v_service_log.text= "view"
                }
                v_service_log.setOnClickListener {
                    if (V_service_log.equals("null")){

                    }else{
                        val viewIntent = Intent("android.intent.action.VIEW",
                            Uri.parse(V_service_log))
                        startActivity(viewIntent)
                    }

                }

                v_current_odometer.text= V_odometer+"km"
                v_VIN.text= V_VIN
                v_price_table.text= "$ "+V_price
                v_status_table.text= V_status
//                post_name.text= Post_name
//                post_number.text= Post_number
                post_date.text= "Posted on : "+Post_date
                bidCount.text="("+bid_count+")"






                if (intent.getStringExtra("type").toString().equals("current_bids")){

                }else{
                    v_price.text= "$ "+V_price
                }

                v_registration_no.text = V_registration_no




                front_image= vehicleDetail.getString("front_image")
                rear_image= vehicleDetail.getString("rear_image")
                left_side_image= vehicleDetail.getString("left_side_image")
                interior_image= vehicleDetail.getString("interior_image")
                cargo_area_image= vehicleDetail.getString("cargo_area_image")
                engine_bay_image= vehicleDetail.getString("engine_bay_image")
                roof_image= vehicleDetail.getString("roof_image")
                wheels_image= vehicleDetail.getString("wheels_image")
                keys_image= vehicleDetail.getString("keys_image")
                owners_Manual_Side = vehicleDetail.getString("owners_manual")

                if (owners_Manual_Side.equals("null")){
                    owners_manual.text = "-"
                }else{
                    owners_manual.text = "view"
                }
                owners_manual.setOnClickListener {
                    if (owners_Manual_Side.equals("null")){

                    }else{
                        val viewIntent = Intent("android.intent.action.VIEW",
                            Uri.parse(owners_Manual_Side))
                        startActivity(viewIntent)
                    }

                }

                recyclerview.layoutManager = LinearLayoutManager(this)

                val data = java.util.ArrayList<CarImageModel2>()


                data.add(CarImageModel2(front_image.toString()))
                if(rear_image.equals("null")){

                }else
                {
                    data.add(CarImageModel2(rear_image.toString()))
                }

                if(left_side_image.equals("null")) {
                }
                else{
                    data.add(CarImageModel2(left_side_image.toString() ))
                }

                if(interior_image.equals("null")) {
                }else{
                    data.add(CarImageModel2(interior_image.toString() ))
                }
                if(cargo_area_image.equals("null")) {
                }
                else {
                    data.add(CarImageModel2(cargo_area_image.toString()))
                }
                if(engine_bay_image.equals("null")) {
                }else{
                    data.add(CarImageModel2(engine_bay_image.toString()))
                }
                if(roof_image.equals("null")) {
                }else{
                    data.add(CarImageModel2(roof_image.toString() ))
                }
                if(wheels_image.equals("null")) {
                }else{
                    data.add(CarImageModel2(wheels_image.toString() ))
                }
                if(keys_image.equals("null")) {
                }else{
                    data.add(CarImageModel2(keys_image.toString()))
                }



                val adapter = CarImageAdapter2(data)


                recyclerview.adapter = adapter


                adapter.setOnItemClickListener(object : CarImageAdapter2.onItemClickListener{
                    override fun onItemClick(position: Int) {


                        val intent = Intent(this@CarDetails, CarImage::class.java).apply {
                            putExtra("id",V_id)


                        }
                        startActivity(intent)
                    }

                })
                recyclerview.setLayoutManager(
                    LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                )
                arIndicatorView.attachTo(recyclerview, true)



                var biddings = res.getJSONArray("biddings")

                if (biddings.length() != 0){

                }


                val data1 = ArrayList<ListofBidingModel>()
                recyclerView.layoutManager = LinearLayoutManager(this)

                for (i in 0 until biddings.length()) {
                    val data: JSONObject = biddings.getJSONObject(i)

                    try {
                        var number2:String

                        bid_id = data.getString("id")

                        var bid_price2 = data.getString("bid_price")
                        var buyer_object = data.getJSONObject("buyer")



                        bidding_type =  data.getString("bidding_type")
                        var name2 = buyer_object.getString("first_name")
                        if (buyer_object.getString("business_phone").equals("null")){
                            number2 = "-"
                        }else
                        {
                             number2= buyer_object.getString("business_phone")
                        }

                        var avatar2 = buyer_object.getString("avatar")
                        var Email = buyer_object.getString("email")



                        if (bidding_type.equals("accepted") || bidding_type.equals("sold")  )
                        {

                            accepted_bid_id = data.getString("id")
                            buyerName = buyer_object.getString("first_name")
                            buyerNumber = buyer_object.getString("business_phone")
                            buyerAvatar = buyer_object.getString("avatar")
                            buyerbusinessname= buyer_object.getString("first_name")
                            buyerbusinessemail= buyer_object.getString("email")
                            buyerABN= buyer_object.getString("abn")
                            buyerpincode= buyer_object.getString("postal_code")
                            buyer_address_line =  buyer_object.getString("address_line")
                            buyer_email =  buyer_object.getString("email")
                            accept_bid_price = data.getString("bid_price")
                            finil_bidType = data.getString("bidding_type")

                            Glide.with(applicationContext).load(buyerAvatar).fitCenter().into(accept_avatar)
                            accept_name.text = "Name: "+buyerName
                            if (buyerNumber.equals("null")){
                                accept_number.text = "Phone: "+"-"
                            }else{
                                accept_number.text = "Phone: "+buyerNumber
                            }

                            accept_email.text = "Email: "+buyer_email

                            if (buyer_address_line.equals("null")){
                                accept_address_line.text = "-"
                            }else{
                                accept_address_line.text = buyer_address_line
                            }


                        }







                        data1.add(ListofBidingModel(avatar2, name2,number2,bid_price2,bid_id.toString(),Bids,v_id.toString(),Email))

                    } catch (e: JSONException) {

                    }

                    try {




                    } catch (e: JSONException) {

                    }


                    Log.i("dfbhdsfhsd",V_id)





                }

                val color11 = ContextCompat.getColor(this, R.color.color1)
                val color2 = ContextCompat.getColor(this, R.color.color2)
                val color3 = ContextCompat.getColor(this, R.color.color3)


                if (V_status.equals("available")){

                    linearLayout.visibility = View.GONE
                    textView3.setText("Edit Car Detail")
                    linearLayout2.setBackgroundColor(color11)
                    accepted_bid_amount.setText("Vehicle Price")
                    v_status.text= "Live Biddings"
                    v_price.text = "$ "+V_price

                }else if (V_status.equals("unavailable")){

                    linearLayout.visibility = View.VISIBLE
                    linearLayout2.setBackgroundColor(color2)
                    accepted_bid_amount.setText("Accepted Bid Amount")
                    textView3.setText("Mark as sold")
                    recyclerView.visibility = View.GONE
                    linear1.visibility =  View.GONE
                    v_status.text= "Accepted Biddings"
                    v_price.text = "$ "+accept_bid_price


                }else if (V_status.equals("sold")) {
                    linearLayout.visibility = View.VISIBLE
                    linearLayout2.setBackgroundColor(color3)
                    textView3.setText("See Buyer Detail")
                    textView2.setTextColor(color1)
                    change.visibility = View.GONE
                    accepted_bid_amount.setText("Sold Amount")
                    recyclerView.visibility = View.GONE
                    linear1.visibility =  View.GONE
                    v_status.text= "Sold Biddings"
                    v_price.text = "$ "+accept_bid_price
                }


                val adapter2 = ListOfBidingAdapter(data1)
                recyclerView.adapter = adapter2

                val myLinearLayoutManager = object : LinearLayoutManager(this) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }

                recyclerView.layoutManager = myLinearLayoutManager

            }, Response.ErrorListener { error ->

                spinKitView.visibility = View.GONE
                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                    Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                }

            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@CarDetails))

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

    override fun onBackPressed() {

        if (back_type.equals("accepted") || back_type.equals("sold")){

            val intent = Intent(this@CarDetails, HomeActivity::class.java).apply {


            }
            startActivity(intent)
        }else{
            super.onBackPressed()
        }

    }


}







data class CarImageModel2 (val image: String ){
}









class  CarImageAdapter2 (private val mList: List<CarImageModel2>) : RecyclerView.Adapter<CarImageAdapter2.ViewHolder>() {


    private  lateinit var mlistner: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistner = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_image2, parent, false)

        return ViewHolder(view,mlistner)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val CarImageModel2 = mList[position]


        Glide.with(holder.itemView).load(CarImageModel2.image).fitCenter().into(holder.imageView)



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View,listener: CarImageAdapter2.onItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)




        init {
            itemView.setOnClickListener {

                listener.onItemClick(adapterPosition)
            }
        }

    }
}