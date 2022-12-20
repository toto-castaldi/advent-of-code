package com.toto_castaldi.aoc

import Aoc202218
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

        assertEquals(4, aoc.nmberAfter0(1000))
        assertEquals(-3, aoc.nmberAfter0(2000))
        assertEquals(2, aoc.nmberAfter0(3000))
    }

}