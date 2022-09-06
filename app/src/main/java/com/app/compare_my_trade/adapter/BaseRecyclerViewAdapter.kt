package com.app.compare_my_trade.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


class BaseRecyclerViewAdapter<T, V : ViewDataBinding>   (@LayoutRes
                                                         private val layoutResourceId: Int,
                                                         private val bindVariableId: Int,
                                                         private val items: MutableList<T>,
                                                         private var dataVariables: Map<Int,Any>?,
                                                         private val onDataBindCallback: OnDataBindCallback<V>
) : androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder<V>>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        return BaseViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutResourceId, parent, false), onDataBindCallback)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
//        if(dataVariables != null) {
//            for (data in dataVariables!!.entries) {
//                holder.viewDataBinding.setVariable(data.key, data.value)
//            }
//        }
        holder.viewDataBinding.setVariable(bindVariableId, getItem(position))
        holder.viewDataBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun getItem(position: Int): T {
        return items[position]
    }
    fun addDataSet(data:List<T>){
        items.addAll(data)
    }

    fun getItems():List<T>{
        return items
    }

    fun cleatDataSet(){
        items.clear()
    }


}