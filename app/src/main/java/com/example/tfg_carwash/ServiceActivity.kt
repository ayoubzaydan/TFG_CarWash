package com.example.tfg_carwash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tfg_carwash.databinding.ActivityServicesBinding

class ServicesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServicesBinding
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra("userId", -1)
        if (userId == -1) {
            finish()
            return
        }

        setupBottomNavigation()
        setupCardViewListeners()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val intent = when (item.itemId) {
                R.id.nav_home -> Intent(this, MainActivity::class.java)
                R.id.nav_bookings -> Intent(this, BookingsActivity::class.java)
                R.id.nav_services -> return@setOnItemSelectedListener true
                R.id.nav_message -> Intent(this, ActivityHistory::class.java)
                R.id.nav_account -> Intent(this, ProfileActivity::class.java)
                else -> null
            }

            intent?.putExtra("userId", userId)?.let {
                startActivity(it)
                true
            } ?: false
        }

        binding.bottomNavigation.selectedItemId = R.id.nav_services
    }

    private fun setupCardViewListeners() {
        val activities = listOf(
            binding.cardSelfServe to SelfServeActivity::class.java,
            binding.cardFullService to FullServiceActivity::class.java,
            binding.cardDeluxeWash to DeluxeWashActivity::class.java,
            binding.cardWaterless to WaterlessWashActivity::class.java,
            binding.cardSteamWash to SteamWashActivity::class.java,
        )

        for ((card, activityClass) in activities) {
            card.setOnClickListener {
                val intent = Intent(this, activityClass)
                intent.putExtra("userId", userId)
                startActivity(intent)
            }
        }
    }
}
