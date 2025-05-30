package com.example.tfg_carwash.localData.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.tfg_carwash.localData.ReservationEntity
import com.example.tfg_carwash.localData.ReservationWithLavado

@Dao
interface ReservationDao {

    @Insert
    fun insert(reservation: ReservationEntity)

    @Query("SELECT * FROM reservations WHERE user_id = :userId")
    fun getReservationsByUser(userId: Int): List<ReservationEntity>

    @Query("SELECT * FROM reservations WHERE date = :date AND time = :time")
    fun getReservationByDateTime(date: String, time: String): ReservationEntity?

    @Query("SELECT * FROM reservations")
    fun getAllReservations(): List<ReservationEntity>

    @Query("DELETE FROM reservations WHERE lavado_id = 0")
    fun deleteInvalidReservations()

    @Transaction
    @Query("SELECT * FROM reservations WHERE user_id = :userId")
    fun getReservationsWithLavado(userId: Int): List<ReservationWithLavado>
}
