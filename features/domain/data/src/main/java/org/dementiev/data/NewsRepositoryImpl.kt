package org.dementiev.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.dementiev.data.entity.NewsItem
import timber.log.Timber
import kotlin.random.Random

internal class NewsRepositoryImpl internal constructor(
    private val localDataSource: MutableDataSource,
    private val remoteDataSource: DataSource
) : NewsRepository {

    private val randomizer = Random(42)

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

    override suspend fun refreshNews(): Flow<Result<List<NewsItem>, Throwable>> {
        return flow {
            // emulate network error
            val random = randomizer.nextInt()
            Timber.d("Got random $random")
            if (random % 3 == 0) {
                emit(Result.Error(Exception("Totally random network error")))
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