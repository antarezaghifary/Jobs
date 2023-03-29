package com.reza.jobs.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

object PositionModel {
    data class Response(
        val code: Int,
        val `data`: List<Data?>? = null,
        val error: Boolean,
        val message: String
    ) {
        @Parcelize
        data class Data(
            val id: String? = null,
            val type: String? = null,
            val url: String? = null,
            val company: String? = null,
            val company_url: String? = null,
            val location: String? = null,
            val title: String? = null,
            val description: String? = null,
            val how_to_apply: String? = null,
            val company_logo: String? = null
        ) : Parcelable
    }
}