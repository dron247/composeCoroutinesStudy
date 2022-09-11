package org.dementiev.data.local

import android.content.Context
import org.dementiev.data.MutableDataSource
import org.dementiev.data.Result
import org.dementiev.data.VoidResult
import org.dementiev.data.entity.NewsItem
import org.dementiev.data.local.db.AppDatabase
import org.dementiev.data.runOperationCatching

internal class LocalDataSource(
    private val context: Context
) : MutableDataSource {
    private val dao = AppDatabase.getDatabase(context).newsDao()
    override suspend fun putNews(news: List<NewsItem>): VoidResult<Throwable> {
        return runOperationCatching {
            dao.insert(news)
        }
    }

    override suspend fun getNews(): Result<List<NewsItem>, Throwable> {
        return runOperationCatching {
            dao.getAll() ?: throw IllegalStateException("Empty db")
        }
    }
}