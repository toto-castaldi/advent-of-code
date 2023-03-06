package com.toto_castaldi.common

import com.toto_castaldi.common.structure.IntCoordinates

class WindRose {
    companion object {
        fun n(intCoordinates: IntCoordinates): IntCoordinates {
            return intCoordinates.clone().move(0, -1)
        }
        fun ne(intCoordinates: IntCoordinates): IntCoordinates {
            return intCoordinates.clone().move(1, -1)
        }
        fun e(intCoordinates: IntCoordinates): IntCoordinates {
            return intCoordinates.clone().move(1, 0)
        }
        fun se(intCoordinates: IntCoordinates): IntCoordinates {
            return intCoordinates.clone().move(1, 1)
        }
        fun s(intCoordinates: IntCoordinates): IntCoordinates {
            return intCoordinates.clone().move(0, 1)
        }
        fun sw(intCoordinates: IntCoordinates): IntCoordinates {
            return intCoordinates.clone().move(-1, 1)
        }
        fun w(intCoordinates: IntCoordinates): IntCoordinates {
            return intCoordinates.clone().move(-1, 0)
        }
        fun nw(intCoordinates: IntCoordinates): IntCoordinates {
            return intCoordinates.clone().move(-1, -1)
        }
    }

}
