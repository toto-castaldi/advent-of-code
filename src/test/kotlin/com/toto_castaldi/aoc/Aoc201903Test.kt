package com.toto_castaldi.aoc

import Aoc201903
import kotlin.test.*
class Aoc201903Test {

    @Test
    fun part1_1() {
        val aoc = Aoc201903()
        aoc + "R8,U5,L5,D3"
        aoc + "U7,R6,D4,L4"
        assertEquals(6, aoc.minimalManhattanDistanceOfIntersections())
    }

    @Test
    fun part1_2() {
        val aoc = Aoc201903()
        aoc + "R75,D30,R83,U83,L12,D49,R71,U7,L72"
        aoc + "U62,R66,U55,R34,D71,R55,D58,R83"
        assertEquals(159, aoc.minimalManhattanDistanceOfIntersections())
    }

    @Test
    fun part1_3() {
        val aoc = Aoc201903()
        aoc + "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
        aoc + "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
        assertEquals(135, aoc.minimalManhattanDistanceOfIntersections())
    }


}