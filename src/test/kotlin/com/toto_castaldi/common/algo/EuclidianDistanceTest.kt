package com.toto_castaldi.common.algo

import kotlin.test.Test
import kotlin.test.assertTrue

class EuclidianDistanceTest {

    @Test
    fun test2D() {
        val a = 25 - EuclidianDistance.distance3D(2.0, 1.0, 3.0, 5.0, 2.0, 26.0)
        val b = 25 - EuclidianDistance.distance3D(2.0, 2.0, 3.0, 5.0, 2.0, 26.0)
        println("$a $b")
        assertTrue { a < b }
    }
}