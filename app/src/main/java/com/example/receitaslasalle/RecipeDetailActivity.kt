import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.receitaslasalle.R
import com.example.receitaslasalle.models.Receita

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var recipeNameTextView: TextView
    private lateinit var recipeDescriptionTextView: TextView
    private lateinit var recipeChefTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        // Obter referências aos TextViews
        recipeNameTextView = findViewById(R.id.textRecipeName)
        recipeDescriptionTextView = findViewById(R.id.textRecipeDescription)
        recipeChefTextView = findViewById(R.id.textRecipeChef)

        // Recuperar os dados passados via Intent
        val recipeId = intent.getStringExtra("RECIPE_ID") ?: "ID não disponível"
        val recipeName = intent.getStringExtra("RECIPE_NAME") ?: "Nome não disponível"
        val recipeDescription = intent.getStringExtra("RECIPE_DESCRIPTION") ?: "Descrição não disponível"
        val recipeChef = intent.getStringExtra("RECIPE_CHEF") ?: "Chefe não disponível"

        // Exibir os dados nas TextViews
        recipeNameTextView.text = recipeName
        recipeDescriptionTextView.text = recipeDescription
        recipeChefTextView.text = recipeChef

        // Carregar a receita do banco de dados (substitua pela sua consulta real ao banco)
        // Por exemplo, usando o método de banco getRecipeById() para buscar os detalhes completos.
        // Exemplo de código fictício, apenas para ilustrar:
        val recipe = getRecipeById(recipeId.toInt())

        // Exibir mais detalhes se necessário
        recipe?.let {
            recipeNameTextView.text = it.nome
            recipeDescriptionTextView.text = it.descricao
            recipeChefTextView.text = it.chefe
        }
    }

    // Simula o carregamento de uma receita do banco de dados
    private fun getRecipeById(id: Int): Receita? {
        // Aqui você deve substituir por uma consulta real ao banco SQLite
        return Receita(id = id, nome = "Exemplo de Receita", descricao = "Descrição da Receita", chefe = "Chef Exemplo")
        }
}