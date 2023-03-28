package com.reza.jobs.data.source.repository

import com.reza.jobs.data.model.PositionModel
import com.reza.jobs.data.source.endpoint.ApiService

class JobRepository(
    private val apiService: ApiService
) {
    suspend fun getPosition(
    ): PositionModel.Response{
        return apiService.getPosition().await()
    }
}