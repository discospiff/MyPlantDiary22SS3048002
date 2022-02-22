package app.plantdiary.myplantdiary22ss3048002

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.plantdiary.myplantdiary22ss3048002.dto.Plant
import app.plantdiary.myplantdiary22ss3048002.service.IPlantService
import app.plantdiary.myplantdiary22ss3048002.service.PlantService
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext.get
import org.koin.java.KoinJavaComponent.inject

class MainViewModel(var plantService: IPlantService = PlantService()) : ViewModel() {
    val plants: MutableLiveData<List<Plant>> = MutableLiveData<List<Plant>>()


    fun fetchPlants() {
        viewModelScope.launch {
            var innerPlants = plantService.fetchPlants()
            plants.postValue(innerPlants)
        }
    }
}