package app.plantdiary.myplantdiary22ss3048002.service

import app.plantdiary.myplantdiary22ss3048002.RetrofitClientInstance
import app.plantdiary.myplantdiary22ss3048002.dao.IPlantDAO
import app.plantdiary.myplantdiary22ss3048002.dto.Plant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

interface IPlantService {
    suspend fun fetchPlants(): List<Plant>?
}

class PlantService : IPlantService {
    override suspend fun fetchPlants(): List<Plant>? {
        return withContext(Dispatchers.IO) {
            val retrofit = RetrofitClientInstance.retrofitInstance?.create(IPlantDAO::class.java)
            val plants = async { retrofit?.getAllPlants() }
            var result = plants.await()?.awaitResponse()?.body()
            return@withContext result
        }
    }
}