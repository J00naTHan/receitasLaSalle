package com.example.receitaslasalle.models

data class Receita(
    val id: Int? = null,             // ID único (gerado automaticamente pelo banco)
    val nome: String = "",           // Nome da receita
    val descricao: String = "",      // Descrição da receita
    val ingredientes: String = "",   // Ingredientes da receita
    val preparo: String = "",
    val chefe: String = ""
)