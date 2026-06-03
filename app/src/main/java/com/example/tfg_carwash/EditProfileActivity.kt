package com.example.tfg_carwash

import com.example.tfg_carwash.model.UserModel
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.tfg_carwash.localData.DatabaseInstance

class EditProfileActivity : AppCompatActivity() {

    private lateinit var nameEdit: EditText
    private lateinit var emailEdit: EditText
    private lateinit var phoneEdit: EditText
    private lateinit var addressEdit: EditText
    private lateinit var btnSave: Button
    private lateinit var matriculaEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("user", UserModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("user") as? UserModel
        }

        nameEdit = findViewById(R.id.editName)
        emailEdit = findViewById(R.id.editEmail)
        phoneEdit = findViewById(R.id.editPhone)
        addressEdit = findViewById(R.id.editAddress)
        matriculaEdit = findViewById(R.id.editMatricula)
        btnSave = findViewById(R.id.btnSaveProfile)


        user?.let {
            nameEdit.setText(it.name)
            emailEdit.setText(it.email)
            phoneEdit.setText(it.phone)
            addressEdit.setText(it.address)
            matriculaEdit.setText(it.matricula)
        }

        btnSave.setOnClickListener {
            val updatedUser = user?.copy(
                name = nameEdit.text.toString().trim(),
                email = emailEdit.text.toString().trim(),
                phone = phoneEdit.text.toString().trim(),
                address = addressEdit.text.toString().trim(),
                matricula = matriculaEdit.text.toString().trim()
            )

            if (updatedUser != null) {
                val db = DatabaseInstance.getDatabase(this)
                db.userDao().updateUser(
                    userId = updatedUser.id.toLong(),
                    name = updatedUser.name ?: "",
                    email = updatedUser.email ?: "",
                    password = updatedUser.password ?: "",
                    phone = updatedUser.phone ?: "",
                    address = updatedUser.address ?: "",
                    matricula = updatedUser.matricula ?: ""
                )
            }

            finish()
        }
    }
}
