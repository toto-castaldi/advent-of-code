package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.Coordinates
import kotlin.math.abs

class ManhattanDistance {
    companion object {
        val between = {
            cFrom: Coordinates, cTo: Coordinates ->

            abs(cFrom.x - cTo.x) + abs(cFrom.y - cTo.y)
        }
    }

}
