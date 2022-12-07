package com.edgar.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edgar.data.services.usersList.UsersListData
import com.edgar.domine.ListUsersUseCase
import com.edgar.evaluacion.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class ListViewModel(
    private val retrofit: Retrofit
): ViewModel() {

    private var _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    var loading: LiveData<Boolean> = _loading

    private var _errorId: MutableLiveData<Int?> = MutableLiveData()
    var errorId: LiveData<Int?> = _errorId

    private var _users: MutableLiveData<MutableList<UsersListData>> = MutableLiveData()
    var users: LiveData<MutableList<UsersListData>> = _users

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _loading.postValue(false)
        _errorId.postValue(R.string.SomethingWasWrong)
    }

    fun resetErrorMessage(){
        _errorId.postValue(null)
    }

    fun tryToGetUsersList(){
        _loading.postValue(true)
        viewModelScope.launch(exceptionHandler) {
            ListUsersUseCase(retrofit).execute().apply {
                if(this.isSuccessful){
                    Log.e("tryToRegisterUser","${this.body()}")
                    _users.postValue(this.body()!!.data.toMutableList())
                }else{
                    Log.e("tryToRegisterUser","${this.errorBody()}")
                    throw Exception()
                }
            }
        }
    }

}