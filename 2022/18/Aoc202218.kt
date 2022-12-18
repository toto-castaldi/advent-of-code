import com.toto_castaldi.common.structure.Matrix3D

class Aoc202218() {
    val vals = mutableListOf<XYZ>()
    data class XYZ (val x: Int, val y:Int, val z:Int)

    fun add(x: Int, y: Int, z: Int) {
        vals.add(XYZ(x,y,z))
    }

    fun countSideExposed(): Int {
        val maxX = vals.maxBy { it.x }.x
        val maxY = vals.maxBy { it.y }.y
        val maxZ = vals.maxBy { it.z }.z
        val matrix3D = Matrix3D<Int>(maxX, maxY, maxZ, 0)
        vals.forEach {
            matrix3D[it.x, it.y, it.z] = 1
        }
        TODO()
    }

    companion object {
        fun run1(fileName: String) {
            println( fileName)
        }
    }

}