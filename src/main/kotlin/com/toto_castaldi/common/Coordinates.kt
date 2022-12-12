package com.toto_castaldi.common

data class Coordinates(var x: Int, var y: Int) {
    fun move(stepX: Int, stepY: Int): Coordinates {
        this.x += stepX
        this.y += stepY
        return this
    }

    fun clone(): Coordinates {
        return Coordinates(x, y)
    }

    operator fun minus(toCoord: Coordinates): Coordinates {
        return Coordinates(x - toCoord.x , y - toCoord.y)
    }

    fun linearIndex(width: Int): Int {
        return y * width + x
    }

}