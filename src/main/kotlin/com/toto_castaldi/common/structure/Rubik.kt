package com.toto_castaldi.common.structure

import com.toto_castaldi.common.Rolling
import kotlin.math.abs

/**
 * https://en.wikipedia.org/wiki/Rubik%27s_Cube#Move_notation
 * BLU WHITE GREEN YELLOW RED ORANGE
*    0     1     2      3   4      5
 */

class Rubik<T>(val blu : T, val  white : T, val  green : T, val  yellow : T, val  red : T, val  orange : T) {
    private var faces = listOf(blu, white, green, yellow, red, orange)
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

    private val opposite = mapOf(
        FACE.W to FACE.Y,
        FACE.Y to FACE.W,
        FACE.R to FACE.O,
        FACE.O to FACE.R,
        FACE.G to FACE.B,
        FACE.B to FACE.G
    )

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

    fun rotateUp(dir : Int = 1): Rubik<T> {
        if (dir < 0) {
            rotateUp(4 - abs(dir) % 4)
        } else {
            for (i in 0 until dir % 4) {
                currentFront = cross[currentUp]!!.rotateUntilDown(currentFront).right
            }
        }
        return this
    }

    fun rotateFront(dir : Int = 1): Rubik<T> {
        if (dir < 0) {
            rotateFront(4 - abs(dir) % 4)
        } else {
            for (i in 0 until dir % 4) {
                currentUp = cross[currentFront]!!.rotateUntilUp(currentUp).left
            }
        }
        return this
    }


    fun rotateRight(dir : Int = 1): Rubik<T> {
        for (i in 0 until abs(dir % 4)) {
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
        return this
    }
}