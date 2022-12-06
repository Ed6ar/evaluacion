package com.edgar.domine.utils

import android.util.Patterns
import android.widget.EditText
import com.edgar.evaluacion.R

fun validateEmail(view: EditText, email: String) {
    view.error = if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        view.context.getString(R.string.InvalidEmail)
    else null
}

fun validatePassword(view: EditText, password: String, passwordConfirmation: String) {
    view.error = when {
        password.isEmpty() -> view.context.getString(R.string.InvalidPassword)
        password.isNotEmpty() && passwordConfirmation.isNotEmpty() && password != passwordConfirmation -> view.context.getString(R.string.PasswordDontMatch)
        else -> null
    }
}