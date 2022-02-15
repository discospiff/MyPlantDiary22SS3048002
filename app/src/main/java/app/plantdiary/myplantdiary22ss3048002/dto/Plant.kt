package app.plantdiary.myplantdiary22ss3048002.dto

import com.google.gson.annotations.SerializedName

data class Plant(@SerializedName("genus") var genus: String, var species: String, var common: String, var cultivar : String = "") {
    override fun toString(): String {
        return common
    }
}