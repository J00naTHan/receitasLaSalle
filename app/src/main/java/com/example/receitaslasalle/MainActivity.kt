package com.example.receitaslasalle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)

        // Criar uma lista de objetos Recipe em vez de Strings
        val dataList = listOf(
            Recipe(name = "Lasanha", description = "Deliciosa lasanha de carne."),
            Recipe(name = "Risoto", description = "Risoto tradicional."),
            Recipe(name = "Strogonoff", description = "Clássico strogonoff de frango." ),
            Recipe(name = "Pizza", description = "Pizza com borda recheada."),
            Recipe(name = "Risoto de camarão", description = "Risoto com camarões frescos." )
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(dataList)
    }
}
