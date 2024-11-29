import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receitaslasalle.R
import com.example.receitaslasalle.models.Receita
import com.example.receitaslasalle.config.DBHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var searchBar: EditText
    private lateinit var fabAddRecipe: FloatingActionButton

    private lateinit var dbHelper: DBHelper
    private var recipesList = mutableListOf<Receita>()
    private var filteredList = mutableListOf<Receita>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchBar = findViewById(R.id.search_bar)
        fabAddRecipe = findViewById(R.id.fab_add_recipe)

        dbHelper = DBHelper(this)  // Inicializa o DBHelper

        // Carregar todas as receitas do banco de dados
        loadRecipes()

        // Configurar o RecyclerView
        adapter = RecipeAdapter(filteredList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Configurar barra de pesquisa
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterList(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Botão flutuante para adicionar receitas
        fabAddRecipe.setOnClickListener {
            // Lógica para abrir uma nova Activity para adicionar receita
            val intent = Intent(this, NovaReceitaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadRecipes() {
        // Buscar todas as receitas do banco de dados

        val recipes = dbHelper.getAllRecipes()
        recipesList.clear()
        recipesList.addAll(recipes)
        filteredList.clear()
        filteredList.addAll(recipesList)
        adapter.notifyDataSetChanged()
    }

    private fun filterList(query: String) {
        // Filtrar receitas com base na consulta
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(recipesList)
        } else {
            filteredList.addAll(recipesList.filter { it.nome.contains(query, ignoreCase = true) })
        }
        adapter.notifyDataSetChanged()
        }
}