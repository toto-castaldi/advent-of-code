package com.toto_castaldi.common.structure

import kotlin.math.sign

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

    /**
     * down
     */
    fun d(): Coordinates {
        return this.move(0,1)
    }

    /**
     * left down
     */
    fun ld(): Coordinates {
        return this.move(-1,1)
    }

    /**
     * right down
     */
    fun rd(): Coordinates {
        return this.move(1,1)
    }

    /**
     * the index in a Matrix width points
     */
    val linearIndex = { width : Int -> y * width + x }

    companion object {
        val isHorizontal = { pFrom: Coordinates, pTo: Coordinates -> pFrom.x != pTo.x && pFrom.y == pTo.y}
        val isVertical = { pFrom: Coordinates, pTo: Coordinates -> pFrom.x == pTo.x && pFrom.y != pTo.y}
        val isDiagonal = { pFrom: Coordinates, pTo: Coordinates -> pFrom.x != pTo.x && pFrom.y != pTo.y}
        fun path(source: Coordinates, destination: Coordinates) = sequence<Coordinates> {
            var step = source

            while (step != destination) {
                val direction = destination - step
                if (direction.x != 0) {
                    step = step.clone().move(sign(direction.x.toDouble()).toInt(),0)
                } else {
                    step = step.clone().move(0, sign(direction.y.toDouble()).toInt())
                }
                yield(step)
            }
        }
    }

}