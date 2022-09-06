package com.app.compare_my_trade.ui.postauthenticationui.ui.moreoptions

import android.app.Application
import androidx.lifecycle.ViewModel
import com.app.compare_my_trade.R
import com.app.compare_my_trade.data.model.MoreOptionsModel
import com.app.compare_my_trade.ui.base.BaseNavigator
import com.app.compare_my_trade.ui.base.BaseViewModel
import org.koin.core.KoinComponent

class MoreOptionsViewModel(application: Application) : BaseViewModel<BaseNavigator>(application),
    KoinComponent {
    //    val menuItems = hashMapOf(
//        "About" to R.drawable.ic_about_icon,
//        "Terms & Policies" to R.drawable.ic_tc_icon,
//        "Help Center" to R.drawable.ic_help_icon,
//        "Invite Friends" to R.drawable.ic_invite_icon,
//        "Logout" to R.drawable.ic_invite_icon
//    )
    val menuItems = listOf(
        MoreOptionsModel("About Us", R.drawable.ic_about_icon),
        MoreOptionsModel("Terms & Policies", R.drawable.ic_tc_icon),
        MoreOptionsModel("Help Center", R.drawable.ic_help_icon),
        MoreOptionsModel("Invite Friends", R.drawable.ic_invite_icon),
        MoreOptionsModel("Logout", R.drawable.ic_invite_icon)
    )
}