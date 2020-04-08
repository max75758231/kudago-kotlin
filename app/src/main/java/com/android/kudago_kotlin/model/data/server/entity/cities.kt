package com.android.kudago_kotlin.model.data.server.entity

import com.android.kudago_kotlin.domain.City

data class City(
    val slug: String,
    val name: String
) {
    fun toDomainModel() = City(
        slug = slug,
        name = name
    )
}