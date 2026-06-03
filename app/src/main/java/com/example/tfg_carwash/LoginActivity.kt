package com.example.tfg_carwash

import com.example.tfg_carwash.localData.AppDatabase
import com.example.tfg_carwash.localData.DatabaseInstance
import com.example.tfg_carwash.utils.PasswordUtils
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var errorTextView: TextView
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        errorTextView = findViewById(R.id.errorTextView)

        db = DatabaseInstance.getDatabase(this)

        loginButton.setOnClickListener {
            errorTextView.visibility = View.GONE

            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                errorTextView.text = "Por favor, completa todos los campos"
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }

            // ✅ Validar email y contraseña
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


            // ✅ Buscar usuario por email (asegúrate de tener este método en UserDao)
            val user = db.userDao().getUserByEmail(email)
            val hashedPassword = PasswordUtils.hash(password)
            val isValidPassword = user != null && (user.password == password || user.password == hashedPassword)

            if (isValidPassword) {
                // ✅ Guardar sesión
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("userId", user.id)
                startActivity(intent)
                finish()
            } else {
                errorTextView.text = "Usuario o contraseña incorrectos"
                errorTextView.visibility = View.VISIBLE
            }
        }

        val registerText = findViewById<TextView>(R.id.registerText)
        registerText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
