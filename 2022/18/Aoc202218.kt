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
        TODO()
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


    fun shrinkY(direction: Int): Matrix2D<Int> {
        var yProgression : IntProgression = 0 ..  maxY
        if (direction < 0) yProgression = yProgression.reversed()
        var actualFace : Matrix2D<Int>? = null
        for (iY in yProgression) {
            if (actualFace == null) {
                actualFace = matrix.getY(iY)
            } else {
                val nextFace = matrix.getY(iY)
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