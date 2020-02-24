package ee.taltech.iti0213.dara

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import ee.taltech.iti0213.dara.constants.C

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val player1Strategy = intent?.extras?.getString(C.PLAYER1_STRATEGY_KEY)
        val player2Strategy = intent?.extras?.getString(C.PLAYER2_STRATEGY_KEY)

        val player1 = findViewById<View>(R.id.player1)
        val player2 = findViewById<View>(R.id.player2)

        player1.findViewById<TextView>(R.id.txt_player).text = player1Strategy
        player2.findViewById<TextView>(R.id.txt_player).text = player2Strategy
    }
}
