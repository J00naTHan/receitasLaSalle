import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var searchBar: EditText
    private lateinit var fabAddRecipe: FloatingActionButton

    private val dataList = mutableListOf(
        "Receita de Bolo",
        "Receita de Lasanha",
        "Receita de Salada",
        "Receita de Pudim"
    )

    private val filteredList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)

        recyclerView = findViewById(R.id.recyclerView)
        searchBar = findViewById(R.id.search_bar)
        fabAddRecipe = findViewById(R.id.fab_add_recipe)

        filteredList.addAll(dataList)

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

        // Botão para adicionar nova receita
        fabAddRecipe.setOnClickListener {
            Toast.makeText(this, "Adicionar nova receita", Toast.LENGTH_SHORT).show()
            // Adicione aqui a lógica para abrir uma tela ou diálogo para inserir receitas
        }
    }

    private fun filterList(query: String) {
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(dataList)
        } else {
            filteredList.addAll(dataList.filter { it.contains(query, ignoreCase = true) })
        }
        adapter.notifyDataSetChanged()
    }
}
