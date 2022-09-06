package com.app.compare_my_trade.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.BR
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.MotorViewModel
import com.app.compare_my_trade.ui.base.BaseFragment
import com.app.compare_my_trade.ui.base.BaseNavigator
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import kotlinx.android.synthetic.main.activity_login_control.*
import kotlinx.android.synthetic.main.prograssbar.*
import org.json.JSONObject
import org.koin.android.ext.android.inject
import java.util.*
import com.android.volley.toolbox.StringRequest

import com.app.compare_my_trade.utils.PreferenceUtils
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.json.JSONArray
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class CreateAccountFragment: AppCompatActivity(){


    lateinit var full_name  : EditText
    lateinit var business_name  : EditText
    lateinit var business_email  : EditText
    lateinit var business_phone  : EditText
    lateinit var abn  : EditText
    lateinit var post_code  : EditText
    lateinit var password  : EditText
//    private var facebook_login: LoginButton? = null
    lateinit var relativeLayout: Button
    lateinit var google_button:Button
    lateinit var state:Spinner

    lateinit var Error1:TextView
    lateinit var Error2:TextView
    lateinit var Error3:TextView
    lateinit var Error4:TextView
    lateinit var Error5:TextView
    lateinit var Error6:TextView
    lateinit var Error7:TextView
    lateinit var Error8:TextView

    lateinit var spinKitView: SpinKitView

//    lateinit var FirstName  : String
//    lateinit var LastName  : String
//    lateinit var EmailAddress  : String
//    lateinit var PhoneNo  : String
//    lateinit var AddressLine  : String
//    lateinit var PostCode  : String
//    lateinit var SetPassword  : String

    lateinit var contine_button:Button
    var State_id: String? =null

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
//    private var callbackManager: CallbackManager? = null

    var status:String? = null
    lateinit var create_tv:TextView
    lateinit var back:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_create_account)


        contine_button = findViewById(R.id.continue_btn)
        full_name = findViewById(R.id.full_name)
        business_name = findViewById(R.id.business_name)
        business_email = findViewById(R.id.business_email)
        business_phone = findViewById(R.id.business_phone)
        abn = findViewById(R.id.abn)
        post_code = findViewById(R.id.post_code)
        password = findViewById(R.id.set_password)
        state = findViewById(R.id.state)

        Error1 = findViewById(R.id.error1)
        Error2 = findViewById(R.id.error2)
        Error3 = findViewById(R.id.error3)
        Error4 = findViewById(R.id.error4)
        Error5 = findViewById(R.id.error5)
        Error6 = findViewById(R.id.error6)
        Error7 = findViewById(R.id.error7)
        Error8 = findViewById(R.id.error8)

        spinKitView = findViewById<SpinKitView>(R.id.progressBar)

        create_tv = findViewById(R.id.create_tv)
        back = findViewById(R.id.back)

        back.setOnClickListener {

            super.onBackPressed()
        }

        val name = ArrayList<String>()
        name.add("Location")
        val URL = "http://motortraders.zydni.com/api/common/locations"

        val queue = Volley.newRequestQueue(this)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                status = "true"
                val res = JSONArray(response)

