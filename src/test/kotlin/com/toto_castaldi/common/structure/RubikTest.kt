package com.toto_castaldi.common.structure

import com.toto_castaldi.common.Rolling
import kotlin.test.*

class RubikTest {

    @Test
    fun rotation2Test() {
        val rubik = Rubik("B", "BIANCO", "VERDE", "GIALLO", "ROSSO", "ARANCIONE")
        rubik.set("BIANCO", "B")
        rubik.rotateRight()
        assertEquals("VERDE", rubik.currentFront())
    }

    @Test
    fun rotation1Test() {
        val rubik = Rubik("B", "BIANCO", "VERDE", "GIALLO", "ROSSO", "ARANCIONE")

        rubik.set("GIALLO", "VERDE")
        assertEquals("GIALLO", rubik.currentFront())

        rubik.rotateUp()
        assertEquals("ROSSO", rubik.currentFront())
        assertEquals("VERDE", rubik.currentUp())

        rubik.rotateFront(2)
        assertEquals("ROSSO", rubik.currentFront())
        assertEquals("B", rubik.currentUp())

        rubik.rotateFront()
        assertEquals("ROSSO", rubik.currentFront())
        assertEquals("BIANCO", rubik.currentUp())

        rubik.rotateFront(-1)
        assertEquals("ROSSO", rubik.currentFront())
        assertEquals("B", rubik.currentUp())

        rubik.rotateUp(-2)
        assertEquals("ARANCIONE", rubik.currentFront())
        assertEquals("B", rubik.currentUp())

        rubik.rotateRight(3)
        assertEquals("B", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())

        rubik.rotateRight(4)
        assertEquals("B", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())

        rubik.rotateRight(-4)
        assertEquals("B", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())

        rubik.rotateUp(4)
        assertEquals("B", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())

        rubik.rotateUp(-4)
        assertEquals("B", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())

        rubik.rotateFront(4)
        assertEquals("B", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())

        rubik.rotateFront(-4)
        assertEquals("B", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())
    }

 

}