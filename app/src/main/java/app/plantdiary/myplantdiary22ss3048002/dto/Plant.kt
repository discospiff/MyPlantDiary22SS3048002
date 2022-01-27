package app.plantdiary.myplantdiary22ss3048002.dto

data class Plant(var genus: String, var species: String, var common: String, var cultivar : String = "") {
    override fun toString(): String {
        return common
    }
}