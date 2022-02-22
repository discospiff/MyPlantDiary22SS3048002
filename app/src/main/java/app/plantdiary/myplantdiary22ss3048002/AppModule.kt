package app.plantdiary.myplantdiary22ss3048002

import app.plantdiary.myplantdiary22ss3048002.service.IPlantService
import app.plantdiary.myplantdiary22ss3048002.service.PlantService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    single<IPlantService> { PlantService() }
}