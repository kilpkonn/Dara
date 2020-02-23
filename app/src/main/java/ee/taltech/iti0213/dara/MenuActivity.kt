package ee.taltech.iti0213.dara

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MenuActivity : AppCompatActivity() {

    private val strategies = listOf("Simple Randomness", "Human") // TODO: Auto scan classes
    private var player1Strategy: String = strategies[0]
    private var player2Strategy: String = strategies[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val player1 = findViewById<View>(R.id.player1)
        val player2 = findViewById<View>(R.id.player2)

        player1.findViewById<TextView>(R.id.txt_playerName).text = getString(R.string.player1)
        player2.findViewById<TextView>(R.id.txt_playerName).text = getString(R.string.player2)

        val player1Spinner = player1.findViewById<Spinner>(R.id.spinner)
        val player2Spinner = player2.findViewById<Spinner>(R.id.spinner)

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            strategies
        )

        player1Spinner.adapter = adapter
        player2Spinner.adapter = adapter

        player1Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                player1Strategy = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        player2Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                player2Strategy = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    fun onStartClicked(view: View) {
        val startGameIntent = Intent(this, GameActivity::class.java)
        startActivity(startGameIntent)
    }
}
