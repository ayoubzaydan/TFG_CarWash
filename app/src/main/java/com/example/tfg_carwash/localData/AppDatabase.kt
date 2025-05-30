package com.example.tfg_carwash.localData

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tfg_carwash.localData.UserEntity
import com.example.tfg_carwash.localData.LavadoEntity
import com.example.tfg_carwash.localData.ReservationEntity
import com.example.tfg_carwash.localData.dao.UserDao
import com.example.tfg_carwash.localData.dao.LavadoDao
import com.example.tfg_carwash.localData.dao.ReservationDao

@Database(
    entities = [UserEntity::class, LavadoEntity::class, ReservationEntity::class],
    version = 4, // Aumenta el número si has hecho cambios
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun lavadoDao(): LavadoDao
    abstract fun reservationDao(): ReservationDao
}
