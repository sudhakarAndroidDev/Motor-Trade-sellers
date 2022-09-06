package com.app.compare_my_trade.ui.postauthenticationui.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.compare_my_trade.R
import com.bumptech.glide.Glide
import com.example.kotlin_project1.CurrentBid.CurrentBidsAdapter
import com.example.kotlin_project1.CurrentBid.CurrentBidsModel

class NotificationAdapter (private val mList: List<NotificationModel>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private  lateinit var mlistner: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistner = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.today_notification, parent, false)

        return ViewHolder(view,mlistner)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val NotificationModel = mList[position]




        holder.textView1.text = NotificationModel.text1
        holder.textView2.text = NotificationModel.text2

        if (NotificationModel.is_read.equals("0")){

            holder.linearLayout.setBackgroundResource(R.drawable.background2)

        }else{
            holder.linearLayout.setBackgroundResource(R.drawable.currentbackground)
        }

        Glide.with(holder.itemView).load(NotificationModel.image).fitCenter().into(holder.imageView)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.n_img)
        val textView1: TextView = itemView.findViewById(R.id.ntv1)
        val textView2: TextView = itemView.findViewById(R.id.ntv2)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linear_notify)
//        val textView3: TextView = itemView.findViewById(R.id.ntv3)


        init {
            itemView.setOnClickListener {

                listener.onItemClick(adapterPosition)
            }
        }

    }
}