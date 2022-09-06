package com.example.kotlin_project1.Favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.compare_my_trade.R
import com.example.kotlin_project1.DeclinedBid.DeclinedBidsAdapter
import com.example.kotlin_project1.DeclinedBid.DeclinedBidsModel


class FavoriteAdapter (private val mList: List<FavoriteModel>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val FavoriteModel = mList[position]


        holder.imageView.setImageResource(FavoriteModel.image)


        holder.textView1.text = FavoriteModel.text1
        holder.textView2.text = FavoriteModel.text2
        holder.textView3.text = FavoriteModel.text3


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image4)
        val textView1: TextView = itemView.findViewById(R.id.tv13)
        val textView2: TextView = itemView.findViewById(R.id.tv14)
        val textView3: TextView = itemView.findViewById(R.id.tv15)


    }
}