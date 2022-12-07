package com.edgar.ui.viewModels

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
) : ViewModel() {

    private var _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    var loading: LiveData<Boolean> = _loading

    private var _errorId: MutableLiveData<Int?> = MutableLiveData()
    var errorId: LiveData<Int?> = _errorId

    private var _users: MutableLiveData<MutableList<UsersListData>> = MutableLiveData()

    private var _usersFiltered: MutableLiveData<MutableList<UsersListData>> = MutableLiveData()
    var usersFiltered: LiveData<MutableList<UsersListData>> = _usersFiltered

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _loading.postValue(false)
        _errorId.postValue(R.string.SomethingWasWrong)
    }

    fun resetErrorMessage() {
        _errorId.postValue(null)
    }

    fun tryToGetUsersList() {
        viewModelScope.launch(exceptionHandler) {
            _loading.postValue(true)
            ListUsersUseCase(retrofit).execute().apply {
                if (this.isSuccessful) {
                    _loading.postValue(false)

                    with(sortUsers(this.body()!!.data.toMutableList())){
                        _users.postValue(this)
                        _usersFiltered.postValue(this)
                    }

                } else {
                    throw Exception()
                }
            }
        }
    }

    private fun sortUsers(toMutableList: MutableList<UsersListData>): MutableList<UsersListData> {
        toMutableList.sortBy { it.first_name.plus(" ${it.last_name}") }
        return toMutableList
    }

    fun filter(name: String){
        val holder = _users.value!!.toMutableList()
        with(holder.filter { it.first_name.plus(" ${it.last_name}").contains(name) }){
            _usersFiltered.postValue(this.toMutableList())
        }
    }

}