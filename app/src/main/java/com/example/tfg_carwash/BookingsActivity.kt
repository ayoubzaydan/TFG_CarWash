package com.example.tfg_carwash

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tfg_carwash.databinding.ActivityBookingsBinding
import com.example.tfg_carwash.localData.DatabaseInstance
import com.example.tfg_carwash.localData.LavadoEntity
import com.example.tfg_carwash.localData.ReservationEntity
import androidx.appcompat.app.AlertDialog

class BookingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingsBinding
    private var userId: Int = -1
    private lateinit var lavadosDisponibles: List<LavadoEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra("userId", -1)
        if (userId == -1) {
            Toast.makeText(this, "Error: usuario no identificado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupBottomNavigation()

        Thread {
            val db = DatabaseInstance.getDatabase(this)
            val lavadoDao = db.lavadoDao()
            lavadosDisponibles = lavadoDao.getAllLavados()

            runOnUiThread {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    lavadosDisponibles.map { "${it.name} - \$${it.price} - ${it.time} min" }
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerWashType.adapter = adapter
            }
        }.start()

        // Spinner de horas
        val availableHours = listOf("10:00", "11:00", "12:00", "13:00", "16:00", "17:00")
        val hourAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, availableHours)
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTimeSlot.adapter = hourAdapter

        binding.btnReserve.setOnClickListener {
            val selectedIndex = binding.spinnerWashType.selectedItemPosition
            if (!::lavadosDisponibles.isInitialized || selectedIndex !in lavadosDisponibles.indices) {
                Toast.makeText(this, "Selecciona un tipo de lavado válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedWash = lavadosDisponibles[selectedIndex]
            val selectedDate = getSelectedDate()
            val selectedTimeSlot = binding.spinnerTimeSlot.selectedItem?.toString() ?: ""

            if (selectedDate.isNotEmpty() && selectedTimeSlot.isNotEmpty()) {
                Thread {
                    val db = DatabaseInstance.getDatabase(this)
                    val reservationDao = db.reservationDao()

                    val existing = reservationDao.getReservationByDateTime(selectedDate, selectedTimeSlot)
                    if (existing == null) {
                        val reservation = ReservationEntity(
                            userId = userId,
                            lavadoId = selectedWash.id,
                            date = selectedDate,
                            time = selectedTimeSlot
                        )
                        reservationDao.insert(reservation)

                        runOnUiThread {
                            val intent = Intent(this, ActivityHistory::class.java)
                            intent.putExtra("userId", userId)
                            startActivity(intent)
                        }
                    } else {
                        runOnUiThread {
                            AlertDialog.Builder(this)
                                .setTitle("Reserva no disponible")
                                .setMessage("La hora seleccionada ya está reservada. Por favor, elige otra hora.")
                                .setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }
                                .show()
                        }                    }
                }.start()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            }
        }

        val lavadonombre = intent.getStringExtra("lavadoPreseleccionado")
        if (lavadonombre != null) {
            val selectedWash = lavadosDisponibles.find { it.name == lavadonombre }
            if (selectedWash != null) {
                val index = lavadosDisponibles.indexOf(selectedWash)
                binding.spinnerWashType.setSelection(index)
            } else {
                Toast.makeText(this, "Lavado preseleccionado no encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getSelectedDate(): String {
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month + 1
        val year = binding.datePicker.year
        return String.format("%04d-%02d-%02d", year, month, day)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.menu.findItem(R.id.nav_bookings).isChecked = true

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val targetIntent = when (item.itemId) {
                R.id.nav_home -> Intent(this, MainActivity::class.java)
                R.id.nav_services -> Intent(this, ServicesActivity::class.java)
                R.id.nav_message -> Intent(this, ActivityHistory::class.java)
                R.id.nav_account -> Intent(this, ProfileActivity::class.java)
                R.id.nav_bookings -> null
                else -> null
            }

            targetIntent?.putExtra("userId", userId)?.let {
                startActivity(it)
                true
            } ?: true
        }
    }
}
