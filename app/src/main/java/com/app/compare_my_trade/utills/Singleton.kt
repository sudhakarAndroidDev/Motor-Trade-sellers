package com.app.compare_my_trade.utills

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.compare_my_trade.R
import com.bumptech.glide.Glide

object Singleton {
    var isNetworkConnected: Boolean = false
    var StatusCode: String = ""
    var mImdb:String=""
    var BaseImageUrl="https://image.tmdb.org/t/p/w185/"
    var connectionCheck:Boolean=false
    var phoneRegex: String ="^[+]?[0-9]{10,13}\$"

    @BindingAdapter("imageUrls")
    @JvmStatic
    fun imageUrls(view: ImageView, imageUrl: String?) {
        if (imageUrl != null && imageUrl != "")
            Glide.with(view.context)
                .load(BaseImageUrl+imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(view)
        else
            Glide.with(view.context)
                .load(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .into(view)
    }

    fun navigateTo(context: Context, clazz: Class<*>, mExtras: Bundle) {
        val intent = Intent(context, clazz)
        intent.putExtras(mExtras)
        context.startActivity(intent)
    }

    fun RecyclerView.addItemDecorationWithoutLastDivider() {

        if (layoutManager !is LinearLayoutManager)
            return

        addItemDecoration(object :
            DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation) {

            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)

                if (parent.getChildAdapterPosition(view) == state.itemCount - 1)
                    outRect.setEmpty()
                else
                    super.getItemOffsets(outRect, view, parent, state)
            }
        })
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }


    fun isValidPhoneNumber(target: CharSequence?): Boolean {
        return if (target == null || target.length < 9 || target.length > 13) {
            false
        } else {
            Patterns.PHONE.matcher(target).matches()
        }
    }


}