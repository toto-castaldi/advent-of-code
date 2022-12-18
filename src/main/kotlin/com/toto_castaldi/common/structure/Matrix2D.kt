package com.toto_castaldi.common.structure

class Matrix2D<T> (val nx: Int, val ny: Int, val defValue : T) {

    private val values = MutableList(nx * ny ) { defValue }

    operator fun set(x: Int, y: Int, value: T) {
        values[y * ny + x] = value
    }

    operator fun get(x: Int, y: Int): T {
        return values[y * ny + x]
    }


}
