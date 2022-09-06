package com.app.compare_my_trade.adapter.binding

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.app.compare_my_trade.adapter.binding.SpinnerExtensions.getSpinnerValue
import com.app.compare_my_trade.adapter.binding.SpinnerExtensions.setSpinnerInverseBindingListener
import com.app.compare_my_trade.adapter.binding.SpinnerExtensions.setSpinnerValue


class SpinnerBindings {
    @BindingAdapter("selectedValue")
    fun Spinner.setSelectedValue(selectedValue: Any?) {
        setSpinnerValue(selectedValue)
    }

    @BindingAdapter("selectedValueAttrChanged")
    fun Spinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {
        setSpinnerInverseBindingListener(inverseBindingListener)
    }

    companion object InverseSpinnerBindings {

        @JvmStatic
        @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
        fun Spinner.getSelectedValue(): Any? {
            return getSpinnerValue()
        }
    }
}