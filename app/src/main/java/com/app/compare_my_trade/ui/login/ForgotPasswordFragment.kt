package com.app.compare_my_trade.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.BR
import com.app.compare_my_trade.R
import com.app.compare_my_trade.databinding.FragmentForgotPasswordBinding
import com.app.compare_my_trade.ui.MotorViewModel
import com.app.compare_my_trade.ui.base.BaseFragment
import com.github.ybq.android.spinkit.SpinKitView
import kotlinx.android.synthetic.main.activity_login_control.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.ext.android.inject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.HashMap

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ForgotPasswordFragment : AppCompatActivity() {
    private  val movieViewModel: MotorViewModel by inject()

    lateinit var email: EditText
    lateinit var button: Button

    lateinit var Email:String

    lateinit var  Error1: TextView

    lateinit var textView: TextView


    lateinit var spinKitView: SpinKitView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_forgot_password)

        email = findViewById(R.id.forgot_et)
        button = findViewById(R.id.login_btn)

        textView = findViewById(R.id.create_tv)

        textView.setOnClickListener {

            val intent = Intent(this, CreateAccountFragment::class.java)
            startActivity(intent)

        }



        Error1 = findViewById(R.id.error1)


        spinKitView = findViewById<SpinKitView>(R.id.progressBar)


        button.setOnClickListener {


            Error1.visibility = View.GONE


            spinKitView.visibility = View.VISIBLE
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            Email = email.text.toString()
            val url = "http://motortraders.zydni.com/api/sellers/forgot-password"

//

            val queue = Volley.newRequestQueue(this)


            val request: StringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->


                    spinKitView.visibility = View.GONE
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(this,"OTP send to your mail",Toast.LENGTH_LONG).show()
                        // findNavController().navigate(R.id.action_forgotpasswordfragment_to_newPasswordFragment)
                        val intent = Intent(this, NewPassword::class.java)
                        intent.putExtra("email",Email)
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


                            em = errors.getJSONArray("email")
                            if (em.equals(null)) {


                            } else {
                                val email: JSONArray = errors.getJSONArray("email")
                                Error1.setText(email.getString(0))
                                Error1.visibility = View.VISIBLE
                            }

                        } catch (e: JSONException) {
                        } catch (error: UnsupportedEncodingException) {
                        }
                    }

                }) {


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Accept", "application/json")

                    return headers
                }

                override fun getParams(): Map<String, String>? {

                    val params: MutableMap<String, String> = HashMap()



                    params["email"] = Email




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