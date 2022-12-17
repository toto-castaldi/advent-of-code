package com.toto_castaldi.common.structure

import com.toto_castaldi.common.Numbers

class PlacedRectangle(val x: Int, val y: Int, val w: Int, val h: Int) {

    fun touch(other: PlacedRectangle): Boolean {
        return Numbers.touch(xBounderies(), other.xBounderies()) || Numbers.touch(yBounderies(), other.yBounderies())
    }

    private fun yBounderies(): IntRange {
        return y..y+h
    }

    private fun xBounderies(): IntRange {
        return x..x+w
    }

    fun intersects(other: PlacedRectangle): Boolean {
        return Numbers.intersects(xBounderies(), other.xBounderies()) || Numbers.intersects(yBounderies(), other.yBounderies())
    }

}
