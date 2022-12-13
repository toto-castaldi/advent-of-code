package com.toto_castaldi.common.algo

import com.toto_castaldi.common.BidimentionalNode
import kotlin.test.Test
import kotlin.test.assertEquals

class DiscreteHillClimbingTest {

    @Test
    fun testSlope() {
        val startingHill = BidimentionalNode(1)
        var pointer = startingHill
        for (i in 2..100) {
            val next = BidimentionalNode(i)
            pointer.neighbor(1, 0, next)
            pointer = next
        }
        val path = DiscreteHillClimbing(startingHill).compute()
        assertEquals(100, path.size)

    }
}