package com.example.tfg_carwash

import com.example.tfg_carwash.localData.AppDatabase
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.tfg_carwash.model.UserModel
import com.example.tfg_carwash.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var db: AppDatabase

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "carwash-db"
        ).allowMainThreadQueries().build()

        // Obtener ID del usuario
        userId = intent.getIntExtra("userId", -1)
        if (userId == -1) {
            userId = getSharedPreferences("session", MODE_PRIVATE).getInt("userId", -1)
        }


        cargarDatosUsuario()

        // Lanzar actividad de edición
        binding.btnEditProfile.setOnClickListener {
            val user = db.userDao().getUserById(userId.toLong())
            user?.let {
                val userModel = UserModel(
                    id = it.id,
                    name = it.name,
                    email = it.email,
                    password = it.password,
                    phone = it.phone,
                    address = it.address,
                    matricula = it.matricula
                )
                val intent = Intent(this, EditProfileActivity::class.java)
                intent.putExtra("user", userModel)
                startActivity(intent)
            }
        }

        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        // ✅ Recargar perfil por si se editó
        if (userId != -1) {
            cargarDatosUsuario()
        } else {
            Toast.makeText(this, "Error al cargar el perfil", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun cargarDatosUsuario() {
        val user = db.userDao().getUserById(userId.toLong())
        user?.let {
            binding.profileName.text = "Nombre: ${it.name}"
            binding.profileEmail.text = "Email: ${it.email}"
            binding.profilePhone.text = "Número: ${it.phone}"
            binding.profileAddress.text = "Dirección: ${it.address}"
            binding.profileMatricula.text = "Matricula: ${it.matricula}"
        } ?: run {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val intent = when (item.itemId) {
                R.id.nav_home -> Intent(this, MainActivity::class.java)
                R.id.nav_bookings -> Intent(this, BookingsActivity::class.java)
                R.id.nav_services -> Intent(this, ServicesActivity::class.java)
                R.id.nav_message -> Intent(this, ActivityHistory::class.java)
                R.id.nav_account -> return@setOnItemSelectedListener true
                else -> null
            }

            intent?.putExtra("userId", userId.toInt())?.let {
                startActivity(it)
                true
            } ?: false
        }

        binding.bottomNavigation.selectedItemId = R.id.nav_account
    }
}
