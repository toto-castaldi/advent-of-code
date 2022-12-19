package com.toto_castaldi.aoc

import Aoc202218
import com.toto_castaldi.common.structure.Matrix2D
import kotlin.test.*

class Aoc202218Test {

    @Test
    fun rubik() {
        val aoc = Aoc202218()
        aoc.add(1,1,1, 7)
        aoc.add(2,1,1, 8)
        aoc.add(1,1,2, 3)
        aoc.add(2,1,2, 4)
        aoc.add(1,2,1, 5)
        aoc.add(2,2,1, 6)
        aoc.add(1,2,2, 1)
        aoc.add(2,2,2, 2)

        var faceIndex = 0
        val face0 = Matrix2D<Int>(2,2,0).add(listOf(7,8)).add(listOf(3,4))
        val face1 = Matrix2D<Int>(2,2,0).add(listOf(5,6)).add(listOf(1,2))

        aoc.navigateY(1).forEach {face ->
            assertEquals(if (faceIndex ++ == 0) face0 else face1, face)
        }
        assertEquals(face0, aoc.shrinkY(1))

        faceIndex = 1
        aoc.navigateY(-1).forEach {face ->
            assertEquals(if (faceIndex -- == 0) face0 else face1, face)
        }
        assertEquals(face1, aoc.shrinkY(-1))

    }

    @Test
    fun part1Simple() {
        val aoc = Aoc202218()
        aoc.add(1, 1, 1)
        aoc.add(2, 1, 1)

        assertEquals(10, aoc.countSideExposed())
    }

    @Test
    @Ignore
    fun part1() {
        val aoc = Aoc202218()
        aoc.add(2,2,2)
        aoc.add(1,2,2)
        aoc.add(3,2,2)
        aoc.add(2,1,2)
        aoc.add(2,3,2)
        aoc.add(2,2,1)
        aoc.add(2,2,3)
        aoc.add(2,2,4)
        aoc.add(2,2,6)
        aoc.add(1,2,5)
        aoc.add(3,2,5)
        aoc.add(2,1,5)
        aoc.add(2,3,5)

        assertEquals(10, aoc.countSideExposed())
    }



}