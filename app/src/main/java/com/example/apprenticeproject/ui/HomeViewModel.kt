package com.example.apprenticeproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.apprenticeproject.network.NetworkResult
import com.example.apprenticeproject.network.responses.HiringResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _dataState: MutableStateFlow<NetworkResult<ArrayList<HiringResponse>>> = MutableStateFlow(NetworkResult.Loading())
    val dataState: LiveData<NetworkResult<ArrayList<HiringResponse>>> = _dataState.asLiveData()

    fun fetchData() {
        viewModelScope.launch {
            homeRepository.getData().collect { result ->
                _dataState.value = result
            }
        }
    }
}
