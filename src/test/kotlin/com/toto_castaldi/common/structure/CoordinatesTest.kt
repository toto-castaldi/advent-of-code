package com.toto_castaldi.common.structure

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class CoordinatesTest {

    @Test
    fun pathTestHorizontalForward() {
        val xFrom = Random.nextInt(100, 200)
        val xTo = Random.nextInt(xFrom + 100, xFrom + 1000)
        val y = Random.nextInt(100, 200)
        var from = Coordinates(xFrom, y)
        var dest = Coordinates(xTo, y)
        val pathSequence = Coordinates.path(from, dest)
        var prevPath = from
        for (p in pathSequence) {
            assertEquals(p.x - 1, prevPath.x)
            assertEquals(p.y, from.y)
            assertEquals(p.y, dest.y)
            prevPath = p
        }
    }

    @Test
    fun pathTestVerticalForward() {
        val yFrom = Random.nextInt(100, 200)
        val yTo = Random.nextInt(yFrom + 100, yFrom + 1000)
        val x = Random.nextInt(100, 200)
        var from = Coordinates(x, yFrom)
        var dest = Coordinates(x, yTo)
        val pathSequence = Coordinates.path(from, dest)
        var prevPath = from
        for (p in pathSequence) {
            assertEquals(p.y - 1, prevPath.y)
            assertEquals(p.x, from.x)
            assertEquals(p.x, dest.x)
            prevPath = p
        }
    }

    @Test
    fun pathTestHorizontalBackward() {
        val xTo = Random.nextInt(100, 200)
        val xFrom = Random.nextInt(xTo + 100, xTo + 1000)

        val y = Random.nextInt(100, 200)
        var from = Coordinates(xFrom, y)
        var dest = Coordinates(xTo, y)
        val pathSequence = Coordinates.path(from, dest)
        var prevPath = from
        for (p in pathSequence) {
            assertEquals(p.x + 1, prevPath.x)
            assertEquals(p.y, from.y)
            assertEquals(p.y, dest.y)
            prevPath = p
        }
    }

}