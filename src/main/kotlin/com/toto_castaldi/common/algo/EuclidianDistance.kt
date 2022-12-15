package com.toto_castaldi.common.algo

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * https://en.wikipedia.org/wiki/Euclidean_distance
 */
class EuclidianDistance {

    private var squares: Double = 0.0

    fun dimension(a: Double, b: Double): EuclidianDistance {
        squares += (a - b).pow(2.0)
        return this
    }

    val compute = { sqrt(squares) }

    companion object {
        val distance3D = {a0: Double, b0: Double, c0: Double, a1: Double, b1: Double, c1 : Double -> EuclidianDistance().dimension(a0, a1).dimension(b0, b1).dimension(c0, c1).compute() }

        val distance2D = {a0: Double, b0: Double, a1: Double, b1: Double -> EuclidianDistance().dimension(a0, a1).dimension(b0, b1).compute() }
    }

}