package org.dementiev.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "news")
data class NewsItem(
    @PrimaryKey
    val id: String = "",
    val title: String = "",
    @SerializedName("body")
    @ColumnInfo(name = "body")
    val content: String = ""
) : Parcelable