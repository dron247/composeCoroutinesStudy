package org.dementiev.news

import org.dementiev.data.entity.NewsItem

sealed class NewsState {
    object Loading : NewsState()
    data class Error(val throwable: Throwable) : NewsState()
    data class Content(val news: List<NewsItem>) : NewsState()
}