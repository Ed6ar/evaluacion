package com.edgar.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val token: String,
    val email: String
): Parcelable
