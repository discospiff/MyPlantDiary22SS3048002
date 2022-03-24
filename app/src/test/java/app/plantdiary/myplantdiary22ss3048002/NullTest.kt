package app.plantdiary.myplantdiary22ss3048002

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.plantdiary.myplantdiary22ss3048002.dto.Specimen
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.fail
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.lang.reflect.TypeVariable

class NullTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    var specimen : Specimen? = null

    @Test
    fun noUnwrap() {
        var specimenString = specimen.toString()
        assertNotNull(specimenString)
    }


    @Test
    fun forceUnwrap() {
        try {
            var specimenString = specimen!!.toString()
            assertNotNull(specimenString)
        } catch (e: NullPointerException) {
            fail("No deal!")
        }
    }

    @Test
    fun nullWithLet() {
        var specimenString = specimen?.let() {
            it.toString()
        } ?: "Specimen is null"
        assertNotNull(specimenString)
    }

    @Test
    fun objectWithLet() {
        specimen = Specimen("Eastern Redbud", 83, "", "Harbin Park", "A nice tree!")
        var specimenString = specimen?.let {
            it.toString()
        } ?: "Specimen is null"
        assertNotNull(specimenString)
    }

    @Test
    fun letAsAFunction() {
        var myFunction : (it: Specimen) -> String = {
            it.toString()
        }
        specimen = Specimen("Eastern Redbud", 83, "", "Harbin Park", "A nice tree!")

        var specimenString = specimen?.let (myFunction) ?: "Specimen is null"

        assertNotNull(specimenString)

        specimen = Specimen("Eastern Redbud", 83, "", "Harbin Park", "A nice tree!")
        specimenString = specimen?.let ({
            it.toString()
        }) ?: "Specimen is null"
        assertNotNull(specimenString)
    }



}