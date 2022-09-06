package com.app.compare_my_trade.ui.spalsh

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.app.compare_my_trade.BR
import com.app.compare_my_trade.MyBackgroundService
import com.app.compare_my_trade.R
import com.app.compare_my_trade.databinding.ActivitySplashBinding
import com.app.compare_my_trade.ui.MotorViewModel
import com.app.compare_my_trade.ui.base.BaseActivity
import com.app.compare_my_trade.ui.base.BaseNavigator
import com.app.compare_my_trade.ui.login.LoginFragment
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.prograssbar.*
import org.koin.android.ext.android.inject


class Splash : BaseActivity<ActivitySplashBinding, MotorViewModel>(), BaseNavigator {
    private  val movieViewModel: MotorViewModel by inject()
    override fun getBindingVariable(): Int {
        return BR.motorVM
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun getViewModel(): MotorViewModel {
        return movieViewModel
    }

    override fun onClickView(v: View?) {
    }

    override fun goTo(clazz: Class<*>, mExtras: Bundle?) {
        val intent = Intent(this, clazz)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spin_kit.visibility=View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({

            if (PreferenceUtils.getTokan(this) == null) {
                val intent = Intent(this,LoginFragment::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        }, 2000)
    }

    override fun onBackPressed() {

        super.onBackPressed()
        finishAffinity()

    }

    override fun onStart() {
        super.onStart()
        if (PreferenceUtils.getTokan(this) == null) {

        } else {
            val serviceIntent = Intent(this,
                MyBackgroundService::class.java)
            this@Splash.startService(serviceIntent)
        }

    }
}