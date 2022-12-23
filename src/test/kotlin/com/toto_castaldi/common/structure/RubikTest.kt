package com.toto_castaldi.common.structure

import kotlin.test.*

class RubikTest {

    @Test
    fun rotationTest() {
        val rubik = Rubik("BLU", "BIANCO", "VERDE", "GIALLO", "ROSSO", "ARANCIONE")

        rubik.set("GIALLO", "VERDE")
        assertEquals("GIALLO", rubik.currentFront())

        rubik.rotateUp()
        assertEquals("ROSSO", rubik.currentFront())
        assertEquals("VERDE", rubik.currentUp())

        rubik.rotateFront(2)
        assertEquals("ROSSO", rubik.currentFront())
        assertEquals("BLU", rubik.currentUp())

        rubik.rotateFront()
        assertEquals("ROSSO", rubik.currentFront())
        assertEquals("BIANCO", rubik.currentUp())

        rubik.rotateFront(-1)
        assertEquals("ROSSO", rubik.currentFront())
        assertEquals("BLU", rubik.currentUp())

        rubik.rotateUp(-2)
        assertEquals("ARANCIONE", rubik.currentFront())
        assertEquals("BLU", rubik.currentUp())

        rubik.rotateRight(3)
        assertEquals("BLU", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())

        rubik.rotateRight(4)
        assertEquals("BLU", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())

        rubik.rotateRight(-4)
        assertEquals("BLU", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())

        rubik.rotateUp(4)
        assertEquals("BLU", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())

        rubik.rotateUp(-4)
        assertEquals("BLU", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())

        rubik.rotateFront(4)
        assertEquals("BLU", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())

        rubik.rotateFront(-4)
        assertEquals("BLU", rubik.currentFront())
        assertEquals("ROSSO", rubik.currentUp())
    }

}