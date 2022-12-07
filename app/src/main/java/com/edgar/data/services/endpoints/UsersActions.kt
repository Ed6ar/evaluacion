package com.edgar.data.services.endpoints

import com.edgar.data.services.usersList.UsersListResponse
import retrofit2.Response
import retrofit2.http.GET

interface UsersActions {
    /**Fot this test i going to set this to 30
     * */
    @GET("users?per_page=30")
    suspend fun getUsers(): Response<UsersListResponse>
}