package com.reza.jobs.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.reza.jobs.data.model.PositionModel
import java.lang.reflect.Type

class CustomDeserializer : JsonDeserializer<List<PositionModel.Response.Data>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<PositionModel.Response.Data> {
        val list = mutableListOf<PositionModel.Response.Data>()
        val jsonArray = json?.asJsonArray

        jsonArray?.forEach {

            if (it.isJsonNull) {
                return@forEach
            }

            try {
                val typeToken = object : TypeToken<PositionModel.Response.Data>() {}.type
                val data = GsonBuilder().create()
                    .fromJson<PositionModel.Response.Data>(it, typeToken)
                list.add(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return list
    }
}