//                var courses = arrayOf(
//                    ""
//                )
//                name.add("State")

                for (i in 0 until res.length()) {
                    var jsonObject: JSONObject = res.getJSONObject(i)


                    var st = jsonObject.getString("name")



                    name.add(st)


                }


                val Adapter1 = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, name
                )
                Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                state.setAdapter(Adapter1)

            }, Response.ErrorListener { error ->
                Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                spinKitView.visibility = View.GONE
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }) {


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")
                // headers.put("Authorization","Bearer 112|9MrQvKhGRJOnZ01nVA7lU9JtxvmfsN5YcozCPNFU")

                return headers
            }

        }
        request.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)





            contine_button.setOnClickListener {


                if (status.equals("true")) {
                    Error1.visibility = View.GONE
                    Error2.visibility = View.GONE
                    Error3.visibility = View.GONE
                    Error4.visibility = View.GONE
                    Error5.visibility = View.GONE
                    Error6.visibility = View.GONE
                    Error7.visibility = View.GONE
                    Error8.visibility = View.GONE

                    var State_name: String? = null
                    State_name = state.selectedItem.toString()

                    if (State_name.equals("Location")) {
                        Error6.setText("The location is required.")
                        Error6.visibility = View.VISIBLE
//                            Toast.makeText(this, "The make is required.", Toast.LENGTH_LONG)
//                                .show()
                        spinKitView.visibility = View.GONE
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    } else {

                        spinKitView.visibility = View.VISIBLE

                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        val URL2 = "http://motortraders.zydni.com/api/common/locations"

                        val queue2 = Volley.newRequestQueue(this)


                        val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
                        object : StringRequest(
                            Method.GET, URL2,
                            Response.Listener { response ->


                                val res = JSONArray(response)

//                var courses = arrayOf(
//                    ""
//                )
//                name.add("State")
                                for (i in 0 until res.length()) {
                                    var jsonObject: JSONObject = res.getJSONObject(i)
                                    if (jsonObject.getString("name").equals(State_name)) {
                                        var state_id = jsonObject.getString("id")

                                        continebutton(state_id);
//                                Toast.makeText(view.context,State_id,Toast.LENGTH_LONG).show()
                                    }

                                }


                            }, Response.ErrorListener { error ->


                                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                                    Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                                    spinKitView.visibility = View.GONE
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            }) {


                            @Throws(AuthFailureError::class)
                            override fun getHeaders(): Map<String, String> {
                                val headers = HashMap<String, String>()
                                headers.put("Accept", "application/json")
                                // headers.put("Authorization","Bearer 112|9MrQvKhGRJOnZ01nVA7lU9JtxvmfsN5YcozCPNFU")

                                return headers
                            }

                        }
                        request2.retryPolicy = DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                        )
                        queue2.add(request2)
                    }
                }
            }


        create_tv.setOnClickListener {
            val intent = Intent(this, LoginFragment::class.java)
            startActivity(intent)
        }



//        state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                val selectedItem = parent.getItemAtPosition(position).toString()
//
//                var State_name:String? = null
//                State_name = state.selectedItem.toString()
//
//                val URL2 = "http://motortraders.zydni.com/api/common/locations"
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
////                var courses = arrayOf(
////                    ""
////                )
////                name.add("State")
//                        for (i in 0 until res.length()) {
//                            var jsonObject: JSONObject = res.getJSONObject(i)
//                            if (jsonObject.getString("name").equals(State_name)) {
//                                State_id = jsonObject.getString("id")
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
//                        // headers.put("Authorization","Bearer 112|9MrQvKhGRJOnZ01nVA7lU9JtxvmfsN5YcozCPNFU")
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


//        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext())

//        callbackManager = CallbackManager.Factory.create()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        relativeLayout = findViewById(R.id.Relative) as Button
        google_button = findViewById(R.id.google_login) as Button
//        facebook_login = findViewById<View>(R.id.facebook_login) as LoginButton


//        facebook_login!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
//            //            fun onSuccess(loginResult: LoginResult) {
////                info.setText(
////                    """
////                User ID: ${loginResult.accessToken.userId}
////                Auth Token: ${loginResult.accessToken.token}
////                """.trimIndent()
////                )
////            }
//            override fun onSuccess(result: LoginResult?) {
//
//                if (result != null) {
////                    Log.i("fgdhfdfsgsh",result.accessToken.userId)
////                    Toast.makeText(activity,result.accessToken.userId,Toast.LENGTH_SHORT).show()
////                    val intent = Intent(activity, HomeActivity::class.java)
////                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
////                    startActivity(intent)
//
//
//                }
//
//
//            }
//
//            override fun onCancel() {
//
//                Log.i("fgdhfdfsgsh","cancel")
//            }
//
//            override fun onError(e: FacebookException) {
//                Log.i("fgdhfdfsgsh","error")
//            }
//
//
//        })



        google_button = findViewById(R.id.google_login) as Button

        google_button.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, Req_Code)
        }

