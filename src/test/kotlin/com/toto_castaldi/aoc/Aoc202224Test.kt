package com.toto_castaldi.aoc

import Aoc202224
import kotlin.test.*

class Aoc202224Test {
    
    @Test
    fun part1() {
        val aoc = Aoc202224()
        aoc.map(0, 6, "#.######")
        aoc.map(1, 6, "#>>.<^<#")
        aoc.map(2, 6, "#.<..<<#")
        aoc.map(3, 6, "#>v.><>#")
        aoc.map(4, 6, "#<^v^^>#")
        aoc.map(5, 6, "######.#")

        assertEquals(18, aoc.stepToExit())
    }


}