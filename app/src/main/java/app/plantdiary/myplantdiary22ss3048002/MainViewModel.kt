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
    val specimens: MutableLiveData<List<Specimen>> = MutableLiveData<List<Specimen>>()

    private lateinit var firestore: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToSpecimens()
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

    fun listenToSpecimens() {
        firestore.collection("specimens").addSnapshotListener {
                snapshot, error ->
            // see of we received an error
            if (error != null) {
                Log.w("listen failed.", error)
                return@addSnapshotListener
            }
            // if we reached this point, there was not an error, and we have data.
            snapshot?.let {
                val allSpecimens = ArrayList<Specimen>()
                val documents = snapshot.documents
                documents.forEach {
                    val specimen = it.toObject(Specimen::class.java)
                    specimen?.let {
                        allSpecimens.add(specimen)
                    }
                }
                // we have a populated collection of specimens.
                specimens.value = allSpecimens
            }
        }
    }
}