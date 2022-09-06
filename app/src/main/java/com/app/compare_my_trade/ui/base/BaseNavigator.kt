package com.app.compare_my_trade.ui.base

import android.os.Bundle
import android.view.View

/**
 *
 */
interface BaseNavigator {
    fun onClickView(v: View?)
    fun goTo(clazz: Class<*>, mExtras: Bundle?)
}