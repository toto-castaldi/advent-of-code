package com.toto_castaldi.common.structure

import kotlin.test.*

class RubikTest {

    @Test
    @Ignore
    fun rotationTest() {
        val rubik = Rubik("BLU", "BIANCO", "VERDE", "GIALLO", "ROSSO", "ARANCIONE")

        rubik.set("GIALLO", "VERDE")
        assertEquals("GIALLO", rubik.currentFace())
        rubik.rotateX()
        assertEquals("ROSSO", rubik.currentFace())
        rubik.rotateX()
        assertEquals("BIANCO", rubik.currentFace())
        assertEquals("VERDE", rubik.currentUp())
        rubik.rotateX()
        assertEquals("ARANCIONE", rubik.currentFace())
        rubik.rotateX()
        assertEquals("GIALLO", rubik.currentFace())
        assertEquals("VERDE", rubik.currentUp())
        rubik.rotateY()
        assertEquals("BIANCO", rubik.currentFace())
        rubik.rotateY()
        assertEquals("BIANCO", rubik.currentFace())
        rubik.rotateY()
        assertEquals("ROSSO", rubik.currentFace())
        rubik.rotateX(-1)
        assertEquals("BLU", rubik.currentFace())
        rubik.rotateY(-1)
        assertEquals("BIANCO", rubik.currentFace())
    }

}