package com.toto_castaldi.aoc

import Aoc202222Part1
import kotlin.test.*

class Aoc202222Part1Test {

    @Test
    fun part() {
        val aoc = Aoc202222Part1()
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

        assertEquals(6032, aoc.finalPassword())
    }

}