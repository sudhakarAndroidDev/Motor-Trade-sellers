package com.app.compare_my_trade

import android.Manifest
import android.R.attr
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.Spinner1.SpinnerAdapter
import com.app.compare_my_trade.Spinner1.SpinnerModel
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_car_details.*
import kotlinx.android.synthetic.main.activity_edit_vehicle.*
import kotlinx.android.synthetic.main.spinner1.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.nio.charset.Charset
import java.util.*
import kotlin.properties.Delegates


class Edit_vehicle : AppCompatActivity() {

    lateinit var make: Spinner
    lateinit var body_type: Spinner
    lateinit var model_year: Spinner
    lateinit var mod: Spinner
    lateinit var transmission: Spinner
    lateinit var drive_type: Spinner
    lateinit var fuel_type: Spinner
    lateinit var servic_book: TextView
    lateinit var des: EditText
    lateinit var odometer: EditText
    lateinit var reg_no: EditText
    lateinit var v_vin: EditText
    lateinit var v_price: EditText
    lateinit var v_name: EditText

    lateinit var Owners_Manual_text: TextView

    lateinit var Error1: TextView
    lateinit var Error2: TextView
    lateinit var Error3: TextView
    lateinit var Error4: TextView
    lateinit var Error5: TextView
    lateinit var Error6: TextView
    lateinit var Error7: TextView
    lateinit var Error8: TextView
    lateinit var Error9: TextView
    lateinit var Error10: TextView
    lateinit var Error11: TextView
    lateinit var Error12: TextView
    lateinit var Error13: TextView
    lateinit var Error14: TextView
    lateinit var Error15: TextView

    lateinit var uriTxt: TextView

    lateinit var spinKitView: SpinKitView


    lateinit var back: TextView

    lateinit var Frort_image: ImageView
    lateinit var Frort_image_btn: ImageView

    lateinit var Rear_image: ImageView
    lateinit var Rear_image_btn: ImageView

    lateinit var Left_Side_image: ImageView
    lateinit var Left_Side_image_btn: ImageView

    lateinit var Interior_Side_image: ImageView
    lateinit var Interior_Side_image_btn: ImageView

    lateinit var Dash_Engine_Bay_image: ImageView
    lateinit var Dash_Engine_Bay_image_btn: ImageView

    lateinit var Cargo_Area_Side_image: ImageView
    lateinit var Cargo_Area_Side_image_btn: ImageView

    lateinit var Wheels_Side_image: ImageView
    lateinit var Wheels_Side_image_btn: ImageView

    lateinit var Roof_Side_image: ImageView
    lateinit var Roof_Side_image_btn: ImageView

    lateinit var Owners_Manual_Side_image: RelativeLayout
    lateinit var Owners_Manual_Side_image_btn: ImageView

    lateinit var Owners_Manual_Side_layout:RelativeLayout

    lateinit var Keys_Side_image: ImageView
    lateinit var Keys_Side_image_btn: ImageView

    lateinit var upload_image:ImageView

    private val PERMISSION_REQUEST_CODE = 200

    lateinit var v_post: LinearLayout

    lateinit var v_post_test: TextView
    var make_id: String? = null
    var body_type_id: String? = null
    var model_year_id: String? = null
    var mod_id: String? = null
    var transmission_id: String? = null
    var drive_type_id: String? = null
    var fuel_type_id: String? = null

    var status: String? = null

    private val ROOT_URL = "http://motortraders.zydni.com/api/sellers/cars/add"
    private val REQUEST_PERMISSIONS = 100
    private val PICK_IMAGE_REQUEST = 1
    private var bitmap: Bitmap? = null
    private var bitmap2: Bitmap? = null
    private var bitmap3: Bitmap? = null
    private var bitmap4: Bitmap? = null
    private var bitmap5: Bitmap? = null
    private var bitmap6: Bitmap? = null
    private var bitmap7: Bitmap? = null
    private var bitmap8: Bitmap? = null
    private var bitmap9: Bitmap? = null
    private var bitmap10: Bitmap? = null
    private var bitmap11: Bitmap? = null


    private val SELECT_PHOTO = 1
    private val SELECT_PHOTO2 = 2
    private val SELECT_PHOTO3 = 3
    private val SELECT_PHOTO4 = 4
    private val SELECT_PHOTO5 = 5
    private val SELECT_PHOTO6 = 6
    private val SELECT_PHOTO7 = 7
    private val SELECT_PHOTO8 = 8
    private val SELECT_PHOTO9 = 9
    private val SELECT_PHOTO10 = 10
    private val SELECT_PHOTO11 = 11

    var imageuri: Uri? = null
    var imageuri2: Uri? = null


    var p_id: String? = null
    var add: String? = null


    var V_name: String? = null
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

    var color by Delegates.notNull<Int>()


    private val CAMERA_REQUEST1 = 12
    private val CAMERA_REQUEST2 = 13
    private val CAMERA_REQUEST3 = 14
    private val CAMERA_REQUEST4 = 15
    private val CAMERA_REQUEST5 = 16
    private val CAMERA_REQUEST6 = 17
    private val CAMERA_REQUEST7 = 18
    private val CAMERA_REQUEST8 = 19
    private val CAMERA_REQUEST9 = 20


    var currentphoto1: String? = null
    var currentphoto2: String? = null
    var currentphoto3: String? = null
    var currentphoto4: String? = null
    var currentphoto5: String? = null
    var currentphoto6: String? = null
    var currentphoto7: String? = null
    var currentphoto8: String? = null
    var currentphoto9: String? = null

    lateinit var owner_view:TextView
    lateinit var log_view:TextView

    private val PICK_FROM_GALLERY = 201

    private val imageView: ImageView? = null
    private val MY_CAMERA_PERMISSION_CODE = 100

    lateinit var make_layout:RelativeLayout
    lateinit var model_layout:RelativeLayout
    lateinit var year_layout:RelativeLayout

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vehicle)


        v_post_test = findViewById(R.id.post_text)
        p_id = intent.getStringExtra("p_id").toString()
        add = intent.getStringExtra("add").toString()


        V_name = intent.getStringExtra("V_name").toString()
        V_body_type = intent.getStringExtra("V_body_type").toString()
        V_fuel_type = intent.getStringExtra("V_fuel_type").toString()
        V_transmission = intent.getStringExtra("V_transmission").toString()
        V_odometer = intent.getStringExtra("V_odometer").toString()
        V_drive_type = intent.getStringExtra("V_drive_type").toString()
        V_make = intent.getStringExtra("V_make").toString()
        V_model_year = intent.getStringExtra("V_model_year").toString()
        V_model = intent.getStringExtra("V_model").toString()
        V_model_des = intent.getStringExtra("V_model_des").toString()
        V_service_log = intent.getStringExtra("V_service_log").toString()
        V_VIN = intent.getStringExtra("V_VIN").toString()
        V_price = intent.getStringExtra("V_price").toString()
        V_registration_no = intent.getStringExtra("V_registration_no").toString()



        front_image = intent.getStringExtra("front_image").toString()
        rear_image = intent.getStringExtra("rear_image").toString()
        interior_image = intent.getStringExtra("interior_image").toString()
        cargo_area_image = intent.getStringExtra("cargo_area_image").toString()
        engine_bay_image = intent.getStringExtra("engine_bay_image").toString()
        roof_image = intent.getStringExtra("roof_image").toString()
        wheels_image = intent.getStringExtra("wheels_image").toString()
        keys_image = intent.getStringExtra("keys_image").toString()
        left_side_image = intent.getStringExtra("left_side_image").toString()
        owners_Manual_Side = intent.getStringExtra("owners_Manual_Side").toString()

//                Frort_image.setImageBitmap(bitmap)
//                Rear_image.setImageBitmap(bitmap2)
//                Left_Side_image.setImageBitmap(bitmap3)
//                Interior_Side_image.setImageBitmap(bitmap4)
//                Dash_Engine_Bay_image.setImageBitmap(bitmap5)
//                Cargo_Area_Side_image.setImageBitmap(bitmap6)
//                Wheels_Side_image.setImageBitmap(bitmap7)
//                Roof_Side_image.setImageBitmap(bitmap8)
//                Keys_Side_image.setImageBitmap(bitmap10)


