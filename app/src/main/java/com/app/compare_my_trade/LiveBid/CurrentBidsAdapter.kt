package com.example.kotlin_project1.CurrentBid

import android.app.Dialog
import android.content.Intent
import android.view.*

import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.Edit_vehicle
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils
import com.bumptech.glide.Glide
import okhttp3.internal.notify
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.HashMap

import java.util.prefs.NodeChangeListener

class  CurrentBidsAdapter (public val mList: List<CurrentBidsModel>) : RecyclerView.Adapter<CurrentBidsAdapter.ViewHolder>() {

    private  lateinit var mlistner: onItemClickListener

//    private  lateinit var mlistner2: onItemClickListener2

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistner = listener
    }

    private  lateinit var mlistner2: onItemClickListener2

//    private  lateinit var mlistner2: onItemClickListener2

    interface onItemClickListener2{
        fun onItemClick2(position: Int)
    }
    fun setOnItemClickListener2(listener2: onItemClickListener2){
        mlistner2 = listener2
    }

    private  lateinit var mlistner3: onItemClickListener3

//    private  lateinit var mlistner2: onItemClickListener2

    interface onItemClickListener3{
        fun onItemClick3(position: Int)
    }
    fun setOnItemClickListener3(listener3: onItemClickListener3){
        mlistner3 = listener3
    }

//    interface onItemClickListener2{
//        fun onItemClick2(position: Int)
//    }
//    fun setOnItemClickListener2(listener2: onItemClickListener2){
//        mlistner2 = listener2
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.current_bids, parent, false)

        return ViewHolder(view,mlistner,mlistner2,mlistner3)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val CurrentBidsModel = mList[position]


        Glide.with(holder.itemView).load(CurrentBidsModel.image).fitCenter().into(holder.imageView)


        holder.textView2.text = CurrentBidsModel.text2
        holder.textView3.text = CurrentBidsModel.text3
        holder.textView4.text = "Ad ID - "+CurrentBidsModel.text4
        holder.textView7.text = "$"+CurrentBidsModel.text6
        holder.textView6.text = CurrentBidsModel.bid_count+" Bids"



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View,listener: onItemClickListener,listener2: onItemClickListener2,listener3: onItemClickListener3) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image1)
        val textView2: TextView = itemView.findViewById(R.id.tv2)
        val textView3: TextView = itemView.findViewById(R.id.tv3)
        val textView4: TextView = itemView.findViewById(R.id.tv4)
        val textView6: TextView = itemView.findViewById(R.id.my_bid_price)
        val textView7: TextView = itemView.findViewById(R.id.your_price)
        val textView5: TextView = itemView.findViewById(R.id.view_car_details)

        var menu:ImageView = itemView.findViewById(R.id.menu_opton)
        val delete:ImageButton = itemView.findViewById(R.id.delete)




        init {
//
//            val CurrentBidsModel = mList[position]
//
//            var p_id= CurrentBidsModel.p_id

            itemView.setOnClickListener {

              listener.onItemClick(adapterPosition)
            }
            delete.setOnClickListener {
                listener2.onItemClick2(adapterPosition)
            }
            menu.setOnClickListener {

                val popup = PopupMenu(it.context, it)
                val inflater: MenuInflater = popup.menuInflater
                inflater.inflate(R.menu.edit_delete, popup.menu)






                popup.setOnMenuItemClickListener { menuItem ->
                    when(menuItem.itemId){
                        R.id.edit-> {

                            listener3.onItemClick3(adapterPosition)
                        }
                        R.id.delete-> {



                        }
                    }
                    true
                }
                popup.show()

            }
//
//
//            menu.setOnClickListener {
//                val popup = PopupMenu(it.context, it)
//                val inflater: MenuInflater = popup.menuInflater
//                inflater.inflate(R.menu.edit_delete, popup.menu)
//                popup.setOnMenuItemClickListener { menuItem ->
//                    when(menuItem.itemId){
//                        R.id.edit-> {
//                            val intent = Intent(it.context, Edit_vehicle::class.java).apply {
//                                putExtra("p_id", p_id)
//                            }
//                            it.context.startActivity(intent)
//                        }
//                        R.id.delete-> {
//
//                            val url = "http://motortraders.zydni.com/api/sellers/cars/delete/"+p_id
//
//                            val queue = Volley.newRequestQueue(it.context)
//
//
//                            val request: StringRequest = object : StringRequest(
//                                Method.DELETE, url,
//                                Response.Listener { response ->
//
//
//
//
//                                    Toast.makeText(it.context,"Deleted", Toast.LENGTH_LONG).show()
//
//
//
//
//                                }, Response.ErrorListener { error ->
//                                    Toast.makeText(it.context,"Deleted", Toast.LENGTH_LONG).show()
//                                    listener2.onItemClick2(adapterPosition)
//
//                                }) {
//
//                                @Throws(AuthFailureError::class)
//                                override fun getHeaders(): Map<String, String> {
//                                    val headers = HashMap<String, String>()
//                                    headers.put("Accept","application/json")
//                                    headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(it.context))
//
//                                    return headers
//                                }
//
//
//                            }
//                            request.retryPolicy = DefaultRetryPolicy(
//                                10000,
//                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//                            )
//                            queue.add(request)
//                        }
//                    }
//                    true
//                }
//                popup.show()
            }








    }
}