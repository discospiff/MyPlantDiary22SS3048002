package app.plantdiary.myplantdiary22ss3048002

import app.plantdiary.myplantdiary22ss3048002.dto.Plant
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
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
}