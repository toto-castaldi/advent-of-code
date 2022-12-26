package com.toto_castaldi.common

enum class CrossDirection {
    R, D, L, U;

    fun rotate(direction : RotateDirection): CrossDirection {
        return when (direction) {
            RotateDirection.UN_CLOCKWISE -> if (ordinal == 0) values().last() else values()[ordinal - 1]
            RotateDirection.CLOCKWISE -> if (ordinal == values().size - 1) values().first() else values()[ordinal + 1]
        }
    }

}