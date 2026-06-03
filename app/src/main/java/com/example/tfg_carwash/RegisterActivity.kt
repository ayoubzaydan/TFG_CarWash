package com.example.tfg_carwash

import com.example.tfg_carwash.localData.AppDatabase
import com.example.tfg_carwash.localData.UserEntity
import com.example.tfg_carwash.localData.DatabaseInstance
import com.example.tfg_carwash.utils.PasswordUtils
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var matriculaEditText: EditText

    private lateinit var errorTextView: TextView
    private lateinit var registerButton: Button

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        addressEditText = findViewById(R.id.addressEditText)
        matriculaEditText = findViewById(R.id.matriculaEditText)

        errorTextView = findViewById(R.id.errorTextView)
        registerButton = findViewById(R.id.registerButton)

        db = DatabaseInstance.getDatabase(this)

        registerButton.setOnClickListener {
            errorTextView.visibility = View.GONE

            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            val phone = phoneEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val matricula = matriculaEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                errorTextView.text = "Por favor, completa todos los campos"
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }
            // Validar email y contraseña
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                errorTextView.text = "Email no válido"
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if (password.length < 6) {
                errorTextView.text = "La contraseña debe tener al menos 6 caracteres"
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (db.userDao().getUserByEmail(email) != null) {
                errorTextView.text = "Ya existe una cuenta con ese email"
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val user = UserEntity(
                name = name,
                email = email,
                password = PasswordUtils.hash(password),
                phone = phone,
                address = address,
                matricula = matricula
            )

            val userId = db.userDao().insert(user)

            if (userId == -1L) {
                errorTextView.text = "Error al registrar usuario."
                errorTextView.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("userId", userId)
                startActivity(intent)
                finish()
            }
        }
    }
}
