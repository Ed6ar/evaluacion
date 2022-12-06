package com.edgar.domine.utils

import android.util.Patterns
import android.widget.EditText
import com.edgar.evaluacion.R

fun validateEmail(view: EditText, email: String) {
    view.error = if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        view.context.getString(R.string.InvalidEmail)
    else null
}

fun validatePassword(view: EditText, password: String) {
    view.error = if(password.isEmpty()) view.context.getString(R.string.InvalidPassword) else null
}