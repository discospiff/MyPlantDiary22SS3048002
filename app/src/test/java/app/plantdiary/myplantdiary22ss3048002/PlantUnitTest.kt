package app.plantdiary.myplantdiary22ss3048002

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.plantdiary.myplantdiary22ss3048002.dto.Plant
import app.plantdiary.myplantdiary22ss3048002.service.PlantService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class PlantUnitTest {
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    lateinit var mvm : MainViewModel

    @MockK
    lateinit var mockPlantService : PlantService

    private val mainThreadSurrogate = newSingleThreadContext("Main Thread")

    @Before
    fun initMocksAndMainThread() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `given a ViewModel with LiveData when populated with plants then results should show eastern redbud` () {
        givenViewModelIsInitializedWithMockData()
        whenPlantServiceFetchCountriesInvoked()
        thenResultsShouldContainEasternRedbud()
    }

    private fun givenViewModelIsInitializedWithMockData() {
        var plants = ArrayList<Plant>()
        val redOak = Plant("Quercus", "rubra", "Red Oak")
        plants.add(redOak)
        plants.add(Plant("Quercus", "alba", "White Oak"))
        plants.add(Plant ("Cercis", "candensis", "Eastern Redbud"))

        coEvery { mockPlantService.fetchPlants()} returns plants

        mvm.plantService = mockPlantService
    }

    private fun whenPlantServiceFetchCountriesInvoked() {
        mvm.fetchPlants()
    }

    private fun thenResultsShouldContainEasternRedbud() {
        var allPlants : List<Plant>? = ArrayList<Plant>()

        val latch = CountDownLatch(1)
        val observer = object : Observer<List<Plant>> {
            override fun onChanged(receivedPlants : List<Plant>?) {
                allPlants = receivedPlants
                latch.countDown()
                mvm.plants.removeObserver(this)
            }

        }

        mvm.plants.observeForever(observer)
        latch.await(10, TimeUnit.SECONDS)
        assertNotNull(allPlants)
        assertTrue(allPlants!!.isNotEmpty())
        var containsCercisCanadensis  = false
        allPlants!!.forEach {
            if (it.genus.equals("Cercis") && it.species.equals("canadensis")) {
                containsCercisCanadensis = true
            }
        }

        assertTrue(containsCercisCanadensis)
    }

}