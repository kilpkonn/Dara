package ee.taltech.iti0213.dara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    fun onStartClicked(view: View) {
        val startGameIntent = Intent(this, GameActivity::class.java)
        startActivity(startGameIntent)
    }
}