//        Toast.makeText(this,p_id.toString(),Toast.LENGTH_LONG).show()

        make = findViewById(R.id.make)
        body_type = findViewById(R.id.body_type)
        model_year = findViewById(R.id.model_year)
        mod = findViewById(R.id.model)
        transmission = findViewById(R.id.transmission)
        drive_type = findViewById(R.id.drive_type)
        fuel_type = findViewById(R.id.fuel_type)
        back = findViewById(R.id.back)
        servic_book = findViewById(R.id.service_log)
        des = findViewById(R.id.desc)
        odometer = findViewById(R.id.odometer)
        reg_no = findViewById(R.id.registration_no)
        v_vin = findViewById(R.id.vehicle_VIN)
        v_price = findViewById(R.id.vehicle_price)
        v_name = findViewById(R.id.v_Name)
        upload_image = findViewById(R.id.upload_img)
        owner_view= findViewById(R.id.owner_view)
        log_view= findViewById(R.id.log_view)

        Owners_Manual_text = findViewById(R.id.Owners_Manual_Side_image_text)

        Frort_image = findViewById(R.id.front_image)
        Frort_image_btn = findViewById(R.id.front_image_btn)

        Rear_image = findViewById(R.id.Rear_image)
        Rear_image_btn = findViewById(R.id.Rear_image_btn)

        Left_Side_image = findViewById(R.id.Left_Side_image)
        Left_Side_image_btn = findViewById(R.id.Left_Side_image_btn)

        Interior_Side_image = findViewById(R.id.Interior_Side_image)
        Interior_Side_image_btn = findViewById(R.id.Interior_Side_image_btn)

        Dash_Engine_Bay_image = findViewById(R.id.Dash_Engine_Bay_Side_image)
        Dash_Engine_Bay_image_btn = findViewById(R.id.Dash_Engine_Bay_Side_image_btn)

        Cargo_Area_Side_image = findViewById(R.id.Cargo_Area_Side_image)
        Cargo_Area_Side_image_btn = findViewById(R.id.Cargo_Area_Side_image_btn)

        Wheels_Side_image = findViewById(R.id.Wheels_Side_image)
        Wheels_Side_image_btn = findViewById(R.id.Wheels_Side_image_btn)


        Roof_Side_image = findViewById(R.id.Roof_Side_image)
        Roof_Side_image_btn = findViewById(R.id.Roof_Side_image_btn)

        Owners_Manual_Side_image = findViewById(R.id.Owners_Manual_Side_image)
        Owners_Manual_Side_layout =findViewById(R.id.Owners_Manual_Side_layout)


        Keys_Side_image = findViewById(R.id.Keys_Side_image)
        Keys_Side_image_btn = findViewById(R.id.Keys_Side_image_btn)



        make_layout = findViewById(R.id.make_layout)
        model_layout = findViewById(R.id.model_layout)
        year_layout= findViewById(R.id.year_layout)


        if (front_image != "null") {
            Glide.with(this).load(front_image).fitCenter().into(Frort_image)
            Frort_image_btn.setBackgroundResource(android.R.color.transparent)
        }

        if (interior_image != "null") {
            Glide.with(this).load(interior_image).fitCenter().into(Interior_Side_image)
            Interior_Side_image_btn.visibility = INVISIBLE
        }

        if (rear_image != "null") {
            Glide.with(this).load(rear_image).fitCenter().into(Rear_image)
            Rear_image_btn.visibility = INVISIBLE
        }

        if (cargo_area_image != "null") {
            Glide.with(this).load(cargo_area_image).fitCenter().into(Cargo_Area_Side_image)
            Cargo_Area_Side_image_btn.visibility = INVISIBLE
        }
        if (engine_bay_image != "null") {
            Glide.with(this).load(engine_bay_image).fitCenter().into(Dash_Engine_Bay_image)
            Dash_Engine_Bay_image_btn.visibility = INVISIBLE
        }
        if (roof_image != "null") {
            Glide.with(this).load(roof_image).fitCenter().into(Roof_Side_image)
            Roof_Side_image_btn.visibility = INVISIBLE
        }
        if (wheels_image != "null") {
            Glide.with(this).load(wheels_image).fitCenter().into(Wheels_Side_image)
            Wheels_Side_image_btn.visibility = INVISIBLE
        }
        if (keys_image != "null") {
            Glide.with(this).load(keys_image).fitCenter().into(Keys_Side_image)
            Keys_Side_image_btn.visibility = INVISIBLE
        }
        if (left_side_image != "null") {
            Glide.with(this).load(left_side_image).fitCenter().into(Left_Side_image)
            Left_Side_image_btn.visibility = INVISIBLE
        }
        if (owners_Manual_Side != "null") {
            owner_view.visibility = VISIBLE
        }
        if (V_service_log != "null") {
            log_view.visibility =VISIBLE
        }



        owner_view.setOnClickListener {
            if (owners_Manual_Side != "null") {
                val viewIntent = Intent("android.intent.action.VIEW",
                    Uri.parse(owners_Manual_Side))
                startActivity(viewIntent)
            }
        }
        log_view.setOnClickListener {
            if (V_service_log != "null") {
                val viewIntent = Intent("android.intent.action.VIEW",
                    Uri.parse(V_service_log))
                startActivity(viewIntent)
            }
        }



        if (V_name != "null") {
            v_name.setText(V_name)
        }
        if (V_model_des != "null") {
            des.setText(V_model_des)
        }
        if (V_odometer != "null") {
            odometer.setText(V_odometer)
        }
        if (V_registration_no != "null") {
            reg_no.setText(V_registration_no)
        }
        if (V_VIN != "null") {
            v_vin.setText(V_VIN)
        }
        if (V_price != "null") {
            v_price.setText(V_price)
        }


        Error1 = findViewById(R.id.error1)
        Error2 = findViewById(R.id.error2)
        Error3 = findViewById(R.id.error3)
        Error4 = findViewById(R.id.error4)
        Error5 = findViewById(R.id.error5)
        Error6 = findViewById(R.id.error6)
        Error7 = findViewById(R.id.error7)
        Error8 = findViewById(R.id.error8)
        Error9 = findViewById(R.id.error9)
        Error10 = findViewById(R.id.error10)
        Error11 = findViewById(R.id.error11)
        Error12 = findViewById(R.id.error12)
        Error13 = findViewById(R.id.error13)
        Error14 = findViewById(R.id.error14)
        Error15 = findViewById(R.id.error15)

        uriTxt = findViewById(R.id.pdf_name)


        spinKitView = findViewById<SpinKitView>(R.id.progressBar)

        spinKitView.visibility = VISIBLE
        v_post = findViewById(R.id.post)

        if (add.equals("add")) {
            back.setText("Post Your Vehicle ")
            v_post_test.setText("Sell your Vehicle")

        } else {
            val color1 = ContextCompat.getColor(this, R.color.blue)
            error1.setTextColor(color1)
            error3.setTextColor(color1)
            error5.setTextColor(color1)
            error3.visibility = VISIBLE
            error3.text = "*can't able to edit the model"
            error5.visibility = VISIBLE
            error5.text = "*can't able to edit the model yeer"
            error1.visibility = VISIBLE
            error1.text = "*can't able to edit the make"
            make.isEnabled = false
            mod.isEnabled = false
            model_year.isEnabled = false
            back.setText("Edit Your Vehicle")
            v_post_test.setText("Save")
        }


        v_post.setOnClickListener {

            if (status.equals("true")) {
                spinKitView.visibility = VISIBLE

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                Error1.visibility = GONE
                Error2.visibility = GONE
                Error3.visibility = GONE
                Error4.visibility = GONE
                Error5.visibility = GONE
                Error6.visibility = GONE
                Error7.visibility = GONE
                Error8.visibility = GONE
                Error9.visibility = GONE
                Error11.visibility = GONE
                Error12.visibility = GONE
                Error13.visibility = GONE
                Error14.visibility = GONE
                Error15.visibility = GONE



                if (add.equals("add")) {


                    if (bitmap != null) {


                        var fuel_type_name: String? = null
                        fuel_type_name = fuel_type.selectedItem.toString()

                        var drive_type_name: String? = null
                        drive_type_name = drive_type.selectedItem.toString()

                        var transmission_name: String? = null
                        transmission_name = transmission.selectedItem.toString()

                        var year: String? = null
                        year = model_year.selectedItem.toString()

                        var body_type_name: String? = null
                        body_type_name = body_type.selectedItem.toString()


                        var mod_name: String? = null
                        mod_name = mod.selectedItem.toString()


                        var make_name: String? = null
                        make_name = make.selectedItem.toString()
                        if (make_name.equals("Select Make")) {
                            Error1.setText("The make is required.")
                            Error1.visibility = VISIBLE
//                            Toast.makeText(this, "The make is required.", Toast.LENGTH_LONG)
//                                .show()
                            spinKitView.visibility = GONE
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        } else if (body_type_name.equals("Select Body Type")) {
                            Error2.setText("The body type is required.")
                            Error2.visibility = VISIBLE
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            spinKitView.visibility = GONE
                        } else if (mod_name.equals("Select Model")) {
                            Error3.setText("The model is required.")
                            Error3.visibility = VISIBLE
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            spinKitView.visibility = GONE
                        } else if (year.equals("Select Model Year")) {
                            Error5.setText("The model year is required.")
                            Error5.visibility = VISIBLE
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            spinKitView.visibility = GONE
                        } else if (transmission_name.equals("Select Transmission")) {
                            Error6.setText("The transmission is required.")
                            Error6.visibility = VISIBLE
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            spinKitView.visibility = GONE
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        } else if (drive_type_name.equals("Select Drive Type")) {
                            Error7.setText("The drive type is required.")
                            Error7.visibility = VISIBLE
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            spinKitView.visibility = GONE
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        } else if (fuel_type_name.equals("Select Fuel Type")) {
                            Error8.setText("The fuel type is required.")
                            Error8.visibility = VISIBLE
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            spinKitView.visibility = GONE
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        } else {


                            val URL2 = "http://motortraders.zydni.com/api/sellers/make"
                            val queue2 = Volley.newRequestQueue(this)


                            val request2: StringRequest =
                                @SuppressLint("ClickableViewAccessibility")
                                object : StringRequest(
                                    Method.GET, URL2,
                                    Response.Listener { response ->


                                        val res = JSONArray(response)


                                        try {
                                            for (i in 0 until res.length()) {
                                                var jsonObject: JSONObject =
                                                    res.getJSONObject(i)
                                                if (jsonObject.getString("name")
                                                        .equals(make_name)
                                                ) {
                                                    make_id = jsonObject.getString("id")

                                                    uploadBitmap()
//                                Toast.makeText(view.context,make_id,Toast.LENGTH_LONG).show()
                                                }

                                            }
                                        } catch (e: Exception) {

                                        }


                                    }, Response.ErrorListener { error ->


                                    }) {


                                    @Throws(AuthFailureError::class)
                                    override fun getHeaders(): Map<String, String> {
                                        val headers = HashMap<String, String>()
                                        headers.put("Accept", "application/json")
                                        headers.put("Authorization",
                                            "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                                        return headers
                                    }

                                }
                            request2.retryPolicy = DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                            )
                            queue2.add(request2)

                        }

//                        Toast.makeText(this, make_id.toString(), Toast.LENGTH_LONG)
//                            .show()
                    } else {
//                spinKitView.visibility = View.GONE
//                Error15.text = "The location field is required"
                        Toast.makeText(this, "The front image is required.", Toast.LENGTH_LONG)
                            .show()
                        spinKitView.visibility = GONE
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }


                } else {

                    var make_name: String? = null
                    make_name = make.selectedItem.toString()

                    val URL2 = "http://motortraders.zydni.com/api/sellers/make"

                    val queue2 = Volley.newRequestQueue(this)


                    val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
                    object : StringRequest(
                        Method.GET, URL2,
                        Response.Listener { response ->


                            val res = JSONArray(response)


                            try {
                                for (i in 0 until res.length()) {
                                    var jsonObject: JSONObject = res.getJSONObject(i)
                                    if (jsonObject.getString("name").equals(make_name)) {
                                        make_id = jsonObject.getString("id")

                                        update_vehicle()
//                                Toast.makeText(view.context,make_id,Toast.LENGTH_LONG).show()
                                    }

                                }
                            } catch (e: Exception) {

                            }


                        }, Response.ErrorListener { error ->


                        }) {


                        @Throws(AuthFailureError::class)
                        override fun getHeaders(): Map<String, String> {
                            val headers = HashMap<String, String>()
                            headers.put("Accept", "application/json")
                            headers.put("Authorization",
                                "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                            return headers
                        }

                    }
                    request2.retryPolicy = DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    )
                    queue2.add(request2)


                }
            }

        }



        back.setOnClickListener {

            super.onBackPressed()
        }


        Frort_image.setOnClickListener {


            val dialog = BottomSheetDialog(this)

            dialog!!.setContentView(R.layout.choose_image)

            val close = dialog.findViewById<TextView>(R.id.close)
            val file = dialog.findViewById<TextView>(R.id.file)
            val camera = dialog.findViewById<TextView>(R.id.camera)

            file!!.setOnClickListener {
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE), PICK_FROM_GALLERY)
                } else {
                    dialog.dismiss()
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, SELECT_PHOTO)
                }
            }
            camera!!.setOnClickListener {

                dialog.dismiss()

//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
//                } else {
//                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST1)
//                }

                if (checkPermission()) {
                    val filename = "Photo"
                    val storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    try {
                        val imagefile = File.createTempFile(filename, ".jpg", storagedirectory)
                        currentphoto1 = imagefile.absolutePath
                        val imageuri = FileProvider.getUriForFile(this,
                            "com.app.compare_my_trade.fileprovider",
                            imagefile)
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
                        startActivityForResult(intent, CAMERA_REQUEST1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    requestPermission()
                }

            }


            close!!.setOnClickListener {

                dialog.dismiss()
            }


            dialog.setCancelable(false)
            dialog.behavior.peekHeight = 10000
            dialog!!.show()

        }


        Rear_image.setOnClickListener {


            val dialog = BottomSheetDialog(this)

            dialog!!.setContentView(R.layout.choose_image)

            val close = dialog.findViewById<TextView>(R.id.close)
            val file = dialog.findViewById<TextView>(R.id.file)
            val camera = dialog.findViewById<TextView>(R.id.camera)

            file!!.setOnClickListener {
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE), PICK_FROM_GALLERY)
                } else {
                    dialog.dismiss()
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, SELECT_PHOTO2)
                }
            }
            camera!!.setOnClickListener {

                dialog.dismiss()

//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
//                } else {
//                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST2)
//                }

//                val filename2 = "Photo"
//                val storagedirectory2 = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                try {
//                    val imagefile2 = File.createTempFile(filename2, ".jpg", storagedirectory2)
//                    currentphoto2 = imagefile2.absolutePath
//                    val imageuri2 = FileProvider.getUriForFile(this,
//                        "com.app.compare_my_trade.fileprovider",
//                        imagefile2)
//                    val intent2 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageuri2)
//                    startActivityForResult(intent2, CAMERA_REQUEST2)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
                if (checkPermission()) {
                    val filename = "Photo"
                    val storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    try {
                        val imagefile = File.createTempFile(filename, ".jpg", storagedirectory)
                        currentphoto2 = imagefile.absolutePath
                        val imageuri = FileProvider.getUriForFile(this,
                            "com.app.compare_my_trade.fileprovider",
                            imagefile)
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
                        startActivityForResult(intent, CAMERA_REQUEST2)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    requestPermission()
                }


            }


            close!!.setOnClickListener {

                dialog.dismiss()
            }


            dialog.setCancelable(false)
            dialog.behavior.peekHeight = 10000
            dialog!!.show()


        }



        Left_Side_image.setOnClickListener {

            val dialog = BottomSheetDialog(this)

            dialog!!.setContentView(R.layout.choose_image)

            val close = dialog.findViewById<TextView>(R.id.close)
            val file = dialog.findViewById<TextView>(R.id.file)
            val camera = dialog.findViewById<TextView>(R.id.camera)

            file!!.setOnClickListener {
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE), PICK_FROM_GALLERY)
                } else {
                    dialog.dismiss()
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, SELECT_PHOTO3)
                }
            }
            camera!!.setOnClickListener {

                dialog.dismiss()

//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
//                } else {
//                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST3)
//                }

                if (checkPermission()) {
                    val filename = "Photo"
                    val storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    try {
                        val imagefile = File.createTempFile(filename, ".jpg", storagedirectory)
                        currentphoto3 = imagefile.absolutePath
                        val imageuri = FileProvider.getUriForFile(this,
                            "com.app.compare_my_trade.fileprovider",
                            imagefile)
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
                        startActivityForResult(intent, CAMERA_REQUEST3)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    requestPermission()
                }


            }


            close!!.setOnClickListener {

                dialog.dismiss()
            }


            dialog.setCancelable(false)
            dialog.behavior.peekHeight = 10000
            dialog!!.show()


        }
        Interior_Side_image.setOnClickListener {

            val dialog = BottomSheetDialog(this)

            dialog!!.setContentView(R.layout.choose_image)

            val close = dialog.findViewById<TextView>(R.id.close)
            val file = dialog.findViewById<TextView>(R.id.file)
            val camera = dialog.findViewById<TextView>(R.id.camera)

            file!!.setOnClickListener {
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE), PICK_FROM_GALLERY)
                } else {
                    dialog.dismiss()
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, SELECT_PHOTO4)
                }
            }
            camera!!.setOnClickListener {

                dialog.dismiss()
//
//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
//                } else {
//                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST4)
//                }

                if (checkPermission()) {
                    val filename = "Photo"
                    val storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    try {
                        val imagefile = File.createTempFile(filename, ".jpg", storagedirectory)
                        currentphoto4 = imagefile.absolutePath
                        val imageuri = FileProvider.getUriForFile(this,
                            "com.app.compare_my_trade.fileprovider",
                            imagefile)
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
                        startActivityForResult(intent, CAMERA_REQUEST4)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    requestPermission()
                }
            }


            close!!.setOnClickListener {

                dialog.dismiss()
            }


            dialog.setCancelable(false)
            dialog.behavior.peekHeight = 10000
            dialog!!.show()


        }
        Dash_Engine_Bay_image.setOnClickListener {

            val dialog = BottomSheetDialog(this)

            dialog!!.setContentView(R.layout.choose_image)

            val close = dialog.findViewById<TextView>(R.id.close)
            val file = dialog.findViewById<TextView>(R.id.file)
            val camera = dialog.findViewById<TextView>(R.id.camera)

            file!!.setOnClickListener {
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE), PICK_FROM_GALLERY)
                } else {
                    dialog.dismiss()
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, SELECT_PHOTO5)
                }
            }
            camera!!.setOnClickListener {

                dialog.dismiss()

//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
//                } else {
//                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST5)
//                }

                if (checkPermission()) {
                    val filename = "Photo"
                    val storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    try {
                        val imagefile = File.createTempFile(filename, ".jpg", storagedirectory)
                        currentphoto5 = imagefile.absolutePath
                        val imageuri = FileProvider.getUriForFile(this,
                            "com.app.compare_my_trade.fileprovider",
                            imagefile)
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
                        startActivityForResult(intent, CAMERA_REQUEST5)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    requestPermission()
                }
            }


            close!!.setOnClickListener {

                dialog.dismiss()
            }


            dialog.setCancelable(false)
            dialog.behavior.peekHeight = 10000
            dialog!!.show()


        }
        Cargo_Area_Side_image.setOnClickListener {

            val dialog = BottomSheetDialog(this)

            dialog!!.setContentView(R.layout.choose_image)

            val close = dialog.findViewById<TextView>(R.id.close)
            val file = dialog.findViewById<TextView>(R.id.file)
            val camera = dialog.findViewById<TextView>(R.id.camera)

            file!!.setOnClickListener {
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE), PICK_FROM_GALLERY)
                } else {
                    dialog.dismiss()
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, SELECT_PHOTO6)
                }
            }
            camera!!.setOnClickListener {

                dialog.dismiss()

//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
//                } else {
//                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST6)
//                }

                if (checkPermission()) {
                    val filename = "Photo"
                    val storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    try {
                        val imagefile = File.createTempFile(filename, ".jpg", storagedirectory)
                        currentphoto6 = imagefile.absolutePath
                        val imageuri = FileProvider.getUriForFile(this,
                            "com.app.compare_my_trade.fileprovider",
                            imagefile)
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
                        startActivityForResult(intent, CAMERA_REQUEST6)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    requestPermission()
                }
            }


            close!!.setOnClickListener {

                dialog.dismiss()
            }


            dialog.setCancelable(false)
            dialog.behavior.peekHeight = 10000
            dialog!!.show()


        }
        Wheels_Side_image.setOnClickListener {

            val dialog = BottomSheetDialog(this)

            dialog!!.setContentView(R.layout.choose_image)

            val close = dialog.findViewById<TextView>(R.id.close)
            val file = dialog.findViewById<TextView>(R.id.file)
            val camera = dialog.findViewById<TextView>(R.id.camera)

            file!!.setOnClickListener {

                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE), PICK_FROM_GALLERY)
                } else {
                    dialog.dismiss()
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, SELECT_PHOTO7)
                }
            }
            camera!!.setOnClickListener {

                dialog.dismiss()

//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
//                } else {
//                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST7)
//                }
                if (checkPermission()) {
                    val filename = "Photo"
                    val storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    try {
                        val imagefile = File.createTempFile(filename, ".jpg", storagedirectory)
                        currentphoto7 = imagefile.absolutePath
                        val imageuri = FileProvider.getUriForFile(this,
                            "com.app.compare_my_trade.fileprovider",
                            imagefile)
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
                        startActivityForResult(intent, CAMERA_REQUEST7)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    requestPermission()
                }
            }


            close!!.setOnClickListener {

                dialog.dismiss()
            }


            dialog.setCancelable(false)
            dialog.behavior.peekHeight = 10000
            dialog!!.show()


        }
        Roof_Side_image.setOnClickListener {

            val dialog = BottomSheetDialog(this)

            dialog!!.setContentView(R.layout.choose_image)

            val close = dialog.findViewById<TextView>(R.id.close)
            val file = dialog.findViewById<TextView>(R.id.file)
            val camera = dialog.findViewById<TextView>(R.id.camera)

            file!!.setOnClickListener {
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE), PICK_FROM_GALLERY)
                } else {
                    dialog.dismiss()
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, SELECT_PHOTO8)
                }
            }
            camera!!.setOnClickListener {

                dialog.dismiss()

//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
//                } else {
//                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST8)
//                }

                if (checkPermission()) {
                    val filename = "Photo"
                    val storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    try {
                        val imagefile = File.createTempFile(filename, ".jpg", storagedirectory)
                        currentphoto8 = imagefile.absolutePath
                        val imageuri = FileProvider.getUriForFile(this,
                            "com.app.compare_my_trade.fileprovider",
                            imagefile)
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
                        startActivityForResult(intent, CAMERA_REQUEST8)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    requestPermission()
                }
            }


            close!!.setOnClickListener {

                dialog.dismiss()
            }


            dialog.setCancelable(false)
            dialog.behavior.peekHeight = 10000
            dialog!!.show()


        }
        Owners_Manual_Side_layout.setOnClickListener {

//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(intent, SELECT_PHOTO9)
//


            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT

            // We will be redirected to choose pdf

            // We will be redirected to choose pdf
            galleryIntent.type = "application/pdf"
            startActivityForResult(galleryIntent, SELECT_PHOTO9)


        }
        Keys_Side_image.setOnClickListener {

            val dialog = BottomSheetDialog(this)

            dialog!!.setContentView(R.layout.choose_image)

            val close = dialog.findViewById<TextView>(R.id.close)
            val file = dialog.findViewById<TextView>(R.id.file)
            val camera = dialog.findViewById<TextView>(R.id.camera)

            file!!.setOnClickListener {

                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE), PICK_FROM_GALLERY)
                } else {
                    dialog.dismiss()
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, SELECT_PHOTO10)
                }
            }
            camera!!.setOnClickListener {

                dialog.dismiss()

//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
//                } else {
//                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST9)
//
//
//                }
                if (checkPermission()) {
                    val filename = "Photo"
                    val storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    try {
                        val imagefile = File.createTempFile(filename, ".jpg", storagedirectory)
                        currentphoto9 = imagefile.absolutePath
                        val imageuri = FileProvider.getUriForFile(this,
                            "com.app.compare_my_trade.fileprovider",
                            imagefile)
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
                        startActivityForResult(intent, CAMERA_REQUEST9)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    requestPermission()
                }


            }


            close!!.setOnClickListener {

                dialog.dismiss()
            }


            dialog.setCancelable(false)
            dialog.behavior.peekHeight = 10000
            dialog!!.show()


        }

        servic_book.setOnClickListener {

//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(intent, SELECT_PHOTO9)
//


            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT

            // We will be redirected to choose pdf

            // We will be redirected to choose pdf
            galleryIntent.type = "application/pdf"
            startActivityForResult(galleryIntent, SELECT_PHOTO11)


        }


        val URL1 = "http://motortraders.zydni.com/api/sellers/make"

        val queue1 = Volley.newRequestQueue(this)


        val request1: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL1,
            Response.Listener { response ->
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                spinKitView.visibility = GONE
                val Make = ArrayList<String>()

                val res = JSONArray(response)

                if (add.equals("add")) {
                    Make.add("Select Make")
                    for (i in 0 until res.length()) {
                        var jsonObject: JSONObject = res.getJSONObject(i)

                        var st = jsonObject.getString("name")


                        Make.add(st)

                    }
                    val Adapter1 = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_item, Make
                    )
                    Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    make.setAdapter(Adapter1)
                } else {

                    for (i in 0 until res.length()) {
                        var jsonObject: JSONObject = res.getJSONObject(i)
                        if (jsonObject.getString("name").equals(V_make)) {
                            var st = jsonObject.getString("name")
                            Make.add(st)
                            make_id = jsonObject.getString("id")

                            for (i in 0 until res.length()) {
                                var jsonObject: JSONObject = res.getJSONObject(i)

                                if (jsonObject.getString("name").equals(V_make)) {

                                } else {
                                    var st = jsonObject.getString("name")
                                    Make.add(st)

                                }

                            }


                        }

                    }
                    val Adapter1 = ArrayAdapter(
                        this,
                        R.layout.disable_text, Make
                    )
                    Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    make.setAdapter(Adapter1)
                }





            }, Response.ErrorListener { error ->
                spinKitView.visibility = GONE
                if (error.toString()
                        .equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")
                ) {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show()
                }

            }) {


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")
                headers.put("Authorization",
                    "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                return headers
            }

        }
        request1.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue1.add(request1)


        var check = 0

        make.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()

                var make_name: String? = null
                make_name = make.selectedItem.toString()

                val URL2 = "http://motortraders.zydni.com/api/sellers/make"

                val queue2 = Volley.newRequestQueue(view.context)


                val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
                object : StringRequest(
                    Method.GET, URL2,
                    Response.Listener { response ->


                        val res = JSONArray(response)


                        try {
                            for (i in 0 until res.length()) {
                                var jsonObject: JSONObject = res.getJSONObject(i)
                                if (jsonObject.getString("name").equals(make_name)) {
                                    make_id = jsonObject.getString("id")

//                                Toast.makeText(view.context,make_id,Toast.LENGTH_LONG).show()
                                }

                            }


                        } catch (e: Exception) {

                        }
                        if (check != 0) {
                            val URL2 =
                                "http://motortraders.zydni.com/api/sellers/car-models?product_brand_id=" + make_id

                            val queue2 = Volley.newRequestQueue(view.context)


                            val request2: StringRequest =
                                @SuppressLint("ClickableViewAccessibility")
                                object : StringRequest(
                                    Method.GET, URL2,
                                    Response.Listener { response ->
                                        val Model = ArrayList<String>()

                                        val res = JSONArray(response)

                                        if (add.equals("add")) {
                                            Model.add("Select Model")
                                            for (i in 0 until res.length()) {
                                                var jsonObject: JSONObject = res.getJSONObject(i)

                                                var st = jsonObject.getString("name")


                                                Model.add(st)

                                            }
                                        }

                                        val Adapter1 = ArrayAdapter(
                                            view.context,
                                            android.R.layout.simple_spinner_item, Model
                                        )
                                        Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                        mod.setAdapter(Adapter1)

                                    }, Response.ErrorListener { error ->


                                    }) {


                                    @Throws(AuthFailureError::class)
                                    override fun getHeaders(): Map<String, String> {
                                        val headers = HashMap<String, String>()
                                        headers.put("Accept", "application/json")
                                        headers.put("Authorization",
                                            "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                                        return headers
                                    }

                                }
                            request2.retryPolicy = DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                            )
                            queue2.add(request2)
                        }

                        check = 1


                    }, Response.ErrorListener { error ->


                    }) {


                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers.put("Accept", "application/json")
                        headers.put("Authorization",
                            "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                        return headers
                    }

                }
                request2.retryPolicy = DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
                queue2.add(request2)


            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        if (add.equals("add")) {
            val Model = ArrayList<String>()

            Model.add("Select Model")

            val Adapter1 = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, Model
            )
            Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mod.setAdapter(Adapter1)
        } else {


            val URL2 = "http://motortraders.zydni.com/api/sellers/make"

            val queue2 = Volley.newRequestQueue(this)


            val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
            object : StringRequest(
                Method.GET, URL2,
                Response.Listener { response ->


                    val res = JSONArray(response)


                    try {
                        for (i in 0 until res.length()) {
                            var jsonObject: JSONObject = res.getJSONObject(i)
                            if (jsonObject.getString("name").equals(V_make)) {
                                make_id = jsonObject.getString("id")


                            }

                        }


                    } catch (e: Exception) {

                    }
                    val URL2 =
                        "http://motortraders.zydni.com/api/sellers/car-models?product_brand_id=" + make_id

                    val queue2 = Volley.newRequestQueue(this)


                    val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
                    object : StringRequest(
                        Method.GET, URL2,
                        Response.Listener { response ->
                            val Model = ArrayList<String>()

                            val res = JSONArray(response)


                            Log.i("dsds", res.toString())

                            for (i in 0 until res.length()) {
                                var jsonObject: JSONObject = res.getJSONObject(i)
                                if (jsonObject.getString("name").equals(V_model)) {
                                    var st = jsonObject.getString("name")
                                    Model.add(st)


                                    mod_id = jsonObject.getString("id")




//                                    Toast.makeText(this, st, Toast.LENGTH_LONG).show()

                                    for (i in 0 until res.length()) {
                                        var jsonObject: JSONObject = res.getJSONObject(i)

                                        if (jsonObject.getString("name").equals(V_model)) {

                                        } else {
                                            var st = jsonObject.getString("name")
                                            Model.add(st)

                                        }

                                    }


                                }


                            }

                            val Adapter1 = ArrayAdapter(
                                this,
                                R.layout.disable_text, Model
                            )
                            Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            mod.setAdapter(Adapter1)

                        }, Response.ErrorListener { error ->


                        }) {


                        @Throws(AuthFailureError::class)
                        override fun getHeaders(): Map<String, String> {
                            val headers = HashMap<String, String>()
                            headers.put("Accept", "application/json")
                            headers.put("Authorization",
                                "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                            return headers
                        }

                    }
                    request2.retryPolicy = DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    )
                    queue2.add(request2)

                }, Response.ErrorListener { error ->


                }) {


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Accept", "application/json")
                    headers.put("Authorization",
                        "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                    return headers
                }

            }
            request2.retryPolicy = DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue2.add(request2)


        }



        mod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()

                var mod_name: String? = null
                mod_name = mod.selectedItem.toString()

                val URL2 =
                    "http://motortraders.zydni.com/api/sellers/car-models?product_brand_id=" + make_id

                val queue2 = Volley.newRequestQueue(view.context)


                val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
                object : StringRequest(
                    Method.GET, URL2,
                    Response.Listener { response ->


                        val res = JSONArray(response)



                        for (i in 0 until res.length()) {
                            var jsonObject: JSONObject = res.getJSONObject(i)
                            if (jsonObject.getString("name").equals(mod_name)) {
                                mod_id = jsonObject.getString("id")


                            }

                        }


                    }, Response.ErrorListener { error ->


                    }) {


                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers.put("Accept", "application/json")
                        headers.put("Authorization",
                            "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                        return headers
                    }

                }
                request2.retryPolicy = DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
                queue2.add(request2)


            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        val URL3 = "http://motortraders.zydni.com/api/sellers/body-types"

        val queue3 = Volley.newRequestQueue(this)


        val request3: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL3,
            Response.Listener { response ->
                val Body_type = ArrayList<String>()

                val res = JSONArray(response)

                if (add.equals("add")) {
                    Body_type.add("Select Body Type")
                    for (i in 0 until res.length()) {
                        var jsonObject: JSONObject = res.getJSONObject(i)

                        var st = jsonObject.getString("title")


                        Body_type.add(st)

                    }
                } else {

                    for (i in 0 until res.length()) {
                        var jsonObject: JSONObject = res.getJSONObject(i)
                        if (jsonObject.getString("title").equals(V_body_type)) {
                            var st = jsonObject.getString("title")
                            Body_type.add(st)
                            body_type_id = jsonObject.getString("id")
                            for (i in 0 until res.length()) {
                                var jsonObject: JSONObject = res.getJSONObject(i)

                                if (jsonObject.getString("title").equals(V_body_type)) {

                                } else {
                                    var st = jsonObject.getString("title")
                                    Body_type.add(st)

                                }

                            }


                        }
                    }
                }


                val Adapter1 = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, Body_type
                )
                Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                body_type.setAdapter(Adapter1)

            }, Response.ErrorListener { error ->


            }) {


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")
                headers.put("Authorization",
                    "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                return headers
            }

        }
        request3.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue3.add(request3)


        body_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            ) {

                var body_type_name: String? = null
                body_type_name = body_type.selectedItem.toString()

                val URL2 = "http://motortraders.zydni.com/api/sellers/body-types"

                val queue2 = Volley.newRequestQueue(view.context)


                val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
                object : StringRequest(
                    Method.GET, URL2,
                    Response.Listener { response ->


                        val res = JSONArray(response)



                        for (i in 0 until res.length()) {
                            var jsonObject: JSONObject = res.getJSONObject(i)
                            if (jsonObject.getString("title").equals(body_type_name)) {
                                body_type_id = jsonObject.getString("id")

//                                Toast.makeText(view.context,State_id,Toast.LENGTH_LONG).show()
                            }

                        }


                    }, Response.ErrorListener { error ->


                    }) {


                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers.put("Accept", "application/json")
                        headers.put("Authorization",
                            "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                        return headers
                    }

                }
                request2.retryPolicy = DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
                queue2.add(request2)


            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        val URL4 = "http://motortraders.zydni.com/api/sellers/model-years"

        val queue4 = Volley.newRequestQueue(this)


        val request4: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL4,
            Response.Listener { response ->
                val MOdel_year = ArrayList<String>()

                val res = JSONArray(response)

                if (add.equals("add")) {
                    MOdel_year.add("Select Model Year")
                    for (i in 0 until res.length()) {
                        val years = res.getString(i)
                        var st = years.toString()


                        MOdel_year.add(st)

                    }
                    val Adapter1 = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_item, MOdel_year
                    )
                    Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    model_year.setAdapter(Adapter1)
                } else {

                    for (i in 0 until res.length()) {
                        val years = res.getString(i)
                        if (years.equals(V_model_year)) {
                            var st = years.toString()
                            MOdel_year.add(st)

                            for (i in 0 until res.length()) {
                                val years = res.getString(i)

                                if (years.equals(V_model_year)) {

                                } else {
                                    var st = years.toString()


                                    MOdel_year.add(st)

                                }

                            }


                        }
                    }
                    val Adapter1 = ArrayAdapter(
                        this,
                        R.layout.disable_text, MOdel_year
                    )
                    Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    model_year.setAdapter(Adapter1)
                }



            }, Response.ErrorListener { error ->


            }) {


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")
                headers.put("Authorization",
                    "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                return headers
            }

        }
        request4.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue4.add(request4)


