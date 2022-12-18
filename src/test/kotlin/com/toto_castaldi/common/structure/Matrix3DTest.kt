package com.toto_castaldi.common.structure

import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix3DTest {

    @Test
    fun testX() {
        val matrix3D = twoTwo()

        val x0 = matrix3D.getX(0)
        assertEquals(7, x0[0,0] )
        assertEquals(5, x0[1,0] )
        assertEquals(3, x0[0,1] )
        assertEquals(1, x0[1,1] )

        val x1 = matrix3D.getX(1)
        assertEquals(8, x1[0,0] )
        assertEquals(6, x1[1,0] )
        assertEquals(4, x1[0,1] )
        assertEquals(2, x1[1,1] )
    }


    private fun twoTwo(): Matrix3D<Int> {
        val matrix3D = Matrix3D<Int>(2, 2, 2, 0)
        matrix3D[0, 0, 0] = 7
        matrix3D[1, 0, 0] = 8
        matrix3D[0, 1, 0] = 5
        matrix3D[1, 1, 0] = 6
        matrix3D[0, 0, 1] = 3
        matrix3D[1, 0, 1] = 4
        matrix3D[0, 1, 1] = 1
        matrix3D[1, 1, 1] = 2
        return matrix3D
    }

}