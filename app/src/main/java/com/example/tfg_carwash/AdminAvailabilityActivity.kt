package com.example.tfg_carwash

import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class AdminAvailabilityActivity : AppCompatActivity() {

    private val availableDates = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_availability)

        // Inicializar todos los días de junio como disponibles
        initializeJuneDates()
        initializeJulyDates()

        val calendarView = findViewById<CalendarView>(R.id.calendarViewAdmin)
        val btnSaveDates = findViewById<Button>(R.id.btnSaveDates)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            if (availableDates.contains(date)) {
                availableDates.remove(date)
                Toast.makeText(this, "$date eliminado de días disponibles.", Toast.LENGTH_SHORT).show()
            } else {
                availableDates.add(date)
                Toast.makeText(this, "$date agregado como día disponible.", Toast.LENGTH_SHORT).show()
            }
        }

        btnSaveDates.setOnClickListener {
            // Aquí puedes guardar los días disponibles en una base de datos o archivo local
            Toast.makeText(this, "Días disponibles guardados: $availableDates", Toast.LENGTH_LONG).show()
        }
    }

    private fun initializeJuneDates() {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = 5 // Junio (los meses empiezan en 0)
        for (day in 1..30) {
            val date = String.format("%04d-%02d-%02d", year, month + 1, day) // Ajusta el mes al formato correcto
            availableDates.add(date)
        }
    }

    private fun initializeJulyDates() {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = 6 // Julio (los meses empiezan en 0)
        for (day in 1..31) {
            val date = String.format("%04d-%02d-%02d", year, month + 1, day)
            availableDates.add(date)
        }
    }

}