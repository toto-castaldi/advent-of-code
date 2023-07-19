package com.toto_castaldi.aoc

import Aoc201803
import kotlin.test.*


class Aoc201803Test {

    @Test
    fun part1() {
        val aoc = Aoc201803()
        aoc + "#1 @ 1,3: 4x4"
        aoc + "#2 @ 3,1: 4x4"
        aoc + "#3 @ 5,5: 2x2"

        assertEquals(4, aoc.squareInchesWithTwoOrMoreClaims())
    }


}