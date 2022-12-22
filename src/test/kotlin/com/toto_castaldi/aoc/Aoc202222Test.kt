package com.toto_castaldi.aoc

import Aoc202222
import kotlin.test.*

class Aoc202222Test {

    @Test
    @Ignore
    fun part1() {
        val aoc = Aoc202222()
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
        
        aoc.navigate("10R5L5R10L4R5L5", 5)

        assertEquals(6032, aoc.finalPassword())
    }

}