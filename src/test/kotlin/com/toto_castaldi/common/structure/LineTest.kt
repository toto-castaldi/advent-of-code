package com.toto_castaldi.common.structure

import kotlin.test.Test
import kotlin.test.assertEquals

class LineTest {

    @Test
    fun intersect0() {
        assertEquals(-1, Line(IntCoordinates(-2,4), IntCoordinates(0,6)).atY(5).toInt())
        assertEquals(1, Line(IntCoordinates(2,4), IntCoordinates(0,6)).atY(5).toInt())
    }

}