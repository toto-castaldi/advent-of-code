package com.toto_castaldi.aoc

import Aoc202225
import kotlin.test.*

class Aoc202225Test {
    
    @Test
    fun conversions() {
        assertEquals(Aoc202225.SNAFUToDecimal("1=-0-2"), 1747)
        assertEquals(Aoc202225.SNAFUToDecimal("12111"), 906)
        assertEquals(Aoc202225.SNAFUToDecimal("2=0="), 198)
        assertEquals(Aoc202225.SNAFUToDecimal("21"), 11)
        assertEquals(Aoc202225.SNAFUToDecimal("2=01"), 201)
        assertEquals(Aoc202225.SNAFUToDecimal("111"), 31)
        assertEquals(Aoc202225.SNAFUToDecimal("20012"), 1257)
        assertEquals(Aoc202225.SNAFUToDecimal("112"), 32)
        assertEquals(Aoc202225.SNAFUToDecimal("1=-1="), 353)
        assertEquals(Aoc202225.SNAFUToDecimal("1-12"), 107)
        assertEquals(Aoc202225.SNAFUToDecimal("12"), 7)
        assertEquals(Aoc202225.SNAFUToDecimal("1="), 3)
        assertEquals(Aoc202225.SNAFUToDecimal("122"), 37)

        assertEquals("2=-1=0", Aoc202225.decimalToSNAFU(4890))
    }


}