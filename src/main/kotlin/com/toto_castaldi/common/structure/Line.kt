package com.toto_castaldi.common.structure

class Line(val a: IntCoordinates, val b: IntCoordinates) {

    fun atY(y: Int): Double {
        return atY(y.toDouble())
    }

    fun atY(y: Double): Double {
        val deltaX = a.x.toDouble() - b.x.toDouble()
        val deltaY = a.y.toDouble() - b.y.toDouble()
        val aLine : Double = deltaY / deltaX
        val bLine : Double = a.y.toDouble() - (deltaY / deltaX) * a.x.toDouble()
        return (y - bLine) / aLine
    }

}