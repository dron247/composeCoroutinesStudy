package org.dementiev.data.remote.client

import okhttp3.OkHttpClient
import org.dementiev.data.remote.api.NewsApi
import org.dementiev.data.remote.client.interceptor.MockInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object NewsClient {
    fun createApi(): NewsApi =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(MockInterceptor())
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.mts.ru/")
            .build()
            .create(NewsApi::class.java)
}