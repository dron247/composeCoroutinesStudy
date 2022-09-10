package org.dementiev.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.dementiev.data.entity.NewsItem
import org.dementiev.data.local.LocalDataSource
import org.dementiev.data.remote.RemoteDataSource
import org.dementiev.data.remote.client.NewsClient

class NewsRepository(
    private val localDataSource: MutableDataSource,
    private val remoteDataSource: DataSource
) {
    companion object {
        // DI container replacement
        fun create(context: Context): NewsRepository {
            return NewsRepository(
                localDataSource = LocalDataSource(context),
                remoteDataSource = RemoteDataSource(NewsClient.createApi())
            )
        }
    }

    suspend fun getNews(): Flow<Result<List<NewsItem>, Throwable>> {
        return flow {
            val localData = localDataSource.getNews()
            if (localData is Result.Success && localData.data.isNotEmpty()) {
                emit(localData)
            } else {
                val remoteData = remoteDataSource.getNews()
                if (remoteData is Result.Success) {
                    localDataSource.putNews(remoteData.data)
                }
                emit(remoteData)
            }
        }
    }
}