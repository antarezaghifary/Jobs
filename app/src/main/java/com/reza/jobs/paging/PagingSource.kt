package com.reza.jobs.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.reza.jobs.data.model.PositionModel
import com.reza.jobs.data.source.repository.JobRepository
import retrofit2.HttpException

class PagingSource(
    private val repository: JobRepository
) : PagingSource<Int, PositionModel.Response>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PositionModel.Response> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getPositionWithPagination(currentPage)
            val data = response
            val responseData = mutableListOf<PositionModel.Response>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PositionModel.Response>): Int? {
        return null
    }

}