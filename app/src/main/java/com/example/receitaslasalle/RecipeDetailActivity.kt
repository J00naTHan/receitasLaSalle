import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.receitaslasalle.R

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        // Obter referências aos TextViews
        val recipeNameTextView: TextView = findViewById(R.id.textRecipeName)
        val recipeDescriptionTextView: TextView = findViewById(R.id.textRecipeDescription)
        val recipeIngredientsTextView: TextView = findViewById(R.id.textRecipeIngredients)

        // Obter os dados passados pela Intent
        val recipeId = intent.getIntExtra("RECIPE_ID", -1)

        // Carregar a receita do banco de dados (substituir pelo seu método de banco)
        val recipe = getRecipeById(recipeId)

        // Exibir os detalhes da receita
        recipeNameTextView.text = recipe?.name ?: "Receita não encontrada"
        recipeDescriptionTextView.text = recipe?.description ?: "Descrição não disponível"
        recipeIngredientsTextView.text = recipe?.ingredients ?: "Ingredientes não disponíveis"
    }

    // Simula o carregamento de uma receita do banco de dados
    private fun getRecipeById(id: Int): Recipe? {
        // Substituir pelo seu método real de banco de dados
        val sampleRecipes = listOf(
            Recipe(1, "Bolo de Chocolate", "Um delicioso bolo de chocolate.", "Farinha, Açúcar, Chocolate, Ovos"),
            Recipe(2, "Lasanha", "Lasanha caseira com molho especial.", "Massa, Molho, Queijo, Carne Moída")
        )
        return sampleRecipes.find { it.id == id }
    }
}

// Classe de modelo para receita (substituir pela sua implementação)
data class Recipe(val id: Int, val name: String, val description: String, val ingredients: String)
