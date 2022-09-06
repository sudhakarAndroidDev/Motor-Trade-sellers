package com.app.compare_my_trade.ui.postauthenticationui.ui.managebids

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.app.compare_my_trade.utils.PreferenceUtils
import com.bumptech.glide.Glide
import com.example.kotlin_project1.CurrentBid.CurrentBidsFragment
import com.example.kotlin_project1.DeclinedBid.DeclinedBidsFragment
import com.example.kotlin_project1.WonBid.WonBidsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.HashMap
import android.widget.FrameLayout
import androidx.fragment.app.FragmentTransaction
import android.R
import android.os.Build
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.app.compare_my_trade.*
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity

import com.app.compare_my_trade.ui.postauthenticationui.ui.home.HomeFragment
import com.example.kotlin_project1.CurrentBid.CurrentBidsAdapter
import com.example.kotlin_project1.CurrentBid.CurrentBidsModel
import com.example.kotlin_project1.CurrentBid.HomeAdapter
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import org.json.JSONArray
import java.util.zip.Inflater


class ManageBidsFragment : Fragment(),manageBids{

//    lateinit var tabLayout: TabLayout
//    lateinit var viewPager: ViewPager
    lateinit var profile:ImageView

    var status: String? = null

    var tabLayout: TabLayout? = null
    var frameLayout: FrameLayout? = null
    var fragment: Fragment? = null
    var fragmentTransaction: FragmentTransaction? = null

    lateinit var bid_count:TextView
    var count:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val root= inflater.inflate(com.app.compare_my_trade.R.layout.fragment_manage_bids, container, false)
        res()
        res2()

//        tabLayout = root.findViewById(R.id.tabLayout)
//        viewPager = root.findViewById(R.id.viewPager)


        tabLayout = root.findViewById(com.app.compare_my_trade.R.id.tabLayout) as TabLayout
        frameLayout = root.findViewById(com.app.compare_my_trade.R.id.frameLayout) as FrameLayout

        fragment = CurrentBidsFragment()

        fragmentTransaction = requireFragmentManager().beginTransaction()
        fragmentTransaction!!.replace(com.app.compare_my_trade.R.id.frameLayout,
            fragment as CurrentBidsFragment
        )
        fragmentTransaction!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction!!.commit()


        tabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // Fragment fragment = null;
                when (tab.position) {
                    0 -> fragment = CurrentBidsFragment()
                    1 -> fragment = WonBidsFragment()
                    2 -> fragment = DeclinedBidsFragment()

                }



                val ft = requireFragmentManager().beginTransaction()
                ft.replace(com.app.compare_my_trade.R.id.frameLayout, fragment!!)
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ft.commit()



            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        profile = root.findViewById<ImageView>(com.app.compare_my_trade.R.id.profile_photo)

        profile.setOnClickListener {

//            findNavController().navigate(R.id.action_navigation_home_to_navigation_more2)
            val intent = Intent(activity, HomeActivity::class.java).apply {
                putExtra("more","more")
            }
            startActivity(intent)

        }
        bid_count = root.findViewById(com.app.compare_my_trade.R.id.bid_count)

        val floatingActionButton=root.findViewById<FloatingActionButton>(com.app.compare_my_trade.R.id.floating2)


        floatingActionButton.setOnClickListener {


            if (status != null) {
                if (status.equals("active")) {

                    val intent = Intent(activity, Edit_vehicle::class.java).apply {
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

                    dialog.setContentView(com.app.compare_my_trade.R.layout.alert_dialog_box)

                    val ok = dialog.findViewById<RelativeLayout>(com.app.compare_my_trade.R.id.yes)
                    val oktext = dialog.findViewById<TextView>(com.app.compare_my_trade.R.id.yestext)
                    val cancel = dialog.findViewById<RelativeLayout>(com.app.compare_my_trade.R.id.no)
                    val title = dialog.findViewById<TextView>(com.app.compare_my_trade.R.id.title)
                    val massage = dialog.findViewById<TextView>(com.app.compare_my_trade.R.id.message)

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










//        tabLayout.addTab(tabLayout.newTab().setText("Live Bids"))
//        tabLayout.addTab(tabLayout.newTab().setText("Accepted"))
//        tabLayout.addTab(tabLayout.newTab().setText("Sold Vehicles"))
//        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
//        val adapter = Adapter(this.requireContext(), getParentFragmentManager()  ,
//            tabLayout.tabCount)
//        viewPager.adapter = adapter
////        viewPager.offscreenPageLimit = 0
//        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                viewPager.currentItem = tab.position
//            }
//            override fun onTabUnselected(tab: TabLayout.Tab) {}
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//
//        })

        return root
    }

    private fun res2() {
        val URL = "http://motortraders.zydni.com/api/sellers/cars/list"

        val queue = Volley.newRequestQueue(activity)

        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                val res = JSONArray(response)
                count = res.length()

                bid_count.text = res.length().toString()


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

    override fun bitCont()
    {
        res2()
    }




    private fun res() {



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

                        if(activity !=null) {
                            Glide.with(this).load(avatar).fitCenter().into(profile)
                        }
                    } catch (e: JSONException) {
                    } catch (error: UnsupportedEncodingException) {
                    }








//                Toast.makeText(requireContext(), avatar.toString(),Toast.LENGTH_LONG).show()
//                Log.i("jdhfisd", avatar.toString())



                }, { error ->
                    val charset: Charset = Charsets.UTF_8


                    Log.i("jdhfisd", error.toString())

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

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }
}

//
//@Suppress("DEPRECATION")
//internal class Adapter(
//    var context: Context,
//    fm: FragmentManager,
//    var totalTabs: Int
//) :
//    FragmentPagerAdapter(fm) {
//    override fun getItem(position: Int): Fragment {
//        return when (position) {
//            0 -> {
//                CurrentBidsFragment()
//            }
//            1 -> {
//                WonBidsFragment()
//            }
//            2->{
//                DeclinedBidsFragment()
//            }
//
//            else -> getItem(position)
//        }
//    }
//    override fun getCount(): Int {
//        return totalTabs
//    }
//}