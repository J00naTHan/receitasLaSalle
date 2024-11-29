package com.example.receitaslasalle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.receitaslasalle.config.DBHelper

class Register : AppCompatActivity() {

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var confirmPasswordInput: EditText
    lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        registerButton = findViewById(R.id.registerButton)

        // Pega os dados enviados do Login
        val usernameFromLogin = intent.getStringExtra("username")
        val passwordFromLogin = intent.getStringExtra("password")

        // Preenche os campos com os valores enviados (se existirem)
        usernameInput.setText(usernameFromLogin)
        passwordInput.setText(passwordFromLogin)

        registerButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            // Verificar se todos os campos estão preenchidos
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show()
            } else {
                // Verificar se o usuário já existe no banco de dados
                val dbHelper = DBHelper(this)
                if (dbHelper.userExists(username)) {
                    Toast.makeText(this, "Usuário já existe.", Toast.LENGTH_SHORT).show()
                } else {
                    // Salvar o usuário no banco de dados
                    val success = dbHelper.registerUser(username, password)
                    if (success) {
                        Toast.makeText(this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show()

                        // Retorna para a tela de login
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.putExtra("username", username)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Erro ao registrar o usuário.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            }
        }
}