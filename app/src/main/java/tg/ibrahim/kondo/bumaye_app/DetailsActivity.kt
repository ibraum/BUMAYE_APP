package tg.ibrahim.kondo.bumaye_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var clientId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        clientId = intent.getStringExtra("CLIENT_ID")?.toInt()

        val btnBack: ImageButton = findViewById(R.id.btnBack)
        val btnUpdate: ImageButton = findViewById(R.id.btnUpdate)
        val btnDelete: ImageButton = findViewById(R.id.btnDelete)

        btnBack.setOnClickListener { finish() }

        btnUpdate.setOnClickListener {
            val intentUpdateClient = Intent(this, UpdateClientActivity::class.java)
            intentUpdateClient.putExtra("clientId", clientId)
            startActivity(intentUpdateClient)
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Supprimer le client")
                .setMessage("Voulez-vous vraiment supprimer ce client ?")
                .setPositiveButton("Oui") { _, _ ->
                    clientId?.let { id ->
                        ClientRepository.clients.removeAll { it.id == id }
                        Toast.makeText(this, "Client supprimé $id", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                .setNegativeButton("Annuler", null)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshClient()
    }

    private fun refreshClient() {
        val client = clientId?.let { id -> ClientRepository.clients.find { it.id == id } }
        if (client != null) {
            displayClient(client)
        } else {
            // si le client a été supprimé par ex.
            Toast.makeText(this, "Client introuvable", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun displayClient(client: Client) {
        // === Identité ===
        findViewById<TextView>(R.id.tvFirstNameValue).text = client.firstName
        findViewById<TextView>(R.id.tvLastNameValue).text = client.lastName
        findViewById<TextView>(R.id.tvPhoneValue).text = client.phoneNumber

        // === Mesures ===
        findViewById<TextView>(R.id.tvShoulderValue).text = client.shoulder.toString()
        findViewById<TextView>(R.id.tvChestValue).text = client.chest.toString()
        findViewById<TextView>(R.id.tvLengthWaistValue).text = client.lengthWaist.toString()
        findViewById<TextView>(R.id.tvVentralTurnValue).text = client.ventralTurn.toString()
        findViewById<TextView>(R.id.tvHipValue).text = client.hip.toString()
        findViewById<TextView>(R.id.tvBodiceLengthValue).text = client.bodiceLength.toString()
        findViewById<TextView>(R.id.tvBeltValue).text = client.belt.toString()
        findViewById<TextView>(R.id.tvSkirtLengthValue).text = client.skirtLength.toString()
        findViewById<TextView>(R.id.tvDressLengthValue).text = client.dressLength.toString()
        findViewById<TextView>(R.id.tvSleeveLengthValue).text = client.sleeveLength.toString()
        findViewById<TextView>(R.id.tvSleeveSizeValue).text = client.sleeveSize.toString()
        findViewById<TextView>(R.id.tvPantsLengthValue).text = client.pantsLength.toString()
        findViewById<TextView>(R.id.tvShortDressLengthValue).text = client.shortDressLength.toString()
        findViewById<TextView>(R.id.tvThighCircumferenceValue).text = client.thighCircumference.toString()
        findViewById<TextView>(R.id.tvKneeLengthValue).text = client.kneeLength.toString()
        findViewById<TextView>(R.id.tvKneeCircumferenceValue).text = client.kneeCircumference.toString()
        findViewById<TextView>(R.id.tvBottomValue).text = client.bottom.toString()
        findViewById<TextView>(R.id.tvOtherMeasuresValue).text = client.otherMeasures.toString()

        // === Suivi commande ===
        findViewById<TextView>(R.id.tvOrderDateValue).text = client.orderDate
        findViewById<TextView>(R.id.tvDeliveryDateValue).text = client.deliveryDate

        // === Paiement ===
        findViewById<TextView>(R.id.tvTotalAmountValue).text = client.totalAmount.toString()
        findViewById<TextView>(R.id.tvAdvanceValue).text = client.advance.toString()
        findViewById<TextView>(R.id.tvRemainderValue).text = client.remainder.toString()
    }
}
