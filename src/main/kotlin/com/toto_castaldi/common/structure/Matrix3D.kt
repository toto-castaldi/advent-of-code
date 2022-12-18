package com.toto_castaldi.common.structure

class Matrix3D<T>(val nx: Int, val ny: Int, val nz: Int, val defValue : T) {

    private val values = MutableList(nx * ny * nz) { defValue }

    operator fun set(x: Int, y: Int, z: Int, value: T) {
        values[z * nz * ny + y * ny + x] = value
    }

    operator fun get(x:Int,y:Int,z:Int): T {
        return values[z * nz * ny + y * ny + x]
    }

    fun getX(x: Int): Matrix2D<T> {
        val result = Matrix2D<T>(ny, nz, defValue)
        for (iy in 0 until ny) {
            for (iz in 0 until nz) {
                result[iy,iz] = this[x, iy, iz]
            }
        }
        return result
    }

    fun getY(y: Int): Matrix2D<T> {
        val result = Matrix2D<T>(nx, nz, defValue)
        for (ix in 0 until nx) {
            for (iz in 0 until nz) {
                result[ix,iz] = this[ix, y, iz]
            }
        }
        return result
    }

    fun getZ(z: Int): Matrix2D<T> {
        val result = Matrix2D<T>(nx, ny, defValue)
        for (ix in 0 until nx) {
            for (iy in 0 until ny) {
                result[ix,iy] = this[ix, iy, z]
            }
        }
        return result
    }

}