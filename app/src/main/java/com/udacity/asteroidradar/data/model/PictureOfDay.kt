package com.udacity.asteroidradar.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "picture_table")
data class PictureOfDay(@Json(name = "media_type") @ColumnInfo(name = "media_type")val mediaType: String, @ColumnInfo(name = "title")val title: String,
                        @PrimaryKey
                        @ColumnInfo(name = "url")val url: String)