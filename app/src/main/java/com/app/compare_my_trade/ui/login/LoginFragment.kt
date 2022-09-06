package com.app.compare_my_trade.ui.login


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils
import com.facebook.*
import com.facebook.login.LoginManager
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
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*


class LoginFragment : AppCompatActivity() {



    lateinit var login_email: EditText
    lateinit var login_password: EditText
    private var facebook_login: LoginButton? = null
    lateinit var relativeLayout: Button


    lateinit var LoginEmail: String
    lateinit var LoginPassword: String

    lateinit var  Error1: TextView
    lateinit var  Error2: TextView

    lateinit var spinKitView: SpinKitView


    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    private var callbackManager: CallbackManager? = null

    lateinit var create_tv:TextView
//        private var callbackManager: CallbackManager? = null
    lateinit var login_btn:Button
    lateinit var google_btn:Button
    lateinit var reset:TextView
    private var backPressedTime:Long = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)



        login_email = findViewById(R.id.email_login)
        login_password = findViewById(R.id.password_login)

        Error1 = findViewById(R.id.error1)
        Error2 = findViewById(R.id.error2)


        create_tv = findViewById(R.id.create_tv)
        login_btn = findViewById(R.id.login_btn)
        google_btn = findViewById(R.id.btn_google)


        reset = findViewById(R.id.reset_tv)

        spinKitView = findViewById<SpinKitView>(R.id.progressBar)


        create_tv.setOnClickListener {
            val intent = Intent(this, CreateAccountFragment::class.java)
            startActivity(intent)
        }

        reset.setOnClickListener {
            val intent = Intent(this, ForgotPasswordFragment::class.java)
            startActivity(intent)


        }



        login_btn.setOnClickListener {


            Error1.visibility = View.GONE
            Error2.visibility = View.GONE

            spinKitView.visibility = View.VISIBLE
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            LoginEmail = login_email.text.toString()
            LoginPassword = login_password.text.toString()
            val requestQueue = Volley.newRequestQueue(this)
            val URL = "http://motortraders.zydni.com/api/sellers/login"


            val request: StringRequest = object : StringRequest(
                Method.POST, URL,
                com.android.volley.Response.Listener { response ->

                    spinKitView.visibility = View.GONE
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    var res = JSONObject(response)

                    var access_token = res.getString("access_token")



                    Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()


                    PreferenceUtils.saveTokan(access_token,this)

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)

                }, com.android.volley.Response.ErrorListener { error ->

                    spinKitView.visibility = View.GONE
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                        Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                    }else {
//                        }else {
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
                override fun getParams()  : Map<String, String>?  {

                    val params: MutableMap<String, String> = HashMap()


                    params["email"]=LoginEmail
                    params["password"]=LoginPassword


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

        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext())

        callbackManager = CallbackManager.Factory.create()


        val accessToken = AccessToken.getCurrentAccessToken()
//        val isLoggedIn = accessToken != null && !accessToken.isExpired







        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        relativeLayout = findViewById(R.id.Relative) as Button
        facebook_login = findViewById<View>(R.id.facebook_login) as LoginButton

        google_btn.setOnClickListener {

            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, Req_Code)


        }
        facebook_login!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            //            fun onSuccess(loginResult: LoginResult) {
//                info.setText(
//                    """
//                User ID: ${loginResult.accessToken.userId}
//                Auth Token: ${loginResult.accessToken.token}
//                """.trimIndent()
//                )
//            }
            override fun onSuccess(result: LoginResult?) {


                val request = GraphRequest.newMeRequest(
                    accessToken,
                    object : GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(
                            `object`: JSONObject?,
                            response: GraphResponse?
                        ) {
                            Log.i("dscsss",`object`!!.get("name").toString())
                        }
                    })
                val parameters = Bundle()
                parameters.putString("fields", "id,name,link")
                request.parameters = parameters
                request.executeAsync()

//                if (result != null) {
//                    Log.i("fgdhfdfsgsh",result.accessToken.userId)
//                    Toast.makeText(applicationContext,result.accessToken.userId,Toast.LENGTH_SHORT).show()
////                    val intent = Intent(activity, HomeActivity::class.java)
////                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
////                    startActivity(intent)
//
//
//
//
//
//                }


            }

            override fun onCancel() {

                Log.i("fgdhfdfsgsh","cancel")
            }

            override fun onError(e: FacebookException) {
                Log.i("fgdhfdfsgsh",e.toString())
            }


        })

//        facebook_login!!.setOnClickListener {
////            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
//
//            LoginManager.getInstance().retrieveLoginStatus(this, object : LoginStatusCallback {
//                override fun onCompleted(accessToken: AccessToken) {
//                    // User was previously logged in, can log them in directly here.
//                    // If this callback is called, a popup notification appears that says
//                    // "Logged in as <User Name>"
//
//
//                }
//
//                override fun onFailure() {
//                    // No access token could be retrieved for the user
//                    Log.i("fgdhfdfsgsh","cancel")
//                }
//
//                override fun onError(exception: Exception) {
//                    Log.i("fgdhfdfsgsh",exception.toString())
//                    // An error occurred
//                }
//
//
//            })
//        }





        relativeLayout.setOnClickListener {

            facebook_login!!.performClick()
        }






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

//    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
//
////            val intent = Intent(activity, HomeActivity::class.java)
////            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
////            startActivity(intent)
//
//
//
//            Log.i("hjh",account.toString())
//
//        } catch (e: ApiException) {
//
//        }
//
//
//    }

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

            Log.i("hjh",userName.toString())


            val requestQueue = Volley.newRequestQueue(this)
            val URL = "http://motortraders.zydni.com/api/sellers/social-login"


            val request: StringRequest = object : StringRequest(
                Method.POST, URL,
                com.android.volley.Response.Listener { response ->


                    var res = JSONObject(response)
                    Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
                    var access_token = res.getString("access_token")
//                    var user = res.getJSONObject("user")
//                   - var subscription_status = user.getJSONObject("subscription_status")

//                    var status = subscription_status.getString("status")

//                    PreferenceUtils.saveStatus(status,this)

                    PreferenceUtils.saveTokan(access_token,this)

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
//                    Toast.makeText(
//                            activity,
//                        status.toString(),
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
    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog.setContentView(R.layout.alert_dialog_box)


            val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
            val oktext = dialog.findViewById<TextView>(R.id.yestext)
            val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
            val cancel_text = dialog.findViewById<TextView>(R.id.no_text)
            val title = dialog.findViewById<TextView>(R.id.title)
            val massage = dialog.findViewById<TextView>(R.id.message)

            title.setText("Alert")
            cancel_text.text = "No"
            oktext.text = "YES"
            massage.setText("Are you sure want to Exit")

            cancel.setOnClickListener {

                dialog.dismiss()
            }
            ok.setOnClickListener {
                super.onBackPressed()
                finishAffinity()
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




            return
        } else {
            Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }


}