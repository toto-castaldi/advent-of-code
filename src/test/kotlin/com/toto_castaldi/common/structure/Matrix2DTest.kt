package com.toto_castaldi.common.structure

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Matrix2DTest {

    @Test
    fun testIterator() {
        val m = build2x2()
        var count = 1
        m.forEach {line ->
            assertEquals(listOf(count, count + 1), line)
            count += 2
        }
    }

    @Test
    fun rowTest() {
        val m = build2x2()
        assertEquals(listOf(1,2), m.rowAt(0))
        assertEquals(listOf(3,4), m.rowAt(1))
    }

    @Test
    fun bakeTest() {
        val m = build2x2().bake()
        assertEquals(listOf(1,2), m.rowAt(0))
        assertEquals(listOf(3,4), m.rowAt(1))
    }


    @Test
    fun columnTest() {
        val m = build2x2()
        assertEquals(listOf(1,3), m.colAt(0))
        assertEquals(listOf(2,4), m.colAt(1))
    }

    @Test
    fun transposeTest() {
        val m = build2x2().transpose()
        assertEquals(listOf(1,3), m.rowAt(0))
        assertEquals(listOf(2,4), m.rowAt(1))
    }

    @Test
    fun subMatrixTest() {
        val matrix = build9x9()
        var sub = matrix.sub(0,0,3,3)
        assertEquals(listOf(0,1,2), sub.rowAt(0))
        assertEquals(listOf(9,10,11), sub.rowAt(1))
        assertEquals(listOf(18,19,20), sub.rowAt(2))

        sub = matrix.sub(1,1,3,3)
        assertEquals(listOf(10,11,12), sub.rowAt(0))
        assertEquals(listOf(19,20,21), sub.rowAt(1))
        assertEquals(listOf(28,29,30), sub.rowAt(2))

        sub = matrix.sub(3,3,3,3)
        assertEquals(listOf(30,31,32), sub.rowAt(0))
        assertEquals(listOf(39,40,41), sub.rowAt(1))
        assertEquals(listOf(48,49,50), sub.rowAt(2))
    }

    @Test
    fun includeTest() {
        val matrix = build9x9()

        assertTrue { 12 in matrix }
        assertTrue { 0 in matrix }
        assertTrue { 80 in matrix }
        assertFalse { -1 in matrix }
        assertFalse { 81 in matrix }
    }


    private fun build2x2(): Matrix2D<Int> {
        val matrix = Matrix2D<Int>(2, 2, 0)
        matrix[0, 0] = 1
        matrix[1, 0] = 2
        matrix[0, 1] = 3
        matrix[1, 1] = 4
        return matrix
    }

    private fun build9x9(): Matrix2D<Int> {
        val matrix = Matrix2D<Int>(9, 9, 0)
        var count = 0
        for (y in 0 until 9) {
            for (x in 0 until 9) {
                matrix[x,y] = count ++
            }
        }
        return matrix
    }



}