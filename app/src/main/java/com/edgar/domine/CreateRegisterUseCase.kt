package com.edgar.domine

import com.edgar.data.services.create.CreateRegisterRequest
import com.edgar.data.services.create.CreateRegisterResponse
import com.edgar.data.services.endpoints.UsersActions
import retrofit2.Response
import retrofit2.Retrofit

class CreateRegisterUseCase(private val retrofit: Retrofit) {
    suspend fun execute(createRegisterRequest: CreateRegisterRequest): Response<CreateRegisterResponse> {
        return retrofit.create(UsersActions::class.java).create(createRegisterRequest)
    }
}