package com.app.compare_my_trade.ui.base

import android.content.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.compare_my_trade.utills.NetworkUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton

abstract class BaseFragment<T : ViewDataBinding, out V : BaseViewModel<*>> : Fragment() {
    var baseActivity: BaseActivity<*, *>? = null
        private set
    var mRootView: View? = null
    private var mRootViewGrp: ViewGroup? = null
    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null
    private var mToast: Toast? = null
    var connectivityReceiverListener: INetworkConnection? = null
    var findLastVisibleItemPositionValue = 9

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V


    private var networkChangeReceiver: NetworkChangeReceiver? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.baseActivity = activity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)

    }

     fun setScrollListener(recyclerView:RecyclerView,view:FloatingActionButton) {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if ((recyclerView.layoutManager as LinearLayoutManager?)?.findFirstVisibleItemPosition()!! >= findLastVisibleItemPositionValue) {
                    view.show()
                } else {
                    view.hide()

                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        mRootViewGrp = container
        return mRootView
    }


    override fun onDetach() {
        hideKeyboard()
        baseActivity = null
        super.onDetach()
    }

    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(activity?.applicationContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }



    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }


    override fun onResume() {
        super.onResume()
//        connectivityReceiverListener = this as INetworkConnection
//        networkChangeReceiver = NetworkChangeReceiver(connectivityReceiverListener)
//        activity?.registerReceiver(
//            networkChangeReceiver,
//            IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
//        )

        Log.d(this.tag,"Resume")
    }

    override fun onPause() {
        super.onPause()
        //activity?.unregisterReceiver(networkChangeReceiver)
    }

    fun toastUnderDevelopment(){
        Toast.makeText(context, "Page Under Development", Toast.LENGTH_SHORT).show()
    }


    /**
     * common toast show for all screens
     *
     */
    fun putToast(message: String?) {
        if (mToast != null) mToast?.cancel()
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        mToast?.show()
    }

}