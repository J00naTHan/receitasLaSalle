package com.example.receitaslasalle.sqlite

import android.content.ContentValues
import android.content.Context
import com.example.receitaslasalle.models.Receita
import com.example.receitaslasalle.config.DBHelper

class ReceitaRepository(context: Context) {

    private val dbHelper: DBHelper = DBHelper(context)

    // Método para salvar uma receita no SQLite
    fun salvarReceita(receita: Receita, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DBHelper.COLUMN_RECIPE_NAME, receita.nome)
            put(DBHelper.COLUMN_RECIPE_DESCRIPTION, receita.descricao)
            put(DBHelper.COLUMN_RECIPE_INGREDIENTS, receita.ingredientes)
            put(DBHelper.COLUMN_RECIPE_PREPARATION, receita.preparo)
        }

        try {
            // Inserir a receita na tabela
            val result = db.insert(DBHelper.TABLE_RECIPES, null, values)

            if (result != -1L) {
                onSuccess()
            } else {
                onFailure(Exception("Falha ao salvar a receita."))
            }
        } catch (e: Exception) {
            onFailure(e)
        } finally {
            db.close()
            }
        }
}
