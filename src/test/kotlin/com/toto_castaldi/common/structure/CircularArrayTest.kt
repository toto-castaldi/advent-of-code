package com.toto_castaldi.common.structure

import kotlin.random.Random
import kotlin.test.*

class CircularArrayTest {

    @Test
    fun findByTest() {
        val a = CircularArray<Int>()
        a.add(1)
        a.add(2)
        a.add(-3)
        a.add(3)
        a.add(-2)
        a.add(0)
        a.add(4)

        var findBy = a.findBy { it == 2 }
        assertNotNull(findBy)
        assertEquals(findBy.data, 2)

        findBy = a.findBy { it == 0 }
        assertNotNull(findBy)
        assertEquals(findBy.data, 0)

        findBy = a.findBy { it == 4 }
        assertNotNull(findBy)
        assertEquals(findBy.data, 4)

        findBy = a.findBy { it == 20 }
        assertNull(findBy)

        assertEquals(listOf(1, 2, -3, 3, -2, 0, 4), a.values())
    }

    @Test
    fun moveRightTest() {
        val a = CircularArray<Int>()
        a.add(1)
        a.add(2)
        a.add(-3)
        a.add(3)
        a.add(-2)
        a.add(0)
        a.add(4)


        a.moveRigth(a.findBy { it == 1 }!!, 1)
        assertEquals(listOf(2, 1, -3, 3, -2, 0, 4), a.values())

    }

}