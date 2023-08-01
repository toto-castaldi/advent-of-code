package com.toto_castaldi.aoc

import Aoc201906
import kotlin.test.*

class Aoc201906Test {

    @Test
    fun part1() {
        val aoc = Aoc201906()
        aoc + "COM)B"
        aoc + "B)C"
        aoc + "C)D"
        aoc + "D)E"
        aoc + "E)F"
        aoc + "B)G"
        aoc + "G)H"
        aoc + "D)I"
        aoc + "E)J"
        aoc + "J)K"
        aoc + "K)L"

        assertEquals(42, aoc.totalOrbitsCount())
    }



}