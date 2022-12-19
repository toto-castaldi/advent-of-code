import com.toto_castaldi.common.structure.Matrix2D
import com.toto_castaldi.common.structure.Matrix3D
import java.io.File

class Aoc202218() {
    private lateinit var matrix: Matrix3D<Int>
    private var sizeZ: Int = 0
    private var sizeY: Int = 0
    private var sizeX: Int = 0
    val points = mutableMapOf<XYZ, Int>()
    data class XYZ (val x: Int, val y:Int, val z:Int)

    fun add(x: Int, y: Int, z: Int) {
        add(x,y,z,0)
    }

    /**
     * 1 base numeration
     */
    fun add(x: Int, y: Int, z: Int, marker: Int) {
        points[XYZ(x,y,z)] = marker
        update()
    }

    private fun update() {
        val minX = points.keys.minBy { it.x }.x
        val minY = points.keys.minBy { it.y }.y
        val minZ = points.keys.minBy { it.z }.z
        sizeX = points.keys.maxBy { it.x }.x - minX + 1
        sizeY = points.keys.maxBy { it.y }.y - minY + 1
        sizeZ = points.keys.maxBy { it.z }.z - minZ + 1

        matrix = Matrix3D<Int>(sizeX , sizeY , sizeZ , -1)
        points.entries.forEach {entry ->
            matrix[entry.key.x - minX, entry.key.y - minY, entry.key.z - minZ] = entry.value
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

    private fun count(direction: Int, maxAxis : Int, slice: (Int) -> Matrix2D<Int>): Int {
        var count = 0
        var sliceB : Matrix2D<Int>? = null
        var axisProgression : IntProgression = 0 until maxAxis
        if (direction < 0) axisProgression = axisProgression.reversed()
        for (axisIndex in axisProgression) {
            if (sliceB == null) {
                sliceB = slice(axisIndex)
                count += countFilled(sliceB)
            } else {
                val sliceA = slice(axisIndex)
                for (y in 0 until sliceA.ny) {
                    for (x in 0 until sliceA.nx) {
                        if (sliceA[x,y] != -1 && sliceB[x,y] == -1) {
                            count ++
                        }
                    }
                }
                sliceB = sliceA
            }
        }
        return count
    }

    private fun countFilled(slice: Matrix2D<Int>): Int {
        return slice.fold(0) { acc, value ->
            acc + value.count { v -> v != -1 }
        }
    }

    private fun countY(dir: Int): Int {
        return count(dir, sizeY) { index: Int -> matrix.getY(index) }
    }

    private fun countX(dir: Int): Int {
        return count(dir, sizeX) { index: Int -> matrix.getX(index) }
    }

    private fun countZ(dir: Int): Int {
        return count(dir, sizeZ) { index: Int -> matrix.getZ(index) }
    }

    companion object {
        fun run1(fileName: String) {
            val aoc = Aoc202218()
            File(fileName).forEachLine {line ->
                val (x, y, z) = line.trim().split(",").map { it.trim().toInt() }
                aoc.add(x,y,z)
            }
            println( aoc.countSideExposed())
        }
    }

}