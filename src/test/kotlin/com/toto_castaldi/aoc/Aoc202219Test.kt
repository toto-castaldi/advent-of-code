package com.toto_castaldi.aoc

import Aoc202219
import kotlin.test.*

class Aoc202219Test {

    @Test
    fun run1() {
        val aoc = Aoc202219()
        aoc + "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian."
        aoc + "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian."

        assertEquals(33, aoc.part1(24))
    }

}