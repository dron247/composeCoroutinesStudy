package org.dementiev.data.remote

import kotlinx.coroutines.delay
import org.dementiev.data.DataSource
import org.dementiev.data.Result
import org.dementiev.data.entity.NewsItem
import org.dementiev.data.remote.api.NewsApi
import org.dementiev.data.runOperationCatching

internal class RemoteDataSource(
    private val api: NewsApi
) : DataSource {
    override suspend fun getNews(): Result<List<NewsItem>, Throwable> {
        return runOperationCatching {
            delay(3000)
            api.getNews()
        }
    }
}