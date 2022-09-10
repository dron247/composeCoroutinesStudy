package org.dementiev.data.remote.api

import org.dementiev.data.entity.NewsItem
import retrofit2.Response
import retrofit2.http.GET

internal interface NewsApi {
    @GET("api/v1/sample")
    fun getNews(): List<NewsItem>
}