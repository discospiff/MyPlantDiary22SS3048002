package app.plantdiary.myplantdiary22ss3048002.dto

data class Specimen(var plantName: String = "", var plantID: Int = 0, var specimenID : String = "", var location: String = "", var description : String = "", var datePlanted : String = "") {
    override fun toString(): String {
        return "$plantName"
    }
}