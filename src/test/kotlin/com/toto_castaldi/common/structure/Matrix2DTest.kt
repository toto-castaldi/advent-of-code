package com.toto_castaldi.common.structure

import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix2DTest {

    @Test
    fun testIterator() {
        val m = build()
        var count = 1
        m.forEach {line ->
            assertEquals(listOf(count, count + 1), line)
            count += 2
        }
    }

    private fun build(): Matrix2D<Int> {
        val matrix = Matrix2D<Int>(2, 2, 0)
        matrix[0, 0] = 1
        matrix[1, 0] = 2
        matrix[0, 1] = 3
        matrix[1, 1] = 4
        return matrix
    }

}