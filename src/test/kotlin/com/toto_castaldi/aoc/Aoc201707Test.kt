package com.toto_castaldi.aoc

import Aoc201707
import kotlin.test.*

class Aoc201707Test {

    @Test
    fun part1() {
        val aoc = Aoc201707()
        aoc + "pbga (66)"
        aoc + "xhth (57)"
        aoc + "ebii (61)"
        aoc + "havc (66)"
        aoc + "ktlj (57)"
        aoc + "fwft (72) -> ktlj, cntj, xhth"
        aoc + "qoyq (66)"
        aoc + "padx (45) -> pbga, havc, qoyq"
        aoc + "tknk (41) -> ugml, padx, fwft"
        aoc + "jptl (61)"
        aoc + "ugml (68) -> gyxo, ebii, jptl"
        aoc + "gyxo (61)"
        aoc + "cntj (57)"

        assertEquals("tknk", aoc.bottomProgramName())
    }


}