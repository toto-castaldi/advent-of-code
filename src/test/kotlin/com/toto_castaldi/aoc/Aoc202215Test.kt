package com.toto_castaldi.aoc

import Aoc202215
import com.toto_castaldi.common.structure.Coordinates
import kotlin.test.*

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
    fun part2() {
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
        assertEquals(56000011, aoc.distressBeaconTuningFrequency(20))
    }

    @Test
    fun ruleAddTest0() {
        val aoc = Aoc202215() + "Sensor at x=8, y=7: closest beacon is at x=2, y=10"
        assertTrue { aoc.isOccupied(Coordinates(8, -2)) }
        assertTrue { aoc.isOccupied(Coordinates(7, -1)) }
        assertTrue { aoc.isOccupied(Coordinates(8, -1)) }
        assertTrue { aoc.isOccupied(Coordinates(9, -1)) }
        assertFalse { aoc.isOccupied(Coordinates(6, -1)) }
        assertFalse { aoc.isOccupied(Coordinates(10, -1)) }
        assertTrue { aoc.isOccupied(Coordinates(8, 16)) }
    }

    @Test
    fun ruleAddTest1() {
        val aoc = Aoc202215() + "Sensor at x=0, y=4: closest beacon is at x=-1, y=5"
        assertEquals(0, aoc.countOccupiedSpotForBeacon(1))
        assertEquals(1, aoc.countOccupiedSpotForBeacon(2))
        assertEquals(3, aoc.countOccupiedSpotForBeacon(3))
        assertEquals(5, aoc.countOccupiedSpotForBeacon(4))
        assertEquals(2, aoc.countOccupiedSpotForBeacon(5))
        assertEquals(1, aoc.countOccupiedSpotForBeacon(6))
        assertEquals(0, aoc.countOccupiedSpotForBeacon(7))
    }

}