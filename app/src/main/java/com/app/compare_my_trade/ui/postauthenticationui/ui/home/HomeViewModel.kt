package com.app.compare_my_trade.ui.postauthenticationui.ui.home

import android.app.Application
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.base.BaseNavigator
import com.app.compare_my_trade.ui.base.BaseViewModel
import org.koin.core.KoinComponent

class HomeViewModel(application: Application) : BaseViewModel<BaseNavigator>(application),KoinComponent {
    val count: Int = 10
    val listOfCars = arrayOf("Audi - 2020 (Very Good Conditions)", "Hyundai Santro - 2019 Automatic", "BMW - 2020", "Lamborghini Gallardo - 2019 Autom...", "Audi - 2020 (Very Good Conditions)")
    val homeHeaders = arrayOf(application.resources.getString(R.string.top_recommendation))
}