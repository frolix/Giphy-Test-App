package com.example.data.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.model.GiphyItem
import com.example.data.repository.GiphyApi
import retrofit2.HttpException
import javax.inject.Inject

class TreadingGiphyPagingSource @Inject constructor(
    private val apiService: GiphyApi,
    private val query: String
) : PagingSource<Int, GiphyItem>() {

    override fun getRefreshKey(state: PagingState<Int, GiphyItem>): Int {
        return state.anchorPosition ?: 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GiphyItem> {
        try {
            val offset = params.key
            val response = apiService.getTrendingGiphy(offset = offset ?: 0, query = query)

            Log.d("TreadingGiphyPagingSource", "HttpException: ${response.body()}")

            return if (response.isSuccessful) {
                val treadingGiphyList = checkNotNull(response.body()?.data)
                val pagination = checkNotNull(response.body()?.pagination)
                val prevKey = pagination.offset - pagination.count!!
                LoadResult.Page(treadingGiphyList, prevKey, offset)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            Log.d("TopNewsPagingSource", "HttpException: $e")
            return LoadResult.Error(e)
        } catch (e: Exception) {
            Log.d("TopNewsPagingSource", "Exception: $e")
            return LoadResult.Error(e)
        }
    }
}

