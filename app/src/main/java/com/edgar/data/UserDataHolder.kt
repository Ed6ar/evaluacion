package com.edgar.data

object UserDataHolder {

    var token: String = ""
    var email: String = ""

    fun setData(userData: UserData){
        this.token = userData.token
        this.email = userData.email
    }
}