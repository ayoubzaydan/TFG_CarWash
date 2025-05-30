package com.example.tfg_carwash

import ReservationAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg_carwash.databinding.ActivityHistoryBinding
import com.example.tfg_carwash.localData.DatabaseInstance
import com.example.tfg_carwash.localData.ReservationWithLavado

class ActivityHistory : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var recyclerView: RecyclerView
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra("userId", -1)
        if (userId == -1) {
            Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        recyclerView = binding.recyclerViewHistory
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadReservations()
        setupBottomNavigation()
    }

    private fun loadReservations() {
        val reservationDao = DatabaseInstance.getDatabase(this).reservationDao()
        val reservations: List<ReservationWithLavado> = reservationDao.getReservationsWithLavado(userId)
        recyclerView.adapter = ReservationAdapter(reservations)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val intent = when (item.itemId) {
                R.id.nav_home -> Intent(this, MainActivity::class.java)
                R.id.nav_bookings -> Intent(this, BookingsActivity::class.java)
                R.id.nav_services -> Intent(this, ServicesActivity::class.java)
                R.id.nav_message -> return@setOnItemSelectedListener true
                R.id.nav_account -> Intent(this, ProfileActivity::class.java)
                else -> null
            }

            intent?.putExtra("userId", userId)?.let {
                startActivity(it)
                true
            } ?: false
        }

        binding.bottomNavigation.selectedItemId = R.id.nav_message
    }
}
