package com.edgar.data.services.usersList

data class UsersListResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<UsersListData>,
    val support:UsersListSupport
)

data class UsersListData(
    val id: Int,
    val email: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val avatar: String? = null
)

data class UsersListSupport(
    val url: String,
    val text: String
)
