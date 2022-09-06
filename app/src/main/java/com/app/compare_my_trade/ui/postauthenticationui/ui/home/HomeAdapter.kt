package com.example.kotlin_project1.CurrentBid

import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.R

import java.util.prefs.NodeChangeListener

class HomeAdapter(private val mList: ArrayList<HomeModel>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.current_bids, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val HomeModel = mList[position]


        holder.imageView.setImageResource(HomeModel.image)



        holder.textView2.text = HomeModel.text2
        holder.textView3.text = HomeModel.text3
        holder.textView4.text = HomeModel.text4

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image1)
        val textView2: TextView = itemView.findViewById(R.id.tv2)
        val textView3: TextView = itemView.findViewById(R.id.tv3)
        val textView4: TextView = itemView.findViewById(R.id.tv4)
        val textView5: TextView = itemView.findViewById(R.id.view_car_details)


        init {
            itemView.setOnClickListener {

                val intent = Intent(it.context, CarDetails::class.java).apply {

                }
                it.context.startActivity(intent)
            }
        }

    }
}