//        model_year.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//
//                var model_year_name:String? = null
//                model_year_name = model_year.selectedItem.toString()
//
//                val URL2 = "http://motortraders.zydni.com/api/sellers/model-years"
//
//                val queue2 = Volley.newRequestQueue(view.context)
//
//
//                val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
//                object : StringRequest(
//                    Method.GET, URL2,
//                    Response.Listener { response ->
//
//
//                        val res = JSONArray(response)
//
//
//
//                        for (i in 0 until res.length()) {
//                            var jsonObject: JSONObject = res.getJSONObject(i)
//                            if (jsonObject.getString("name").equals(model_year_name)) {
//                                model_year_id = jsonObject.getString("id")
//
////                                Toast.makeText(view.context,State_id,Toast.LENGTH_LONG).show()
//                            }
//
//                        }
//
//
//                    }, Response.ErrorListener { error ->
//
//
//                    }) {
//
//
//                    @Throws(AuthFailureError::class)
//                    override fun getHeaders(): Map<String, String> {
//                        val headers = HashMap<String, String>()
//                        headers.put("Accept", "application/json")
//                        headers.put("Authorization","Bearer 629|fQ3DA9GeMy96pHnHWLew81C69jesOxhpTyZVaoIR")
//
//                        return headers
//                    }
//
//                }
//                request2.retryPolicy = DefaultRetryPolicy(
//                    10000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//                )
//                queue2.add(request2)
//
//
//            } // to close the onItemSelected
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//
//            }
//        }


        val URL5 = "http://motortraders.zydni.com/api/sellers/transmissions"

        val queue5 = Volley.newRequestQueue(this)


        val request5: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL5,
            Response.Listener { response ->
                val Transmission = ArrayList<String>()

                val res = JSONArray(response)

                if (add.equals("add")) {
                    Transmission.add("Select Transmission")
                    for (i in 0 until res.length()) {
                        var jsonObject: JSONObject = res.getJSONObject(i)

                        var st = jsonObject.getString("title")


                        Transmission.add(st)

                    }
                } else {

                    for (i in 0 until res.length()) {
                        var jsonObject: JSONObject = res.getJSONObject(i)
                        if (jsonObject.getString("title").equals(V_transmission)) {
                            var st = jsonObject.getString("title")
                            Transmission.add(st)
                            transmission_id = jsonObject.getString("id")
                            for (i in 0 until res.length()) {
                                var jsonObject: JSONObject = res.getJSONObject(i)

                                if (jsonObject.getString("title").equals(V_transmission)) {

                                } else {
                                    var st = jsonObject.getString("title")
                                    Transmission.add(st)

                                }

                            }


                        }
                    }
                }

                val Adapter1 = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, Transmission
                )
                Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                transmission.setAdapter(Adapter1)

            }, Response.ErrorListener { error ->


            }) {


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")
                headers.put("Authorization",
                    "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                return headers
            }

        }
        request5.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue5.add(request5)



        transmission.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            ) {

                var transmission_name: String? = null
                transmission_name = transmission.selectedItem.toString()

                val URL2 = "http://motortraders.zydni.com/api/sellers/transmissions"

                val queue2 = Volley.newRequestQueue(view.context)


                val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
                object : StringRequest(
                    Method.GET, URL2,
                    Response.Listener { response ->


                        val res = JSONArray(response)



                        for (i in 0 until res.length()) {
                            var jsonObject: JSONObject = res.getJSONObject(i)
                            if (jsonObject.getString("title").equals(transmission_name)) {
                                transmission_id = jsonObject.getString("id")

//                                Toast.makeText(view.context,State_id,Toast.LENGTH_LONG).show()
                            }

                        }


                    }, Response.ErrorListener { error ->


                    }) {


                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers.put("Accept", "application/json")
                        headers.put("Authorization",
                            "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                        return headers
                    }

                }
                request2.retryPolicy = DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
                queue2.add(request2)


            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        val URL6 = "http://motortraders.zydni.com/api/sellers/drive-types"

        val queue6 = Volley.newRequestQueue(this)


        val request6: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL6,
            Response.Listener { response ->
                val Drive_Type = ArrayList<String>()

                val res = JSONArray(response)

                if (add.equals("add")) {
                    Drive_Type.add("Select Drive Type")
                    for (i in 0 until res.length()) {
                        var jsonObject: JSONObject = res.getJSONObject(i)

                        var st = jsonObject.getString("title")


                        Drive_Type.add(st)

                    }
                } else {

                    for (i in 0 until res.length()) {
                        var jsonObject: JSONObject = res.getJSONObject(i)
                        if (jsonObject.getString("title").equals(V_drive_type)) {
                            var st = jsonObject.getString("title")
                            Drive_Type.add(st)
                            drive_type_id = jsonObject.getString("id")

                            for (i in 0 until res.length()) {
                                var jsonObject: JSONObject = res.getJSONObject(i)

                                if (jsonObject.getString("title").equals(V_drive_type)) {

                                } else {
                                    var st = jsonObject.getString("title")
                                    Drive_Type.add(st)

                                }

                            }


                        }
                    }
                }

                val Adapter1 = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, Drive_Type
                )
                Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                drive_type.setAdapter(Adapter1)

            }, Response.ErrorListener { error ->


            }) {


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")
                headers.put("Authorization",
                    "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                return headers
            }

        }
        request6.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue6.add(request6)


        drive_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            ) {

                var drive_type_name: String? = null
                drive_type_name = drive_type.selectedItem.toString()

                val URL2 = "http://motortraders.zydni.com/api/sellers/drive-types"

                val queue2 = Volley.newRequestQueue(view.context)


                val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
                object : StringRequest(
                    Method.GET, URL2,
                    Response.Listener { response ->


                        val res = JSONArray(response)



                        for (i in 0 until res.length()) {
                            var jsonObject: JSONObject = res.getJSONObject(i)
                            if (jsonObject.getString("title").equals(drive_type_name)) {
                                drive_type_id = jsonObject.getString("id")

//                                Toast.makeText(view.context,State_id,Toast.LENGTH_LONG).show()
                            }

                        }


                    }, Response.ErrorListener { error ->


                    }) {


                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers.put("Accept", "application/json")
                        headers.put("Authorization",
                            "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                        return headers
                    }

                }
                request2.retryPolicy = DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
                queue2.add(request2)


            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        val URL7 = "http://motortraders.zydni.com/api/sellers/fuel-types"

        val queue7 = Volley.newRequestQueue(this)


        val request7: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL7,
            Response.Listener { response ->
                val Fuel_Type = ArrayList<String>()

                val res = JSONArray(response)

                if (add.equals("add")) {
                    Fuel_Type.add("Select Fuel Type")
                    for (i in 0 until res.length()) {
                        var jsonObject: JSONObject = res.getJSONObject(i)

                        var st = jsonObject.getString("name")


                        Fuel_Type.add(st)

                    }
                } else {

                    for (i in 0 until res.length()) {
                        var jsonObject: JSONObject = res.getJSONObject(i)
                        if (jsonObject.getString("name").equals(V_fuel_type)) {
                            var st = jsonObject.getString("name")
                            Fuel_Type.add(st)
                            fuel_type_id = jsonObject.getString("id")
                            for (i in 0 until res.length()) {
                                var jsonObject: JSONObject = res.getJSONObject(i)

                                if (jsonObject.getString("name").equals(V_fuel_type)) {

                                } else {
                                    var st = jsonObject.getString("name")
                                    Fuel_Type.add(st)

                                }

                            }


                        }
                    }
                }

                val Adapter1 = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, Fuel_Type
                )
                Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                fuel_type.setAdapter(Adapter1)

            }, Response.ErrorListener { error ->


            }) {


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")
                headers.put("Authorization",
                    "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                return headers
            }

        }
        request7.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue7.add(request7)

        fuel_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            ) {

                var fuel_type_name: String? = null
                fuel_type_name = fuel_type.selectedItem.toString()

                val URL2 = "http://motortraders.zydni.com/api/sellers/fuel-types"

                val queue2 = Volley.newRequestQueue(view.context)


                val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
                object : StringRequest(
                    Method.GET, URL2,
                    Response.Listener { response ->


                        val res = JSONArray(response)

                        status = "true"

                        for (i in 0 until res.length()) {
                            var jsonObject: JSONObject = res.getJSONObject(i)
                            if (jsonObject.getString("name").equals(fuel_type_name)) {
                                fuel_type_id = jsonObject.getString("id")

//                                Toast.makeText(view.context,State_id,Toast.LENGTH_LONG).show()
                            }

                        }


                    }, Response.ErrorListener { error ->


                    }) {


                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers.put("Accept", "application/json")
                        headers.put("Authorization",
                            "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                        return headers
                    }

                }
                request2.retryPolicy = DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
                queue2.add(request2)


            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


    }

    private fun update_vehicle() {

        spinKitView.visibility = VISIBLE

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        var url: String = "http://motortraders.zydni.com/api/sellers/cars/update/" + p_id
//        if (bitmap != null) {
        uploadBitmap2(url)
        spinKitView.visibility = VISIBLE
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        } else {
////                spinKitView.visibility = View.GONE
////                Error15.text = "The location field is required"
//            Toast.makeText(this, "The location field is required.", Toast.LENGTH_LONG)
//                .show()
//
//        }


    }

    private fun makefun(url: String) {


        val dialog = Dialog(this)

        dialog.setContentView(R.layout.spinner1)

        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.70).toInt()
        dialog!!.window?.setLayout(width, height)

        dialog.show()

        val listview = dialog.findViewById<RecyclerView>(R.id.listview)


        val queue = Volley.newRequestQueue(this)


        val request: StringRequest =
            object : StringRequest(
                Method.GET, url,
                Response.Listener { response ->

                    val res = JSONArray(response)

                    listview!!.layoutManager = LinearLayoutManager(this)

                    val name = ArrayList<SpinnerModel>()


                    for (i in 0 until res.length()) {
                        val data: JSONObject = res.getJSONObject(i)

                        var data1 = data.getString("name")

                        name.add(SpinnerModel(data1))
                    }

                    var adapter = SpinnerAdapter(name)

                    listview!!.adapter = adapter

                    adapter.setOnItemClickListener(object : SpinnerAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val model: SpinnerModel = name.get(position)

                            dialog.dismiss()
                            var text = model.text1
//                             Toast.makeText(applicationContext,text,Toast.LENGTH_LONG).show()
//                            make.text = text
                        }

                    })

                }, Response.ErrorListener { error ->


                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Accept", "application/json")
                    headers.put("Authorization",
                        "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

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

    private fun modelYearfun(url: String) {

        val dialog = Dialog(this)

        dialog.setContentView(R.layout.spinner1)

        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.50).toInt()
        dialog!!.window?.setLayout(width, height)

        dialog.show()

        val listview = dialog.findViewById<RecyclerView>(R.id.listview)


        val queue = Volley.newRequestQueue(this)


        val request: StringRequest =
            object : StringRequest(
                Method.GET, url,
                Response.Listener { response ->

                    val res = JSONArray(response)

                    listview!!.layoutManager = LinearLayoutManager(this)

                    val name = ArrayList<SpinnerModel>()


                    for (i in 0 until res.length()) {
                        val data = res.getString(i)

                        // var data1 = data.getString("name")

                        name.add(SpinnerModel(data.toString()))
                    }


                    var adapter = SpinnerAdapter(name)

                    listview!!.adapter = adapter

                    adapter.setOnItemClickListener(object : SpinnerAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val model: SpinnerModel = name.get(position)

                            dialog.dismiss()
                            var text = model.text1
                            // Toast.makeText(applicationContext,text,Toast.LENGTH_LONG).show()

//                            model_year.text = text
                        }

                    })

                }, Response.ErrorListener { error ->


                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Accept", "application/json")
                    headers.put("Authorization",
                        "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

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

    private fun uploadImg() {

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    REQUEST_PERMISSIONS
                )
            }
        } else {
            Log.e("Else", "Else")
            showFileChooser()
        }

    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)


    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//
