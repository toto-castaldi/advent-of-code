import com.toto_castaldi.common.structure.IntCoordinates
import com.toto_castaldi.common.structure.Matrix2D
import com.toto_castaldi.common.structure.Matrix3D
import java.io.File

private const val AIR = -1
private const val LAVA = 0
private const val WATER = -2

/**
 * 0 Lava
 * -1 Air
 * -2 Water
 */
class Aoc202218() {
    private lateinit var matrix: Matrix3D<Int>
    private var sizeZ: Int = 0
    private var sizeY: Int = 0
    private var sizeX: Int = 0
    val points = mutableMapOf<IntCoordinates, Int>()

    fun add(x: Int, y: Int, z: Int) {
        add(x,y,z,LAVA)
    }

    /**
     * 1 base numeration
     */
    fun add(x: Int, y: Int, z: Int, marker: Int) {
        points[IntCoordinates(x,y,z)] = marker
        update()
    }

    private fun update() {
        //val minX = points.keys.minBy { it.x }.x
        //val minY = points.keys.minBy { it.y }.y
        //val minZ = points.keys.minBy { it.z }.z
        sizeX = points.keys.maxBy { it.x }.x + 1//- minX
        sizeY = points.keys.maxBy { it.y }.y + 1//- minY
        sizeZ = points.keys.maxBy { it.z }.z + 1//- minZ

        matrix = Matrix3D<Int>(sizeX , sizeY , sizeZ , AIR)
        points.entries.forEach {entry ->
            //matrix[entry.key.x - minX, entry.key.y - minY, entry.key.z - minZ] = entry.value
            matrix[entry.key.x , entry.key.y , entry.key.z ] = entry.value
        }
    }


    fun countNotConnectedSides(): Int {
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
                        if (sliceA[x,y] != AIR && sliceB[x,y] == AIR) {
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
            acc + value.count { v -> v != AIR }
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

    fun countExteriorSides(): Int {
        fillAirCubes()
        return countNotConnectedSides()
    }

    private fun fillAirCubes() {
        //external Air to Water
        val x = 0
        val y = 0
        val z = 0
        if (matrix[x, y, z ] != AIR) throw Exception("invalid starting cube")
        AirToWater(x, y, z)
        //Air to Lava
        for (p in matrix.iterator()) {
            if (matrix[p] == AIR) matrix[p] = LAVA
        }
        //Water to Air
        for (p in matrix) {
            if (matrix[p] == WATER) matrix[p] = AIR
        }
    }

    private fun AirToWater(x: Int, y: Int, z: Int) {
        matrix[x, y, z] = WATER //from air to water
        if (x < sizeX - 1 && matrix[x + 1, y, z] == AIR) AirToWater(x + 1, y, z)
        if (y < sizeY - 1 && matrix[x , y + 1, z] == AIR) AirToWater(x , y + 1, z)
        if (z < sizeZ - 1 && matrix[x , y, z + 1] == AIR) AirToWater(x , y , z + 1)
    }

    companion object {
        fun run2(fileName: String) {
            val aoc = Aoc202218()
            File(fileName).forEachLine {line ->
                val (x, y, z) = line.trim().split(",").map { it.trim().toInt() }
                aoc.add(x,y,z)
            }
            println( aoc.countExteriorSides())

            //openscad
            val openScadFileName = "${fileName.replace("input.txt", "droplet")}.2.scad"
            val openscadScript = File(openScadFileName).printWriter()
            openscadScript.println("module Droplet() {")
            for (cube in aoc.points.keys) {
                openscadScript.println("\ttranslate([${cube.x},${cube.y},${cube.z}])")
                openscadScript.println("\t\tcube([1,1,1],true);")
            }
            openscadScript.println("}")
            openscadScript.println("Droplet();")
            openscadScript.flush()
            openscadScript.close()
            println(openScadFileName)
        }

        fun run1(fileName: String) {
            val aoc = Aoc202218()
            File(fileName).forEachLine {line ->
                val (x, y, z) = line.trim().split(",").map { it.trim().toInt() }
                aoc.add(x,y,z)
            }
            println( aoc.countNotConnectedSides())
        }
    }

}