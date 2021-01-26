package com.osamaaftab.freenowmvvm.domain.usecase.base

import com.osamaaftab.freenowmvvm.domain.model.ErrorModel

interface UseCaseResponse<in Type> {

    fun onSuccess(result: Type)

    fun onError(errorModel: ErrorModel?)
}