package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.IntCoordinates
import kotlin.math.abs

class ManhattanDistance {
    companion object {
        val between = {
                cFrom: IntCoordinates, cTo: IntCoordinates ->

            abs(cFrom.x - cTo.x) + abs(cFrom.y - cTo.y)
        }
    }

}
