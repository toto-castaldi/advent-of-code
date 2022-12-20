package com.toto_castaldi.aoc

import Aoc202220
import kotlin.test.*

class Aoc202220Test {


    @Test
    fun part1() {
        val aoc = Aoc202220()
        aoc + 1
        aoc + 2
        aoc + -3
        aoc + 3
        aoc + -2
        aoc + 0
        aoc + 4

        aoc.arrangeAllElements()

        assertEquals(4, aoc.numberAfter0(1000))
        assertEquals(-3, aoc.numberAfter0(2000))
        assertEquals(2, aoc.numberAfter0(3000))

        assertEquals(3,aoc.numberAfter0(1000)!! + aoc.numberAfter0(2000)!! + aoc.numberAfter0(3000)!!)
    }

    @Test
    fun part2() {
        val aoc = Aoc202220()
        aoc + 1
        aoc + 2
        aoc + -3
        aoc + 3
        aoc + -2
        aoc + 0
        aoc + 4

        aoc.decryptionKey(811589153)

        println("Initial arrangement:")
        println(aoc.circle.values(aoc.circle.findBy { it.value ==  811589153L}!!))


        for (i in 1 .. 10) {
            aoc.arrangeAllElements()
            println("After $i rounds of mixing:")
            println(aoc.circle.values(aoc.circle.findBy { it.value ==  811589153L}!!))
        }

        assertEquals(811589153, aoc.numberAfter0(1000))
        assertEquals(2434767459, aoc.numberAfter0(2000))
        assertEquals(-1623178306, aoc.numberAfter0(3000))

        assertEquals(1623178306, aoc.numberAfter0(1000)!! + aoc.numberAfter0(2000)!! + aoc.numberAfter0(3000)!!)

    }

}