package com.app.compare_my_trade.ui.postauthenticationui

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.R

import com.app.compare_my_trade.ui.login.LoginControlActivity
import com.app.compare_my_trade.ui.postauthenticationui.ui.home.HomeFragment
import com.app.compare_my_trade.ui.postauthenticationui.ui.managebids.ManageBidsFragment
import com.app.compare_my_trade.ui.postauthenticationui.ui.moreoptions.MoreOptionsFragment
import com.app.compare_my_trade.ui.postauthenticationui.ui.notifications.NotificationsFragment
import com.app.compare_my_trade.utils.PreferenceUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import github.com.st235.lib_expandablebottombar.ExpandableBottomBarMenuItem
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class HomeActivity : AppCompatActivity() {
    private var backPressedTime:Long = 0
    //    private lateinit var binding: ActivityHomeBinding
//    val navController by lazy { findNavController(R.id.nav_host_fragment_activity_home) }
//     // Passing each menu ID as a set of Ids because each
//    // menu should be considered as top level destinations.
//    private val     appBarConfiguration by lazy {
//         AppBarConfiguration(
//             setOf(
//                 R.id.navigation_home, R.id.navigation_manage_bids, R.id.navigation_notifications, R.id.navigation_more
//             )
//         )
//     }
    lateinit var  navView: BottomNavigationView


    lateinit var mGoogleSignInClient: GoogleSignInClient

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        binding = ActivityHomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//



//        navView.setupWithNavController(navController)

        navView = findViewById(R.id.nav_view)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signOut()


        if (intent.getStringExtra("more").toString().equals("more"))
        {
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_home, MoreOptionsFragment())
                .commit()
            navView.setSelectedItemId(R.id.navigation_more)
        }
        else{
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_home, HomeFragment())
                .commit()
        }

        if (intent.getStringExtra("nav").toString().equals("notification")){
            navView.setSelectedItemId(R.id.navigation_notifications)
        }








        if (PreferenceUtils.getTokan(this) == null) {

            navView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->


                when (item.itemId) {

                    R.id.navigation_home -> {
                        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_home, HomeFragment() ).commit()
                    }
                    R.id.navigation_manage_bids -> {






                        val dialog = Dialog(this)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

                        dialog.setContentView(R.layout.alert_dialog_box)


                        val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
                        val oktext = dialog.findViewById<TextView>(R.id.yestext)
                        val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
                        val title = dialog.findViewById<TextView>(R.id.title)
                        val massage = dialog.findViewById<TextView>(R.id.message)

                        title.setText("Alert")
                        oktext.text = "OK"
                        massage.setText("You need sign in or sign up before continuing")

                        cancel.setOnClickListener {

                            dialog.dismiss()
                        }
                        ok.setOnClickListener {

                            val intent =
                                Intent(this, LoginControlActivity::class.java).apply {
                                }
                            startActivity(intent)
                            dialog.dismiss()
                        }

//                        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
//                        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//                        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
                        dialog.show()

                    }
                    R.id.navigation_notifications -> {
                        val dialog = Dialog(this)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

                        dialog.setContentView(R.layout.alert_dialog_box)


                        val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
                        val oktext = dialog.findViewById<TextView>(R.id.yestext)
                        val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
                        val title = dialog.findViewById<TextView>(R.id.title)
                        val massage = dialog.findViewById<TextView>(R.id.message)

                        title.setText("Alert")
                        oktext.text = "OK"
                        massage.setText("You need sign in or sign up before continuing")

                        cancel.setOnClickListener {

                            dialog.dismiss()
                        }
                        ok.setOnClickListener {

                            val intent =
                                Intent(this, LoginControlActivity::class.java).apply {
                                }
                            startActivity(intent)
                            dialog.dismiss()
                        }


                        dialog.show()

                    }
                    R.id.navigation_more -> {

                        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_home, MoreOptionsFragment()).commit()
                    }
                }

                true

            })

        }else{
            navView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
                var temp: Fragment? = null
                when (item.itemId) {

                    R.id.navigation_home -> {
                        temp = HomeFragment()
                    }
                    R.id.navigation_manage_bids -> {
                        temp = ManageBidsFragment()
                    }
                    R.id.navigation_notifications -> {
                        temp = NotificationsFragment()
                        val badgeDrawable = navView.getBadge( R.id.navigation_notifications)
                        if (badgeDrawable != null) {
                            badgeDrawable.isVisible = false
                            badgeDrawable.clearNumber()
                        }
                    }
                    R.id.navigation_more -> {

                        temp = MoreOptionsFragment()
                    }
                }
                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_home, temp!!).commit()
                true

            })

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
            dialog.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)

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

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                // ...
            }
    }

    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                // ...
            }
    }

    override fun onStart() {
        super.onStart()


        val URL = "http://motortraders.zydni.com/api/sellers/notification/list"

        val queue = Volley.newRequestQueue(this)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->



                val res = JSONObject(response)

                var successfulBids = res.getJSONArray("data")
                var total = res.getInt("total")

                val last_notification= 0
                try {
                    var last = 0

                    try {
                        for (i in 0 until successfulBids.length()) {
                            val data: JSONObject = successfulBids.getJSONObject(i)


                            var is_read =data.getInt("is_read")
                            var id =data.getString("id")
                            var title =data.getString("title")
                            var notification_message =data.getString("notification_message")
                            var car_id =data.getString("car_id")

                            if (is_read.equals(0)){
                                last++

                            }

                            if (is_read.equals(0) && last_notification.equals(i)){
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                    val channel = NotificationChannel("My Notification",
//                                        "My Notification",
//                                        NotificationManager.IMPORTANCE_HIGH)
//                                    val manager =
//                                        getSystemService(NotificationManager::class.java)
//                                    manager.createNotificationChannel(channel)
//                                }
//                                val uri =
//                                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//                                val builder: NotificationCompat.Builder =
//                                    NotificationCompat.Builder(this, "My Notification")
//                                        .setSound(uri)
//                                        .setContentTitle(title)
//                                        .setContentText(notification_message)
//                                        .setAutoCancel(true)
//                                        .setSmallIcon(R.drawable.logo)
//                                        .setLargeIcon(BitmapFactory.decodeResource(applicationContext.getResources(), R.drawable.logo))
//                                        .setVibrate(longArrayOf(1000, 1000, 1000,
//                                            1000, 1000))
//                                        .setOnlyAlertOnce(true)



//                                val notificationIntent =
//                                    Intent(this, CarDetails::class.java).apply {
//                                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                                        putExtra("v_id",car_id)
//                                        putExtra("n_id",id)
//                                    }
//                                val contentIntent =
//                                    PendingIntent.getActivity(this, 0, notificationIntent,
//                                        PendingIntent.FLAG_UPDATE_CURRENT)
//                                builder.setContentIntent(contentIntent)

                                // Add as notification

                                // Add as notification
//                                val manager =
//                                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//                                manager.notify(0, builder.build())

                            }




//                            Log.i("frfwfwefwefdwede",last_notification.toString() +"  "+i)

//                            if (PreferenceUtils.getLength(this) == 0) {
//                                PreferenceUtils.saveLength(successfulBids.length(), this)
//                            } else if (PreferenceUtils.getLength(this) < i) {
//                                PreferenceUtils.saveLength(successfulBids.length(),
//                                    this)
//                                try {
//
//
//                                } catch (e: JSONException) {
//                                }
//                            }


                        }
                        if (last != 0){
                            var badge = navView.getOrCreateBadge(R.id.navigation_notifications)
                            badge.isVisible = true

                            badge.number = last
                        }else{
                            val badgeDrawable = navView.getBadge( R.id.navigation_notifications)
                            if (badgeDrawable != null) {
                                badgeDrawable.isVisible = false
                                badgeDrawable.clearNumber()
                            }
                        }
                    } catch (e: Exception) {

                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->




            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@HomeActivity))

                return headers
            }



        }
        request.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)

    }
}