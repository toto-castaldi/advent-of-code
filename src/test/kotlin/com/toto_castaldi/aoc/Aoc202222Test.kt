package com.toto_castaldi.aoc

import Aoc202222
import kotlin.test.*

class Aoc202222Test {

    @Test
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
        
        aoc.navigate("10R5L5R10L4R5L5")

        assertEquals(6032, aoc.finalPassword())
    }

    @Test
    @Ignore
    fun part2() {
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

        aoc.set3DConf(Aoc202222.EXAMPLE_MAP)
        aoc.navigate("10R5L5R10L4R5L5", 6)
        assertEquals(5031, aoc.finalPassword())
    }

}