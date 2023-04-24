package com.architectcoders.aacboard.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DashboardEntity (
    @PrimaryKey
    val id: Int,
    val name: String,
    val rows: Int,
    val columns: Int
)
