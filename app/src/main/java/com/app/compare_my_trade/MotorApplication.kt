package com.app.compare_my_trade

import androidx.multidex.MultiDexApplication
import com.app.compare_my_trade.di.KoinCoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MotorApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        val moduleList = listOf(KoinCoreModule().koinCoreModule)
        startKoin{
            androidContext(this@MotorApplication)
            modules(moduleList)
        }
    }
}
