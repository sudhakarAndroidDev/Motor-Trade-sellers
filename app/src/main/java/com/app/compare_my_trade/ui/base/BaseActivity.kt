package com.app.compare_my_trade.ui.base

import android.content.*

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.compare_my_trade.R
import com.app.compare_my_trade.utills.NetworkUtils
import com.app.compare_my_trade.utills.Singleton
import com.app.mobile_trade.utills.Alert


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : androidx.appcompat.app.AppCompatActivity() {

    private var mViewDataBinding: T? = null
        private set
    private var mViewModel: V? = null
        private set
    private var networkChangeReceiver: NetworkChangeReceiver? = null
    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V



    private var isNoNetAlertVisible = false
    private var fragmentManager: FragmentManager? = null
    companion object {
        const val INTERNET_RESULT_CODE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager = supportFragmentManager
        performDataBinding()
        }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
    }
     fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
     }

    fun getViewDataBinding(): T {
        return mViewDataBinding!!
    }

    fun animationTransanction() {
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit)
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext)
    }


    /**
     * common toast show for all screens
     *
     */
    fun putToast(message: String?) {
        Toast.makeText(applicationContext, "" + message, Toast.LENGTH_SHORT).show()
    }



    fun showAlert(){
        AlertDialog.Builder(application)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("App Exit")
            .setMessage("Are you sure you want to Exit?")
            .setPositiveButton("Yes") { _, _ -> ActivityCompat.finishAffinity(this) }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        overridePendingTransition(R.anim.anim_pop_enter, R.anim.anim_pop_exit)
    }

    inner class NetworkChangeReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            //showNetworkError()
        }
    }

    override fun onPause() {
        super.onPause()
       // unregisterReceiver(networkChangeReceiver)
    }

    override fun onResume() {
        super.onResume()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }


    fun showNetworkError(): Boolean {
        // Show popup when network is no available
        val isNetwork = NetworkUtils.isNetworkConnected(this)
        Singleton.isNetworkConnected = NetworkUtils.isNetworkConnected(this)
        if (!isNoNetAlertVisible && !NetworkUtils.isNetworkConnected(this)) {
            isNoNetAlertVisible = true
            Alert.createOkAlert(this@BaseActivity, "Alert", "No internet connection or network failure",
                    object : Alert.OnAlertClickListener {
                        override fun onPositive(dialog: DialogInterface) {
                            dialog.dismiss()
                            isNoNetAlertVisible = false
                        }

                        override fun onNegative(dialog: DialogInterface) {
                            dialog.dismiss()
                        }

                    }).show()
        }
        return isNetwork
    }

}