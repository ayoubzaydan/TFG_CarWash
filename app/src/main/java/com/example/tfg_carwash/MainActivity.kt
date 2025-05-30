package com.example.tfg_carwash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tfg_carwash.databinding.ActivityMainBinding
import com.example.tfg_carwash.localData.DatabaseInstance

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentUserId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Recuperar el ID desde el intent
        currentUserId = intent.getIntExtra("userId", -1)
        if (currentUserId == -1) {
            Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // ✅ Recuperar usuario y mostrar saludo
        val db = DatabaseInstance.getDatabase(this)
        val user = db.userDao().getUserById(currentUserId.toLong())
        if (user != null) {
            binding.tvWelcome.text = "Bienvenid@ de nuevo, ${user.name}!"
        } else {
            binding.tvWelcome.text = "Bienvenid@ de nuevo!"
            Toast.makeText(this, "Error al cargar datos de usuario", Toast.LENGTH_SHORT).show()
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val intent = when (item.itemId) {
                R.id.nav_home -> null
                R.id.nav_bookings -> Intent(this, BookingsActivity::class.java)
                R.id.nav_services -> Intent(this, ServicesActivity::class.java)
                R.id.nav_message -> Intent(this, ActivityHistory::class.java)
                R.id.nav_account -> Intent(this, ProfileActivity::class.java)
                else -> null
            }

            intent?.putExtra("userId", currentUserId)?.let {
                startActivity(it)
                true
            } ?: true
        }

        binding.bottomNavigation.selectedItemId = R.id.nav_home
    }
}
