package com.toto_castaldi.aoc

import Aoc202222Part2
import kotlin.test.*

class Aoc202222Part2Test {

    @Test
    fun part() {
        val aoc = Aoc202222Part2(Aoc202222Part2.EXAMPLE_MAP)
        aoc + "        ...#"
        aoc + "        .#.."
        aoc + "        #..."
        aoc + "        ...."
        aoc + "...#.......#"
        aoc + "........#..."
        aoc + "..#....#...."
        aoc + "..........#."
        aoc + "        ...#...."
        aoc + "        .....#.."
        aoc + "        .#......"
        aoc + "        ......#."

        aoc.navigate("10R5L5R10L4R5L5")
        assertEquals(5031, aoc.finalPassword())
    }

}