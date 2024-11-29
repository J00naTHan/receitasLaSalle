package com.example.receitaslasalle.models

data class Receita(
    val id: String? = null,       // ID único (gerado pelo Firebase)
    val nome: String = "",        // Nome da receita
    val descricao: String = "",   // Descrição da receita
    val chefe: String = ""        // Nome do chefe
)
