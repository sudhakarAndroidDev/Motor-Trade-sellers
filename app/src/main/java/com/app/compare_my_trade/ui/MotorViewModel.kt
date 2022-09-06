package com.app.compare_my_trade.ui

import android.app.Application
import android.view.View
import android.widget.AdapterView
import com.app.compare_my_trade.ui.base.BaseNavigator
import com.app.compare_my_trade.ui.base.BaseViewModel

import org.koin.core.KoinComponent
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.app.compare_my_trade.data.model.beforelogin.login.LoginResponse
import com.app.compare_my_trade.data.model.beforelogin.register.CreateAccountResponse
import com.app.compare_my_trade.data.model.beforelogin.state.StateListResponseItem
import com.app.compare_my_trade.network.model.BaseResponse
import com.app.compare_my_trade.network.model.Resource
import com.app.compare_my_trade.repo.beforelogin.BeforeLoginRepoList
import org.koin.core.inject


class MotorViewModel(application: Application) : BaseViewModel<BaseNavigator>(application),
    KoinComponent
{
    var username = ObservableField("mano@gmail.com")
    var password = ObservableField("K"+"$"+"hruthi71")

    var firstName=ObservableField<String>()
    var lastName=ObservableField<String>()
    var email=ObservableField<String>()
    var phoneNumber=ObservableField<String>()
    var address=ObservableField<String>()
    var postCode=ObservableField<String>()
    var setPassword=ObservableField<String>()
    var state=ObservableField<String>()
    var countryID=ObservableField<String>()
    private val beforeLoginRepo: BeforeLoginRepoList by inject()
     val isLoading = MutableLiveData<Boolean>()
    var loginResponse = MutableLiveData<Resource<BaseResponse<LoginResponse>>>()
    var stateList = ObservableField<List<String>>()
    var stateArrayValue=ArrayList<StateListResponseItem>()
    var createAccountApiResponse=MutableLiveData<Resource<BaseResponse<CreateAccountResponse>>>()
    var stateListResponse=MutableLiveData<List<StateListResponseItem>>()

    fun onClickAction(view: View?) {
        getNavigator().onClickView(view)
    }

    fun postLoginResponse() {
        beforeLoginRepo.getLoginResponse(username.get()!!,password.get()!!,loginResponse)
    }

     fun postRegistrationResponse() {
         beforeLoginRepo.getCreateAccountResponse(
             firstName = firstName.get()!!,
             lastName = lastName.get()!!,
             email = email.get()!!,
             password= setPassword.get()!!,
             addressLine = address.get()!!,
             postalCode = postCode.get()!!,
             locationId = countryID.get()!!,
             phoneNumber=phoneNumber.get()!!,
             response = createAccountApiResponse
         )
    }


    fun getStateListResponse(){
        beforeLoginRepo.getStateListResponse(stateListResponse)
    }


    fun onSelectSectionItem(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        stateArrayValue.let {
//            state.set(it[pos].name)
//            countryID.set(it[pos].country_id)
        }

    }


}