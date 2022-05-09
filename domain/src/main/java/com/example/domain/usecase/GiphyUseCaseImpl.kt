package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.GiphyItem
import com.example.domain.repository.GiphyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GiphyUseCaseImpl @Inject constructor(private val giphyRepository: GiphyRepository) :
    GiphyUseCase {
    override suspend fun execute(): Flow<PagingData<GiphyItem>> {
        return giphyRepository.getGiphy()
    }

}