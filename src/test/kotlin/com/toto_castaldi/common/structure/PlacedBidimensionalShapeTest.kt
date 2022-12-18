package com.toto_castaldi.common.structure

import kotlin.test.*

class PlacedBidimensionalShapeTest {

    private val freeR = 7
    private val freeL = 1
    private val hLineShape = BidimensionalShape(arrayOf("####"))
    private val hLongLineShape = BidimensionalShape(arrayOf("#######"))
    private val crossShape = BidimensionalShape(arrayOf(
    ".#.",
    "###",
    ".#."
    ))
    private val lShape = BidimensionalShape(arrayOf(
    "..#",
    "..#",
    "###"
    ))

    private fun r(pds : PlacedBidimensionalShape) { pds.moveInBounderies(1,0,freeL..freeR, Int.MIN_VALUE..Int.MAX_VALUE) }
    private fun l(pds : PlacedBidimensionalShape) { pds.moveInBounderies(-1,0,freeL..freeR, Int.MIN_VALUE..Int.MAX_VALUE)  }
    private fun d(pds : PlacedBidimensionalShape) { pds.moveInBounderies(0,1,freeL..freeR, Int.MIN_VALUE..3)  }

    @Test
    fun testIntersects() {
        val line = PlacedBidimensionalShape(Coordinates(2,1), hLineShape)
        val cross = PlacedBidimensionalShape(Coordinates(0,0), crossShape)

        assertTrue { line.intersect(cross) }
        assertTrue { cross.intersect(line) }

        line.move(1,0)

        assertFalse { line.intersect(cross) }
        assertFalse { cross.intersect(line) }

        line.move(10,20)

        assertFalse { line.intersect(cross) }
        assertFalse { cross.intersect(line) }
    }

    @Test
    fun testMove() {
        val pds = PlacedBidimensionalShape(Coordinates(3,0),hLineShape)

        r(pds)
        d(pds)

        r(pds)
        d(pds)

        r(pds)
        d(pds)

        l(pds)
        d(pds)

        assertEquals(Coordinates(3,3), pds.anchorPoint)
    }

    @Test
    fun testSumLCross(){
        val cross =  PlacedBidimensionalShape(Coordinates(2,2), crossShape)
        val l =  PlacedBidimensionalShape(Coordinates(0,0), lShape)

        cross + l

        assertEquals(5, cross.shape.getHeight())
        assertEquals("..#..", cross.shape.visualDescription[0])
        assertEquals("..#..", cross.shape.visualDescription[1])
        assertEquals("####.", cross.shape.visualDescription[2])
        assertEquals("..###", cross.shape.visualDescription[3])
        assertEquals("...#.", cross.shape.visualDescription[4])
    }

    @Test
    fun testSum202217() {
        var rock =  PlacedBidimensionalShape(Coordinates(0,3), hLineShape)
        var stack =  PlacedBidimensionalShape(Coordinates(0,4), hLongLineShape)

        stack + rock

        assertEquals(2, stack.shape.getHeight())
        assertEquals("####...", stack.shape.visualDescription[0])
        assertEquals("#######", stack.shape.visualDescription[1])

        rock =  PlacedBidimensionalShape(Coordinates(1,3), hLineShape)
        stack =  PlacedBidimensionalShape(Coordinates(0,4), hLongLineShape)

        stack + rock

        assertEquals(".####..", stack.shape.visualDescription[0])
        assertEquals("#######", stack.shape.visualDescription[1])

    }

    @Test
    fun testSumA() {
        val lineA = PlacedBidimensionalShape(Coordinates(0,0), hLineShape)
        val stack = PlacedBidimensionalShape(Coordinates(2,1), hLineShape)

        stack + lineA

        assertEquals(2, stack.shape.getHeight())
        assertEquals("####..", stack.shape.visualDescription[0])
        assertEquals("..####", stack.shape.visualDescription[1])
    }

    @Test
    fun testIn() {
        val cross = PlacedBidimensionalShape(Coordinates(0,0), crossShape)
        assertFalse { Coordinates(0,2) in cross }
        assertTrue { Coordinates(1,1) in cross }
    }
}