package com.example.giphytestapp.presintation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.GiphyItem
import com.example.domain.usecase.GiphyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GetGiphyListViewModel @Inject constructor(
    private val giphyUseCase: GiphyUseCase
) : ViewModel() {

    private var currentResult: Flow<PagingData<GiphyItem>>? = null
    private var currentQueryValue : String? = null


    fun searchGiphy(product : String): Flow<PagingData<GiphyItem>> {
        val lastResult = currentResult
        if(product == currentQueryValue && lastResult != null) {
            return lastResult
        }

        currentQueryValue = product
        Log.d("GetGiphyListViewModel", "product: ${product}")

        val newResult: Flow<PagingData<GiphyItem>> =
            giphyUseCase.execute(product).cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

}