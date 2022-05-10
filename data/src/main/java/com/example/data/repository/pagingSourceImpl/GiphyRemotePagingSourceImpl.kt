package com.example.data.repository.pagingSourceImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.network.TreadingGiphyPagingSource
import com.example.data.repository.GiphyApi
import com.example.domain.model.GiphyItem
import com.example.domain.repository.GiphyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GiphyRemotePagingSourceImpl @Inject constructor(
    private val apiService: GiphyApi,
) : GiphyRepository {
    override  fun getGiphy(query: String): Flow<PagingData<GiphyItem>> = Pager(
        config = PagingConfig(pageSize = 25, prefetchDistance = 2),
        pagingSourceFactory = { TreadingGiphyPagingSource(apiService = apiService, searchValue = query) }
    ).flow
}