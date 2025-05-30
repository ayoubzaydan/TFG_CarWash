package com.example.tfg_carwash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.tfg_carwash.localData.DatabaseInstance
import com.example.tfg_carwash.localData.LavadoEntity
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        thread {
            DatabaseInstance.getDatabase(applicationContext).reservationDao().deleteInvalidReservations()
            inicializarLavadosSiNoExisten()
        }

        // Ir a LoginActivity después de 2 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000)
    }

    private fun inicializarLavadosSiNoExisten() {
        val db = DatabaseInstance.getDatabase(applicationContext)
        val lavadoDao = db.lavadoDao()

        val lavadosEnBD = lavadoDao.getAllLavados()
        if (lavadosEnBD.isEmpty()) {
            val lavadosIniciales = listOf(
                LavadoEntity(name = "Autoservicio", price = 10.0, description = "Lavado exterior básico", time = 50),
                LavadoEntity(name = "Servicio Completo", price = 20.0, description = "Lavado exterior e interior", time = 90),
                LavadoEntity(name = "Lavado Deluxe", price = 30.0, description = "Lavado completo con encerado", time = 120),
                LavadoEntity(name = "Lavado sin Agua", price = 15.0, description = "Lavado ecológico sin agua", time = 60),
                LavadoEntity(name = "Lavado a Vapor", price = 25.0, description = "Lavado completo con productos premium", time = 150)
            )
            lavadoDao.insertAll(lavadosIniciales)
        }
    }
}
