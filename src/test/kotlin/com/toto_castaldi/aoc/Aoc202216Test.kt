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
        assertEquals(1651, aoc.mostPressureInMinutes(30))
    }

}