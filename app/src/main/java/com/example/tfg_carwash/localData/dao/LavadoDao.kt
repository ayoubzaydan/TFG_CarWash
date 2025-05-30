package com.example.tfg_carwash.localData.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tfg_carwash.localData.LavadoEntity

@Dao
interface LavadoDao {

    @Query("SELECT * FROM lavado_table")
    fun getAllLavados(): List<LavadoEntity>

    @Query("SELECT * FROM lavado_table WHERE id = :lavadoId")
    fun getLavadoById(lavadoId: Long): LavadoEntity?

    @Query("SELECT * FROM lavado_table WHERE name = :nombre LIMIT 1")
    fun getLavadoByName(nombre: String): LavadoEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(lavado: LavadoEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(lavados: List<LavadoEntity>)


}
