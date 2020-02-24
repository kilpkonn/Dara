package ee.taltech.iti0213.dara

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ee.taltech.iti0213.dara.game.constants.C


class MenuActivity : AppCompatActivity() {

    private val strategies = listOf("Simple Randomness", "Human") // TODO: Auto scan classes
    private var player1Strategy: Int = 0
    private var player2Strategy: Int = 0

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
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                player1Strategy = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        player2Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                player2Strategy = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        player1Strategy = savedInstanceState?.getInt(C.PLAYER1_STRATEGY_KEY) ?: 0
        player2Strategy = savedInstanceState?.getInt(C.PLAYER2_STRATEGY_KEY) ?: 0

        val player1 = findViewById<View>(R.id.player1)
        val player2 = findViewById<View>(R.id.player2)

        val player1Spinner = player1.findViewById<Spinner>(R.id.spinner)
        val player2Spinner = player2.findViewById<Spinner>(R.id.spinner)

        player1Spinner.setSelection(player1Strategy)
        player2Spinner.setSelection(player2Strategy)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putInt(C.PLAYER1_STRATEGY_KEY, player1Strategy)
            putInt(C.PLAYER2_STRATEGY_KEY, player2Strategy)
        }
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState)

    }

    fun onStartClicked(view: View) {
        val startGameIntent = Intent(this, GameActivity::class.java)
        val bundle = Bundle()
        bundle.putString(C.PLAYER1_STRATEGY_KEY, strategies[player1Strategy])
        bundle.putString(C.PLAYER2_STRATEGY_KEY, strategies[player2Strategy])
        startGameIntent.putExtras(bundle)
        startActivity(startGameIntent)
    }
}
