package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.GiphyItem
import kotlinx.coroutines.flow.Flow

interface GiphyUseCase {

    suspend fun execute(): Flow<PagingData<GiphyItem>>

}