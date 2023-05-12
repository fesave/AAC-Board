package com.architectcoders.aacboard.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DashboardEntity::class, CellEntity::class], version = 1, exportSchema = false)
abstract class DashboardDatabase : RoomDatabase() {

    abstract fun dashboardDao(): DashboardDao
}