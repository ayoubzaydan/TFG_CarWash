package com.example.tfg_carwash.localData

import android.provider.ContactsContract.CommonDataKinds.Photo
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "matricula") val matricula: String? // Matricula del coche del usuario
)

@Entity(tableName = "lavado_table")
data class LavadoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "time") val time: Int? // Tiempo en minutos
)

@Entity(tableName = "reservations")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "lavado_id") val lavadoId: Int,
    @ColumnInfo(name = "date") val date: String, // Formato YYYY-MM-DD
    @ColumnInfo(name = "time") val time: String // Formato HH:MM
)

data class ReservationWithLavado(
    @Embedded val reservation: ReservationEntity,
    @Relation(
        parentColumn = "lavado_id",
        entityColumn = "id"
    )
    val lavado: LavadoEntity
)
