package org.dementiev.data

import org.dementiev.data.entity.NewsItem

interface MutableDataSource : DataSource {
    suspend fun putNews(news: List<NewsItem>): VoidResult<Throwable>
}