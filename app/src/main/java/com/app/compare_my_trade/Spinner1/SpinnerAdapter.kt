package com.app.compare_my_trade.Spinner1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.databinding.adapters.CompoundButtonBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.compare_my_trade.R
import androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked






class SpinnerAdapter (private val mList: List<SpinnerModel>) : RecyclerView.Adapter<SpinnerAdapter.ViewHolder>() {

    private  lateinit var mlistner: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistner = listener
    }
    public val flist: List<SpinnerModel>
        get() {
            TODO()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.spinner_text, parent, false)

        return ViewHolder(view,mlistner)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val SpinnerModel = mList[position]



        holder.textView1.text = SpinnerModel.text1



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    @SuppressLint("ResourceAsColor")
    class ViewHolder (ItemView: View, listener: onItemClickListener)  : RecyclerView.ViewHolder(ItemView)  {

        val textView1= itemView.findViewById<TextView>(R.id.f_text)
//        val r_group = itemView.findViewById<RadioGroup>(R.id.r_group)



        init {







            textView1.setOnClickListener {



                    listener.onItemClick(adapterPosition)









            }
        }

    }


}