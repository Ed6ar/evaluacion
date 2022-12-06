package com.edgar.data.modules

import com.edgar.ui.viewModels.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val VIEW_MODELS = module {
    viewModelOf(::SignInViewModel)
}