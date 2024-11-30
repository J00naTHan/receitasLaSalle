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

    // Componentes da interface
    private lateinit var username: EditText
    private lateinit var pass: EditText
    private lateinit var isConected: CheckBox
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Configurar padding para bordas
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar componentes
        username = findViewById(R.id.username)
        pass = findViewById(R.id.password)
        isConected = findViewById(R.id.conected)
        loginButton = findViewById(R.id.login)
        registerButton = findViewById(R.id.register)

        // Recuperar informações salvas no SharedPreferences
        val sharedPreferences = getSharedPreferences("rememberAccount", MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("username", "")
        val savedPassword = sharedPreferences.getString("password", "")

        username.setText(savedUsername ?: "")
        pass.setText(savedPassword ?: "")

        // Configurar botão de login
        loginButton.setOnClickListener {
            val usernameInput = username.text.toString().trim()
            val passwordInput = pass.text.toString().trim()

            if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            } else {
                validateLogin(usernameInput, passwordInput)
            }
        }

        // Configurar botão de registro
        registerButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            intent.putExtra("username", username.text.toString())
            intent.putExtra("password", pass.text.toString())
            startActivity(intent)
        }
    }

    /**
     * Valida as credenciais no banco de dados.
     */
    private fun validateLogin(usernameInput: String, passwordInput: String) {
        val dbHelper = DBHelper(applicationContext)
        val db = dbHelper.readableDatabase

        try {
            // Consulta no banco de dados
            val cursor = db.query(
                DBHelper.TABLE_USERS, // Nome da tabela
                arrayOf(DBHelper.COLUMN_USER_PASSWORD), // Seleciona a coluna de senha
                "${DBHelper.COLUMN_USER_NAME} = ?", // Condição WHERE para username
                arrayOf(usernameInput), // Parâmetro do WHERE
                null,
                null,
                null
            )

            // Verifica se o cursor tem resultados
            if (cursor.moveToFirst()) {
                val passwordIndex = cursor.getColumnIndex(DBHelper.COLUMN_USER_PASSWORD)
                if (passwordIndex != -1) {
                    val storedPassword = cursor.getString(passwordIndex)

                    // Comparação das senhas
                    if (storedPassword == passwordInput) {
                        // Login válido, redireciona para MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Senha inválida
                        Toast.makeText(this, "Senha inválida.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Erro ao acessar a coluna de senha.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Usuário não encontrado
                Toast.makeText(this, "Usuário não encontrado.", Toast.LENGTH_SHORT).show()
            }

            cursor.close() // Fechando o cursor
        } catch (e: Exception) {
            // Tratamento de exceção
            e.printStackTrace()
            Toast.makeText(this, "Erro ao acessar o banco de dados: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            db.close() // Fechando o banco de dados
        }
    }

}
