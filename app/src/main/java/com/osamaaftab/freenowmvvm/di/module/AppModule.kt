package com.osamaaftab.freenowmvvm.di.module

import com.osamaaftab.freenowmvvm.util.ApiErrorHandle
import org.koin.dsl.module

val AppModule = module {
    single { provideApiError() }
}

fun provideApiError(): ApiErrorHandle {
    return ApiErrorHandle()
}