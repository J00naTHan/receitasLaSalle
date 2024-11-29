import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.receitaslasalle.R
import com.example.receitaslasalle.models.Receita
import com.example.receitaslasalle.config.DBHelper

class NovaReceitaActivity : AppCompatActivity() {

    private lateinit var nomeEditText: EditText
    private lateinit var descricaoEditText: EditText
    private lateinit var chefeEditText: EditText
    private lateinit var salvarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_receita)

        nomeEditText = findViewById(R.id.editTextNome)
        descricaoEditText = findViewById(R.id.editTextDescricao)
        chefeEditText = findViewById(R.id.editTextChefe)
        salvarButton = findViewById(R.id.buttonSalvar)

        salvarButton.setOnClickListener {
            val nome = nomeEditText.text.toString()
            val descricao = descricaoEditText.text.toString()
            val chefe = chefeEditText.text.toString()

            if (nome.isNotEmpty() && descricao.isNotEmpty() && chefe.isNotEmpty()) {
                val receita = Receita(nome = nome, descricao = descricao, chefe = chefe)

                // Usando DBHelper para salvar a receita no SQLite
                val dbHelper = DBHelper(applicationContext)

                try {
                    val isSaved = dbHelper.salvarReceita(receita)

                    if (isSaved) {
                        Toast.makeText(this, "Receita salva com sucesso!", Toast.LENGTH_SHORT).show()
                        finish() // Fecha a tela após salvar
                    } else {
                        Toast.makeText(this, "Erro ao salvar receita. Tente novamente.", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Erro ao salvar receita: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
            }
        }
}