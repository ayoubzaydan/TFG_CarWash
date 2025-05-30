package com.example.tfg_carwash.localData

import android.content.Context
import androidx.room.Room

object DatabaseInstance {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "carwash-db"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
        return INSTANCE!!
    }
}
