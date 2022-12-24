package com.toto_castaldi.common.structure

import com.toto_castaldi.common.Rolling
import kotlin.test.*

class RubikTest {

    @Test
    fun rotation2Test() {
        val rubik = Rubik(S("BLU"), S("BIANCO"), S("VERDE"), S("GIALLO"), S("ROSSO"), S("ARANCIONE"))
        rubik.set(S("BIANCO"), S("BLU"))
        rubik.rotateRight()
        assertEquals(S("VERDE"), rubik.currentFront())
    }

    @Test
    fun rotation1Test() {
        val rubik = Rubik(S("BLU"), S("BIANCO"), S("VERDE"), S("GIALLO"), S("ROSSO"), S("ARANCIONE"))

        rubik.set(S("GIALLO"), S("VERDE"))
        assertEquals(S("GIALLO"), rubik.currentFront())

        rubik.rotateUp()
        assertEquals(S("ROSSO"), rubik.currentFront())
        assertEquals(S("VERDE"), rubik.currentUp())

        rubik.rotateFront(2)
        assertEquals(S("ROSSO"), rubik.currentFront())
        assertEquals(S("BLU"), rubik.currentUp())

        rubik.rotateFront()
        assertEquals(S("ROSSO"), rubik.currentFront())
        assertEquals(S("BIANCO"), rubik.currentUp())

        rubik.rotateFront(-1)
        assertEquals(S("ROSSO"), rubik.currentFront())
        assertEquals(S("BLU"), rubik.currentUp())

        rubik.rotateUp(-2)
        assertEquals(S("ARANCIONE"), rubik.currentFront())
        assertEquals(S("BLU"), rubik.currentUp())

        rubik.rotateRight(3)
        assertEquals(S("BLU"), rubik.currentFront())
        assertEquals(S("ROSSO"), rubik.currentUp())

        rubik.rotateRight(4)
        assertEquals(S("BLU"), rubik.currentFront())
        assertEquals(S("ROSSO"), rubik.currentUp())

        rubik.rotateRight(-4)
        assertEquals(S("BLU"), rubik.currentFront())
        assertEquals(S("ROSSO"), rubik.currentUp())

        rubik.rotateUp(4)
        assertEquals(S("BLU"), rubik.currentFront())
        assertEquals(S("ROSSO"), rubik.currentUp())

        rubik.rotateUp(-4)
        assertEquals(S("BLU"), rubik.currentFront())
        assertEquals(S("ROSSO"), rubik.currentUp())

        rubik.rotateFront(4)
        assertEquals(S("BLU"), rubik.currentFront())
        assertEquals(S("ROSSO"), rubik.currentUp())

        rubik.rotateFront(-4)
        assertEquals(S("BLU"), rubik.currentFront())
        assertEquals(S("ROSSO"), rubik.currentUp())
    }

    private data class S(val value: String) : Rolling {
        override fun turnUnclockwise() {
            
        }

        override fun turnClockwise() {
            
        }

    }

}