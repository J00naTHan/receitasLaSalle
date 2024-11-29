package com.example.receitaslasalle

import MainActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.receitaslasalle.config.DBHelper

class LoginActivity : AppCompatActivity() {

    lateinit var username: EditText
    lateinit var pass: EditText
    lateinit var isConected: CheckBox
    lateinit var loginButton: Button
    lateinit var register: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        username = findViewById(R.id.username)
        pass = findViewById(R.id.password)
        isConected = findViewById(R.id.conected)
        loginButton = findViewById(R.id.login)
        register = findViewById(R.id.register)

        val sharedPreferences = getSharedPreferences("rememberAccount", MODE_PRIVATE)

        val savedUsername = sharedPreferences.getString("username", "")
        if (savedUsername != "") username.setText(savedUsername)

        val savedPass = sharedPreferences.getString("password", "")
        if (savedPass != "") pass.setText(savedPass)

        loginButton.setOnClickListener {
            // validar apenas para o caso dos valores passados estarem no banco de dados
            if ((username.text.toString().equals("Jonathan")) && (pass.text.toString().equals("1234"))) {

                var intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }

        register.setOnClickListener {

            loginButton.setOnClickListener {
                val usernameInput = username.text.toString()
                val passwordInput = pass.text.toString()

                // Verificar as credenciais no banco de dados
                val dbHelper = DBHelper(applicationContext)

                // Chama o método validarLogin passando o username e password
                val isValid = dbHelper.validarLogin(usernameInput, passwordInput)

                if (isValid) {
                    // Se o login for válido, vai para a MainActivity
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // Se o login for inválido, exibe uma mensagem de erro
                    Toast.makeText(applicationContext, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}