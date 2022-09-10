package org.dementiev.data

import org.dementiev.data.entity.NewsItem

interface DataSource {
    suspend fun getNews(): Result<List<NewsItem>, Throwable>
}