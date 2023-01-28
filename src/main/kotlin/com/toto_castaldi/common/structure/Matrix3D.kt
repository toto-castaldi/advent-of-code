package com.toto_castaldi.common.structure

class Matrix3D<T>(val nx: Int, val ny: Int, val nz: Int, val defValue : T) {

    private val values = MutableList(nx * ny * nz) { defValue }

    operator fun set(x: Int, y: Int, z: Int, value: T) {
        values[index(x, y, z)] = value
    }

    operator fun set(coordinates: IntCoordinates, value: T) {
        set(coordinates.x, coordinates.y, coordinates.z, value)
    }

    private fun index(x: Int, y: Int, z: Int) = z * ny * nx + y * nx + x

    operator fun get(x:Int,y:Int,z:Int): T {
        return values[index(x,y,z)]
    }
    operator fun get(coordinates: IntCoordinates): T {
        return get(coordinates.x, coordinates.y, coordinates.z)
    }


    fun getZ(z: Int): Matrix2D<T> {
        val result = Matrix2D<T>(nx, ny, defValue)
        for (iy in 0 until ny) {
            for (ix in 0 until nx) {
                result[ix,iy] = this[ix, iy, z]
            }
        }
        return result
    }

    fun getY(y: Int): Matrix2D<T> {
        val result = Matrix2D<T>(nx, nz, defValue)
        for (iz in 0 until nz) {
            for (ix in 0 until nx) {
                result[ix,iz] = this[ix, y, iz]
            }
        }
        return result
    }

    fun getX(x: Int): Matrix2D<T> {
        val result = Matrix2D<T>(ny, nz, defValue)
        for (iz in 0 until nz) {
            for (iy in 0 until ny) {
                result[iy,iz] = this[x, iy, iz]
            }
        }
        return result
    }

    override fun toString(): String {
        return values.toString()
    }

    fun coordinates() = sequence<IntCoordinates> {
        for (x in 0 until nx) {
            for (y in 0 until ny) {
                for (z in 0 until nz) {
                    yield(IntCoordinates(x, y, z))
                }
            }
        }
    }

}