package com.toto_castaldi.common

import kotlin.test.Test
import kotlin.test.assertEquals

class NumbersTest {

    @Test
    fun testMerge() {
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