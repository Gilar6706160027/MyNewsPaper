package com.gilar.mynewspaper.model

data class NewsResponse(
    var articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)