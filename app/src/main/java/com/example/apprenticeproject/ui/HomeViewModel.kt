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

    private val _dataState: MutableStateFlow<NetworkResult<ArrayList<HiringResponse>>> =
        MutableStateFlow(NetworkResult.Loading())
    val dataState: LiveData<NetworkResult<ArrayList<HiringResponse>>> = _dataState.asLiveData()

    fun fetchData() {
        viewModelScope.launch {
            homeRepository.getData().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        var filteredData =
                            result.data?.filter { !it.name.isNullOrBlank() }?.sortedWith(
                                compareBy(
                                    { it.listId },
                                    { it.name?.substringAfter("Item ")?.toInt() },
//                                    { it.name }, // In case we want ordering in string format we can use this instead
                                )
                            )
                        filteredData?.let {
                            _dataState.value = NetworkResult.Success(ArrayList(it))
                        }
                    }
                    else -> {
                        _dataState.value = result
                    }
                }
            }
        }
    }

    fun reverseData() {
        viewModelScope.launch {
            val list = _dataState.value.data
            list?.let {
                it.reverse()
                _dataState.value = NetworkResult.Success(it)
            }
        }
    }
}
