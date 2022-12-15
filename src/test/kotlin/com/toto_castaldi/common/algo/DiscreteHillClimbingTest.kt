package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.BidimentionalNode
import kotlin.test.Test
import kotlin.test.assertEquals

class DiscreteHillClimbingTest {

    @Test
    fun testSlope() {
        val startingHill = BidimentionalNode(DistanceAndHeight(1.toDouble(),1.0))
        var pointer = startingHill
        val len = 10
        for (i in 2..10) {
            val next = BidimentionalNode(DistanceAndHeight(i.toDouble(),i.toDouble()))
            pointer.neighbor(1, 0, next)
            pointer = next
        }

        val maxDistance = EuclidianDistance.distance2D(
            pointer.data.distance, pointer.data.height,
            startingHill.data.distance, startingHill.data.height
        )

        val eval: (BidimentionalNode<DiscreteHillClimbingTest.DistanceAndHeight>) -> Double = {
            maxDistance - EuclidianDistance.distance2D(
                pointer.data.distance, pointer.data.height,
                it.data.distance, it.data.height
            )
        }
        val path = DiscreteHillClimbing(startingHill).compute(pointer, eval)

        assertEquals(len, path.size)
    }

    @Test
    fun testPlaneHeightMap() {
        val len = 5
        val matrix = (1..len).map { (1..len).map { 1 }.toList() }

        val (lastNode, _) = BidimentionalNode.build(matrix) { x,y,h -> XYH(x,y,h)  }
        val firstNode = lastNode!!.topLeft()
        BidimentionalNode.printNodes(firstNode) {
            it.h.toString()
        }

        val maxDistance = EuclidianDistance.distance3D(
            len.toDouble(), len.toDouble(), 1.0,
            1.0, 1.0, 1.0
        )

        val eval: (BidimentionalNode<XYH>) -> Double = {
            maxDistance - EuclidianDistance.distance3D(
                lastNode.data.x.toDouble(), lastNode.data.y.toDouble(), lastNode.data.h.toDouble(),
                it.data.x.toDouble(), it.data.y.toDouble(), it.data.h.toDouble(),
            )
        }
        val path = DiscreteHillClimbing(firstNode).compute(lastNode, eval)

        println(path)
        assertEquals(len, path.size)
    }

    data class DistanceAndHeight(val distance: Double, val height: Double)
    data class XYH(val x: Int, val y: Int, val h : Int)
}