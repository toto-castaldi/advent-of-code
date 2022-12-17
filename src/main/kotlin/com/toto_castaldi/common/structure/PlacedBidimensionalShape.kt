package com.toto_castaldi.common.structure

import com.toto_castaldi.common.Numbers

/**
 * the anchor point is top left in the containing box of the shapr
 */
class PlacedBidimensionalShape(val anchorPoint: Coordinates, var shape: BidimensionalShape) {

    fun move(x: Int, y: Int) {
        anchorPoint.move(x,y)
    }

    fun moveInBounderies(x: Int, y: Int, boundX: IntRange, boundY: IntRange) {
        val tm = tryMove(x, y)
        if (Numbers.includes(boundX, tm.minX() .. tm.maxX()) && Numbers.includes(boundY, tm.minY() .. tm.maxY())) {
            move(x, y)
        }
    }


    operator fun plus(currentPiece: PlacedBidimensionalShape): PlacedBidimensionalShape {
        var newLines = shape.visualDescription.toMutableList()
        for (i in currentPiece.shape.visualDescription.indices.reversed()) {
            newLines.add(0, currentPiece.shape.visualDescription[i])
        }
        anchorPoint.move(0, -currentPiece.shape.getHeight())
        shape = BidimensionalShape(newLines.toTypedArray())
        return this
    }

    private fun checkCommonSpots(other: PlacedBidimensionalShape, delta : Int): Boolean {

        val lIndeces = verticalValues(this)
        val otherIndeces = verticalValues(other, delta)
        val checkIndeces = lIndeces.intersect(otherIndeces)
        println(anchorPoint)
        println(shape.getHeight())
        println(lIndeces)
        println("############")
        println(delta)
        println(other.anchorPoint)
        println(other.shape.getHeight())
        println(otherIndeces)
        println(checkIndeces)
        for (i in checkIndeces) {
            val l = shape.visualDescription[i - anchorPoint.y]
            val r = other.shape.visualDescription[i - anchorPoint.y]
            for ((cIndex, c) in l.withIndex()) {
                if (cIndex < r.length && c == '#' && r[cIndex] == '#') return true
            }
        }
        return false
    }

    fun intesects(other: PlacedBidimensionalShape): Boolean {
        return checkCommonSpots(other, 0)
    }

    fun onTopOf(other: PlacedBidimensionalShape): Boolean {
        return checkCommonSpots(other, 1)
    }

    val minX = { anchorPoint.x }
    val minY = { anchorPoint.y }
    val maxX = { anchorPoint.x + shape.getWidth() - 1}
    val maxY = { anchorPoint.y + shape.getHeight() - 1}

    private val tryMove = { x: Int, y: Int -> PlacedBidimensionalShape( anchorPoint.clone().move(x,y), shape) }

    companion object {
        fun verticalValues(pbs: PlacedBidimensionalShape, delta: Int = 0): IntRange {
            return pbs.anchorPoint.y + delta..pbs.shape.visualDescription.size + pbs.anchorPoint.y + delta
        }
    }

}
