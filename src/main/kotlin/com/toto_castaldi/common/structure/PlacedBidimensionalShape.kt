package com.toto_castaldi.common.structure

import com.toto_castaldi.common.Numbers

/**
 * the anchor point is top left in the containing box of the shape
 */
class PlacedBidimensionalShape(var anchorPoint: IntCoordinates, var shape: BidimensionalShape) {

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
        val sVl = verticalValues(this)
        val oVl = verticalValues(other)
        val cVl = sVl.intersect(oVl)
        val sHl = horizontalValues(this)
        val oHl = horizontalValues(other)

        val xs = sHl.union(oHl)
        val ys = sVl.union(oVl)

        val replacedLines = mutableMapOf<Int, String>()

        for (y in cVl) {

            val sL:String = fillWithEmpty(xs, y)
            val oL = other.fillWithEmpty(xs, y)

            var result = ""

            for ((i, c) in sL.withIndex()) {
                if (c == BidimensionalShape.POINT_CHAR) {
                    result += c
                } else {
                    result += oL[i]
                }
            }

            replacedLines[y] = result
        }

        val newLines = mutableListOf<String>()

        for (y in ys.toList().sorted()) {
            if (y in replacedLines.keys) {
                newLines.add(replacedLines[y]!!)
            } else if (y in sVl) {
                newLines.add(fillWithEmpty(xs, y))
            } else {
                newLines.add(other.fillWithEmpty(xs, y))
            }
        }

        anchorPoint = IntCoordinates(xs.min(), anchorPoint.y - other.shape.getHeight() )
        shape = BidimensionalShape(newLines.toTypedArray())
        return this
    }

    private fun fillWithEmpty(cHl: Set<Int>, y: Int): String {
        var result = ""
        val my = horizontalValues(this)
        val on = shape.visualDescription[y - anchorPoint.y]
        for (i in cHl.toList().sorted()) {
            if (i in my) {
                result += on[i - anchorPoint.x]
            } else {
                result += BidimensionalShape.NULL_CHAR
            }
        }
        return result
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

    operator fun contains(coordinates: IntCoordinates): Boolean {
        val c = coordinates.clone().move(-anchorPoint.x, -anchorPoint.y)
        if (shape.visualDescription.size > c.y) {
            if (c.y >= 0) {
                val l = shape.visualDescription[c.y]
                if (c.x >= 0 && l.length > c.x) {
                    return l[c.x] == BidimensionalShape.POINT_CHAR
                }
            }
        }
        return false
    }

    /**
     *
         ..#.#..
         #####..
         ..###..
         ...#...
         ..####.
         #######
        ,
        6
        ,
        3

        ->

         ...#...
         ..####.
         #######

     */
    fun subFromBottom(bottomY: Int, height: Int): BidimensionalShape {
        val visualDescription = mutableListOf<String>()
        val mY = maxY()
        for (y in 1 ..  height) {
            val index = shape.visualDescription.size - y + bottomY - mY
            if (index > 0) {
                visualDescription.add(0, shape.visualDescription[index])
            }
        }
        return BidimensionalShape(visualDescription.toTypedArray())
    }

    val minX = { anchorPoint.x }
    val minY = { anchorPoint.y }
    val maxX = { anchorPoint.x + shape.getWidth() - 1}
    val maxY = { anchorPoint.y + shape.getHeight() - 1}

    private val tryMove = { x: Int, y: Int -> PlacedBidimensionalShape( anchorPoint.clone().move(x,y), shape) }

    companion object {
        fun verticalValues(pbs: PlacedBidimensionalShape): IntRange {
            return pbs.anchorPoint.y until pbs.shape.visualDescription.size + pbs.anchorPoint.y
        }

        fun horizontalValues(pbs: PlacedBidimensionalShape): IntRange {
            return pbs.anchorPoint.x until pbs.shape.getWidth() + pbs.anchorPoint.x
        }

    }

}
