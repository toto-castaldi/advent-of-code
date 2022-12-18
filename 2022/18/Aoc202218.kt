import com.toto_castaldi.common.structure.Matrix3D

class Aoc202218() {
    val vals = mutableListOf<XYZ>()
    data class XYZ (val x: Int, val y:Int, val z:Int)

    fun add(x: Int, y: Int, z: Int) {
        vals.add(XYZ(x-1,y-1,z-1))
    }

    fun countSideExposed(): Int {
        val maxX = vals.maxBy { it.x }.x
        val maxY = vals.maxBy { it.y }.y
        val maxZ = vals.maxBy { it.z }.z
        val matrix3D = Matrix3D<Int>(maxX + 1, maxY + 1, maxZ + 1, 0)
        vals.forEach {
            matrix3D[it.x, it.y, it.z] = 1
        }
        for (x in 0 .. maxX) {
            println("x ${matrix3D.getX(x)}")
        }
        for (y in 0 .. maxY) {
            println("y ${matrix3D.getY(y)}")
        }
        for (z in 0 .. maxZ) {
            println("y ${matrix3D.getZ(z)}")
        }
        TODO()
    }

    companion object {
        fun run1(fileName: String) {
            println( fileName)
        }
    }

}