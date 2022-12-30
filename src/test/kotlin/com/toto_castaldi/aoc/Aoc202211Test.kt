package com.toto_castaldi.aoc

import Aoc202211
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class Aoc202211Test {

    @Test
    fun testModule() {
        val a = Random.nextInt(1, 100)
        val n = Random.nextInt(1, 100)
        val k = Random.nextInt(1, 100)
        assertEquals((a % (k * n)) % n , a % n)
    }

    @Test
    fun part2Test() {
        val lines = listOf(
            "Monkey 0:",
            "   Starting items: 79, 98",
            "   Operation: new = old * 19",
            "   Test: divisible by 23",
            "       If true: throw to monkey 2",
            "       If false: throw to monkey 3",
            "Monkey 1:",
            "   Starting items: 54, 65, 75, 74",
            "   Operation: new = old + 6",
            "   Test: divisible by 19",
            "       If true: throw to monkey 2",
            "       If false: throw to monkey 0",
            "Monkey 2:",
            "   Starting items: 79, 60, 97",
            "   Operation: new = old * old",
            "   Test: divisible by 13",
            "       If true: throw to monkey 1",
            "       If false: throw to monkey 3",
            "Monkey 3:",
            "   Starting items: 74",
            "   Operation: new = old + 3",
            "   Test: divisible by 17",
            "       If true: throw to monkey 0",
            "       If false: throw to monkey 1",
        )
        val aoc = Aoc202211(lines.chunked(6))
        assertEquals(2713310158, aoc.part2())
    }

}