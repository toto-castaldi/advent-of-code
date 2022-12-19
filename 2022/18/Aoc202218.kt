import com.toto_castaldi.common.structure.Matrix2D
import com.toto_castaldi.common.structure.Matrix3D
import kotlin.math.max
import kotlin.math.sign

class Aoc202218() {
    private lateinit var matrix: Matrix3D<Int>
    private var maxZ: Int = 0
    private var maxY: Int = 0
    private var maxX: Int = 0
    val points = mutableMapOf<XYZ, Int>()
    data class XYZ (val x: Int, val y:Int, val z:Int)

    fun add(x: Int, y: Int, z: Int) {
        add(x,y,z,0)
    }

    /**
     * 1 base numeration
     */
    fun add(x: Int, y: Int, z: Int, marker: Int) {
        points[XYZ(x-1,y-1,z-1)] = marker
        update()
    }

    private fun update() {
        maxX = points.keys.maxBy { it.x }.x
        maxY = points.keys.maxBy { it.y }.y
        maxZ = points.keys.maxBy { it.z }.z
        matrix = Matrix3D<Int>(maxX + 1, maxY + 1, maxZ + 1, -1)
        points.entries.forEach {entry ->
            matrix[entry.key.x, entry.key.y, entry.key.z] = entry.value
        }
    }


    fun countSideExposed(): Int {
        var result = 0
        result += countX(1)
        result += countX(-1)
        result += countY(1)
        result += countY(-1)
        result += countZ(1)
        result += countZ(-1)

        return result
    }

    private fun countX(dir: Int): Int {
        return shrinkX(dir).fold(0) { acc, value ->
            acc + value.count { v -> v != -1 }
        }
    }

    private fun countY(dir: Int): Int {
        return shrinkY(dir).fold(0) { acc, value ->
            acc + value.count { v -> v != -1 }
        }
    }

    private fun countZ(dir: Int): Int {
        return shrinkZ(dir).fold(0) { acc, value ->
            acc + value.count { v -> v != -1 }
        }
    }

    fun navigateY(direction: Int) = sequence() {
        val dir = sign(direction.toDouble()).toInt()
        var indexY = if (dir > 0) 0 else maxY
        while (indexY <= maxY && indexY >=0) {
            val res = matrix.getY(indexY)
            indexY += dir
            yield(res)
        }
    }

    fun shrinkX(direction: Int): Matrix2D<Int> {
        return shrink(direction) { m: Matrix3D<Int>, index: Int -> m.getX(index) }
    }

    fun shrinkY(direction: Int): Matrix2D<Int> {
        return shrink(direction) { m: Matrix3D<Int>, index: Int -> m.getY(index) }
    }

    fun shrinkZ(direction: Int): Matrix2D<Int> {
        return shrink(direction) { m: Matrix3D<Int>, index: Int -> m.getZ(index) }
    }

    private fun shrink(direction: Int, slice: (Matrix3D<Int>, Int) -> Matrix2D<Int>): Matrix2D<Int> {
        var yProgression : IntProgression = 0 ..  maxY
        if (direction < 0) yProgression = yProgression.reversed()
        var actualFace : Matrix2D<Int>? = null
        for (iY in yProgression) {
            if (actualFace == null) {
                actualFace = slice(matrix, iY)
            } else {
                val nextFace = slice(matrix, iY)
                val maxX = max(actualFace.nx, nextFace.nx)
                val maxY = max(actualFace.ny, nextFace.ny)
                val merged = Matrix2D<Int>(maxX, maxY, -1)
                for (x in 0 until maxX) {
                    for (y in 0 until maxY) {
                        merged[x,y] = if (actualFace.validCoord(x,y) && actualFace[x,y] != -1) {
                            actualFace[x,y]
                        } else if (nextFace.validCoord(x,y) && nextFace[x,y] != -1) {
                            nextFace[x,y]
                        } else {
                            -1
                        }
                    }
                }
                actualFace = merged
            }
        }
        return actualFace!!
    }

    companion object {
        fun run1(fileName: String) {
            println( fileName)
        }
    }

}