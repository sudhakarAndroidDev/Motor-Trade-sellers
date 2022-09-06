package com.app.compare_my_trade.adapter.binding

import android.R
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.InverseBindingListener

object SpinnerExtensions {


    /**
     * set spinner value
     */
    fun Spinner.setSpinnerValue(value: Any?) {
        if (adapter != null ) {
            val position = (adapter as ArrayAdapter<*>).getPosition(value as Nothing?)
            setSelection(position, false)
            tag = position
        }
    }
    /**
     * set spinner onItemSelectedListener listener
     */
    fun Spinner.setSpinnerInverseBindingListener(listener: InverseBindingListener?) {
        when (listener) {
            null -> {
                onItemSelectedListener = null
            }
            else -> {
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        if (tag != position) {
                            listener.onChange()
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            }
        }
    }
    /**
     * get spinner value
     */
    fun Spinner.getSpinnerValue(): Any? {
        return selectedItem
    }


}