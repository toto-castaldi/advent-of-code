package com.toto_castaldi.common.structure

/**
 * the anchor point is top left in the containing box of the shapr
 */
class PlacedBidimensionalShape(val anchorPoint: Coordinates, val shape: BidimensionalShape) {

    fun move(x: Int, y: Int) {
        anchorPoint.move(x,y)
    }

    fun height(): Int {
        TODO("Not yet implemented")
    }

    fun touch(stack: PlacedBidimensionalShape): Boolean {
        TODO("Not yet implemented")
    }


    operator fun plus(currentPiece: PlacedBidimensionalShape): PlacedBidimensionalShape {
        TODO("Not yet implemented")
        return this
    }

    val maxX = { anchorPoint.x + shape.width - 1}
    val maxY = { anchorPoint.y + shape.height - 1}


    val tryMove = { x: Int, y: Int -> PlacedBidimensionalShape( anchorPoint.clone().move(x,y), shape) }

}
