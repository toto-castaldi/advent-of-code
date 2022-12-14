package com.toto_castaldi.aoc

import Aoc202214
import com.toto_castaldi.common.Coordinates
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Aoc202214Test {

    @Test
    fun part1() {
        val aoc202214 = Aoc202214(500)
        aoc202214 + Aoc202214.parsePath("498,4 -> 498,6 -> 496,6")
        aoc202214 + Aoc202214.parsePath("503,4 -> 502,4 -> 502,9 -> 494,9")

        assertEquals(24,  aoc202214.blockedSandCount())
    }

    @Test
    fun testBricksEl() {
        val aoc202214 = Aoc202214(500)
        aoc202214 + Aoc202214.parsePath("498,4 -> 498,6 -> 496,6")
        aoc202214 + Aoc202214.parsePath("503,4 -> 502,4 -> 502,9 -> 494,9")
        assertTrue {
            Coordinates(503,4) in aoc202214.bricks
        }
    }

    @Test
    fun testBricks() {
        assertEquals(setOf(Coordinates(10,1), Coordinates(11,1)), (Aoc202214(500)  + Aoc202214.parsePath("10,1 -> 11,1")).bricks)
        assertEquals(setOf(
            Coordinates(10,1),
            Coordinates(11,1),
            Coordinates(12,1),
            Coordinates(13,1),
            Coordinates(14,1),
            Coordinates(15,1),
        ), (Aoc202214(500)  + Aoc202214.parsePath("10,1 -> 11,1 -> 15,1")).bricks)
        assertEquals(setOf(
            Coordinates(10,10),
            Coordinates(10,11),
            Coordinates(10,12),
            Coordinates(10,13),
            Coordinates(10,14),
            Coordinates(10,15),
        ), (Aoc202214(500)  + Aoc202214.parsePath("10,10 -> 10,11 -> 10,15")).bricks)
    }

}