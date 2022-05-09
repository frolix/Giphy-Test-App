package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.GiphyItem
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {

    fun getGiphy(): Flow<PagingData<GiphyItem>>

}