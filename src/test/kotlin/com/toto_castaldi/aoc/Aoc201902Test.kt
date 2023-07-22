package com.toto_castaldi.aoc

import Aoc201902
import kotlin.test.*
class Aoc201902Test {

    @Test
    fun part1() {
        val aoc = Aoc201902()
        assertEquals(listOf(2,0,0,0,99), aoc.execute("1,0,0,0,99"))
        assertEquals(listOf(2,3,0,6,99), aoc.execute("2,3,0,3,99"))
        assertEquals(listOf(2,4,4,5,99,9801), aoc.execute("2,4,4,5,99,0"))
        assertEquals(listOf(30,1,1,4,2,5,6,0,99), aoc.execute("1,1,1,4,99,5,6,0,99"))
    }

}