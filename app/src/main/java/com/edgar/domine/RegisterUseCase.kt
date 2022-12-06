package com.edgar.domine

import com.edgar.data.services.endpoints.Authentication
import com.edgar.data.services.register.RegisterRequest
import com.edgar.data.services.register.RegisterResponse
import retrofit2.Response
import retrofit2.Retrofit

class RegisterUseCase(private val retrofit: Retrofit) {
    suspend fun execute(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return retrofit.create(Authentication::class.java).setRegister(registerRequest)
    }
}