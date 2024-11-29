package com.example.receitaslasalle.firebase

import com.example.receitaslasalle.models.Receita
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReceitaRepository {

    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("receitas")

    // Método para salvar uma receita no Firebase
    fun salvarReceita(receita: Receita, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        // Gerar um ID único para a receita
        val receitaId = database.push().key

        if (receitaId != null) {
            val receitaComId = receita.copy(id = receitaId)

            // Salvar a receita no Firebase
            database.child(receitaId).setValue(receitaComId)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        } else {
            onFailure(Exception("Falha ao gerar ID para a receita."))
        }
    }
}
