package com.reza.jobs.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

object PositionModel {
    data class Response(
        val code: Int,
        val `data`: List<Data>,
        val error: Boolean,
        val message: String
    ) {
        @Parcelize
        data class Data(
            val type: Int,
            val id: String,
            val name: String,
            val url: String,
            val company: String,
            val company_url: String,
            val location: String,
            val title: String,
            val desc: String,
            val apply: String,
            val company_logo: String
        ) : Parcelable
    }
}