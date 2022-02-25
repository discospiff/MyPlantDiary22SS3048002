package app.plantdiary.myplantdiary22ss3048002

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.plantdiary.myplantdiary22ss3048002.dto.Plant
import app.plantdiary.myplantdiary22ss3048002.dto.Specimen
import app.plantdiary.myplantdiary22ss3048002.service.IPlantService
import app.plantdiary.myplantdiary22ss3048002.service.PlantService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext.get
import org.koin.java.KoinJavaComponent.inject

class MainViewModel(var plantService: IPlantService = PlantService()) : ViewModel() {
    val plants: MutableLiveData<List<Plant>> = MutableLiveData<List<Plant>>()

    private lateinit var firestore: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun fetchPlants() {
        viewModelScope.launch {
            var innerPlants = plantService.fetchPlants()
            plants.postValue(innerPlants)
        }
    }

    fun save(specimen: Specimen) {
        val document = firestore.collection("specimens").document()
        val handle = document.set(specimen)
        handle.addOnSuccessListener { Log.d("Firebase", "Document Saved") }
        handle.addOnFailureListener { Log.e("Firebase", "Save failed $it  ") }
    }
}