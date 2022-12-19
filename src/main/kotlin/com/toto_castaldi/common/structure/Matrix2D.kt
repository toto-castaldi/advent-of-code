package com.toto_castaldi.common.structure

class Matrix2D<T> (val nx: Int, val ny: Int, val defValue : T) : Iterable<List<T>> {

    private val values = MutableList(nx * ny ) { defValue }

    operator fun set(x: Int, y: Int, value: T) {
        values[y * ny + x] = value
    }

    operator fun get(x: Int, y: Int): T {
        return values[y * ny + x]
    }

    override fun toString(): String {
        var result = ""
        for (y in 0 until ny) {
            for (x in 0 until nx) {
                result += (this[x,y].toString() + " ")
            }
            result += "\n"
        }
        return result
    }

    fun bake(): Matrix2D<T> {
        val result = Matrix2D<T>(nx, ny, defValue)
        result.values.addAll(values)
        return result
    }

    override fun iterator(): Iterator<List<T>> {
        return values.chunked(ny).iterator()
    }
}
