package com.app.compare_my_trade.di

import com.app.compare_my_trade.data.local.SharedPreferenceImp
import com.app.compare_my_trade.network.clientbuilder.ApiInterceptor
import com.app.compare_my_trade.network.clientbuilder.MotorClientBuilder
import com.app.compare_my_trade.repo.AllMotorAPIRepo
import com.app.compare_my_trade.repo.beforelogin.BeforeLoginRepoList
import com.app.compare_my_trade.ui.MotorViewModel
import com.app.compare_my_trade.ui.postauthenticationui.ui.home.HomeViewModel
import com.app.compare_my_trade.ui.postauthenticationui.ui.moreoptions.MoreOptionsViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.single

class KoinCoreModule {
    val koinCoreModule = module {
        single<SharedPreferenceImp>()
        single<ApiInterceptor>()
        single{MotorClientBuilder(get(),get())}
        single<AllMotorAPIRepo>()
        single<BeforeLoginRepoList>()

        viewModel<MotorViewModel>()
        viewModel<HomeViewModel>()
        viewModel<MoreOptionsViewModel>()
}
}