package com.toto_castaldi.common

import kotlin.test.Test
import kotlin.test.assertEquals

class NumbersTest {

    @Test
    fun testFreeSpot() {
        assertEquals(setOf(16), Numbers.freeSpots(setOf(10..15,17..100)))
        assertEquals(setOf(16,17), Numbers.freeSpots(setOf(10..15,18..100)))
        assertEquals(emptySet(), Numbers.freeSpots(setOf(10..15,15..100)))
        assertEquals(emptySet(), Numbers.freeSpots(setOf(10..15,10..100)))
    }
    @Test
    fun testMerge() {
        assertEquals(setOf(2..2, 3..13),
            Numbers.merged(setOf(
                2..2,
                11..13
            ),3..13))
        assertEquals(setOf(5..100), Numbers.merged(setOf(10..100), 5..30))
        assertEquals(setOf(5..100), Numbers.merged(setOf(5..30), 10..100))
        assertEquals(setOf(5..100), Numbers.merged(setOf(20..30), 5..100))
        assertEquals(setOf(5..40),
            Numbers.merged(setOf(
                5..15,
                20..25,
                30..40
            ),10..35))

    }


}