package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.BidimentionalNode
import java.lang.Math.pow
import kotlin.math.sqrt
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class DiscreteHillClimbingTest {

    @Test
    fun testSlope() {
        val startingHill = BidimentionalNode(DistanceAndHeight(1,1))
        var pointer = startingHill
        for (i in 2..100) {
            val next = BidimentionalNode(DistanceAndHeight(i,i))
            pointer.neighbor(1, 0, next)
            pointer = next
        }

        val path = DiscreteHillClimbing(startingHill).compute(pointer) {
            sqrt(
                pow(pointer.data.distance.toDouble() - it.data.distance.toDouble(), 2.toDouble()) +
                   pow(pointer.data.height.toDouble() - it.data.height.toDouble(), 2.toDouble())
            ).toInt()
        }
        assertEquals(100, path.size)

    }

    data class DistanceAndHeight(val distance: Int, val height: Int) {

    }
}