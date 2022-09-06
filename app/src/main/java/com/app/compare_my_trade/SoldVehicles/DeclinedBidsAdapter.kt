package com.example.kotlin_project1.DeclinedBid

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.R
import com.bumptech.glide.Glide
import com.example.kotlin_project1.CurrentBid.CurrentBidsAdapter
import com.example.kotlin_project1.WonBid.WonBidsAdapter
import com.example.kotlin_project1.WonBid.WonBidsModel

class DeclinedBidsAdapter (private val mList: List<DeclinedBidsModel>) : RecyclerView.Adapter<DeclinedBidsAdapter.ViewHolder>() {


    private  lateinit var mlistner: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistner = listener
    }



    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.declined_bids, parent, false)

        return ViewHolder(view,mlistner)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val DeclinedBidsModel = mList[position]



        Glide.with(holder.itemView).load(DeclinedBidsModel.image).fitCenter().into(holder.imageView)


        holder.textView2.text = DeclinedBidsModel.text2
        holder.textView3.text = DeclinedBidsModel.text3
        holder.textView4.text = "Ad ID - "+DeclinedBidsModel.text4
        holder.textView7.text = "$"+DeclinedBidsModel.text6
        holder.textView6.text = DeclinedBidsModel.buyer_name


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View,listener: DeclinedBidsAdapter.onItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image1)
        val textView2: TextView = itemView.findViewById(R.id.tv2)
        val textView3: TextView = itemView.findViewById(R.id.tv3)
        val textView4: TextView = itemView.findViewById(R.id.tv4)
        val textView6: TextView = itemView.findViewById(R.id.buyer_name)
        val textView7: TextView = itemView.findViewById(R.id.bid_price)
        val textView5: TextView = itemView.findViewById(R.id.view_car_details)

        init {

            itemView.setOnClickListener {

                listener.onItemClick(adapterPosition)

            }

        }
    }
}