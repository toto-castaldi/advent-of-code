package com.toto_castaldi.aoc

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
}