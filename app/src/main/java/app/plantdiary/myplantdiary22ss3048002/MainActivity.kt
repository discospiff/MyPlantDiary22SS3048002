package app.plantdiary.myplantdiary22ss3048002

import android.content.res.Configuration
import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import app.plantdiary.myplantdiary22ss3048002.R
import app.plantdiary.myplantdiary22ss3048002.dto.Plant
import app.plantdiary.myplantdiary22ss3048002.dto.Specimen
import app.plantdiary.myplantdiary22ss3048002.ui.theme.MyPlantDiary22SS3048002Theme
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel<MainViewModel>()
    private var inPlantName: String = ""
    private var selectedPlant : Plant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel.fetchPlants()
            val plants by viewModel.plants.observeAsState(initial = emptyList())

            // create some temporary dummy specimen data
            val specimens by viewModel.specimens.observeAsState(initial = emptyList())

            MyPlantDiary22SS3048002Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SpecimenFacts("Android", plants, specimens, viewModel.selectedSpecimen)
                }
                var foo = plants
                var i = 1 + 1
            }
        }
    }


    @Composable
    fun SpecimenFacts(name: String, plants: List<Plant> = ArrayList<Plant>(), specimens : List<Specimen> = ArrayList<Specimen>(), selectedSpecimen : Specimen = Specimen()) {
        var inLocation by remember (selectedSpecimen.specimenID) { mutableStateOf(selectedSpecimen.location) }
        var inDescription by remember(selectedSpecimen.specimenID) { mutableStateOf(selectedSpecimen.description) }
        var inDatePlanted by remember(selectedSpecimen.specimenID) { mutableStateOf(selectedSpecimen.datePlanted) }
        val context = LocalContext.current
        Column {
            SpecimenSpinner(specimens = specimens)
            TextFieldWithDropdownUsage(plants, "Select a Plant", 3, selectedSpecimen)
            OutlinedTextField(
                value = inLocation,
                onValueChange = { inLocation = it },
                label = { Text(stringResource(R.string.location)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = inDescription,
                onValueChange = { inDescription = it },
                label = { Text(stringResource(R.string.description)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = inDatePlanted,
                onValueChange = { inDatePlanted = it },
                label = { Text(stringResource(R.string.datePlanted)) },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    viewModel.selectedSpecimen.apply {
                        plantName = inPlantName
                        plantID = selectedPlant?.let {
                            it.id
                        } ?: 0
                        location = inLocation
                        description = inDescription
                        datePlanted = inDatePlanted
                    }
                    viewModel.saveSpecimen()
                    Toast.makeText(
                        context,
                        "Specimen: ${viewModel.selectedSpecimen.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                Text(text = "Save")
            }
            Button (
                onClick = {
                    signOn()
                }
                    ) {
                Text(text = "Logon")
            }
        }
    }

    private fun signOn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract())
        { res -> this.signInResult(res)}

    private fun signInResult(res: FirebaseAuthUIAuthenticationResult?) {
        TODO("Not yet implemented")
    }


    @Composable
    fun TextFieldWithDropdownUsage(dataIn: List<Plant>, label: String = "", take: Int = 3, selectedSpecimen: Specimen = Specimen()) {

        val dropDownOptions = remember { mutableStateOf(listOf<Plant>()) }
        val textFieldValue = remember(selectedSpecimen.specimenID) { mutableStateOf(TextFieldValue(selectedSpecimen.plantName)) }
        val dropDownExpanded = remember { mutableStateOf(false) }

        fun onDropdownDismissRequest() {
            dropDownExpanded.value = false
        }

        fun onValueChanged(value: TextFieldValue) {
            inPlantName = value.text
            dropDownExpanded.value = true
            textFieldValue.value = value
            dropDownOptions.value = dataIn.filter {
                it.toString().startsWith(value.text) && it.toString() != value.text
            }.take(take)
        }

        TextFieldWithDropdown(
            modifier = Modifier.fillMaxWidth(),
            value = textFieldValue.value,
            setValue = ::onValueChanged,
            onDismissRequest = ::onDropdownDismissRequest,
            dropDownExpanded = dropDownExpanded.value,
            list = dropDownOptions.value,
            label = label
        )
    }

    @Composable
    fun TextFieldWithDropdown(
        modifier: Modifier = Modifier,
        value: TextFieldValue,
        setValue: (TextFieldValue) -> Unit,
        onDismissRequest: () -> Unit,
        dropDownExpanded: Boolean,
        list: List<Plant>,
        label: String = ""
    ) {
        Box(modifier) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (!focusState.isFocused)
                            onDismissRequest()
                    },
                value = value,
                onValueChange = setValue,
                label = { Text(label) },
                colors = TextFieldDefaults.outlinedTextFieldColors()
            )
            DropdownMenu(
                expanded = dropDownExpanded,
                properties = PopupProperties(
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                onDismissRequest = onDismissRequest
            ) {
                list.forEach { text ->
                    DropdownMenuItem(onClick = {
                        setValue(
                            TextFieldValue(
                                text.toString(),
                                TextRange(text.toString().length)
                            )
                        )
                        selectedPlant = text

                    }) {
                        Text(text = text.toString())
                    }
                }
            }
        }
    }

    @Composable
    fun SpecimenSpinner (specimens : List<Specimen>) {
        var expanded by remember {mutableStateOf(false)}
        var specimenText by remember {mutableStateOf("Specimen Collection")}
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row(Modifier
                .padding(24.dp)
                .clickable {
                    expanded = !expanded
                }
                .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = specimenText, fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
                Icon (imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    specimens.forEach {
                        specimen -> DropdownMenuItem( onClick = {
                           expanded = false
                        if (specimen.plantName == viewModel.NEW_SPECIMEN) {
                            // create a new specimen object
                            specimenText = ""
                            specimen.plantName = ""
                        } else {
                            // we have selected an existing specimen.
                            specimenText = specimen.toString()
                            selectedPlant = Plant(genus = "", species = "", common = specimen.plantName, id = specimen.plantID)
                            inPlantName = specimen.plantName
                        }

                        viewModel.selectedSpecimen = specimen

                    }) {
                            Text (text = specimen.toString())
                    }

                    }
                }
            }
        }
    }

    @Preview(name = "Light Mode", showBackground = true)
    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
        name = "Dark Mode"
    )
    @Composable
    fun DefaultPreview() {
        MyPlantDiary22SS3048002Theme {
            // A surface container using the 'background' color from the theme
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxWidth()
            ) {
                SpecimenFacts("Android")
            }
        }
    }
}