package com.toto_castaldi.aoc

import Aoc201706
import kotlin.test.*

class Aoc201706Test {

    @Test
    @Ignore
    fun part1() {
        val aoc = Aoc201706()
        aoc + 0
        aoc + 2
        aoc + 7
        aoc + 0

        assertEquals(5, aoc.redistributionCountBeforeLoop())
    }


}