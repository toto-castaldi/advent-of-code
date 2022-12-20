package com.toto_castaldi.aoc

import Aoc202220
import kotlin.test.*

class Aoc202220Test {


    @Test
    fun part1() {
        val aoc = Aoc202220()
        aoc + 1
        aoc + 2
        aoc + -3
        aoc + 3
        aoc + -2
        aoc + 0
        aoc + 4

        aoc.arrangeAllElements()

        assertEquals(4, aoc.numberAfter0(1000))
        assertEquals(-3, aoc.numberAfter0(2000))
        assertEquals(2, aoc.numberAfter0(3000))

        assertEquals(3,aoc.numberAfter0(1000)!! + aoc.numberAfter0(2000)!! + aoc.numberAfter0(3000)!!)
    }

}