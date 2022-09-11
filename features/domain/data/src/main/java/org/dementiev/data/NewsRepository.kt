package org.dementiev.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.dementiev.data.entity.NewsItem
import org.dementiev.data.local.LocalDataSource
import org.dementiev.data.remote.RemoteDataSource
import org.dementiev.data.remote.client.NewsClient

interface NewsRepository {
    companion object {
        // DI container replacement
        fun create(context: Context): NewsRepository {
            return NewsRepositoryImpl(
                localDataSource = LocalDataSource(context),
                remoteDataSource = RemoteDataSource(NewsClient.createApi())
            )
        }
    }

    suspend fun getNews(): Flow<Result<List<NewsItem>, Throwable>>
}