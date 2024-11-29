package com.example.receitaslasalle.config

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.receitaslasalle.models.Receita

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "recipeapp.db"
        const val DATABASE_VERSION = 1

        // Tabela de receitas
        const val TABLE_RECIPES = "recipes"
        const val COLUMN_RECIPE_ID = "id"
        const val COLUMN_RECIPE_NAME = "name"
        const val COLUMN_RECIPE_DESCRIPTION = "description"
        const val COLUMN_RECIPE_INGREDIENTS = "ingredients"
        const val COLUMN_RECIPE_PREPARATION = "preparation"

        // Tabela de usuários
        const val TABLE_USERS = "users"
        const val COLUMN_USER_ID = "username"
        const val COLUMN_USER_NAME = "name"
        const val COLUMN_USER_EMAIL = "email"
        const val COLUMN_USER_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Criação da tabela de receitas
        val createRecipesTable = """
            CREATE TABLE $TABLE_RECIPES (
                $COLUMN_RECIPE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_RECIPE_NAME TEXT,
                $COLUMN_RECIPE_DESCRIPTION TEXT,
                $COLUMN_RECIPE_INGREDIENTS TEXT,
                $COLUMN_RECIPE_PREPARATION TEXT
            )
        """
        db.execSQL(createRecipesTable)

        // Criação da tabela de usuários
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID TEXT PRIMARY KEY,
                $COLUMN_USER_NAME TEXT,
                $COLUMN_USER_EMAIL TEXT,
                $COLUMN_USER_PASSWORD TEXT
            )
        """
        db.execSQL(createUsersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Deleta as tabelas antigas
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RECIPES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")

        // Chama onCreate para recriar as tabelas com a nova estrutura
        onCreate(db)
        }
    // Adicionar este método na classe DBHelper
    fun validarLogin(username: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            DBHelper.TABLE_USERS, // Nome da tabela
            null, // Seleciona todas as colunas
            "${DBHelper.COLUMN_USER_ID} = ?", // Condição WHERE
            arrayOf(username), // Parâmetros para o WHERE
            null, // Agrupamento
            null, // Ordem
            null // Limitação
        )

        var isValid = false

        // Verifica se há um usuário encontrado
        if (cursor != null && cursor.moveToFirst()) {
            val storedPassword = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_USER_PASSWORD))

            // Aqui você pode fazer a comparação da senha, considerando se você usou criptografia
            if (password == storedPassword) {
                isValid = true
            }
        }

        cursor.close()
        db.close()
        return isValid
    }
    // Adicionar este método no DBHelper
    fun salvarReceita(receita: Receita): Boolean {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(DBHelper.COLUMN_RECIPE_NAME, receita.nome)
            put(DBHelper.COLUMN_RECIPE_DESCRIPTION, receita.descricao)
            put(DBHelper.COLUMN_RECIPE_INGREDIENTS, receita.ingredientes) // Pode ser nulo, se não tiver
            put(DBHelper.COLUMN_RECIPE_PREPARATION, receita.preparo)       // Pode ser nulo, se não tiver
        }

        // Tenta inserir a receita no banco de dados e retorna um booleano
        return try {
            val result = db.insert(DBHelper.TABLE_RECIPES, null, values)
            db.close()
            result != -1L  // Retorna true se a inserção for bem-sucedida
        } catch (e: Exception) {
            db.close()
            false  // Retorna false em caso de erro
            }
    }
    // Verificar se o usuário já existe
    fun userExists(username: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USER_NAME = ?"
        val cursor = db.rawQuery(query, arrayOf(username))

        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()

        return exists
    }

    // Registrar um novo usuário
    fun registerUser(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_NAME, username)
            put(COLUMN_USER_PASSWORD, password)
        }

        val result = db.insert(TABLE_USERS, null, values)
        db.close()

        return result != -1L // Se o ID for -1, significa que a inserção falhou
        }
    fun getAllRecipes(): List<Receita> {
        val db = readableDatabase
        val recipeList = mutableListOf<Receita>()

        val query = "SELECT * FROM $TABLE_RECIPES"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME))
                val descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_DESCRIPTION))
                val ingredientes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_INGREDIENTS))
                val preparo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_PREPARATION))

                // Cria um objeto Receita com os dados recuperados
                val receita = Receita(id, nome, descricao, ingredientes, preparo)
                recipeList.add(receita)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return recipeList
    }

}