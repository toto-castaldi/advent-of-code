package com.toto_castaldi.aoc

import Aoc201705
import kotlin.test.*

class Aoc201705Test {
    @Test
    fun part1() {
        val aoc = Aoc201705()
        aoc + 0
        aoc + 3
        aoc + 0
        aoc + 1
        aoc + -3

        assertEquals(5, aoc.stepToExitRule(false))
    }

    @Test
    fun part2() {
        val aoc = Aoc201705()
        aoc + 0
        aoc + 3
        aoc + 0
        aoc + 1
        aoc + -3

        assertEquals(10, aoc.stepToExitRule(true))
    }


}