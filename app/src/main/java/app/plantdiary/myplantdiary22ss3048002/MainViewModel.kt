package app.plantdiary.myplantdiary22ss3048002

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.plantdiary.myplantdiary22ss3048002.dto.Plant
import app.plantdiary.myplantdiary22ss3048002.service.PlantService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val plants: MutableLiveData<List<Plant>> = MutableLiveData<List<Plant>>()
    var plantService: PlantService = PlantService()

    fun fetchPlants() {
        viewModelScope.launch {
            var innerPlants = plantService.fetchPlants()
            plants.postValue(innerPlants)
        }
    }
}