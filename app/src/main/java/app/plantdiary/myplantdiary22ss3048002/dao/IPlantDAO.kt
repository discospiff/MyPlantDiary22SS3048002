package app.plantdiary.myplantdiary22ss3048002.dao

import app.plantdiary.myplantdiary22ss3048002.dto.Plant
import retrofit2.Call
import retrofit2.http.GET

interface IPlantDAO {

    @GET("/discospiff/data/main/plants.md")
    fun getAllPlants() : Call<List<Plant>>
}