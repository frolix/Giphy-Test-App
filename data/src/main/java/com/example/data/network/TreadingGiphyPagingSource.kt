package com.example.data.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.repository.GiphyApi
import com.example.domain.model.GiphyItem
import retrofit2.HttpException

class TreadingGiphyPagingSource(
    private val apiService: GiphyApi,
    private val searchValue: String
) : PagingSource<Int, GiphyItem>() {

    override fun getRefreshKey(state: PagingState<Int, GiphyItem>): Int {
        return state.anchorPosition ?: 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GiphyItem> {
        val page = params.key ?: 0
        return try {
            if (searchValue.isNullOrEmpty()) {
                val giphyTrendingList = apiService.getTrendingGiphy(offset = page)
                val treadingGiphyList = checkNotNull(giphyTrendingList.body()?.data)

                LoadResult.Page(
                    data = treadingGiphyList,
                    prevKey = if (page == 0) null else page - 10,
                    nextKey = if (treadingGiphyList.isEmpty()) null else page + 10
                )
            } else {
                val searchGiphyData = apiService.getSearchGiphy(
                    offset = page,
                    searchValue = searchValue
                )
                val searchGiphyList = checkNotNull(searchGiphyData.body()?.data)

                LoadResult.Page(
                    data = searchGiphyList,
                    prevKey = if (page == 0) null else page - 10,
                    nextKey = if (searchGiphyList.isEmpty()) null else page + 10
                )

            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    /*
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GiphyItem> {
        try {

            val page = params.key ?: 0


            Log.d(
                "TreadingGiphyPagingSource",
                "params.key: ${params.key} params.loadSize ${params.loadSize}"
            )
            val offset = params.key ?: 0
            val pageSize = params.loadSize
            val response =
                apiService.getTrendingGiphy(offset = offset ?: 0, query = query, limit = pageSize)

//            Log.d("TreadingGiphyPagingSource", "HttpException: ${response.body()}")

            return if (response.isSuccessful) {
                val treadingGiphyList = checkNotNull(response.body()?.data)
                val pagination = checkNotNull(response.body()?.pagination)
                Log.d("TreadingGiphyPagingSource", "pagination.offset ${pagination.offset} pagination.offset${pagination.offset}")

                val prevKey = pagination.offset+ 5
                val nextKey = pagination.count?.plus(pagination.offset)
                Log.d("TreadingGiphyPagingSource", "prevKey $prevKey nextKey$offset")
                LoadResult.Page(treadingGiphyList, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            Log.d("TreadingGiphyPagingSource", "HttpException: $e")
            return LoadResult.Error(e)
        } catch (e: Exception) {
            Log.d("TreadingGiphyPagingSource", "Exception: $e")
            return LoadResult.Error(e)
        }
    }

     */
}

