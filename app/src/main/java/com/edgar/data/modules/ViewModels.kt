package com.edgar.data.modules

import com.edgar.ui.viewModels.MainViewModel
import com.edgar.ui.viewModels.SignInViewModel
import com.edgar.ui.viewModels.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val VIEW_MODELS = module {
    viewModel { SignInViewModel(retrofit = get()) }
    viewModel { SignUpViewModel(retrofit = get()) }
    viewModel { MainViewModel() }
}