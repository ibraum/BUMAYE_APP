package tg.ibrahim.kondo.bumaye_app

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var addClient: LinearLayout
    private lateinit var viewClients: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addClient = findViewById<LinearLayout>(R.id.add_button)
        viewClients = findViewById<LinearLayout>(R.id.view_list)

        addClient.setOnClickListener {
            var intent = Intent(this@MainActivity, FormActivity::class.java)
            startActivity(intent)
        }

        viewClients.setOnClickListener {
            var intent = Intent(this@MainActivity, ClientsActivity::class.java)
            startActivity(intent)
        }
    }
}