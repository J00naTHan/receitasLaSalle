import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receitaslasalle.R

class RecipeAdapter(private val recipes: List<String>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        // Inflar o layout do item customizado
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.textView.text = recipes[position].nome

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NovaReceitaActivity::class.java)
            intent.putExtra("RECIPE_NAME", recipes[position].nome)
            intent.putExtra("RECIPE_DESCRIPTION", recipes[position].descricao)
            intent.putExtra("RECIPE_CHEF", recipes[position].chefe)
            holder.itemView.context.startActivity(intent)
        }
    }





    override fun getItemCount(): Int {
        return recipes.size
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Associar a TextView do layout customizado
        val textView: TextView = itemView.findViewById(R.id.textRecipeName)
    }
}
