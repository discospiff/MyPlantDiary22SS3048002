package app.plantdiary.myplantdiary22ss3048002

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.plantdiary.myplantdiary22ss3048002.dto.Plant
import app.plantdiary.myplantdiary22ss3048002.dto.Specimen
import app.plantdiary.myplantdiary22ss3048002.dto.User
import app.plantdiary.myplantdiary22ss3048002.service.IPlantService
import app.plantdiary.myplantdiary22ss3048002.service.PlantService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext.get
import org.koin.java.KoinJavaComponent.inject

class MainViewModel(var plantService: IPlantService = PlantService()) : ViewModel() {
    var user: User? = null
    val plants: MutableLiveData<List<Plant>> = MutableLiveData<List<Plant>>()
    val specimens: MutableLiveData<List<Specimen>> = MutableLiveData<List<Specimen>>()
    var selectedSpecimen by mutableStateOf(Specimen())
    val NEW_SPECIMEN = "New Specimen"

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

    fun saveSpecimen() {
        user?.let{
            user ->
        val document = if (selectedSpecimen.specimenID == null || selectedSpecimen.specimenID.isEmpty()) {
            // insert
            firestore.collection("users").document(user.uid).collection("specimens").document()
        } else {
            // update
            firestore.collection("users").document(user.uid).collection("specimens").document(selectedSpecimen.specimenID)
        }

        selectedSpecimen.specimenID = document.id
        val handle = document.set(selectedSpecimen)
        handle.addOnSuccessListener { Log.d("Firebase", "Document Saved") }
        handle.addOnFailureListener { Log.e("Firebase", "Save failed $it  ") }
        }
    }

    fun listenToSpecimens() {
        user?.let{
            user ->
            firestore.collection("users").document(user.uid).collection("specimens").addSnapshotListener {
                    snapshot, error ->
                // see of we received an error
                if (error != null) {
                    Log.w("listen failed.", error)
                    return@addSnapshotListener
                }
                // if we reached this point, there was not an error, and we have data.
                snapshot?.let {
                    val allSpecimens = ArrayList<Specimen>()
                    allSpecimens.add(Specimen(NEW_SPECIMEN))
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

    fun saveUser() {
        user?.let {
            user ->
            val handle = firestore.collection("users").document(user.uid).set(user)
            handle.addOnSuccessListener { Log.d("Firebase", "User Saved") }
            handle.addOnFailureListener { Log.e("Firebase", "User save failed $it") }

        }
    }
}