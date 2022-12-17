package com.toto_castaldi.common.structure

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlacedBidimensionalShapeTest {

    private val freeR = 7
    private val freeL = 1
    private val hLineShape = BidimensionalShape(arrayOf("####"))
    private val cross = BidimensionalShape(arrayOf(
    ".#.",
    "###",
    ".#."
    ))

    fun r(pds : PlacedBidimensionalShape) { pds.moveInBounderies(1,0,freeL..freeR, Int.MIN_VALUE..Int.MAX_VALUE) }
    fun l(pds : PlacedBidimensionalShape) { pds.moveInBounderies(-1,0,freeL..freeR, Int.MIN_VALUE..Int.MAX_VALUE)  }
    fun d(pds : PlacedBidimensionalShape, freeD : Int) { pds.moveInBounderies(0,1,freeL..freeR, Int.MIN_VALUE..freeD)  }

    @Test
    fun testTouch() {
        val freeD = Int.MAX_VALUE
        val pds = PlacedBidimensionalShape(Coordinates(3,0),cross)
        val static = PlacedBidimensionalShape(Coordinates(3,6),cross)

        l(pds)
        d(pds,freeD)

        r(pds)
        d(pds,freeD)

        l(pds)
        d(pds,freeD)

        assertTrue { pds.touch(static) }
    }

    @Test
    fun testMove() {
        val freeD = 3
        val pds = PlacedBidimensionalShape(Coordinates(3,0),hLineShape)

        r(pds)
        d(pds,freeD)

        r(pds)
        d(pds,freeD)

        r(pds)
        d(pds,freeD)

        l(pds)
        d(pds,freeD)

        assertEquals(Coordinates(3,3), pds.anchorPoint)
    }
}