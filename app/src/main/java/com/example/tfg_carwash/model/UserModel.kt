package com.example.tfg_carwash.model


import com.example.tfg_carwash.localData.UserEntity
import java.io.Serializable

data class UserModel(
    val id: Int = 0,
    val name: String?,
    val email: String?,
    val password: String?,
    val phone: String?,
    val address: String?,
    val matricula: String?
) : Serializable

fun UserEntity.toModel(): UserModel {
    return UserModel(id, name, email, password, phone, address, matricula)
}

fun UserModel.toEntity(): UserEntity {
    return UserEntity(id, name, email, password, phone, address, matricula)
}
