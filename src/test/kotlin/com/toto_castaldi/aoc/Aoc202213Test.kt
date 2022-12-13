package com.toto_castaldi.aoc

import Aoc202213
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Aoc202213Test {

    @Test
    fun part1() {
        val aoc202213 = Aoc202213()
        var pair = aoc202213.addPair()
        pair.left().add(1).add(1).add(3).add(1).add(1)
        pair.rigth().add(1).add(1).add(5).add(1).add(1)
        assertTrue { pair.isInRightOrder() }

        pair = aoc202213.addPair()
        pair.left().push().add(1)
        pair.rigth().push().add(2).add(3).add(4)
        assertTrue { pair.isInRightOrder() }

        pair = aoc202213.addPair()
        pair.left().add(9)
        pair.rigth().push().add(8).add(7).add(6)
        assertFalse { pair.isInRightOrder() }

        pair = aoc202213.addPair()
        pair.left().push().add(4).add(4).pop().add(4).add(4)
        pair.rigth().push().add(4).add(4).pop().add(4).add(4).add(4)
        assertTrue { pair.isInRightOrder() }

        pair = aoc202213.addPair()
        pair.left().add(7).add(7).add(7).add(7)
        pair.rigth().add(7).add(7).add(7)
        assertFalse { pair.isInRightOrder() }

        pair = aoc202213.addPair()
        pair.rigth().add(3)
        assertTrue { pair.isInRightOrder() }

        pair = aoc202213.addPair()
        pair.left().push().push().pop().pop()
        pair.rigth().push().pop()
        assertFalse { pair.isInRightOrder() }

        pair = aoc202213.addPair()
        pair.left().add(1).push().add(2).push().add(3).push().add(4).push().add(5).add(6).add(7).pop().pop().pop().pop().add(8).add(9)
        pair.rigth().add(1).push().add(2).push().add(3).push().add(4).push().add(5).add(6).add(0).pop().pop().pop().pop().add(8).add(9)
        assertFalse { pair.isInRightOrder() }

        assertEquals(listOf(1,2,4,6), aoc202213.correctOrderIndexes())
        assertEquals(13, aoc202213.correctOrderIndexes().fold(0) { acc, value -> acc + value})
    }

}