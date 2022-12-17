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


    operator fun plus(other: PlacedBidimensionalShape): PlacedBidimensionalShape {
        val shiftX = other.anchorPoint.x - anchorPoint.x
        var first = other
        var second = this
        if (this.anchorPoint.y < other.anchorPoint.y) {
            first = this
            second = other
        }

        var newLines = mutableListOf<String>()
        for (line in first.shape.visualDescription) {
            var l = line
            if (shiftX > 0) l = l.padEnd(shiftX + l.length, BidimensionalShape.NULL_CHAR)
            if (shiftX < 0) l = l.padStart(-shiftX + l.length, BidimensionalShape.NULL_CHAR)
            newLines.add(l)
        }
        for (line in second.shape.visualDescription) {
            var l = line
            if (shiftX < 0) l = l.padEnd(-shiftX + l.length, BidimensionalShape.NULL_CHAR)
            if (shiftX > 0) l = l.padStart(shiftX + l.length, BidimensionalShape.NULL_CHAR)
            newLines.add(l)
        }
        anchorPoint.move(shiftX, -other.shape.getHeight())
        shape = BidimensionalShape(newLines.toTypedArray())
        return this
    }

    private fun checkCommonSpots(other: PlacedBidimensionalShape): Boolean {
        val lIndeces = verticalValues(this)
        val otherIndeces = verticalValues(other)
        val checkIndeces = lIndeces.intersect(otherIndeces)
        val shiftX = anchorPoint.x - other.anchorPoint.x
        for (i in checkIndeces) {
            var l = shape.visualDescription[i - anchorPoint.y]
            var r = other.shape.visualDescription[i - other.anchorPoint.y]
            if (shiftX > 0) {
                l = l.padStart(shiftX + l.length, BidimensionalShape.NULL_CHAR)
            } else {
                r = r.padStart(-shiftX + r.length, BidimensionalShape.NULL_CHAR)
            }
            for ((cIndex, c) in l.withIndex()) {
                if (cIndex < r.length && c == BidimensionalShape.POINT_CHAR && r[cIndex] == BidimensionalShape.POINT_CHAR) return true
            }
        }
        return false
    }

    fun intersect(other: PlacedBidimensionalShape): Boolean {
        return checkCommonSpots(other)
    }

    fun onTopOf(other: PlacedBidimensionalShape): Boolean {
        return tryMove(0,1).checkCommonSpots(other)
    }

    operator fun contains(coordinates: Coordinates): Boolean {
        val c = coordinates.clone().move(-anchorPoint.x, -anchorPoint.y)
        if (shape.visualDescription.size > c.y) {
            val l = shape.visualDescription[c.y]
            if (l.length > c.x) {
                return l[c.x] == BidimensionalShape.POINT_CHAR
            }
        }
        return false
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
