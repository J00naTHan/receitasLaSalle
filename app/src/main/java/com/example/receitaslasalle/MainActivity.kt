package com.example.receitaslasalle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var username: EditText
    lateinit var pass: EditText
    lateinit var isConected: CheckBox
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        username = findViewById(R.id.username)
        pass = findViewById(R.id.password)
        isConected = findViewById(R.id.conected)
        loginButton = findViewById(R.id.login)

        val sharedPreferences = getSharedPreferences("rememberAccount", MODE_PRIVATE)

        val savedUsername = sharedPreferences.getString("username", "")
        if (savedUsername != "") username.setText(savedUsername)

        val savedPass = sharedPreferences.getString("password", "")
        if (savedPass != "") pass.setText(savedPass)

        isConected.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked == true) {
                val sharedPreferences = getSharedPreferences("rememberAccount", MODE_PRIVATE).edit()
                sharedPreferences.putString("username", username.text.toString())
                sharedPreferences.putString("password", pass.text.toString())
                sharedPreferences.apply()
            }
        }

        loginButton.setOnClickListener {
            if ((username.text.toString().equals("Jonathan")) && (pass.text.toString().equals("1234"))) {

                var intent = Intent(applicationContext, Tela2::class.java)
                startActivity(intent)
            }
        }
    }
}