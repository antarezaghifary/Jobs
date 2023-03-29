package com.reza.jobs.data.source.endpoint

import com.reza.jobs.data.model.PositionModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("positions.json")
    fun getPosition(
    ): Deferred<List<PositionModel.Response.Data>>

    @GET("positions.json")
    fun getSearchPosition(
        @Query("description") description: String,
        @Query("location") location: String,
    ): Deferred<List<PositionModel.Response.Data>>
}