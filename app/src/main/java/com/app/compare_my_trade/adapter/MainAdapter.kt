package com.app.compare_my_trade.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.app.compare_my_trade.R

internal class MainAdapter(
    private val context: Context,
    private val types: Array<String>,
) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var textView: TextView
    override fun getCount(): Int {
        return types.size
    }
    override fun getItem(position: Int): Any? {
        return null
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.item_type_details, null)
        }
        textView = convertView!!.findViewById(R.id.mtv_typeDetail)
        textView.text = types[position]
        return convertView
    }
}