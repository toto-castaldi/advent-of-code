package com.toto_castaldi.common.structure

import com.toto_castaldi.common.Rolling

/**
 *        --------------------> X
 *        |
 *        |
 *        |
 *        |
 *        |
 *        v
 *
 *        Y
 */
class Matrix2D<T> (var nx: Int, var ny: Int, val defValue : T) : Iterable<List<T>>, Rolling {

    private var values = MutableList(nx * ny ) { defValue }
    private var addIndex = 0

    operator fun set(x: Int, y: Int, value: T) {
        values[index(x,y)] = value
    }

    operator fun get(x: Int, y: Int): T {
        return values[index(x,y)]
    }

    private fun index(x: Int, y: Int) = y * nx + x

    operator fun contains(value: T):Boolean {
        return value in values
    }


    override fun toString(): String {
        return format { it -> it.toString() }
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
        return values.chunked(nx)[i]
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
    override fun turnUnclockwise() {
        val m = Matrix2D(ny,nx, defValue)
        for (ix in 0 until nx) {
            for (iy in 0 until ny) {
                m[iy, ix] = this[nx - ix - 1, iy]
            }
        }
        this.nx = m.nx
        this.ny = m.ny
        this.values = m.values
    }

    override fun turnClockwise() {
        val m = Matrix2D(ny,nx, defValue)
        for (ix in 0 until nx) {
            for (iy in 0 until ny) {
                m[iy, ix] = this[ix, ny - iy - 1]
            }
        }
        this.nx = m.nx
        this.ny = m.ny
        this.values = m.values
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Matrix2D<*>

        return values == other.values
    }

    fun format(fmt : (T) -> String): String {
        var result = ""
        for (y in 0 until ny) {
            for (x in 0 until nx) {
                result += (fmt(this[x,y]) + " ")
            }
            result += "\n"
        }
        return result
    }

    fun putSubmap(x: Int, y: Int, matrix: Matrix2D<T>): Matrix2D<T> {
        for (iy in 0 until matrix.ny) {
            for (ix in 0 until matrix.nx) {
                this[ix + x, iy + y] = matrix[ix, iy]
            }
        }
        return this
    }
}
