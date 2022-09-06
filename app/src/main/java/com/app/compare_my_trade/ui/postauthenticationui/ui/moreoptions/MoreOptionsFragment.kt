package com.app.compare_my_trade.ui.postauthenticationui.ui.moreoptions

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.*
import com.app.compare_my_trade.ui.spalsh.Splash
import com.app.compare_my_trade.utils.PreferenceUtils
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.json.JSONException
import org.json.JSONObject

class MoreOptionsFragment : Fragment() {

//    private val moreOptionsViewModel: MoreOptionsViewModel by inject()

    lateinit var Full_name:TextView
    lateinit var Email:TextView
    lateinit var Avatar:ImageView
    lateinit var plan_name:TextView
    lateinit var plan_type:TextView
    lateinit var plan_price:TextView
    lateinit var bids_limit:TextView

    lateinit var plan:LinearLayout
    lateinit var choose_plan:LinearLayout
    lateinit var choose_plan_text:TextView

    lateinit var change_pass:TextView

    lateinit var spinKitView: SpinKitView
    lateinit var imageView:ImageView

    lateinit var mGoogleSignInClient: GoogleSignInClient

    var res: JSONObject? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view= inflater.inflate(R.layout.fragment_more_options, container, false)

        res()

        val linearLayout1 = view.findViewById<ImageView>(R.id.linear4)
        val linearLayout2 = view.findViewById<TextView>(R.id.linear_about)
        val linearLayout3 = view.findViewById<TextView>(R.id.linear_teams_and_condition)
        val linearLayout4 = view.findViewById<TextView>(R.id.linear_help_center)
        val logout = view.findViewById<TextView>(R.id.logout)



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)


        Full_name = view.findViewById(R.id.name)
        Email = view.findViewById(R.id.email)
        Avatar = view.findViewById(R.id.avatar)

        plan_name = view.findViewById(R.id.plan_name)
        plan_price = view.findViewById(R.id.plan_price)
        plan_type = view.findViewById(R.id.plan_type)
        bids_limit = view.findViewById(R.id.plan_count)

        plan = view.findViewById(R.id.plan)
        choose_plan = view.findViewById(R.id.choose_plan)
        choose_plan_text = view.findViewById(R.id.choose_plan_text)

        imageView = view.findViewById(R.id.image)

        spinKitView = view.findViewById<SpinKitView>(R.id.progressBar)

        change_pass = view.findViewById(R.id.change_password)

        if (PreferenceUtils.getLength(activity).equals(1)){
            change_pass.visibility = View.GONE
        }else{
            change_pass.visibility = View.VISIBLE
        }
            change_pass.setOnClickListener {

                val intent = Intent(activity, ChangePassword::class.java).apply {

                }
                startActivity(intent)
            }

//        if (res != null) {
            choose_plan_text.setOnClickListener {

                val intent = Intent(activity, PlanDetails::class.java).apply {

                }
                startActivity(intent)
            }
//        }
//        LoginManager.getInstance().logOut()


        linearLayout1.setOnClickListener {

            val intent = Intent(activity, ProfileSettings::class.java).apply {

            }
            startActivity(intent)
        }

        linearLayout2.setOnClickListener {

            val intent = Intent(activity, AboutUs::class.java).apply {

            }
            startActivity(intent)
        }
        linearLayout3.setOnClickListener {

            val intent = Intent(activity, Teams_and_conditions::class.java).apply {

            }
            startActivity(intent)
        }
        linearLayout4.setOnClickListener {

            val intent = Intent(activity, HelpCenter::class.java).apply {

            }
            startActivity(intent)
        }
        logout.setOnClickListener {

            val dialog = Dialog(it.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog.setContentView(R.layout.alert_dialog_box)
            dialog.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)

            val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
            val oktext = dialog.findViewById<TextView>(R.id.yestext)
            val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
            val cancel_text = dialog.findViewById<TextView>(R.id.no_text)
            val title = dialog.findViewById<TextView>(R.id.title)
            val massage = dialog.findViewById<TextView>(R.id.message)
            val loading = dialog.findViewById<SpinKitView>(R.id.progressBar)

            title.setText("Alert")
            cancel_text.text = "No"
            oktext.text = "YES"
            massage.setText("Are you sure want to logout")

            cancel.setOnClickListener {

                dialog.dismiss()
            }
            ok.setOnClickListener {


                loading.visibility = View.VISIBLE
                activity?.getWindow()?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    val requestQueue = Volley.newRequestQueue(activity)
                    val URL = "http://motortraders.zydni.com/api/sellers/logout"


                    val request: StringRequest = object : StringRequest(
                        Method.POST, URL,
                        com.android.volley.Response.Listener { response ->

                            loading.visibility = View.GONE
                            activity?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            val tokan: String? = null

                            PreferenceUtils.saveTokan(tokan, activity)
                            PreferenceUtils.saveLength(0, activity)


                            val intent = Intent(activity, Splash::class.java)
                            startActivity(intent)
                            signOut()
                            Toast.makeText(activity,"Success",Toast.LENGTH_LONG).show()

                        }, com.android.volley.Response.ErrorListener { error ->


                            loading.visibility = View.GONE
                            activity?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                                Toast.makeText(activity,"Check your internet connection", Toast.LENGTH_LONG).show()

                            }

                        }) {

                        @Throws(AuthFailureError::class)
                        override fun getHeaders(): Map<String, String> {
                            val headers = java.util.HashMap<String, String>()
                            headers.put("Accept","application/json")
                            headers.put("Authorization", "Bearer " + PreferenceUtils.getTokan(activity))
                            return headers
                        }
                        override fun getParams()  : Map<String, String>?  {

                            val params: MutableMap<String, String> = java.util.HashMap()





                            return params

                        }

                    }
                    request.retryPolicy = DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    )
                    requestQueue.add(request)

                }






            //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
            // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.show()

        }

        return view
    }

    private fun res() {


        val URL = "http://motortraders.zydni.com/api/sellers/detail"

        val queue = Volley.newRequestQueue(activity)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            { response ->

                spinKitView.visibility = View.GONE

                res = JSONObject(response)

                var fisrt_name = res!!.getString("first_name")
                var last_name = res!!.getString("last_name")
                var email = res!!.getString("email")
                var avatar = res!!.getString("avatar")
                var subscription_status = res!!.getJSONObject("subscription_status")
                var status = subscription_status.getString("status")



                if (status.equals("active")){
                    plan.visibility = View.VISIBLE
                    choose_plan.visibility = View.GONE
                }else{
                    plan.visibility = View.GONE
                    choose_plan.visibility = View.VISIBLE
                }

                var limit = subscription_status.getString("limit")
                try {
                    var current_subscription = subscription_status.getJSONObject("current_subscription")
                    var plan_data = current_subscription.getJSONObject("plan_data")
                    var name = plan_data.getString("name")
                    var type = plan_data.getString("type")
                    var cost = plan_data.getString("cost")




                    plan_name.text = name
                    plan_type.text = "/ "+type
                    plan_price.text = "$ "+cost
                } catch (e: JSONException) {

                }



                bids_limit.text = limit+" bidding limits"


                Full_name.text = fisrt_name


                Email.text = email
                if(activity !=null) {
                    Glide.with(this).load(avatar).fitCenter().into(Avatar)
                }

//                Toast.makeText(activity,fisrt_name.toString(),Toast.LENGTH_LONG).show()
//                Log.i("jdhfisd",last_name.toString())



            }, { error ->

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

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(requireActivity()) {
                // ...
            }
    }


}