package com.edgar.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edgar.data.services.create.CreateRegisterRequest
import com.edgar.domine.CreateRegisterUseCase
import com.edgar.evaluacion.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class CreateOrUpdateViewModel(
    private val retrofit: Retrofit
): ViewModel() {

    private var _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    var loading: LiveData<Boolean> = _loading

    private var _errorId: MutableLiveData<Int?> = MutableLiveData()
    var errorId: LiveData<Int?> = _errorId

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _loading.postValue(false)
        _errorId.postValue(R.string.SomethingWasWrong)
    }

    fun resetErrorMessage() {
        _errorId.postValue(null)
    }

    fun tryToCreateRegister(createRegisterRequest: CreateRegisterRequest){
        viewModelScope.launch(exceptionHandler) {
            _loading.postValue(true)
            CreateRegisterUseCase(retrofit).execute(createRegisterRequest).apply {
                if(this.isSuccessful){
                    Log.e("result","${this.body()}")
                    _loading.postValue(false)
                }else{
                    throw Exception()
                }
            }
        }
    }

}