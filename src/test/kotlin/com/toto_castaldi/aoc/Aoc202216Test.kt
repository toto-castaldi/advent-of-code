package com.toto_castaldi.aoc

import Aoc202216
import kotlin.test.*

class Aoc202216Test {

    @Test
    @Ignore
    fun part1() {
        val aoc = Aoc202216()
        aoc + "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB"
        aoc + "Valve BB has flow rate=13; tunnels lead to valves CC, AA"
        aoc + "Valve CC has flow rate=2; tunnels lead to valves DD, BB"
        aoc + "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE"
        aoc + "Valve EE has flow rate=3; tunnels lead to valves FF, DD"
        aoc + "Valve FF has flow rate=0; tunnels lead to valves EE, GG"
        aoc + "Valve GG has flow rate=0; tunnels lead to valves FF, HH"
        aoc + "Valve HH has flow rate=22; tunnel leads to valve GG"
        aoc + "Valve II has flow rate=0; tunnels lead to valves AA, JJ"
        aoc + "Valve JJ has flow rate=21; tunnel leads to valve II"
        assertEquals(listOf("AA", "DD", "CC", "BB", "AA", "II", "JJ", "II", "AA", "DD", "EE", "FF", "GG", "HH", "GG", "FF", "EE", "DD", "CC"),
            aoc.computeBestPath("AA", 30).nodes())
        assertEquals(1651, aoc.mostPressureInMinutes("AA", 30))
    }

    @Test
    fun mostPressureInMinutes() {
        var aoc = Aoc202216()
        aoc + "Valve AA has flow rate=10; tunnels lead to valves AA"
        assertEquals(listOf("AA"), aoc.computeBestPath("AA", 1).nodes())

        aoc = Aoc202216()
        aoc + "Valve AA has flow rate=10; tunnels lead to valves AA"
        assertEquals(listOf("AA"), aoc.computeBestPath("AA", 2).nodes())

        aoc = Aoc202216()
        aoc + "Valve AA has flow rate=10; tunnels lead to valves AA"
        assertEquals(listOf("AA", "AA"), aoc.computeBestPath("AA", 3).nodes())

        aoc = Aoc202216()
        aoc + "Valve AA has flow rate=10; tunnels lead to valves AA"
        assertEquals(listOf("AA", "AA", "AA"), aoc.computeBestPath("AA", 4).nodes())

        aoc = Aoc202216()
        aoc + "Valve AA has flow rate=10; tunnels lead to valves AA"
        assertEquals(0, aoc.mostPressureInMinutes("AA", 1))

        aoc = Aoc202216()
        aoc + "Valve AA has flow rate=10; tunnels lead to valves AA"
        assertEquals(10, aoc.mostPressureInMinutes("AA", 2))

        aoc = Aoc202216()
        aoc + "Valve AA has flow rate=10; tunnels lead to valves AA"
        assertEquals(20, aoc.mostPressureInMinutes("AA", 3))

        aoc = Aoc202216()
        aoc + "Valve AA has flow rate=10; tunnels lead to valves AA"
        assertEquals(90, aoc.mostPressureInMinutes("AA", 10))
    }

    @Test
    fun navigation() {
        var aoc = Aoc202216()
        aoc + "Valve AA has flow rate=0; tunnels lead to valves BB"
        aoc + "Valve BB has flow rate=0; tunnels lead to valves AA"
        assertEquals(listOf("AA"), aoc.computeBestPath("AA", 1).nodes())

        aoc = Aoc202216()
        aoc + "Valve AA has flow rate=0; tunnels lead to valves BB"
        aoc + "Valve BB has flow rate=0; tunnels lead to valves AA"
        assertEquals(listOf("AA", "BB"), aoc.computeBestPath("AA", 2).nodes())

        aoc = Aoc202216()
        aoc + "Valve AA has flow rate=0; tunnels lead to valves BB"
        aoc + "Valve BB has flow rate=13; tunnels lead to valves AA"
        assertEquals(listOf("AA","BB","AA","BB"), aoc.computeBestPath("AA", 5).nodes())

        aoc = Aoc202216()
        aoc + "Valve AA has flow rate=0; tunnels lead to valves BB"
        aoc + "Valve BB has flow rate=0; tunnels lead to valves AA"
        assertEquals(listOf("AA","BB","AA","BB", "AA"), aoc.computeBestPath("AA", 5).nodes())

        aoc = Aoc202216()
        aoc + "Valve AA has flow rate=0; tunnels lead to valves BB"
        aoc + "Valve BB has flow rate=0; tunnels lead to valves CC"
        aoc + "Valve CC has flow rate=0; tunnels lead to valves AA"
        assertEquals(listOf("AA","BB","CC","AA", "BB"), aoc.computeBestPath("AA", 5).nodes())

        //le valvole già aperte non fanno sprecare tempo per aprirle ancora
        aoc = Aoc202216()
        aoc + "Valve AA has flow rate=0; tunnels lead to valves BB"
        aoc + "Valve BB has flow rate=1; tunnels lead to valves CC"
        aoc + "Valve CC has flow rate=2; tunnels lead to valves AA"
        assertEquals(listOf("AA","BB","CC", "AA", "BB", "CC"), aoc.computeBestPath("AA", 8).nodes())
    }

}