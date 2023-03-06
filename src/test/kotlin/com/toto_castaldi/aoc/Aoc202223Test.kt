package com.toto_castaldi.aoc

import Aoc202223
import kotlin.test.*

class Aoc202223Test {


    @Test
    @Ignore
    fun part1() {
        val aoc = Aoc202223()
        aoc.map(0, "....#..")
        aoc.map(1, "..###.#")
        aoc.map(2, "#...#.#")
        aoc.map(3, ".#...##")
        aoc.map(4, "#.###..")
        aoc.map(5, "##.#.##")
        aoc.map(6, ".#..#..")

        assertEquals(110, aoc.emptyGroud(10))
    }

}