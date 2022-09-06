package com.app.compare_my_trade.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.MotorViewModel
import com.github.ybq.android.spinkit.SpinKitView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.ext.android.inject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.HashMap

class NewPassword : AppCompatActivity() {


    lateinit var otp: EditText
    lateinit var newpassword: EditText
    lateinit var cpassword: EditText
    lateinit var button: Button



    lateinit var Otp:String
    lateinit var NewPassword:String
    lateinit var CPassword:String
    lateinit var Email:String

    lateinit var  Error1: TextView
    lateinit var  Error2: TextView


    lateinit var spinKitView: SpinKitView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)




        otp = findViewById(R.id.otp)
        newpassword = findViewById(R.id.npassword_edt)
        cpassword = findViewById(R.id.confirm_password_edt)
        button = findViewById(R.id.update_btn)

        Error1 = findViewById(R.id.error1)
        Error2 = findViewById(R.id.error2)


        spinKitView = findViewById<SpinKitView>(R.id.progressBar)




        val Email:String = intent.getStringExtra("email").toString()

        button.setOnClickListener {

            val url = "http://motortraders.zydni.com/api/sellers/verify-password"
            Otp = otp.text.toString()
            NewPassword = newpassword.text.toString()
            CPassword = cpassword.text.toString()
//
            Error1.visibility = View.GONE
            Error2.visibility = View.GONE


            spinKitView.visibility = View.VISIBLE
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            val queue = Volley.newRequestQueue(this)


            val request: StringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->


                    spinKitView.visibility = View.GONE
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                    Toast.makeText(this,response.toString(), Toast.LENGTH_LONG).show()
//                    Log.i("ertyukijhgfds",response.toString())
                    Toast.makeText(this,"Password updated",Toast.LENGTH_LONG).show()
                        val intent = Intent(this, LoginFragment::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                        startActivity(intent)



                }, Response.ErrorListener { error ->
                    spinKitView.visibility = View.GONE
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                        Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                        try {

                            val charset: Charset = Charsets.UTF_8

                            val jsonObject = String(error.networkResponse.data, charset)
                            val data = JSONObject(jsonObject)
                            val errors: JSONObject = data.getJSONObject("errors")

                            var em: JSONArray? = null


                            em = errors.getJSONArray("otp")
                            if (em.equals(null)) {


                            } else {
                                val email: JSONArray = errors.getJSONArray("otp")
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
                            ps = errors.getJSONArray("password")
                            if (ps.equals(null)) {


                            } else {
                                val password: JSONArray = errors.getJSONArray("password")
                                Error2.setText(password.getString(0))
                                Error2.visibility = View.VISIBLE
                            }

                        } catch (e: JSONException) {
                        } catch (error: UnsupportedEncodingException) {
                        }

                    }
                }) {

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Accept","application/json")

                    return headers
                }

                override fun getParams(): Map<String, String>? {

                    val params: MutableMap<String, String> = HashMap()



                    params["email"] = Email
                    params["otp"] = Otp
                    params["password"] = NewPassword
                    params["password_confirmation"] = CPassword




                    return params


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