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
    fun testIntersects() {
        val line = PlacedBidimensionalShape(Coordinates(2,1), hLineShape)
        val cross = PlacedBidimensionalShape(Coordinates(0,0), crossShape)

        assertTrue { line.intesects(cross) }
        assertTrue { cross.intesects(line) }

        line.move(1,0)

        assertFalse { line.intesects(cross) }
        assertFalse { cross.intesects(line) }

        line.move(10,20)

        assertFalse { line.intesects(cross) }
        assertFalse { cross.intesects(line) }
    }

    @Test
    fun testOnTop() {
        val cross = PlacedBidimensionalShape(Coordinates(0,0), crossShape)
        val line = PlacedBidimensionalShape(Coordinates(2,2), hLineShape)

        assertFalse { line.onTopOf(cross) }
        assertTrue { cross.onTopOf(line) }

        cross.move(0,-1)
        assertFalse { cross.onTopOf(line) }
        assertFalse { line.onTopOf(cross) }

        cross.move(-1,1)
        assertFalse { cross.onTopOf(line) }
        assertFalse { line.onTopOf(cross) }
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

    @Test
    fun testSum() {
        val line1 = PlacedBidimensionalShape(Coordinates(-1,-1), hLineShape)
        val line0 = PlacedBidimensionalShape(Coordinates(0,0), hLineShape)
        val stack = PlacedBidimensionalShape(Coordinates(0,1), hLineShape)

        assertEquals(4, stack.shape.getWidth())
        assertEquals(1, stack.shape.getHeight())

        stack + line0

        assertEquals(4, stack.shape.getWidth())
        assertEquals(2, stack.shape.getHeight())

        stack + line1

        assertEquals(5, stack.shape.getWidth())
        assertEquals(3, stack.shape.getHeight())
    }
}