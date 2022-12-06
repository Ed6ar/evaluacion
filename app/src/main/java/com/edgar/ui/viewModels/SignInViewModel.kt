package com.edgar.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edgar.data.services.signIn.SignInRequest
import com.edgar.domine.LoginUseCase
import com.edgar.evaluacion.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class SignInViewModel(
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

    fun tryToSignIn(signInRequest: SignInRequest, onSuccess: (token: String) -> Unit){
        viewModelScope.launch(exceptionHandler) {
            _loading.postValue(true)
            LoginUseCase(retrofit).execute(signInRequest = signInRequest).apply {
                if(this.isSuccessful){
                    Log.e("tryToSignIn","${this.body()}")
                    _loading.postValue(false)
                    onSuccess(this.body()!!.token!!)
                }else{
                    Log.e("tryToSignIn","${this.errorBody()}")
                    throw Exception()
                }
            }
        }
    }

}