//            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
//                val picUri = data.data
//                filePath = getPath(picUri)
//                if (filePath != null) {
//                    try {
////                    textView.text = "File Selected"
//                        Log.d("filePath", filePath.toString())
//
//
//
//                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, picUri)
//
//
//                        frort_image.setImageBitmap(bitmap)
//
//
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    Toast.makeText(
//                        this, "no image selected",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//    }

    fun getPath(uri: Uri?): String? {
        var cursor: Cursor? = uri?.let { contentResolver.query(it, null, null, null, null) }
        cursor!!.moveToFirst()
        var document_id: String = cursor.getString(0)
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
        cursor.close()
        cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null
        )
        cursor!!.moveToFirst()
        val path: String = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        cursor.close()
        return path
    }

    fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun getFileDataFromDrawable2(bitmap2: Bitmap): ByteArray? {
        val byteArrayOutputStream2 = ByteArrayOutputStream()
        bitmap2.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream2)
        return byteArrayOutputStream2.toByteArray()

    }


    fun getStringPdf(imageuri: Uri?): ByteArray {
        var inputStream: InputStream? = null
        var byteArrayOutputStream = ByteArrayOutputStream()
        try {
            inputStream = contentResolver.openInputStream(imageuri!!)
            val buffer = ByteArray(1024)
            byteArrayOutputStream = ByteArrayOutputStream()
            var bytesRead: Int
            while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        val pdfByteArray = byteArrayOutputStream.toByteArray()
//        return Base64.encodeToString(pdfByteArray, Base64.DEFAULT)
        return byteArrayOutputStream.toByteArray()
    }


    fun getStringPdf2(imageuri2: Uri?): ByteArray {
        var inputStream: InputStream? = null
        var byteArrayOutputStream = ByteArrayOutputStream()
        try {
            inputStream = contentResolver.openInputStream(imageuri2!!)
            val buffer = ByteArray(1024)
            byteArrayOutputStream = ByteArrayOutputStream()
            var bytesRead: Int
            while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        val pdfByteArray = byteArrayOutputStream.toByteArray()
//        return Base64.encodeToString(pdfByteArray, Base64.DEFAULT)
        return byteArrayOutputStream.toByteArray()
    }

    private fun uploadBitmap() {

        v_post.isEnabled = false

        try {


            var year: String? = null
            year = model_year.selectedItem.toString()
//            var name = v_name.text.toString()
            var description = des.text.toString()
            var mileage = odometer.text.toString()
            var reg_number = reg_no.text.toString()
            var vehicle_vin = v_vin.text.toString()
            var vehicle_Price = v_price.text.toString()




            Error1.visibility = GONE
            Error2.visibility = GONE
            Error3.visibility = GONE
            Error4.visibility = GONE
            Error5.visibility = GONE
            Error6.visibility = GONE
            Error7.visibility = GONE
            Error8.visibility = GONE
            Error9.visibility = GONE
            Error11.visibility = GONE
            Error12.visibility = GONE
            Error13.visibility = GONE
            Error14.visibility = GONE
            Error15.visibility = GONE

            spinKitView.visibility = VISIBLE
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


            val volleyMultipartRequest: VolleyMultipartRequest =
                object : VolleyMultipartRequest(
                    Request.Method.POST, ROOT_URL,
                    Response.Listener { response ->
                        v_post.isEnabled = true
                        try {
                            val obj = JSONObject(String(response.data))


                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            spinKitView.visibility = GONE
                            Toast.makeText(
                                applicationContext,
                                "Success",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this, HomeActivity::class.java).apply {

                            }
                            startActivity(intent)

//                        Log.i("ertyukijhgfds",data1.getString(0).toString())
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error ->

                        v_post.isEnabled = true
                        spinKitView.visibility = GONE
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        if (error.toString()
                                .equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")
                        ) {
                            Toast.makeText(this,
                                "Check your internet connection",
                                Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
//                    val charset: Charset = Charsets.UTF_8
//
//                    val jsonObject = String(error.networkResponse.data, charset)
//                    val data = JSONObject(jsonObject)
//                    val errors: JSONObject = data.getJSONObject("errors")
//
//                    Toast.makeText(
//                        applicationContext,
//                        errors.toString(),
//                        Toast.LENGTH_SHORT
//                    ).show()

                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var em: JSONArray? = null


                                em = errors.getJSONArray("product_brand_id")
                                if (em.equals(null)) {


                                } else {
                                    val email: JSONArray = errors.getJSONArray("product_brand_id")
                                    Error1.setText(email.getString(0))
                                    Error1.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }

                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("body_type_id")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("body_type_id")
                                    Error2.setText(password.getString(0))
                                    Error2.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var em: JSONArray? = null


                                em = errors.getJSONArray("product_make_id")
                                if (em.equals(null)) {


                                } else {
                                    val email: JSONArray = errors.getJSONArray("product_make_id")
                                    Error3.setText(email.getString(0))
                                    Error3.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }

                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("model_description")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray =
                                        errors.getJSONArray("model_description")
                                    Error4.setText(password.getString(0))
                                    Error4.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var em: JSONArray? = null


                                em = errors.getJSONArray("model_year")
                                if (em.equals(null)) {


                                } else {
                                    val email: JSONArray = errors.getJSONArray("model_year")
                                    Error5.setText("The model_year field is required.")
                                    Error5.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }

                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("transmission_id")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("transmission_id")
                                    Error6.setText(password.getString(0))
                                    Error6.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("drive_type_id")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("drive_type_id")
                                    Error7.setText(password.getString(0))
                                    Error7.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("fuel_type_id")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("fuel_type_id")
                                    Error8.setText(password.getString(0))
                                    Error8.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var em: JSONArray? = null


                                em = errors.getJSONArray("name")
                                if (em.equals(null)) {


                                } else {
                                    val email: JSONArray = errors.getJSONArray("name")
                                    Error9.setText(email.getString(0))
                                    Error9.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }

                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("business_phone")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("business_phone")
                                    Error10.setText(password.getString(0))
                                    Error10.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var em: JSONArray? = null


                                em = errors.getJSONArray("odometer_mileage")
                                if (em.equals(null)) {


                                } else {
                                    val email: JSONArray = errors.getJSONArray("odometer_mileage")
                                    Error11.setText(email.getString(0))
                                    Error11.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }

                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("vehicle_registration_number")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray =
                                        errors.getJSONArray("vehicle_registration_number")
                                    Error12.setText(password.getString(0))
                                    Error12.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("vehicle_vin")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("vehicle_vin")
                                    Error13.setText(password.getString(0))
                                    Error13.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("vehicle_price")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("vehicle_price")
                                    Error14.setText(password.getString(0))
                                    Error14.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }








                            try {
                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")


//                        Toast.makeText(this,errors.toString(),Toast.LENGTH_LONG).show()


                            } catch (e: JSONException) {

                            } catch (error: UnsupportedEncodingException) {

                            }

                        }
                    }) {
                    override fun getByteData(): Map<String, DataPart> {

                        val params: MutableMap<String, DataPart> = HashMap()
                        val imagename = System.currentTimeMillis()
                        params["front_image"] =
                            DataPart("$imagename.png", getFileDataFromDrawable(bitmap!!))

                        if (bitmap2 != null) {

                            val imagename = System.currentTimeMillis()
                            params["rear_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap2!!))
                        }
                        if (bitmap3 != null) {

                            val imagename = System.currentTimeMillis()
                            params["left_side_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap3!!))
                        }
                        if (bitmap4 != null) {

                            val imagename = System.currentTimeMillis()
                            params["interior_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap4!!))
                        }
                        if (bitmap5 != null) {

                            val imagename = System.currentTimeMillis()
                            params["engine_bay_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap5!!))
                        }
                        if (bitmap6 != null) {

                            val imagename = System.currentTimeMillis()
                            params["cargo_area_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap6!!))
                        }
                        if (bitmap7 != null) {

                            val imagename = System.currentTimeMillis()
                            params["wheels_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap7!!))
                        }
                        if (bitmap8 != null) {

                            val imagename = System.currentTimeMillis()
                            params["roof_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap8!!))
                        }
                        if (imageuri != null) {

                            val imagename = System.currentTimeMillis()
                            params["owners_manual"] =
                                DataPart("$imagename.pdf", getStringPdf(imageuri))
                        }
                        if (bitmap10 != null) {

                            val imagename = System.currentTimeMillis()
                            params["keys_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap10!!))
                        }

                        if (imageuri2 != null) {

                            val imagename = System.currentTimeMillis()
                            params["service_log_book"] =
                                DataPart("$imagename.pdf", getStringPdf(imageuri2))
                        }




                        return params
                    }

                    override fun getParams(): Map<String, String>? {

                        val params: MutableMap<String, String> = HashMap()



                        params["product_brand_id"] = make_id.toString()
                        params["product_make_id"] = mod_id.toString()
                        params["body_type_id"] = body_type_id.toString()
                        params["drive_type_id"] = drive_type_id.toString()
                        params["fuel_type_id"] = fuel_type_id.toString()
                        params["transmission_id"] = transmission_id.toString()
                        params["model_year"] = year
//                        params["name"] = name
                        params["model_description"] = description
                        params["odometer_mileage"] = mileage
                        params["vehicle_registration_number"] = reg_number
                        params["vehicle_vin"] = vehicle_vin
                        params["vehicle_price"] = vehicle_Price


//                    val byteArrayOutputStream = ByteArrayOutputStream()
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
//                    val image =
//                        Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
//                    val name = Calendar.getInstance().timeInMillis.toString()
//
//                    params["front_image"]= image

                        return params


                    }

                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers.put("Accept", "application/json")
                        headers.put("Authorization",
                            "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                        return headers
                    }
                }

            volleyMultipartRequest.retryPolicy = DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            //adding the request to volley
            Volley.newRequestQueue(this).add(volleyMultipartRequest)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun uploadBitmap2(url: String) {

        v_post.isEnabled = false
        try {


            var year = model_year.selectedItem.toString()
//            var name = v_name.text.toString()
            var description = des.text.toString()
            var mileage = odometer.text.toString()
            var reg_number = reg_no.text.toString()
            var vehicle_vin = v_vin.text.toString()
            var vehicle_Price = v_price.text.toString()


            Error1.visibility = GONE
            Error2.visibility = GONE
            Error3.visibility = GONE
            Error4.visibility = GONE
            Error5.visibility = GONE
            Error6.visibility = GONE
            Error7.visibility = GONE
            Error8.visibility = GONE
            Error9.visibility = GONE
            Error11.visibility = GONE
            Error12.visibility = GONE
            Error13.visibility = GONE
            Error14.visibility = GONE
            Error15.visibility = GONE


            val volleyMultipartRequest: VolleyMultipartRequest =
                object : VolleyMultipartRequest(
                    Request.Method.POST, url,
                    Response.Listener { response ->
                        v_post.isEnabled = true
                        try {
                            spinKitView.visibility = GONE
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            val obj = JSONObject(String(response.data))

                            spinKitView.visibility = GONE
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(
                                applicationContext,
                                "Success",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this, HomeActivity::class.java).apply {

                            }
                            startActivity(intent)

//                        Log.i("ertyukijhgfds",data1.getString(0).toString())
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error ->
                        v_post.isEnabled = true


                        spinKitView.visibility = GONE
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        spinKitView.visibility = GONE
                        if (error.toString()
                                .equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")
                        ) {
                            Toast.makeText(this,
                                "Check your internet connection",
                                Toast.LENGTH_LONG).show()
                        } else {

                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
//                        val charset: Charset = Charsets.UTF_8
//
//                        val jsonObject = String(error.networkResponse.data, charset)
//                        val data = JSONObject(jsonObject)
//                        val errors: JSONObject = data.getJSONObject("errors")
//
//                        Toast.makeText(
//                            applicationContext,
//                            errors.toString(),
//                            Toast.LENGTH_SHORT
//                        ).show()

                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var em: JSONArray? = null


                                em = errors.getJSONArray("product_brand_id")
                                if (em.equals(null)) {


                                } else {
                                    val email: JSONArray = errors.getJSONArray("product_brand_id")
                                    Error1.setText(email.getString(0))
                                    Error1.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }

                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("body_type_id")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("body_type_id")
                                    Error2.setText(password.getString(0))
                                    Error2.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var em: JSONArray? = null


                                em = errors.getJSONArray("product_make_id")
                                if (em.equals(null)) {


                                } else {
                                    val email: JSONArray = errors.getJSONArray("product_make_id")
                                    Error3.setText(email.getString(0))
                                    Error3.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }

                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("model_description")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray =
                                        errors.getJSONArray("model_description")
                                    Error4.setText(password.getString(0))
                                    Error4.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var em: JSONArray? = null


                                em = errors.getJSONArray("model_year")
                                if (em.equals(null)) {


                                } else {
                                    val email: JSONArray = errors.getJSONArray("model_year")
                                    Error5.setText("The model_year field is required.")
                                    Error5.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }

                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("transmission_id")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("transmission_id")
                                    Error6.setText(password.getString(0))
                                    Error6.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("drive_type_id")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("drive_type_id")
                                    Error7.setText(password.getString(0))
                                    Error7.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("fuel_type_id")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("fuel_type_id")
                                    Error8.setText(password.getString(0))
                                    Error8.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var em: JSONArray? = null


                                em = errors.getJSONArray("name")
                                if (em.equals(null)) {


                                } else {
                                    val email: JSONArray = errors.getJSONArray("name")
                                    Error9.setText(email.getString(0))
                                    Error9.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }

                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("business_phone")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("business_phone")
                                    Error10.setText(password.getString(0))
                                    Error10.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var em: JSONArray? = null


                                em = errors.getJSONArray("odometer_mileage")
                                if (em.equals(null)) {


                                } else {
                                    val email: JSONArray = errors.getJSONArray("odometer_mileage")
                                    Error11.setText(email.getString(0))
                                    Error11.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }

                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("vehicle_registration_number")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray =
                                        errors.getJSONArray("vehicle_registration_number")
                                    Error12.setText(password.getString(0))
                                    Error12.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("vehicle_vin")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("vehicle_vin")
                                    Error13.setText(password.getString(0))
                                    Error13.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }
                            try {

                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")

                                var ps: JSONArray? = null

//
                                ps = errors.getJSONArray("vehicle_price")
                                if (ps.equals(null)) {


                                } else {
                                    val password: JSONArray = errors.getJSONArray("vehicle_price")
                                    Error14.setText(password.getString(0))
                                    Error14.visibility = VISIBLE
                                }

                            } catch (e: JSONException) {
                            } catch (error: UnsupportedEncodingException) {
                            }








                            try {
                                val charset: Charset = Charsets.UTF_8

                                val jsonObject = String(error.networkResponse.data, charset)
                                val data = JSONObject(jsonObject)
                                val errors: JSONObject = data.getJSONObject("errors")


//                        Toast.makeText(this,errors.toString(),Toast.LENGTH_LONG).show()


                            } catch (e: JSONException) {

                            } catch (error: UnsupportedEncodingException) {

                            }
                        }

                    }) {
                    override fun getByteData(): Map<String, DataPart> {

                        val params: MutableMap<String, DataPart> = HashMap()
                        val imagename = System.currentTimeMillis()
                        if (bitmap != null) {
                            params["front_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap!!))
                        }
                        if (bitmap2 != null) {

                            val imagename = System.currentTimeMillis()
                            params["rear_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap2!!))
                        }
                        if (bitmap3 != null) {

                            val imagename = System.currentTimeMillis()
                            params["left_side_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap3!!))
                        }
                        if (bitmap4 != null) {

                            val imagename = System.currentTimeMillis()
                            params["interior_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap4!!))
                        }
                        if (bitmap5 != null) {

                            val imagename = System.currentTimeMillis()
                            params["engine_bay_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap5!!))
                        }
                        if (bitmap6 != null) {

                            val imagename = System.currentTimeMillis()
                            params["cargo_area_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap6!!))
                        }
                        if (bitmap7 != null) {

                            val imagename = System.currentTimeMillis()
                            params["wheels_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap7!!))
                        }
                        if (bitmap8 != null) {

                            val imagename = System.currentTimeMillis()
                            params["roof_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap8!!))
                        }
                        if (imageuri != null) {

                            val imagename = System.currentTimeMillis()
                            params["owners_manual"] =
                                DataPart("$imagename.pdf", getStringPdf(imageuri))
                        }
                        if (bitmap10 != null) {

                            val imagename = System.currentTimeMillis()
                            params["keys_image"] =
                                DataPart("$imagename.png", getFileDataFromDrawable(bitmap10!!))
                        }

                        if (imageuri2 != null) {

                            val imagename = System.currentTimeMillis()
                            params["service_log_book"] =
                                DataPart("$imagename.pdf", getStringPdf(imageuri2))
                        }


                        return params
                    }

                    override fun getParams(): Map<String, String>? {

                        val params: MutableMap<String, String> = HashMap()



                        params["product_brand_id"] = make_id.toString()
                        params["product_make_id"] = mod_id.toString()
                        params["body_type_id"] = body_type_id.toString()
                        params["drive_type_id"] = drive_type_id.toString()
                        params["fuel_type_id"] = fuel_type_id.toString()
                        params["transmission_id"] = transmission_id.toString()
                        params["model_year"] = year
//                        params["name"] = name
                        params["model_description"] = description
                        params["odometer_mileage"] = mileage
                        params["vehicle_registration_number"] = reg_number
                        params["vehicle_vin"] = vehicle_vin
                        params["vehicle_price"] = vehicle_Price
                        params["_method"] = "PUT"

//                    val byteArrayOutputStream = ByteArrayOutputStream()
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
//                    val image =
//                        Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
//                    val name = Calendar.getInstance().timeInMillis.toString()
//
//                    params["front_image"]= image

                        return params


                    }

                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers.put("Accept", "application/json")
                        headers.put("Authorization",
                            "Bearer " + PreferenceUtils.getTokan(this@Edit_vehicle))

                        return headers
                    }
                }

            volleyMultipartRequest.retryPolicy = DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            //adding the request to volley
            Volley.newRequestQueue(this).add(volleyMultipartRequest)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//    if (resultCode == RESULT_OK) {
//        bitmap = data!!.extras!!["data"] as Bitmap?
//        frort_image.setImageBitmap(bitmap)
//
//
//    }
        if (requestCode == SELECT_PHOTO && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val path = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, path)
                Frort_image.setImageBitmap(bitmap)
                Frort_image_btn.setBackgroundResource(android.R.color.transparent)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (requestCode == SELECT_PHOTO2 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val path = data.data
            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(contentResolver, path)
                Rear_image.setImageBitmap(bitmap2)
                Rear_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == SELECT_PHOTO3 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val path = data.data
            try {
                bitmap3 = MediaStore.Images.Media.getBitmap(contentResolver, path)
                Left_Side_image.setImageBitmap(bitmap3)
                Left_Side_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == SELECT_PHOTO4 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val path = data.data
            try {
                bitmap4 = MediaStore.Images.Media.getBitmap(contentResolver, path)
                Interior_Side_image.setImageBitmap(bitmap4)
                Interior_Side_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == SELECT_PHOTO5 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val path = data.data
            try {
                bitmap5 = MediaStore.Images.Media.getBitmap(contentResolver, path)
                Dash_Engine_Bay_image.setImageBitmap(bitmap5)
                Dash_Engine_Bay_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == SELECT_PHOTO6 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val path = data.data
            try {
                bitmap6 = MediaStore.Images.Media.getBitmap(contentResolver, path)
                Cargo_Area_Side_image.setImageBitmap(bitmap6)
                Cargo_Area_Side_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == SELECT_PHOTO7 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val path = data.data
            try {
                bitmap7 = MediaStore.Images.Media.getBitmap(contentResolver, path)
                Wheels_Side_image.setImageBitmap(bitmap7)
                Wheels_Side_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == SELECT_PHOTO8 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val path = data.data
            try {
                bitmap8 = MediaStore.Images.Media.getBitmap(contentResolver, path)
                Roof_Side_image.setImageBitmap(bitmap8)
                Roof_Side_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == SELECT_PHOTO9 && resultCode == AppCompatActivity.RESULT_OK && data != null) {

            var uri = data!!.data
            try {



                val uriString = uri.toString()
                val myFile = File(uriString)
                val path = myFile.absolutePath
                var displayName: String? = null

                if (uriString.startsWith("content://")) {
                    var cursor: Cursor? = null
                    try {
                        cursor =
                            getContentResolver().query(uri!!, null, null, null, null)
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName =
                                cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        }
                    } finally {
                        cursor!!.close()
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.name
                }


//                bitmap9 = MediaStore.Images.Media.getBitmap(contentResolver, uri)
//                Owners_Manual_Side_image.setImageBitmap(bitmap9)
                imageuri = uri
//                Owners_Manual_Side_image.visibility = GONE
                upload_image.visibility = View.GONE
                Owners_Manual_text.text = displayName

                color = ContextCompat.getColor(this, R.color.green)
                Owners_Manual_text.setTextColor(color)
//                uriTxt.text=uri.toString()


            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == SELECT_PHOTO10 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val path = data.data
            try {
                bitmap10 = MediaStore.Images.Media.getBitmap(contentResolver, path)
                Keys_Side_image.setImageBitmap(bitmap10)
                Keys_Side_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (requestCode == SELECT_PHOTO11 && resultCode == AppCompatActivity.RESULT_OK && data != null) {

            var uri = data!!.data
            try {
//                bitmap11 = MediaStore.Images.Media.getBitmap(contentResolver, uri)
//                Owners_Manual_Side_image.setImageBitmap(bitmap11)
                val uriString = uri.toString()
                val myFile = File(uriString)
                val path = myFile.absolutePath
                var displayName: String? = null

                if (uriString.startsWith("content://")) {
                    var cursor: Cursor? = null
                    try {
                        cursor =
                            getContentResolver().query(uri!!, null, null, null, null)
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName =
                                cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        }
                    } finally {
                        cursor!!.close()
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.name
                }
                imageuri2 = uri

                servic_book.text = displayName
                color = ContextCompat.getColor(this, R.color.green)
                servic_book.setTextColor(color)


            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


        if (requestCode === CAMERA_REQUEST1 && resultCode === RESULT_OK) {

            try {
//                bitmap = data!!.extras!!.get("data") as Bitmap
                bitmap = BitmapFactory.decodeFile(currentphoto1)
                Frort_image.setImageBitmap(bitmap)
                Frort_image_btn.setBackgroundResource(android.R.color.transparent)
            } catch (e: IOException) {
                e.printStackTrace()
            }


        }


        if (requestCode == CAMERA_REQUEST2 && resultCode == RESULT_OK) {

            try {
//                bitmap2 = data!!.extras!!.get("data") as Bitmap
                bitmap2 = BitmapFactory.decodeFile(currentphoto2)
                Rear_image.setImageBitmap(bitmap2)
                Rear_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == CAMERA_REQUEST3 && resultCode == RESULT_OK) {

            try {
//                bitmap3 = data!!.extras!!.get("data") as Bitmap
                bitmap3 = BitmapFactory.decodeFile(currentphoto3)
                Left_Side_image.setImageBitmap(bitmap3)
                Left_Side_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == CAMERA_REQUEST4 && resultCode == RESULT_OK) {

            try {
//                bitmap4 = data!!.extras!!.get("data") as Bitmap
                bitmap4 = BitmapFactory.decodeFile(currentphoto4)
                Interior_Side_image.setImageBitmap(bitmap4)
                Interior_Side_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == CAMERA_REQUEST5 && resultCode == RESULT_OK) {

            try {
//                bitmap5 = data!!.extras!!.get("data") as Bitmap
                bitmap5 = BitmapFactory.decodeFile(currentphoto5)
                Dash_Engine_Bay_image.setImageBitmap(bitmap5)
                Dash_Engine_Bay_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == CAMERA_REQUEST6 && resultCode == RESULT_OK) {

            try {
//                bitmap6 = data!!.extras!!.get("data") as Bitmap
                bitmap6 = BitmapFactory.decodeFile(currentphoto6)
                Cargo_Area_Side_image.setImageBitmap(bitmap6)
                Cargo_Area_Side_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == CAMERA_REQUEST7 && resultCode == RESULT_OK) {

            try {
//                bitmap7 = data!!.extras!!.get("data") as Bitmap
                bitmap7 = BitmapFactory.decodeFile(currentphoto7)
                Wheels_Side_image.setImageBitmap(bitmap7)
                Wheels_Side_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == CAMERA_REQUEST8 && resultCode == RESULT_OK) {

            try {
//                bitmap8 = data!!.extras!!.get("data") as Bitmap
                bitmap8 = BitmapFactory.decodeFile(currentphoto8)
                Roof_Side_image.setImageBitmap(bitmap8)
                Roof_Side_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (requestCode == CAMERA_REQUEST9 && resultCode == RESULT_OK) {

            try {
//                bitmap10 = data!!.extras!!.get("data") as Bitmap
                bitmap10 = BitmapFactory.decodeFile(currentphoto9)
                Keys_Side_image.setImageBitmap(bitmap10)
                Keys_Side_image_btn.visibility = INVISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


    }


    private fun imageToSting(bitmap: Bitmap): ByteArray? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
        //        return Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return byteArrayOutputStream.toByteArray()
    }
//    fun CheckPermission(): Boolean {
//        return if ((ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            )
//                    != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.CAMERA
//            )
//                    != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            )
//                    != PackageManager.PERMISSION_GRANTED)
//        ) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE
//                ) || ActivityCompat.shouldShowRequestPermissionRationale(
//                    this,
//                    Manifest.permission.CAMERA
//                ) || ActivityCompat.shouldShowRequestPermissionRationale(
//                    this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                )
//            ) {
//                AlertDialog.Builder(this)
//                    .setTitle("Permission")
//                    .setMessage("Please accept the permissions")
//                    .setPositiveButton(
//                        "ok"
//                    ) { dialogInterface, i -> //Prompt the user once explanation has been shown
//                        ActivityCompat.requestPermissions(
//                            this,
//                            arrayOf(
//                                Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.CAMERA,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE
//                            ),
//                            tasnuvaoshin.com.volleyimageupload.MainActivity.MY_PERMISSIONS_REQUEST_LOCATION
//                        )
//                        startActivity(
//                            Intent(
//                                this,
//                                tasnuvaoshin.com.volleyimageupload.MainActivity::class.java
//                            )
//                        )
//                        this.overridePendingTransition(0, 0)
//                    }
//                    .create()
//                    .show()
//            } else {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    ),
//                    tasnuvaoshin.com.volleyimageupload.MainActivity.MY_PERMISSIONS_REQUEST_LOCATION
//                )
//
//                ActivityCompat.requestPermissions(
//                    this@MainActivity,
//                    arrayOf(
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    ),
//                    tasnuvaoshin.com.volleyimageupload.MainActivity.MY_PERMISSIONS_REQUEST_LOCATION
//                )
//            }
//            false
//        } else {
//            true
//        }
//    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE)
    }


}