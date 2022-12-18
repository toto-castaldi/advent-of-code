package com.toto_castaldi.aoc

import Aoc202217
import Aoc202218
import kotlin.test.*

class Aoc202218Test {

    @Test
    fun part1() {
        var aoc = Aoc202218()
        aoc.add(1,1,1)
        aoc.add(2,1,1)

        assertEquals(10, aoc.countSideExposed())

        aoc = Aoc202218()
        aoc.add(2,2,2)
        aoc.add(1,2,2)
        aoc.add(3,2,2)
        aoc.add(2,1,2)
        aoc.add(2,3,2)
        aoc.add(2,2,1)
        aoc.add(2,2,3)
        aoc.add(2,2,4)
        aoc.add(2,2,6)
        aoc.add(1,2,5)
        aoc.add(3,2,5)
        aoc.add(2,1,5)
        aoc.add(2,3,5)

        assertEquals(10, aoc.countSideExposed())
    }



}