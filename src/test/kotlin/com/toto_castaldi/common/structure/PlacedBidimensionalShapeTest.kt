package com.toto_castaldi.common.structure

import kotlin.test.Test
import kotlin.test.assertEquals

class PlacedBidimensionalShapeTest {

    private val hLineShape = BidimensionalShape(arrayOf("####"))

    @Test
    fun testMove() {
        val freeR = 7
        val freeL = 1
        val freeD = 3
        val pds = PlacedBidimensionalShape(Coordinates(3,0),hLineShape)

        fun r() { if (pds.tryMove(1,0).maxX() in freeL .. freeR) pds.move(1,0) }
        fun l() { if (pds.tryMove(-1,0).maxX() in freeL .. freeR) pds.move(-1,0) }
        fun d() { if (pds.tryMove(0,1).maxY() <= freeD) pds.move(0,1) }

        r()
        d()

        r()
        d()

        r()
        d()

        l()
        d()

        assertEquals(Coordinates(3,3), pds.anchorPoint)
    }
}