package tg.ibrahim.kondo.bumaye_app

class Client (
    var id: Int,
    // Client
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,

    // Measure
    var shoulder: Float,
    var chest: Float,
    var lengthWaist: Float,
    var ventralTurn: Float,
    var hip: Float,
    var bodiceLength: Float,
    var belt: Float,
    var skirtLength: Float,
    var dressLength: Float,
    var sleeveLength: Float,
    var sleeveSize: Float,
    var pantsLength: Float,
    var shortDressLength: Float,
    var thighCircumference: Float,
    var kneeLength: Float,
    var kneeCircumference: Float,
    var bottom: Float,
    var otherMeasures: String,

    // Order tracking
    var orderDate: String,
    var deliveryDate: String,

    // Payment
    var totalAmount: Double,
    var advance: Double,
    var remainder: Double
    ) {
    override fun toString(): String {
        return "Client(firstName='$firstName', lastName='$lastName', shoulder=$shoulder, chest=$chest, lengthWaist=$lengthWaist, ventralTurn=$ventralTurn, hip=$hip, bodiceLength=$bodiceLength, belt=$belt, skirtLength=$skirtLength, dressLength=$dressLength, sleeveLength=$sleeveLength, sleeveSize=$sleeveSize, pantsLength=$pantsLength, shortDressLength=$shortDressLength, thighCircumference=$thighCircumference, kneeLength=$kneeLength, kneeCircumference=$kneeCircumference, bottom=$bottom, otherMeasures=$otherMeasures, orderDate=$orderDate, deliveryDate=$deliveryDate, totalAmount=$totalAmount, advance=$advance, remainder=$remainder)"
    }
}