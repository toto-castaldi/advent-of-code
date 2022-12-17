package com.toto_castaldi.common.structure

import com.toto_castaldi.common.Numbers

/**
 * the anchor point is top left in the containing box of the shapr
 */
class PlacedBidimensionalShape(val anchorPoint: Coordinates, val shape: BidimensionalShape) {

    fun move(x: Int, y: Int) {
        anchorPoint.move(x,y)
    }

    fun touch(other: PlacedBidimensionalShape): Boolean {
        return shape.containingSquare().touch(other.shape.containingSquare()) || shape.containingSquare().intersects(other.shape.containingSquare())
    }


    operator fun plus(currentPiece: PlacedBidimensionalShape): PlacedBidimensionalShape {
        TODO("Not yet implemented")
        return this
    }

    fun moveInBounderies(x: Int, y: Int, boundX: IntRange, boundY: IntRange) {
        val tm = tryMove(x, y)
        if (Numbers.includes(boundX, tm.minX() .. tm.maxX()) && Numbers.includes(boundY, tm.minY() .. tm.maxY())) {
            move(x, y)
        }
    }

    val minX = { anchorPoint.x }
    val minY = { anchorPoint.y }
    val maxX = { anchorPoint.x + shape.width - 1}
    val maxY = { anchorPoint.y + shape.height - 1}


    private val tryMove = { x: Int, y: Int -> PlacedBidimensionalShape( anchorPoint.clone().move(x,y), shape) }

}
