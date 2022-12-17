package com.toto_castaldi.common.structure

import kotlin.test.Test
import kotlin.test.assertEquals

class LineTest {

    @Test
    fun intersect0() {
        assertEquals(-1, Line(Coordinates(-2,4), Coordinates(0,6)).atY(5).toInt())
        assertEquals(1, Line(Coordinates(2,4), Coordinates(0,6)).atY(5).toInt())
    }

}