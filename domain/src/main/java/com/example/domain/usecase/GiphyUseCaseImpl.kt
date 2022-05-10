package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.GiphyItem
import com.example.domain.repository.GiphyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GiphyUseCaseImpl @Inject constructor(private val giphyRepository: GiphyRepository) :
    GiphyUseCase {
    override fun execute(quaery: String): Flow<PagingData<GiphyItem>> {
        return giphyRepository.getGiphy(quaery)
    }

}