//        relativeLayout.setOnClickListener {
//
//            facebook_login!!.performClick()
//        }





    }

    private fun continebutton(state_id:String) {

        var Fullname = full_name.text.toString()
        var BusinessName= business_name.text.toString()
        var BusinessEmail= business_email.text.toString()
        var BusinessPhoneNo= business_phone.text.toString()
        var Abn= abn.text.toString()
        var PostCode= post_code.text.toString()
        var SetPassword= password.text.toString()
//
//        Toast.makeText(activity, State_id.toString(), Toast.LENGTH_SHORT).show()
//        Toast.makeText(activity, LastName, Toast.LENGTH_SHORT).show()
//        Toast.makeText(activity, EmailAddress, Toast.LENGTH_SHORT).show()
//        Toast.makeText(activity, PhoneNo, Toast.LENGTH_SHORT).show()
//        Toast.makeText(activity, PostCode, Toast.LENGTH_SHORT).show()
//        Toast.makeText(activity, AddressLine, Toast.LENGTH_SHORT).show()
//        Toast.makeText(activity, SetPassword, Toast.LENGTH_SHORT).show()
//
//
//        Log.i("iuwhdiw",State_id.toString())
//        Log.i("iuwhdiw",LastName)
//        Log.i("iuwhdiw",EmailAddress)
//        Log.i("iuwhdiw",PhoneNo)
//        Log.i("iuwhdiw",AddressLine)
//        Log.i("iuwhdiw",PostCode)
//        Log.i("iuwhdiw",SetPassword)



        Error1.visibility = View.GONE
        Error2.visibility = View.GONE
        Error3.visibility = View.GONE
        Error4.visibility = View.GONE
        Error5.visibility = View.GONE
        Error6.visibility = View.GONE
        Error7.visibility = View.GONE
        Error8.visibility = View.GONE





        val RegURL = "http://motortraders.zydni.com/api/sellers/register"


        val requestQueue = Volley.newRequestQueue(this)





        val request: StringRequest = object : StringRequest(
            Method.POST, RegURL,
             { response ->

                 spinKitView.visibility = View.GONE
                 getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                 Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
                val intent = Intent(this, LoginFragment::class.java)
                 intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)


            }, { error ->

                spinKitView.visibility = View.GONE
                Log.i("fdfdsfsd",error.toString())

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                    Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }else {
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                    try {

                        val charset: Charset = Charsets.UTF_8

                        val jsonObject = String(error.networkResponse.data, charset)
                        val data = JSONObject(jsonObject)
                        Log.i("sdnjsn", data.toString())
                        val errors: JSONObject = data.getJSONObject("errors")

                        Log.i("sdnjsn", data.toString())
//                    .    Toast.makeText(this,data.toString(), Toast.LENGTH_LONG).show()
                        var em: JSONArray? = null


                        em = errors.getJSONArray("first_name")
                        if (em.equals(null)) {


                        } else {
                            val email: JSONArray = errors.getJSONArray("first_name")
                            Error1.setText(email.getString(0))
                            Error1.visibility = View.VISIBLE
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
                        ps = errors.getJSONArray("business_name")
                        if (ps.equals(null)) {


                        } else {
                            val password: JSONArray = errors.getJSONArray("business_name")
                            Error2.setText(password.getString(0))
                            Error2.visibility = View.VISIBLE
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


                        em = errors.getJSONArray("business_email")
                        if (em.equals(null)) {


                        } else {
                            val email: JSONArray = errors.getJSONArray("business_email")
                            Error3.setText(email.getString(0))
                            Error3.visibility = View.VISIBLE
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
                            Error4.setText(password.getString(0))
                            Error4.visibility = View.VISIBLE
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


                        em = errors.getJSONArray("address_line")
                        if (em.equals(null)) {


                        } else {
                            val email: JSONArray = errors.getJSONArray("address_line")
                            Error5.setText(email.getString(0))
                            Error5.visibility = View.VISIBLE
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
                        ps = errors.getJSONArray("location_id")
                        if (ps.equals(null)) {


                        } else {
                            val password: JSONArray = errors.getJSONArray("location_id")
                            Error6.setText("The location field is required.")
                            Error6.visibility = View.VISIBLE
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
                        ps = errors.getJSONArray("postal_code")
                        if (ps.equals(null)) {


                        } else {
                            val password: JSONArray = errors.getJSONArray("postal_code")
                            Error7.setText(password.getString(0))
                            Error7.visibility = View.VISIBLE
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
                        ps = errors.getJSONArray("password")
                        if (ps.equals(null)) {


                        } else {
                            val password: JSONArray = errors.getJSONArray("password")
                            Error8.setText(password.getString(0))
                            Error8.visibility = View.VISIBLE
                        }

                    } catch (e: JSONException) {
                    } catch (error: UnsupportedEncodingException) {
                    }


//                try {
//                    val charset: Charset = Charsets.UTF_8
//
//                    val jsonObject = String(error.networkResponse.data, charset)
//                    val data = JSONObject(jsonObject)
//                    val errors: JSONObject = data.getJSONObject("errors")
//
//
//                    Toast.makeText(activity,errors.toString(),Toast.LENGTH_LONG).show()
//
//
//                    var data1:JSONArray = errors.getJSONArray("first_name")
//                    if (data1 != null){
//
//                    }
//                    Toast.makeText(activity,data1.getString(0).toString(),Toast.LENGTH_LONG).show()
//                    Log.i("ertyukijhgfds",data1.getString(0).toString())
//
//                    var data2:JSONArray = errors.getJSONArray("email")
//                    Toast.makeText(activity,data2.getString(0).toString(),Toast.LENGTH_LONG).show()
//                    Log.i("ertyukijhgfds",data2.getString(0).toString())
//
//                    var data3:JSONArray = errors.getJSONArray("password")
//                    Toast.makeText(activity,data3.getString(0).toString(),Toast.LENGTH_LONG).show()
//                    Log.i("ertyukijhgfds",data3.getString(0).toString())
//
//                    var data4:JSONArray = errors.getJSONArray("business_name")
//                    Toast.makeText(activity,data4.getString(0).toString(),Toast.LENGTH_LONG).show()
//                    Log.i("ertyukijhgfds",data4.getString(0).toString())
//
//                    var data5:JSONArray = errors.getJSONArray("business_phone")
//                    Toast.makeText(activity,data5.getString(0).toString(),Toast.LENGTH_LONG).show()
//                    Log.i("ertyukijhgfds",data5.getString(0).toString())
//
//                    var data6:JSONArray = errors.getJSONArray("business_email")
//                    Toast.makeText(activity,data6.getString(0).toString(),Toast.LENGTH_LONG).show()
//                    Log.i("ertyukijhgfds",data6.getString(0).toString())
//
//                    var data7:JSONArray = errors.getJSONArray("address_line")
//                    Toast.makeText(activity,data7.getString(0).toString(),Toast.LENGTH_LONG).show()
//                    Log.i("ertyukijhgfds",data7.getString(0).toString())
//
//                    var data8:JSONArray = errors.getJSONArray("location_id")
//                    Toast.makeText(activity,data8.getString(0).toString(),Toast.LENGTH_LONG).show()
//                    Log.i("ertyukijhgfds",data8.getString(0).toString())
//
//                    var data9:JSONArray = errors.getJSONArray("postal_code")
//                    Toast.makeText(activity,data9.getString(0).toString(),Toast.LENGTH_LONG).show()
//                    Log.i("ertyukijhgfds",data9.getString(0).toString())
//
//
//
//
//
//
//                } catch (e: JSONException) {
//
//                } catch (error: UnsupportedEncodingException) {
//
//                }
                }

            }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")


                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getParams()  : Map<String, String>?  {

                val params: MutableMap<String, String> = HashMap()


                params["first_name"]=Fullname
                //   params["last_name"]= "efdd"
                params["email"]= BusinessEmail
                params["password"]=SetPassword

                params["business_name"]=BusinessName
                params["business_phone"]=BusinessPhoneNo
                params["business_email"]=BusinessEmail
//                params["abn"]= "123456782"
//                params["buyer_license_no"]= ""
//                params["business_registration_document"]= ""

                params["address_line"]= Abn
                params["location_id"]= state_id
                params["postal_code"]= PostCode


                return params

            }

        }
        request.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(request)





//        val jsonObject = JSONObject()
//
//        try {
////            jsonObject.put("token","" )
//
//            jsonObject.put("first_name","test111")
//            //   params["last_name"]= "efdd"
//            jsonObject.put("email","test111@gmail.com")
//            jsonObject.put("password","Sudhakar$5")
//
//            jsonObject.put("business_name","test111")
//            jsonObject.put("business_phone","9876543210")
//            jsonObject.put("business_email","test111@gmail.com")
//            jsonObject.put("abn","123456782")
////                params["buyer_license_no"]= ""
////                params["business_registration_document"]= ""
//
//            jsonObject.put("address_line","test111")
//            jsonObject.put("location_id","2")
//            jsonObject.put("postal_code","test111")
//
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//
//        val jsonArrayRequest: JsonObjectRequest = object : JsonObjectRequest(
//            Request.Method.POST, URL, jsonObject,
//            Response.Listener<JSONObject> { response ->
//                Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()
//                Log.i("edfghjerf", response.toString())
//            },
//            Response.ErrorListener { error ->
//
//                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
//                Log.i("edfghjerf", error.toString())
//
//            }) {
//            @Override
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val headers = HashMap<String, String>()
//                headers.put("Accept","application/json")
//
//
//                return headers
//            }
//
//        }
//        jsonArrayRequest.setRetryPolicy(
//            DefaultRetryPolicy(
//                10000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//            )
//        )
//        requestQueue.add(jsonArrayRequest)


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == Req_Code) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            jkf()
//            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
//            updateUI(null)
        }
    }

    private fun jkf() {
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
//                val personName = acct.displayName
//                val personGivenName = acct.givenName
//                val personFamilyName = acct.familyName
//                val personEmail = acct.email
//                val personId = acct.id
            var userName = (acct!!.displayName)
            var userEmail =(acct.email)
            var userId = (acct.id)


            val requestQueue = Volley.newRequestQueue(this)
            val URL = "http://motortraders.zydni.com/api/sellers/social-login"


            val request: StringRequest = object : StringRequest(
                Method.POST, URL,
                com.android.volley.Response.Listener { response ->


                    var res = JSONObject(response)
                    Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
                    var access_token = res.getString("access_token")
//                    var user = res.getJSONObject("user")
//                    var subscription_status = user.getJSONObject("subscription_status")
//
//                    var status = subscription_status.getString("status")
//
//                    PreferenceUtils.saveStatus(status,this)

                    PreferenceUtils.saveTokan(access_token,this)

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
//                    Toast.makeText(
//                            activity,
//                        res.toString(),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        Log.e("fdsfsd",res.toString())


                }, com.android.volley.Response.ErrorListener { error ->
                    if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                        Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    }
//                        Toast.makeText(
//                            activity,
//                            "Error: password in Abcd@5 this format",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        Log.e("fdsfsd",error.toString())
                }) {



                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Accept","application/json")

                    return headers
                }

                override fun getParams()  : Map<String, String>?  {

                    val params: MutableMap<String, String> = HashMap()





                    params["email"]=userEmail.toString()
                    params["first_name"]=userName.toString()
                    params["google_id"]=userId.toString()




                    return params






                }





            }
            request.retryPolicy = DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            requestQueue.add(request)

//            Toast.makeText(activity, userEmail, Toast.LENGTH_SHORT).show()
//            Toast.makeText(activity, userId, Toast.LENGTH_SHORT).show()
//            Toast.makeText(activity, userName, Toast.LENGTH_SHORT).show()
//
//            Log.i("fhiufhfofa", userEmail.toString())
//            Log.i("fhiufhfofa", userId.toString())
//            Log.i("fhiufhfofa", userName.toString())
        }
    }

}