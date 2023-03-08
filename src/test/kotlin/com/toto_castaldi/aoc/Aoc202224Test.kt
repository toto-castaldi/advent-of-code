package com.toto_castaldi.aoc

import Aoc202224
import kotlin.test.*

class Aoc202224Test {
    
    @Test
    @Ignore
    fun part1() {
        val aoc = Aoc202224()
        aoc.map(0, "#.######")
        aoc.map(0, "#>>.<^<#")
        aoc.map(0, "#.<..<<#")
        aoc.map(0, "#>v.><>#")
        aoc.map(0, "#<^v^^>#")
        aoc.map(0, "######.#")

        assertEquals(18, aoc.stepToExit())
    }


}