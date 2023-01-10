package com.toto_castaldi.aoc

import Aoc202212
import kotlin.test.Test
import kotlin.test.assertEquals

class Aoc202212Test {

    @Test
    fun part1Test() {
        val aoc = Aoc202212()
        aoc + "Sabqponm"
        aoc + "abcryxxl"
        aoc + "accszExk"
        aoc + "acctuvwj"
        aoc + "abdefghi"

        assertEquals(31, aoc.shortestPath())

    }

    @Test
    fun part2Test() {
        val aoc = Aoc202212()
        aoc + "Sabqponm"
        aoc + "abcryxxl"
        aoc + "accszExk"
        aoc + "acctuvwj"
        aoc + "abdefghi"

        assertEquals(29, aoc.fewestShortestPath())

    }

}