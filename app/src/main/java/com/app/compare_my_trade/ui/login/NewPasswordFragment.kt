package com.app.compare_my_trade.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.BR
import com.app.compare_my_trade.R
import com.app.compare_my_trade.databinding.FragmentNewPasswordBinding
import com.app.compare_my_trade.ui.MotorViewModel
import com.app.compare_my_trade.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_login_control.*
import org.koin.android.ext.android.inject
import java.util.HashMap

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NewPasswordFragment :  Fragment() {

    private  val movieViewModel: MotorViewModel by inject()
    lateinit var otp: EditText
    lateinit var newpassword: EditText
    lateinit var cpassword: EditText


    lateinit var Otp:String
    lateinit var NewPassword:String
    lateinit var CPassword:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_password, container, false)


        otp = view.findViewById(R.id.otp)
        newpassword = view.findViewById(R.id.npassword_edt)
        cpassword = view.findViewById(R.id.confirm_password_edt)



        Otp = otp.text.toString()
        NewPassword = newpassword.text.toString()
        CPassword = cpassword.text.toString()


        val url = "http://motortraders.zydni.com/api/buyers/forgot-password"

//

        val queue = Volley.newRequestQueue(activity)


        val request: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->


                if (response.equals(null)) {

                    Toast.makeText(activity, "invalid ", Toast.LENGTH_SHORT).show()

                } else {

                    findNavController().navigate(R.id.action_newPasswordFragment_to_loginfragment)
                }

            }, Response.ErrorListener { error ->
                Toast.makeText(
                    activity,
                    "Fail to get response = $error",
                    Toast.LENGTH_SHORT
                ).show()
            }) {


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")

                return headers
            }

            override fun getParams(): Map<String, String>? {

                val params: MutableMap<String, String> = HashMap()



                params["email"] = "sudhakarbsc55@gmail.com"
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


        return view
    }
}