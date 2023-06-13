package org.d3if3100.youkas.db.entity

import com.squareup.moshi.Json

data class News(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "totalResults")
    val totalResults:Int = 0,
    @Json(name = "articles")
    val articles: List<Article> = listOf(),
)