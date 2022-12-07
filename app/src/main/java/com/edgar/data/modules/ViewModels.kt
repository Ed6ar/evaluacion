package com.edgar.data.modules

import com.edgar.ui.viewModels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val VIEW_MODELS = module {
    viewModel { SignInViewModel(retrofit = get()) }
    viewModel { SignUpViewModel(retrofit = get()) }
    viewModel { MainViewModel() }
    viewModel { ListViewModel(retrofit = get()) }
    viewModel { CreateOrUpdateViewModel(retrofit = get()) }
}