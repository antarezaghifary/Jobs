package com.reza.jobs.data.source.repository

import com.reza.jobs.data.model.PositionModel
import com.reza.jobs.data.source.endpoint.ApiService
import kotlinx.coroutines.awaitAll

class JobRepository(
    private val apiService: ApiService
) {
    suspend fun getPosition(
    ): List<PositionModel.Response.Data> {
        return apiService.getPosition().await()
    }

    suspend fun getSearchPosition(
        description: String,
        location: String
    ): List<PositionModel.Response.Data> {
        return apiService.getSearchPosition(
            description,
            location
        ).await()
    }

    suspend fun getPositionWithPagination(
        page: Int
    ): List<PositionModel.Response> {
        return apiService.getPositionWithPagination(
            page
        ).await()
    }
}