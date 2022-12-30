package com.toto_castaldi.aoc

import Aoc202214
import com.toto_castaldi.common.structure.IntCoordinates
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Aoc202214Test {

    @Test
    fun part1() {
        val aoc202214 = Aoc202214(500)
        aoc202214 + Aoc202214.parsePath("498,4 -> 498,6 -> 496,6")
        aoc202214 + Aoc202214.parsePath("503,4 -> 502,4 -> 502,9 -> 494,9")

        assertEquals(24,  aoc202214.blockedSandCount(true))
    }

    @Test
    fun part2() {
        val aoc202214 = Aoc202214(500)
        aoc202214 + Aoc202214.parsePath("498,4 -> 498,6 -> 496,6")
        aoc202214 + Aoc202214.parsePath("503,4 -> 502,4 -> 502,9 -> 494,9")
        aoc202214.addFloor(2)

        assertEquals(93,  aoc202214.blockedSandCount(true))
    }

    @Test
    fun testBricksEl() {
        val aoc202214 = Aoc202214(500)
        aoc202214 + Aoc202214.parsePath("498,4 -> 498,6 -> 496,6")
        aoc202214 + Aoc202214.parsePath("503,4 -> 502,4 -> 502,9 -> 494,9")
        assertTrue {
            IntCoordinates(503,4) in aoc202214.bricks
        }
    }

    @Test
    fun testBricks() {
        assertEquals(setOf(IntCoordinates(10,1), IntCoordinates(11,1)), (Aoc202214(500)  + Aoc202214.parsePath("10,1 -> 11,1")).bricks)
        assertEquals(setOf(
            IntCoordinates(10,1),
            IntCoordinates(11,1),
            IntCoordinates(12,1),
            IntCoordinates(13,1),
            IntCoordinates(14,1),
            IntCoordinates(15,1),
        ), (Aoc202214(500)  + Aoc202214.parsePath("10,1 -> 11,1 -> 15,1")).bricks)
        assertEquals(setOf(
            IntCoordinates(10,10),
            IntCoordinates(10,11),
            IntCoordinates(10,12),
            IntCoordinates(10,13),
            IntCoordinates(10,14),
            IntCoordinates(10,15),
        ), (Aoc202214(500)  + Aoc202214.parsePath("10,10 -> 10,11 -> 10,15")).bricks)
    }

}