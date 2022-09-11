package org.dementiev.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.dementiev.data.entity.NewsItem

internal class NewsRepositoryImpl internal constructor(
    private val localDataSource: MutableDataSource,
    private val remoteDataSource: DataSource
) : NewsRepository {

    override suspend fun getNews(): Flow<Result<List<NewsItem>, Throwable>> {
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