package com.toto_castaldi.common.structure

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PlacedRectangleTest {

    @Test
    fun testIntersects() {
        var a = PlacedRectangle(2,1, 3, 3)
        var b = PlacedRectangle(4,3, 4, 1)
        assertTrue { a.intersects(b) }

        a = PlacedRectangle(2,0, 3, 3)
        b = PlacedRectangle(4,3, 4, 1)
        assertFalse { a.intersects(b) }
    }

    @Test
    fun testTouch() {
        var a = PlacedRectangle(2,0, 3, 3)
        var b = PlacedRectangle(4,3, 4, 1)
        assertTrue { a.touch(b) }

        a = PlacedRectangle(2,-1, 3, 3)
        b = PlacedRectangle(4,3, 4, 1)
        assertFalse { a.intersects(b) }
    }

}