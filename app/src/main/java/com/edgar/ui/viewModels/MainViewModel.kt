package com.edgar.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private var _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    var loading: LiveData<Boolean> = _loading

    private var _navigate: MutableLiveData<String?> = MutableLiveData(null)
    var navigate: LiveData<String?> = _navigate

    fun setLoadingState(state: Boolean){
        _loading.postValue(state)
    }

    fun navigateToUserActivity(userData: String?){
        _navigate.postValue(userData)
    }

}