package com.architectcoders.aacboard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.architectcoders.aacboard.database.dao.DashboardDao
import com.architectcoders.aacboard.database.entity.CellEntity
import com.architectcoders.aacboard.database.entity.DashboardEntity

@Database(entities = [DashboardEntity::class, CellEntity::class], version = 1, exportSchema = false)
abstract class DashboardDatabase : RoomDatabase() {

    abstract fun dashboardDao(): DashboardDao
}