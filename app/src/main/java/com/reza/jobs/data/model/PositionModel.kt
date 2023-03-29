package com.reza.jobs.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

object PositionModel {
    data class Response(
        val code: Int,
        val `data`: List<Data?>,
        val error: Boolean,
        val message: String
    ) {
        @Parcelize
        data class Data(
            val type: String? = null,
            val id: String? = null,
            val name: String? = null,
            val url: String? = null,
            val company: String? = null,
            val company_url: String? = null,
            val location: String? = null,
            val title: String? = null,
            val description: String? = null,
            val apply: String? = null,
            val company_logo: String? = null
        ) : Parcelable
    }
}