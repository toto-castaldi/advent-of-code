package com.toto_castaldi.common.structure

class Matrix2D<T> (val nx: Int, val ny: Int, val defValue : T) : Iterable<List<T>> {

    private var values = MutableList(nx * ny ) { defValue }
    private var addIndex = 0

    operator fun set(x: Int, y: Int, value: T) {
        values[y * ny + x] = value
    }

    operator fun get(x: Int, y: Int): T {
        return values[y * ny + x]
    }

    operator fun contains(value: T):Boolean {
        return value in values
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
        val newValues = mutableListOf<T>()
        newValues.addAll(values)
        result.values = newValues
        return result
    }

    override fun iterator(): Iterator<List<T>> {
        return values.chunked(ny).iterator()
    }

    fun rowAt(i : Int): List<T> {
        return values.chunked(ny)[i]
    }

    fun colAt(i: Int): List<T> {
        return bake().transpose().rowAt(i)
    }

    fun transpose(): Matrix2D<T> {
        val result = Matrix2D<T>(ny,nx, defValue)

        for (y in 0 until ny) {
            for (x in 0 until nx) {
                result[y,x] = this[x,y]
            }
        }

        return result
    }

    fun sub(x: Int, y: Int, w: Int, h: Int): Matrix2D<T> {
        val result = Matrix2D(w, h, defValue)
        for (iy in y until y + h) {
            for (ix in x until x + w) {
                result[ix - x,iy - y] = this[ix, iy]
            }
        }
        return result
    }

    fun add(elements: List<T>): Matrix2D<T> {
        for (x in 0 until nx) {
            if (x < elements.size) {
                this[x, addIndex] = elements[x]
            }
        }
        addIndex ++
        return this
    }

    val validCoord = { x: Int, y: Int -> x in 0 until nx && y in 0 until ny}

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Matrix2D<*>

        return values == other.values
    }
}
