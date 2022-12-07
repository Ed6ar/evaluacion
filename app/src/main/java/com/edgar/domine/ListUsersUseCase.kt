package com.edgar.domine

import com.edgar.data.services.endpoints.UsersActions
import com.edgar.data.services.usersList.UsersListResponse
import retrofit2.Response
import retrofit2.Retrofit

class ListUsersUseCase(private val retrofit: Retrofit) {
    suspend fun execute(): Response<UsersListResponse> {
        return retrofit.create(UsersActions::class.java).getUsers()
    }
}