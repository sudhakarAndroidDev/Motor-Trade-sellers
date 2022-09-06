package com.example.kotlin_project1.WonBid

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.ListOfBiding.ListofBidingModel
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils
import com.bumptech.glide.Glide
import com.example.kotlin_project1.CurrentBid.CurrentBidsAdapter
import com.example.kotlin_project1.CurrentBid.CurrentBidsModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.HashMap


class ListOfBidingAdapter (private val mList: List<ListofBidingModel>) : RecyclerView.Adapter<ListOfBidingAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_of_bidings, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ListofBidingModel = mList[position]


        Glide.with(holder.itemView).load(ListofBidingModel.image).fitCenter().into(holder.imageView)


        holder.textView1.text = "Name: "+ListofBidingModel.text1
        holder.email.text = "Email: "+ListofBidingModel.email
        if (ListofBidingModel.text1 != null){
            holder.textView2.text = "Phone: "+ListofBidingModel.text2
        }
        holder.textView3.text = "$"+ListofBidingModel.text3
//        holder.textView4.text = ListofBidingModel.text4


        var v_id = ListofBidingModel.v_id






            holder.accept.setOnClickListener {

                var id = ListofBidingModel.id


                val dialog = Dialog(it.context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

                dialog.setContentView(R.layout.alert_dialog_box)
//                dialog.getWindow()!!.setBackgroundDrawableResource(R.drawable.currentbackground);
                dialog.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
                val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
                val oktext = dialog.findViewById<TextView>(R.id.yestext)
                val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
                val cancel_text = dialog.findViewById<TextView>(R.id.no_text)
                val title = dialog.findViewById<TextView>(R.id.title)
                val massage = dialog.findViewById<TextView>(R.id.message)

                title.setText("Alert")
                cancel_text.text = "No"
                oktext.text = "YES"
                massage.setText("Are you sure want to Accept the bid")

                cancel.setOnClickListener {

                    dialog.dismiss()
                }
                ok.setOnClickListener {

                    val URL = "http://motortraders.zydni.com/api/sellers/bid/accept/" + id
                    val queue = Volley.newRequestQueue(it.context)
                    val request: StringRequest = object : StringRequest(
                        Method.POST, URL,
                        Response.Listener { response ->

                            var res = response.toString()

                            if (res.equals("true")) {
                                val intent = Intent(it.context, CarDetails::class.java).apply {


                                    this.putExtra("bids", "AcceptedBids")
                                    this.putExtra("type", "accepted")
                                    this.putExtra("v_id", v_id)
                                }
                                it.context.startActivity(intent)
                                Toast.makeText(it.context, "Success", Toast.LENGTH_LONG)
                            }

                        }, Response.ErrorListener { error ->

                            if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                                Toast.makeText(it.context,"Check your internet connection", Toast.LENGTH_LONG).show()
                            }
                        }) {
                        @Throws(AuthFailureError::class)
                        override fun getHeaders(): Map<String, String> {
                            val headers = HashMap<String, String>()
                            headers.put("Accept", "application/json")
                            headers.put("Authorization",
                                "Bearer " + PreferenceUtils.getTokan(it.context))

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
                dialog.show()

            }







        }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.bid_photo)
        val textView1: TextView = itemView.findViewById(R.id.name)
        val textView2: TextView = itemView.findViewById(R.id.pin)
        val textView3: TextView = itemView.findViewById(R.id.price)
        val accept: LinearLayout = itemView.findViewById(R.id.accept)
        val email:TextView = itemView.findViewById(R.id.email)


//
        init {

        }
    }
}