package com.edgar.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edgar.data.services.register.RegisterRequest
import com.edgar.domine.RegisterUseCase
import com.edgar.evaluacion.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class SignUpViewModel(
    private val retrofit: Retrofit
): ViewModel() {

    private var _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    var loading: LiveData<Boolean> = _loading

    private var _errorId: MutableLiveData<Int?> = MutableLiveData()
    var errorId: LiveData<Int?> = _errorId

    fun resetErrorMessage(){
        _errorId.postValue(null)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _loading.postValue(false)
        _errorId.postValue(R.string.SomethingWasWrong)
    }

    fun tryToRegisterUser(registerRequest: RegisterRequest, onSuccess: (token: String) -> Unit){
        viewModelScope.launch(exceptionHandler) {
            _loading.postValue(true)
            RegisterUseCase(retrofit).execute(registerRequest = registerRequest).apply {
                if(this.isSuccessful){
                    Log.e("tryToRegisterUser","${this.body()}")
                    _loading.postValue(false)
                    onSuccess(this.body()!!.token!!)
                }else{
                    Log.e("tryToRegisterUser","${this.errorBody()}")
                    throw Exception()
                }
            }
        }
    }

}