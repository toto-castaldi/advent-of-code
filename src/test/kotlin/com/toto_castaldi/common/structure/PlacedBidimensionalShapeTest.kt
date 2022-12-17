package com.toto_castaldi.common.structure

import kotlin.test.*

class PlacedBidimensionalShapeTest {

    private val freeR = 7
    private val freeL = 1
    private val hLineShape = BidimensionalShape(arrayOf("####"))
    private val crossShape = BidimensionalShape(arrayOf(
    ".#.",
    "###",
    ".#."
    ))

    private fun r(pds : PlacedBidimensionalShape) { pds.moveInBounderies(1,0,freeL..freeR, Int.MIN_VALUE..Int.MAX_VALUE) }
    private fun l(pds : PlacedBidimensionalShape) { pds.moveInBounderies(-1,0,freeL..freeR, Int.MIN_VALUE..Int.MAX_VALUE)  }
    private fun d(pds : PlacedBidimensionalShape, freeD : Int) { pds.moveInBounderies(0,1,freeL..freeR, Int.MIN_VALUE..freeD)  }

    @Test
    @Ignore
    fun testIntersects() {
        val line = PlacedBidimensionalShape(Coordinates(1,2), hLineShape)
        val cross = PlacedBidimensionalShape(Coordinates(1,0), crossShape)
        assertFalse { cross.intesects(line) }
        assertFalse { line.intesects(cross) }

        cross.move(0,1)
        assertTrue { cross.intesects(line) }
        assertTrue { line.intesects(cross) }
    }

    @Test
    @Ignore
    fun testOnTop() {
        val line = PlacedBidimensionalShape(Coordinates(1,2), hLineShape)
        val cross = PlacedBidimensionalShape(Coordinates(1,-1), crossShape)
        assertFalse { line.onTopOf(cross) }
        assertFalse { cross.onTopOf(line) }

        cross.move(0,1)
        assertTrue { cross.onTopOf(line) }
        assertFalse { line.intesects(cross) }
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