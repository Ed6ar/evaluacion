package com.edgar.domine

import com.edgar.data.services.endpoints.Authentication
import com.edgar.data.services.signIn.SignInRequest
import com.edgar.data.services.signIn.SignInResponse
import retrofit2.Response
import retrofit2.Retrofit

class LoginUseCase(private val retrofit: Retrofit) {
    suspend fun execute(signInRequest: SignInRequest): Response<SignInResponse> {
        return retrofit.create(Authentication::class.java).setLogin(signInRequest = signInRequest)
    }
}