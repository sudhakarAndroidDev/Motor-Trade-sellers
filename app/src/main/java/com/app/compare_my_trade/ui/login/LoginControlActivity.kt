package com.app.compare_my_trade.ui.login


import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.app.compare_my_trade.BR
import com.app.compare_my_trade.R
import com.app.compare_my_trade.databinding.ActivityLoginControlBinding
import com.app.compare_my_trade.ui.MotorViewModel
import com.app.compare_my_trade.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login_control.*
import org.koin.android.ext.android.inject

class LoginControlActivity : BaseActivity<ActivityLoginControlBinding, MotorViewModel>() {
    private  val motorViewModel: MotorViewModel by inject()

    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun getBindingVariable(): Int {
        return BR.motorVM
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login_control
    }

    override fun getViewModel(): MotorViewModel {
        return motorViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(getViewDataBinding().toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_login_screen)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_login_screen)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}