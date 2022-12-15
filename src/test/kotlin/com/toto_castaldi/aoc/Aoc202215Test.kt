package com.toto_castaldi.aoc

import Aoc202215
import com.toto_castaldi.common.structure.Coordinates
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Aoc202215Test {

    @Test
    fun part1() {
        val aoc = Aoc202215()
        aoc + "Sensor at x=2, y=18: closest beacon is at x=-2, y=15"
        aoc + "Sensor at x=9, y=16: closest beacon is at x=10, y=16"
        aoc + "Sensor at x=13, y=2: closest beacon is at x=15, y=3"
        aoc + "Sensor at x=12, y=14: closest beacon is at x=10, y=16"
        aoc + "Sensor at x=10, y=20: closest beacon is at x=10, y=16"
        aoc + "Sensor at x=14, y=17: closest beacon is at x=10, y=16"
        aoc + "Sensor at x=8, y=7: closest beacon is at x=2, y=10"
        aoc + "Sensor at x=2, y=0: closest beacon is at x=2, y=10"
        aoc + "Sensor at x=0, y=11: closest beacon is at x=2, y=10"
        aoc + "Sensor at x=20, y=14: closest beacon is at x=25, y=17"
        aoc + "Sensor at x=17, y=20: closest beacon is at x=21, y=22"
        aoc + "Sensor at x=16, y=7: closest beacon is at x=15, y=3"
        aoc + "Sensor at x=14, y=3: closest beacon is at x=15, y=3"
        aoc + "Sensor at x=20, y=1: closest beacon is at x=15, y=3"
        assertEquals(26, aoc.countOccupiedSpotForBeacon(10))
    }

    @Test
    fun ruleAddTest() {
        val aoc = Aoc202215() + "Sensor at x=8, y=7: closest beacon is at x=2, y=10"
        assertTrue { aoc.isOccupied(Coordinates(8, -2)) }
        assertTrue { aoc.isOccupied(Coordinates(7, -1)) }
        assertTrue { aoc.isOccupied(Coordinates(8, -1)) }
        assertTrue { aoc.isOccupied(Coordinates(9, -1)) }
        assertFalse { aoc.isOccupied(Coordinates(6, -1)) }
        assertFalse { aoc.isOccupied(Coordinates(10, -1)) }
        assertTrue { aoc.isOccupied(Coordinates(8, 16)) }
    }

}