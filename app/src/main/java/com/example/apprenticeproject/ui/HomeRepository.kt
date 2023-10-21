package com.example.apprenticeproject.ui

import com.example.apprenticeproject.network.ApiRequest
import com.example.apprenticeproject.network.HiringApi
import com.example.apprenticeproject.network.NetworkResult
import com.example.apprenticeproject.network.responses.HiringResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class HomeRepository
@Inject
constructor(
    private val api: HiringApi
) : ApiRequest() {

    fun getData() : Flow<NetworkResult<ArrayList<HiringResponse>>> = flow {
        emit(apiRequest { api.getHiringJson() })
    }.onStart {
        emit(NetworkResult.Loading())
    }.flowOn(Dispatchers.IO)
}