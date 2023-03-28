package com.reza.jobs.data.source.endpoint

import com.reza.jobs.data.model.PositionModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {
    @GET("positions.json")
    fun getPosition(
    ): Deferred<PositionModel.Response>
}