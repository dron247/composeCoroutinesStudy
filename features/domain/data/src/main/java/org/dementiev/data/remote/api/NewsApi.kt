package org.dementiev.data.remote.api

import org.dementiev.data.entity.NewsItem
import retrofit2.http.GET

internal interface NewsApi {
    @GET("api/v1/sample")
    suspend fun getNews(): List<NewsItem>
}