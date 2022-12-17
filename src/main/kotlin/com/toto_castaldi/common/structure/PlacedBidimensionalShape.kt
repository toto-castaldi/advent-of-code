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

    private fun checkCommonSpots(other: PlacedBidimensionalShape): Boolean {
        val lIndeces = verticalValues(this)
        val otherIndeces = verticalValues(other)
        val checkIndeces = lIndeces.intersect(otherIndeces)
        println("A ###########")
        println(anchorPoint)
        println(lIndeces)
        println("B ############")
        println(other.anchorPoint)
        println(otherIndeces)
        println("COMMO ############")
        println(checkIndeces)
        val shiftX = anchorPoint.x - other.anchorPoint.x
        println("shift $shiftX")
        for (i in checkIndeces) {
            var l = shape.visualDescription[i - anchorPoint.y]
            var r = other.shape.visualDescription[i - other.anchorPoint.y]
            if (shiftX < 0) {
                r = r.padStart(-shiftX + r.length, BidimensionalShape.NULL_CHAR)
            } else {
                l = l.padStart(shiftX + l.length, BidimensionalShape.NULL_CHAR)
            }

            println("$l vs $r")
            for ((cIndex, c) in l.withIndex()) {
                if (cIndex < r.length && c == '#' && r[cIndex] == '#') return true
            }
        }
        return false
    }

    fun intesects(other: PlacedBidimensionalShape): Boolean {
        return checkCommonSpots(other)
    }

    fun onTopOf(other: PlacedBidimensionalShape): Boolean {
        return tryMove(0,1).checkCommonSpots(other)
    }

    val minX = { anchorPoint.x }
    val minY = { anchorPoint.y }
    val maxX = { anchorPoint.x + shape.getWidth() - 1}
    val maxY = { anchorPoint.y + shape.getHeight() - 1}

    private val tryMove = { x: Int, y: Int -> PlacedBidimensionalShape( anchorPoint.clone().move(x,y), shape) }

    companion object {
        fun verticalValues(pbs: PlacedBidimensionalShape): IntRange {
            return pbs.anchorPoint.y ..pbs.shape.visualDescription.size + pbs.anchorPoint.y  - 1
        }
    }

}
