package com.toto_castaldi.aoc

import Aoc202217
import kotlin.test.*

class Aoc202217Test {

    @Test
    fun part1() {
        val aoc = Aoc202217(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
        assertEquals(3068, aoc.towerHeight(2022))
    }

    @Test
    fun part2() {
        val aoc = Aoc202217(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
        aoc.patternInfo(200)
        assertEquals(1514285714288, aoc.towerHeight(1000000000000))
    }



}