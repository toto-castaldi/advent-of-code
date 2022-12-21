package com.toto_castaldi.aoc

import Aoc202221
import kotlin.test.*

class Aoc202221Test {


    @Test
    fun part1() {
        val aoc = Aoc202221()
        aoc + "root: pppw + sjmn"
        aoc + "dbpl: 5"
        aoc + "cczh: sllz + lgvd"
        aoc + "zczc: 2"
        aoc + "ptdq: humn - dvpt"
        aoc + "dvpt: 3"
        aoc + "lfqf: 4"
        aoc + "humn: 5"
        aoc + "ljgn: 2"
        aoc + "sjmn: drzm * dbpl"
        aoc + "sllz: 4"
        aoc + "pppw: cczh / lfqf"
        aoc + "lgvd: ljgn * ptdq"
        aoc + "drzm: hmdt - zczc"
        aoc + "hmdt: 32"

        assertEquals(152, aoc.monkeyYells("root"))
    }

    @Test
    fun part2() {
        val aoc = Aoc202221()
        aoc + "root: pppw + sjmn"
        aoc + "dbpl: 5"
        aoc + "cczh: sllz + lgvd"
        aoc + "zczc: 2"
        aoc + "ptdq: humn - dvpt"
        aoc + "dvpt: 3"
        aoc + "lfqf: 4"
        aoc + "humn: 5"
        aoc + "ljgn: 2"
        aoc + "sjmn: drzm * dbpl"
        aoc + "sllz: 4"
        aoc + "pppw: cczh / lfqf"
        aoc + "lgvd: ljgn * ptdq"
        aoc + "drzm: hmdt - zczc"
        aoc + "hmdt: 32"

        assertEquals(301,aoc.resolve("root", null))
    }

}