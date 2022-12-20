package com.toto_castaldi.common.structure

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

        assertEquals(listOf(1, 2, -3, 3, -2, 0, 4), a.values(a.findBy { it == 1 }!!))
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
        assertEquals(listOf(2, 1, -3, 3, -2, 0, 4), a.values(a.findBy { it == 2 }!!))
        check(a,4,2,1)
        check(a,2,1,-3)
        check(a,1,-3,3)
        check(a,3,-2,0)
        check(a,-2,0,4)
        check(a,0,4,2)

        a.moveRigth(a.findBy { it == 2 }!!, 2)
        assertEquals(listOf(1, -3, 2, 3, -2, 0, 4), a.values(a.findBy { it == 1 }!!))
        check(a,4,1,-3)
        check(a,1,-3,2)
        check(a,-3,2,3)
        check(a,2,3,-2)
        check(a,3,-2,0)
        check(a,-2,0,4)

        a.moveRigth(a.findBy { it == -3 }!!, -3)
        assertEquals(listOf(1, 2, 3, -2, -3, 0, 4), a.values(a.findBy { it == 1 }!!))

        a.moveRigth(a.findBy { it == 3 }!!, 3)

        a.moveRigth(a.findBy { it == -2 }!!, -2)

        a.moveRigth(a.findBy { it == 0 }!!, 0)

        a.moveRigth(a.findBy { it == 4 }!!, 4)

        println(a.values(a.findBy { it == 1 }!!))
    }

    private fun check(ca: CircularArray<Int>, p: Int, a: Int, n: Int) {
        val find1 = ca.findBy { it == a }!!
        assertEquals(p, find1.prevNode?.data)
        assertEquals(n, find1.nextNode?.data)
    }

}