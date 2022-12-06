package com.edgar.data.modules

import com.edgar.data.getRetrofit
import org.koin.dsl.module

val RETROFIT = module {
    single { getRetrofit() }
}