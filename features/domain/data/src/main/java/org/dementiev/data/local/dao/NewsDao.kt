package org.dementiev.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import org.dementiev.data.entity.NewsItem

@Dao
internal interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAll(): List<NewsItem>?

    @Query("SELECT * FROM news WHERE id = :id")
    fun getById(id: Long): NewsItem?

    @Insert(onConflict = REPLACE)
    fun insert(news: List<NewsItem>)

    @Update
    fun update(news: NewsItem?)

    @Delete
    fun delete(news: NewsItem?)
}