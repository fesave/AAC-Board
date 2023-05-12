package com.architectcoders.aacboard

import android.app.Application
import androidx.room.Room
import com.architectcoders.aacboard.model.database.DashboardDatabase

class App : Application() {

    lateinit var db: DashboardDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            DashboardDatabase::class.java, "dashboard-db"
        ).build()
    }
}