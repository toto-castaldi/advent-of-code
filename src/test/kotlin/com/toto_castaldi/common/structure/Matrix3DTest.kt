package com.toto_castaldi.common.structure

import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix3DTest {

    @Test
    fun setGetTest() {
        val matrix3D = Matrix3D<Int>(2, 2, 2, 0)
        matrix3D[0,0,0] = 1
        assertEquals(1, matrix3D[0,0,0])

        matrix3D[1,0,1] = 2
        assertEquals(2, matrix3D[1,0,1])

        matrix3D[0,1,1] = 3
        assertEquals(3, matrix3D[0,1,1])

        matrix3D[1,1,1] = 4
        assertEquals(4, matrix3D[1,1,1])
    }

    @Test
    fun xTest() {
        val matrix3D = twoTwo()
        val slice0 = matrix3D.getX(0)

        assertEquals(7, slice0[0,0] )
        assertEquals(5, slice0[1,0] )
        assertEquals(3, slice0[0,1] )
        assertEquals(1, slice0[1,1] )

        val slice1 = matrix3D.getX(1)
        assertEquals(8, slice1[0,0] )
        assertEquals(6, slice1[1,0] )
        assertEquals(4, slice1[0,1] )
        assertEquals(2, slice1[1,1] )
    }

    @Test
    fun yTest() {
        val matrix3D = twoTwo()

        val slice0 = matrix3D.getY(0)
        assertEquals(7, slice0[0,0] )
        assertEquals(8, slice0[1,0] )
        assertEquals(3, slice0[0,1] )
        assertEquals(4, slice0[1,1] )

        val slice1 = matrix3D.getY(1)
        assertEquals(5, slice1[0,0] )
        assertEquals(6, slice1[1,0] )
        assertEquals(1, slice1[0,1] )
        assertEquals(2, slice1[1,1] )
    }

    @Test
    fun zTest() {
        val matrix3D = twoTwo()

        val slice0 = matrix3D.getZ(0)
        assertEquals(7, slice0[0,0] )
        assertEquals(8, slice0[1,0] )
        assertEquals(5, slice0[0,1] )
        assertEquals(6, slice0[1,1] )

        val slice1 = matrix3D.getZ(1)
        assertEquals(3, slice1[0,0] )
        assertEquals(4, slice1[1,0] )
        assertEquals(1, slice1[0,1] )
        assertEquals(2, slice1[1,1] )
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