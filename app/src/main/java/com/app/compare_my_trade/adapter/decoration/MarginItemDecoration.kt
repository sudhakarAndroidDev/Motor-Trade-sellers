package com.app.compare_my_trade.adapter.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceHeight: Int?) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if(spaceHeight!=null) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    top = spaceHeight
                }
                bottom = spaceHeight
            }else{
                top = 10
                bottom = 10
            }
        }
    }
}