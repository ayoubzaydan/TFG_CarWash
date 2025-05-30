package com.example.tfg_carwash.localData.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tfg_carwash.localData.UserEntity


@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM user_table WHERE id = :userId")
    fun getUserById(userId: Long): UserEntity?

    @Insert
    fun insert(user: UserEntity): Long

    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    fun getUserByEmail(email: String): UserEntity?


    @Query("""
        UPDATE user_table 
        SET name = :name, email = :email, password = :password, phone = :phone, address = :address, matricula = :matricula 
        WHERE id = :userId
    """)
    fun updateUser(
        userId: Long,
        name: String,
        email: String,
        password: String,
        phone: String,
        address: String,
        matricula: String
    )
}


