package tg.ibrahim.kondo.bumaye_app

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etOrderDate: EditText
    private lateinit var etDeliveryDate: EditText
    private lateinit var etTotalAmount: EditText
    private lateinit var etAdvance: EditText
    private lateinit var etRemainder: EditText
    private lateinit var btnSaveClient: Button

    // Mesures
    private lateinit var etShoulder: EditText
    private lateinit var etChest: EditText
    private lateinit var etLengthWaist: EditText
    private lateinit var etVentralTurn: EditText
    private lateinit var etHip: EditText
    private lateinit var etBodiceLength: EditText
    private lateinit var etBelt: EditText
    private lateinit var etSkirtLength: EditText
    private lateinit var etDressLength: EditText
    private lateinit var etSleeveLength: EditText
    private lateinit var etSleeveSize: EditText
    private lateinit var etPantsLength: EditText
    private lateinit var etShortDressLength: EditText
    private lateinit var etThighCircumference: EditText
    private lateinit var etKneeLength: EditText
    private lateinit var etKneeCircumference: EditText
    private lateinit var etBottom: EditText
    private lateinit var etOtherMeasures: EditText

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var orderDateCalendar = Calendar.getInstance()
    private var deliveryDateCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        initViews()
        setupDatePickers()
        setupCalculateRemainder()
        setupSaveButton()
    }

    private fun initViews() {
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etOrderDate = findViewById(R.id.etOrderDate)
        etDeliveryDate = findViewById(R.id.etDeliveryDate)
        etTotalAmount = findViewById(R.id.etTotalAmount)
        etAdvance = findViewById(R.id.etAdvance)
        etRemainder = findViewById(R.id.etRemainder)
        btnSaveClient = findViewById(R.id.btnSaveClient)

        etShoulder = findViewById(R.id.etShoulder)
        etChest = findViewById(R.id.etChest)
        etLengthWaist = findViewById(R.id.etLengthWaist)
        etVentralTurn = findViewById(R.id.etVentralTurn)
        etHip = findViewById(R.id.etHip)
        etBodiceLength = findViewById(R.id.etBodiceLength)
        etBelt = findViewById(R.id.etBelt)
        etSkirtLength = findViewById(R.id.etSkirtLength)
        etDressLength = findViewById(R.id.etDressLength)
        etSleeveLength = findViewById(R.id.etSleeveLength)
        etSleeveSize = findViewById(R.id.etSleeveSize)
        etPantsLength = findViewById(R.id.etPantsLength)
        etShortDressLength = findViewById(R.id.etShortDressLength)
        etThighCircumference = findViewById(R.id.etThighCircumference)
        etKneeLength = findViewById(R.id.etKneeLength)
        etKneeCircumference = findViewById(R.id.etKneeCircumference)
        etBottom = findViewById(R.id.etBottom)
        etOtherMeasures = findViewById(R.id.etOtherMeasures)
    }

    private fun setupDatePickers() {
        etOrderDate.setOnClickListener { showDatePicker(orderDateCalendar, etOrderDate) }
        etDeliveryDate.setOnClickListener { showDatePicker(deliveryDateCalendar, etDeliveryDate) }
    }

    private fun showDatePicker(calendar: Calendar, editText: EditText) {
        val dpd = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                editText.setText(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dpd.show()
    }

    private fun setupCalculateRemainder() {
        etTotalAmount.doOnTextChanged { text, _, _, _ ->
            calculateRemainder()
        }

        etAdvance.doOnTextChanged { text, _, _, _ ->
            calculateRemainder()
        }
    }

    private fun calculateRemainder() {
        val total = etTotalAmount.text.toString().toDoubleOrNull() ?: 0.0
        val advance = etAdvance.text.toString().toDoubleOrNull() ?: 0.0
        if (total >= 0 && advance >= 0) {
            etRemainder.setText(String.format("%.2f", total - advance))
        } else {
            etRemainder.setText("")
        }
    }

    private fun setupSaveButton() {
        btnSaveClient.setOnClickListener {
            if (validateForm()) {
                val client = createClientFromForm()
                ClientRepository.clients.add(0, client)
                Log.d("FormActivity", "Clients totaux : ${ClientRepository.clients.size}")
                print("hguergueiguigurieguiruivfguiergvuiergvuveguieguiruigeugruie" + client.toString())
                Toast.makeText(this, "${ClientRepository.clients.size}" +client.toString(), Toast.LENGTH_LONG).show()
                finish() // ferme l'activité
            }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        if (etFirstName.text.toString().trim().isEmpty()) {
            etFirstName.error = "Prénom obligatoire"
            etFirstName.requestFocus()
            valid = false
        }

        if (etLastName.text.toString().trim().isEmpty()) {
            etLastName.error = "Nom obligatoire"
            etLastName.requestFocus()
            valid = false
        }

        if (!etPhoneNumber.text.toString().matches(Regex("^[0-9]{8,12}$"))) {
            etPhoneNumber.error = "Numéro invalide (8 à 12 chiffres)"
            etPhoneNumber.requestFocus()
            valid = false
        }

        if (etOrderDate.text.isEmpty()) {
            etOrderDate.error = "Selectionnez une date"
            valid = false
        }

        if (etDeliveryDate.text.isEmpty()) {
            etDeliveryDate.error = "Selectionnez une date"
            valid = false
        }

        if (deliveryDateCalendar.before(orderDateCalendar)) {
            etDeliveryDate.error = "Date de livraison doit être ≥ commande"
            valid = false
        }

        val total = etTotalAmount.text.toString().toDoubleOrNull()
        val advance = etAdvance.text.toString().toDoubleOrNull()
        if (total == null || total < 0) {
            etTotalAmount.error = "Montant total invalide"
            etTotalAmount.requestFocus()
            valid = false
        }
        if (advance == null || advance < 0) {
            etAdvance.error = "Avance invalide"
            etAdvance.requestFocus()
            valid = false
        }

        if ((total ?: 0.0) < (advance ?: 0.0)) {
            etAdvance.error = "L'avance n'est pas supérieure au total"
            etAdvance.requestFocus()
            valid = false
        }

        return valid
    }

    private fun createClientFromForm(): Client {
        var totalAmount = etTotalAmount.text.toString().toDoubleOrNull() ?: 0.0
        var advance = etAdvance.text.toString().toDoubleOrNull() ?: 0.0
        return Client(
            id = ClientRepository.nextId(),
            firstName = etFirstName.text.toString().trim(),
            lastName = etLastName.text.toString().trim(),
            phoneNumber = etPhoneNumber.text.toString().trim(),
            orderDate = etOrderDate.text.toString(),
            deliveryDate = etDeliveryDate.text.toString(),
            totalAmount = totalAmount,
            advance = advance,
            remainder = (totalAmount - advance),
            shoulder = etShoulder.text.toString().toFloatOrNull() ?: 0f,
            chest = etChest.text.toString().toFloatOrNull() ?: 0f,
            lengthWaist = etLengthWaist.text.toString().toFloatOrNull() ?: 0f,
            ventralTurn = etVentralTurn.text.toString().toFloatOrNull() ?: 0f,
            hip = etHip.text.toString().toFloatOrNull() ?: 0f,
            bodiceLength = etBodiceLength.text.toString().toFloatOrNull() ?: 0f,
            belt = etBelt.text.toString().toFloatOrNull() ?: 0f,
            skirtLength = etSkirtLength.text.toString().toFloatOrNull() ?: 0f,
            dressLength = etDressLength.text.toString().toFloatOrNull() ?: 0f,
            sleeveLength = etSleeveLength.text.toString().toFloatOrNull() ?: 0f,
            sleeveSize = etSleeveSize.text.toString().toFloatOrNull() ?: 0f,
            pantsLength = etPantsLength.text.toString().toFloatOrNull() ?: 0f,
            shortDressLength = etShortDressLength.text.toString().toFloatOrNull() ?: 0f,
            thighCircumference = etThighCircumference.text.toString().toFloatOrNull() ?: 0f,
            kneeLength = etKneeLength.text.toString().toFloatOrNull() ?: 0f,
            kneeCircumference = etKneeCircumference.text.toString().toFloatOrNull() ?: 0f,
            bottom = etBottom.text.toString().toFloatOrNull() ?: 0f,
            otherMeasures = etOtherMeasures.text.toString()
        )
    }
}
