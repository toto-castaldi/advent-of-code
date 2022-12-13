package com.toto_castaldi.aoc

import Aoc202213
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Aoc202213Test {

    @Test
    fun parsePacket() {
        val aoc202213 = Aoc202213.build(listOf("[1,[2,[3,[4,[5,6]]]],7,8,9]","[1,[2,[3,[4,[5,6]]]],0,8,9]"))
        assertEquals("[1,[2,[3,[4,[5,6]]]],7,8,9]", aoc202213.getPair(1).left().toString())
    }

    @Test
    fun listParseInteger() {
        val aoc202213 = Aoc202213.build(listOf("[[1],[2,3,4]]" ,"[[1],4]"))
        assertEquals("[[1],[2,3,4]]", aoc202213.getPair(1).left().toString())
        assertEquals("[[1],4]", aoc202213.getPair(1).rigth().toString())
    }

    @Test
    fun bothInteger() {
        val aoc202213 = Aoc202213()
        var pair = aoc202213.addPair()
        pair.left().add(1).add(3)
        pair.rigth().add(1).add(5)
        assertTrue { pair.isInRightOrder() }
    }

    @Test
    fun intList() {
        val aoc202213 = Aoc202213.build(listOf("[1]" ,"[[2]]"))
        assertTrue { aoc202213.getPair(1).isInRightOrder() }
    }

    @Test
    fun emptyL() {
        val aoc202213 = Aoc202213.build(listOf("[]" ,"[1]"))
        assertTrue { aoc202213.getPair(1).isInRightOrder() }
    }

    @Test
    fun emptyLList5() {
        val aoc202213 = Aoc202213.build(listOf("[]" ,"[[[[[1]]]]]"))
        assertTrue { aoc202213.getPair(1).isInRightOrder() }
    }

    @Test
    fun emptyR() {
        val aoc202213 = Aoc202213.build(listOf("[1]" ,"[0]"))
        assertFalse { aoc202213.getPair(1).isInRightOrder() }
    }


    @Test
    fun intListList() {
        val aoc202213 = Aoc202213.build(listOf("[1]" ,"[[[[2]]]]"))
        assertTrue { aoc202213.getPair(1).isInRightOrder() }
    }

    @Test
    fun listInt() {
        val aoc202213 = Aoc202213.build(listOf("[[[[1]]]]" ,"[2]"))
        assertTrue { aoc202213.getPair(1).isInRightOrder() }
    }

    @Test
    fun leftRunsOut() {
        val pair = Aoc202213().addPair()
        pair.left().add(7).add(7).add(7).add(7)
        pair.rigth().add(7).add(7).add(7)
        assertFalse { pair.isInRightOrder() }
    }

    @Test
    fun rightRunsOut() {
        val pair = Aoc202213().addPair()
        pair.left().add(7).add(7)
        pair.rigth().add(7).add(7).add(7)
        assertTrue { pair.isInRightOrder() }
    }

    @Test
    fun part1() {
        val aoc202213 = Aoc202213()
        var pair = aoc202213.addPair()
        pair.left() + 1 + 1 + 3 + 1 + 1
        pair.rigth() + 1 + 1 + 5 + 1 + 1
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