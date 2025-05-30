package com.example.tfg_carwash

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.tfg_carwash.databinding.ActivityServiciocompletoBinding

class FullServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiciocompletoBinding
    private var userId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiciocompletoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra("userId", -1)
        if (userId == -1) {
            finish()
            return
        }
        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val intent = when (item.itemId) {
                R.id.nav_home -> Intent(this, MainActivity::class.java)
                R.id.nav_bookings -> Intent(this, BookingsActivity::class.java)
                R.id.nav_services -> Intent (this, ServicesActivity::class.java)
                R.id.nav_message -> Intent(this, ActivityHistory::class.java)
                R.id.nav_account -> Intent(this, ProfileActivity::class.java)
                else -> null
            }

            intent?.putExtra("userId", userId)?.let {
                startActivity(it)
                true
            } ?: false
        }

    }

}
