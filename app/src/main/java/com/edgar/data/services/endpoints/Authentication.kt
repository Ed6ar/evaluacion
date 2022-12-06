package com.edgar.data.services.endpoints

import com.edgar.data.services.register.RegisterRequest
import com.edgar.data.services.register.RegisterResponse
import com.edgar.data.services.signIn.SignInRequest
import com.edgar.data.services.signIn.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Authentication {
    @POST("register")
    suspend fun setRegister(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("login")
    suspend fun setLogin(@Body signInRequest: SignInRequest): Response<SignInResponse>
}