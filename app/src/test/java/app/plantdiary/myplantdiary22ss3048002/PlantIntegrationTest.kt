package app.plantdiary.myplantdiary22ss3048002

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.plantdiary.myplantdiary22ss3048002.dto.Plant
import app.plantdiary.myplantdiary22ss3048002.service.PlantService
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PlantIntegrationTest {

    lateinit var plantService : PlantService

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    var allPlants : List<Plant>? = ArrayList<Plant>()

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun onePlusTwo_EqualsThree() {
        assertEquals(3, 1+ 2)
    }

    @Test
    fun twoPlusTwo_EqualsFour() {
        assertEquals(4, 2+2)
    }

    @Test
    fun easternRedbud_ShouldReturnEasternRedbud() {
        var plant = Plant("Cercis", "canadensis", "Eastern Redbud")
        assertEquals("Eastern Redbud", plant.toString())
    }

    @Test
    fun `Given three and four When we add them together Then we get seven` () {
        assertEquals(7, 3+4)
    }

    @Test
    fun `Try some nulls` () {
        var foo : String? = null
        assertNull(foo)
        var bar = foo?.toString()
        assertNull(bar)
        var baz = foo?.toString() ?: "I like fuji apples"
        assertEquals("I like fuji apples", baz)

    }

    @Test
    fun `Given plant data are available when I search for Redbud then I should receive Cercis canadensis`() = runTest {
        givenPlantServiceIsInitialized()
        whenPlantDataAreReadAndParsed()
        thenPlantCollectionShouldContainCercisCanadensis()
    }

    private fun givenPlantServiceIsInitialized() {
        plantService = PlantService()
    }

    private suspend fun whenPlantDataAreReadAndParsed() {
        allPlants = plantService.fetchPlants()
    }

    private fun thenPlantCollectionShouldContainCercisCanadensis() {
        assertNotNull(allPlants)
        assertTrue(allPlants!!.isNotEmpty())
        var containsCercisCanadensis = false
        allPlants!!.forEach {
            if (it.genus.equals("Cercis") && it.species.equals("canadensis")) {
                containsCercisCanadensis = true
            }
        }
        assertTrue(containsCercisCanadensis)
    }
}