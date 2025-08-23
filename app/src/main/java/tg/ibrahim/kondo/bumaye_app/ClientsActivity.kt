package tg.ibrahim.kondo.bumaye_app

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ClientsActivity : AppCompatActivity() {
    private lateinit var clientListRc: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients)

        // Initialisation du RecyclerView
        clientListRc = findViewById(R.id.client_list_rc)
        clientListRc.layoutManager = LinearLayoutManager(this)

        // Initialisation des clients
        ClientRepository.initializeClients()

        // Création de l'adapter classique
        val clientAdapter = ClientAdapter(ClientRepository.clients as ArrayList<Client>)
        clientListRc.adapter = clientAdapter
        clientAdapter.setOnItemClickListener(object : ClientAdapter.OnItemClickListener {
            override fun onItemClick(client: Client) {
                val fullName = "${client.lastName} ${client.firstName}"
                val orderDate = client.orderDate
                val deliveryDate = client.deliveryDate
                val amount = client.totalAmount.toString()
                val intent = Intent(this@ClientsActivity, DetailsActivity::class.java).apply {
                    putExtra("CLIENT_ID", client.id.toString())
                    putExtra("PRENOM", client.firstName)
                    putExtra("NOM", client.lastName)
                    putExtra("TELEPHONE", client.phoneNumber)

                    // Mesures
                    putExtra("E_PAULE", client.shoulder.toString())
                    putExtra("POITRINE", client.chest.toString())
                    putExtra("LONGUEUR_TAILLE", client.lengthWaist.toString())
                    putExtra("TOUR_VENTRE", client.ventralTurn.toString())
                    putExtra("HANCHES", client.hip.toString())
                    putExtra("LONGUEUR_BUSTIER", client.bodiceLength.toString())
                    putExtra("CEINTURE", client.belt.toString())
                    putExtra("LONGUEUR_JUPE", client.skirtLength.toString())
                    putExtra("LONGUEUR_ROBE", client.dressLength.toString())
                    putExtra("LONGUEUR_MANCHE", client.sleeveLength.toString())
                    putExtra("TAILLE_MANCHE", client.sleeveSize.toString())
                    putExtra("LONGUEUR_PANTALON", client.pantsLength.toString())
                    putExtra("LONGUEUR_ROBE_COURTE", client.shortDressLength.toString())
                    putExtra("TOUR_CUISSE", client.thighCircumference.toString())
                    putExtra("LONGUEUR_GENOU", client.kneeLength.toString())
                    putExtra("TOUR_GENOU", client.kneeCircumference.toString())
                    putExtra("BAS", client.bottom.toString())
                    putExtra("AUTRES_MESURES", client.otherMeasures.toString())

                    // Suivi commande
                    putExtra("DATE_COMMANDE", client.orderDate)
                    putExtra("DATE_LIVRAISON", client.deliveryDate)

                    // Paiement
                    putExtra("MONTANT_TOTAL", client.totalAmount.toString())
                    putExtra("AVANCE", client.advance.toString())
                    putExtra("RESTE", client.remainder.toString())
                }
                startActivity(intent)
                val message =
                    "Nom: $fullName\nDate commande: $orderDate\nDate livraison: $deliveryDate\nMontant: $amount"
                Toast.makeText(this@ClientsActivity, message, Toast.LENGTH_LONG).show()

                // printTv.text = message
            }
        })

        val etSearch = findViewById<EditText>(R.id.etSearch)
        val btnAddClient = findViewById<TextView>(R.id.btnAddClient)
// Clic sur "+"
        btnAddClient.setOnClickListener {
            val intent = Intent(this@ClientsActivity, FormActivity::class.java)
            startActivity(intent)
        }

// Recherche
        etSearch.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().lowercase(Locale.getDefault())

            val filtered = if (query.isEmpty()) {
                ArrayList(ClientRepository.clients)
            } else {
                ClientRepository.clients.filter {
                    it.firstName.lowercase().contains(query) || it.lastName.lowercase().contains(query)
                }.toCollection(ArrayList())
            }

            clientAdapter.updateList(filtered)
        }

        //printTv = findViewById(R.id.print_tv)
        //LOGIN ACTIVITY
        //val email = intent.getStringExtra("EMAIL")
        //val password = intent.getStringExtra("PASSWORD")

        //REGISTER ACTIVITY
        val nom = intent.getStringExtra("NOM")
        val prenom = intent.getStringExtra("PRENOM")
        val email = intent.getStringExtra("EMAIL")
        val filiere = intent.getStringExtra("FILIERE")
        val sexe = intent.getStringExtra("SEXE")
        val password = intent.getStringExtra("PASSWORD")

        //printTv.text = "Nom = $nom\nPrenom = $prenom\nEmail = $email\nFiliere = $filiere\nSexe = $sexe\nPassword = $password"
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        (clientListRc.adapter as ClientAdapter).notifyDataSetChanged()
    }

    private val REQUEST_CALL_PERMISSION = 1
    private var phoneToCall: String? = null

    fun requestCallPermission(phone: String) {
        phoneToCall = phone
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            makeCall(phone)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CALL_PERMISSION
            )
        }
    }

    private fun makeCall(phone: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = "tel:$phone".toUri()
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                phoneToCall?.let { makeCall(it) }
            } else {
                Toast.makeText(this, "Permission refusée", Toast.LENGTH_SHORT).show()
            }
        }
    }
}