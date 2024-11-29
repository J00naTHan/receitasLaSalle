package com.example.receitaslasalle

data class User(
    val username: String = "",      // Nome de usuário (identificador único)
    val name: String = "",          // Nome completo do usuário
    val email: String = "",         // E-mail do usuário
    val password: String = ""       // Senha do usuário (pode ser criptografada)
)
