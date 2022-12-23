package com.toto_castaldi.common.structure

import kotlin.math.abs


/**
 * https://en.wikipedia.org/wiki/Rubik%27s_Cube#Move_notation
 * BLU WHITE GREEN YELLOW RED ORANGE
*    0     1     2      3   4      5
 */

class Rubik<T>(vararg val faces : T) {
    private var currentFront = FACE.Y
    private var currentUp = FACE.G

    private enum class FACE {
        B, W, G, Y, R, O
    }

    private val cross = mapOf(
        FACE.G to Cross(FACE.W, FACE.R, FACE.Y, FACE.O),
        FACE.W to Cross(FACE.B, FACE.R, FACE.G, FACE.O),
        FACE.B to Cross(FACE.Y, FACE.R, FACE.W, FACE.O),
        FACE.Y to Cross(FACE.G, FACE.R, FACE.B, FACE.O),
        FACE.R to Cross(FACE.W, FACE.B, FACE.Y, FACE.G),
        FACE.O to Cross(FACE.W, FACE.G, FACE.Y, FACE.B)
    )

    init {
        if (faces.size != 6) throw Exception("Provide 6 different faces instead $faces")
    }

    fun set(front: T, up : T) {
        currentFront = FACE.values()[faces.indexOf(front)]
        currentUp = FACE.values()[faces.indexOf(up)]
        checkFU()
    }

    private fun checkFU() {
        if (currentUp !in cross[currentFront]!!) throw Exception("${this.currentUp} can't be on top of $currentFront")
    }

    fun currentFront(): T {
        return faces[currentFront.ordinal]
    }

    fun currentUp(): T {
        return faces[this.currentUp.ordinal]
    }

    fun rotateUp(dir : Int = 1) {
        for (i in 0 until abs(dir)) {
            if (dir > 0) {
                currentFront = cross[currentUp]!!.moveToDown(currentFront).right
            } else {
                currentFront = cross[currentUp]!!.moveToDown(currentFront).left
            }
        }
    }

    fun rotateFront(dir : Int = 1) {
        for (i in 0 until abs(dir)) {
            if (dir > 0) {
                currentUp = cross[currentFront]!!.moveToUp(currentUp).left
            } else {
                currentUp = cross[currentFront]!!.moveToUp(currentUp).right
            }
        }
    }

    fun rotateRight(dir : Int = 1) {
        for (i in 0 until abs(dir)) {
            if (dir > 0) {
                rotateUp()
                rotateFront()
                rotateUp(-1)
            } else {
                rotateUp(-1)
                rotateFront()
                rotateUp()
            }
        }
    }



}