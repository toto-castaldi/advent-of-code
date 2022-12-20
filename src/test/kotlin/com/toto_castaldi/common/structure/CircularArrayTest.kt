package com.toto_castaldi.common.structure

import kotlin.random.Random
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class CircularArrayTest {

    @Test
    fun testSet() {
        val a = CircularArray<Int>()
        a.add(1)
        a.add(2)

        a[0] = 3
        assertEquals(arrayListOf(3,2), a)

        a[1] = 4
        assertEquals(arrayListOf(3,4), a)

        a[2] = 5
        assertEquals(arrayListOf(5,4), a)

        a[20000] = 6
        assertEquals(arrayListOf(6,4), a)

        a[20001] = 7
        assertEquals(arrayListOf(6,7), a)

        a[-1] = 8
        assertEquals(arrayListOf(6,8), a)

        a[-2] = 9
        assertEquals(arrayListOf(9,8), a)

    }

    @Test
    fun testGet() {
        val a = CircularArray<Int>()
        a.add(1)
        a.add(2)

        assertEquals(1, a[0])
        assertEquals(2, a[1])
        assertEquals(1, a[2])
        assertEquals(1, a[1000])
        assertEquals(2, a[1001])
        assertEquals(2, a[-1])
        assertEquals(1, a[-2])
        assertEquals(2, a[-3])
        assertEquals(2, a[-151])
        assertEquals(1, a[-152])
    }

    @Test
    @Ignore
    fun testMoveLeft() {
        val a = CircularArray<Int>()
        a.add(1)
        a.add(2)
        a.add(-3)
        a.add(3)
        a.add(-2)
        a.add(0)
        a.add(4)

        println(a)
        a.moveLeft(5, 3)
        assertEquals(arrayListOf(1, 2, 0, -3, 3, -2, 4), a)

        a.moveLeft(0, 1)
        assertEquals(arrayListOf(2, 0, -3, 3, -2, 4, 1), a)
    }

    @Test
    @Ignore
    fun testMoveRight() {
        val a = CircularArray<Int>()
        a.add(1)
        a.add(2)
        a.add(-3)
        a.add(3)
        a.add(-2)
        a.add(0)
        a.add(4)

        a.moveRight(0, 1)
        assertEquals(arrayListOf(2, 1, -3, 3, -2, 0, 4), a)

        a.moveRight(0, a[0])
        assertEquals(arrayListOf(1, -3, 2, 3, -2, 0, 4), a)

        println(a)
        a.moveRight(1, a[1])
        assertEquals(arrayListOf(1, 2, 3, -2, -3, 0, 4), a)
    }

}