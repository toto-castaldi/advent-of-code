package com.toto_castaldi.aoc

import Aoc202217
import kotlin.test.*

class Aoc202217Test {

    @Test
    fun part1() {
        val aoc = Aoc202217(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
        assertEquals(3068, aoc.towerHeight(2022, 5))
    }


}