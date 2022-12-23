import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.Matrix2D
import java.io.File

class Aoc202222() {
    private lateinit var navigation2D: Matrix2D<MapPoint>
    private lateinit var route: String

    private var navigation3Ds = mutableListOf<Matrix2D<MapPoint>>()
    private var cubeMap: CubeMap? = null
    private var direction: Direction = Direction.R
    private var index3D = 0
    private var actionIndex = -1
    private var y: Int = 0
    private var x: Int = -1
    private val lines = mutableListOf<String>()
    private var maxLen = Int.MIN_VALUE
    private var minLen = Int.MAX_VALUE

    interface CubeMap {
        fun subMap0(aoc : Aoc202222, rawData: Matrix2D<MapPoint>)
        fun subMap1(aoc : Aoc202222, rawData: Matrix2D<MapPoint>)
        fun subMap2(aoc : Aoc202222, rawData: Matrix2D<MapPoint>)
        fun subMap3(aoc : Aoc202222, rawData: Matrix2D<MapPoint>)
        fun subMap4(aoc : Aoc202222, rawData: Matrix2D<MapPoint>)
        fun subMap5(aoc : Aoc202222, rawData: Matrix2D<MapPoint>)
        fun adjacent(aoc202222: Aoc202222, currentIndex : Int,  direction: Direction, x: Int, y: Int): Proposal3D

    }
    enum class Direction {
        R, D, L, U
    }

    enum class MapPoint {
        EMPTY, FLOOR, WALL, S_R, S_L, S_U, S_D
    }

    enum class RotateDir {
        CLOCKWISE, UN_CLOCKWISE
    }

    operator fun plus(mapLine: String) {
        lines.add(mapLine)
        if (mapLine.length > maxLen) maxLen = mapLine.length
        if (mapLine.trim().length < minLen) minLen = mapLine.trim().length
    }


    fun navigate(inputRoute: String, debug : Int = -1) {
        route = inputRoute
        init()
        var time = 0
        while (hasMoreAction() && (debug == -1 || time < debug)) {
            val action = nextAction()
            if (action.isRotating) {
                when (val r = action.rotateCommand) {
                    RotateDir.CLOCKWISE -> rotateRight()
                    RotateDir.UN_CLOCKWISE -> rotateLeft()
                    else -> throw Exception("Unknown rotation $r")
                }
                markMap()
            } else {
                move(action.steps!!)
            }
            time ++
        }
        println(format(navigation2D))
    }

    private fun format(map : Matrix2D<MapPoint>): String {
        return map.format() {
            when (it) {
                MapPoint.EMPTY -> " "
                MapPoint.FLOOR -> "."
                MapPoint.WALL -> "#"
                MapPoint.S_R -> ">"
                MapPoint.S_L -> "<"
                MapPoint.S_U -> "^"
                MapPoint.S_D -> "v"
            }
        }
    }


    private fun hasMoreAction(): Boolean {
        return actionIndex < route.length - 1
    }

    private fun init() {
        (0..6).forEach { _ -> navigation3Ds.add(Matrix2D(0,0,MapPoint.EMPTY))}
        navigation2D = Matrix2D(maxLen, lines.size, MapPoint.EMPTY)
        for ((y, line) in lines.withIndex()) {
            for ((x, char) in line.toCharArray().toList().withIndex()) {
                navigation2D[x, y] = when (char) {
                    ' ' -> MapPoint.EMPTY
                    '.' -> {
                        if (this.x == -1) this.x = x
                        MapPoint.FLOOR
                    }

                    '#' -> MapPoint.WALL
                    else -> throw Exception("unknown point ($char)")
                }
            }
        }
        if (cubeMap == null) {
            navigation2D[x, y] = MapPoint.S_R
        } else {
            cubeMap!!.subMap0(this, navigation2D)
            cubeMap!!.subMap1(this, navigation2D)
            cubeMap!!.subMap2(this, navigation2D)
            cubeMap!!.subMap3(this, navigation2D)
            cubeMap!!.subMap4(this, navigation2D)
            cubeMap!!.subMap5(this, navigation2D)


        }
    }

    fun finalPassword(): Int {
        return 1000 * (y + 1) + 4 * (x + 1) + value(direction)
    }

    private fun value(facing: Direction): Int {
        return when(facing) {
            Direction.R -> 0
            Direction.D -> 1
            Direction.L -> 2
            Direction.U -> 3
        }
    }

    private fun nextAction(): Action {
        actionIndex ++
        if (route[actionIndex].isLetter()) {
            return Action.rotating(route[actionIndex])
        } else {
            var a = ""
            while (actionIndex < route.length && route[actionIndex].isDigit()) {
                a += route[actionIndex ++]
            }
            actionIndex --
            return Action.steps(a.toInt())
        }
    }

    private fun rotateLeft() {
        if (direction.ordinal == 0) {
            direction = Direction.values().last()
        } else {
            direction = Direction.values()[direction.ordinal - 1]
        }
    }

    private fun rotateRight() {
        if (direction.ordinal == Direction.values().size -1) {
            direction = Direction.values().first()
        } else {
            direction = Direction.values()[(direction.ordinal + 1)]
        }
    }

    data class Action(val isRotating : Boolean, val rotateCommand : RotateDir?, val steps : Int?) {

        companion object {
            fun rotating(c: Char): Action {
                return Action(true, if (c == 'L') RotateDir.UN_CLOCKWISE else RotateDir.CLOCKWISE, null)
            }

            fun steps(steps: Int): Action {
                return Action(false, null, steps)
            }
        }

    }

    private fun move(steps: Int) {
        for (i in 0 until steps) {
            if (cubeMap == null) {
                val coord = nextSpot2D()
                if (resolveMap()[coord.x, coord.y] != MapPoint.WALL) {
                    x = coord.x
                    y = coord.y
                }
            } else {
                val proposal3D = nextSpot3D()
                if (resolveMap(proposal3D.indexMap)[proposal3D.coord.x, proposal3D.coord.y] != MapPoint.WALL) {
                    x = proposal3D.coord.x
                    y = proposal3D.coord.y
                    index3D = proposal3D.indexMap
                    direction = proposal3D.direction
                }
            }
            markMap()
        }
    }

    data class Proposal3D (val indexMap : Int, val coord : Coordinates, val direction : Direction)

    private fun resolveMap(index3Dmap : Int = index3D): Matrix2D<MapPoint> {
        return if (cubeMap == null) navigation2D else navigation3Ds[index3Dmap]
    }

    private fun markMap() {
        resolveMap()[x, y] = when (direction) {
            Direction.D -> MapPoint.S_D
            Direction.U -> MapPoint.S_U
            Direction.L -> MapPoint.S_L
            Direction.R -> MapPoint.S_R
        }
    }

    private fun nextSpot3D(): Proposal3D {
        val resolveMap = resolveMap()
        return when (direction) {
            Direction.R -> {
                if (x + 1 == resolveMap.nx) {
                    cubeMap!!.adjacent(this, index3D, direction, x, y)
                } else {
                    Proposal3D(index3D, Coordinates(x + 1, y), direction)
                }
            }
            Direction.L -> {
                if (x == 0) {
                    cubeMap!!.adjacent(this, index3D, direction, x, y)
                } else {
                    Proposal3D(index3D, Coordinates(x - 1, y), direction)
                }
            }
            Direction.D -> {
                if (y + 1 == resolveMap.ny) {
                    cubeMap!!.adjacent(this, index3D, direction, x, y)
                } else {
                    Proposal3D(index3D, Coordinates(x , y + 1), direction)
                }
            }
            Direction.U -> {
                if (y == 0) {
                    cubeMap!!.adjacent(this, index3D, direction, x, y)
                } else {
                    Proposal3D(index3D, Coordinates(x , y - 1), direction)
                }
            }
        }
    }

    private fun nextSpot2D(): Coordinates {
        return when (direction) {
            Direction.R -> {
                if (x + 1 == navigation2D.nx || navigation2D[x + 1, y] == MapPoint.EMPTY) {
                    var i = 0
                    while (navigation2D[i, y] == MapPoint.EMPTY) i++
                    Coordinates(i, y)
                } else {
                    Coordinates(x + 1, y)
                }
            }

            Direction.L -> {
                if (x == 0 || navigation2D[x - 1, y] == MapPoint.EMPTY) {
                    var i = navigation2D.nx - 1
                    while (navigation2D[i, y] == MapPoint.EMPTY) i--
                    Coordinates(i, y)
                } else {
                    Coordinates(x - 1, y)
                }
            }

            Direction.D -> {
                if (y + 1 == navigation2D.ny || navigation2D[x, y + 1] == MapPoint.EMPTY) {
                    var i = 0
                    while (navigation2D[x, i] == MapPoint.EMPTY) i++
                    Coordinates(x, i)
                } else {
                    Coordinates(x, y + 1)
                }
            }

            Direction.U -> {
                if (y == 0 || navigation2D[x, y - 1] == MapPoint.EMPTY) {
                    var i = navigation2D.ny - 1
                    while (navigation2D[x, i] == MapPoint.EMPTY) i--
                    Coordinates(x, i)
                } else {
                    Coordinates(x, y - 1)
                }
            }
        }
    }

    fun set3DConf(map: CubeMap) {
        cubeMap = map
    }

    companion object {


        val EXAMPLE_MAP: CubeMap = object : CubeMap {
            override fun subMap0(aoc: Aoc202222, rawData: Matrix2D<MapPoint>) {
                aoc.navigation3Ds[0] = rawData.sub(aoc.minLen * 2,0, aoc.minLen, aoc.minLen)
            }

            override fun subMap1(aoc: Aoc202222, rawData: Matrix2D<MapPoint>) {
                aoc.navigation3Ds[1] = rawData.sub(0,aoc.minLen, aoc.minLen, aoc.minLen)
            }

            override fun subMap2(aoc: Aoc202222, rawData: Matrix2D<MapPoint>) {
                aoc.navigation3Ds[2] = rawData.sub(aoc.minLen,aoc.minLen, aoc.minLen, aoc.minLen)
            }

            override fun subMap3(aoc: Aoc202222, rawData: Matrix2D<MapPoint>) {
                aoc.navigation3Ds[3] = rawData.sub(aoc.minLen * 2,aoc.minLen, aoc.minLen, aoc.minLen)
            }

            override fun subMap4(aoc: Aoc202222, rawData: Matrix2D<MapPoint>) {
                aoc.navigation3Ds[4] = rawData.sub(aoc.minLen * 2,aoc.minLen * 2, aoc.minLen, aoc.minLen)
            }

            override fun subMap5(aoc: Aoc202222, rawData: Matrix2D<MapPoint>) {
                aoc.navigation3Ds[5] = rawData.sub(aoc.minLen * 3,aoc.minLen * 2, aoc.minLen, aoc.minLen)
            }

            override fun adjacent(
                aoc: Aoc202222,
                currentIndex: Int,
                direction: Direction,
                x: Int,
                y: Int
            ): Proposal3D {
                val m = aoc.minLen
                val o = { value : Int -> m - value}
                return when (currentIndex) {
                    0 -> {
                        when (direction) {
                            Direction.R -> Proposal3D(5, Coordinates(m , o(y)), Direction.L)
                            Direction.L -> Proposal3D(2, Coordinates(y , 0), Direction.D)
                            Direction.U -> Proposal3D(1, Coordinates(o(x) , 0), Direction.D)
                            Direction.D -> Proposal3D(3, Coordinates(x , 0), Direction.D)
                        }
                    }
                    1 -> {
                        when (direction) {
                            Direction.R -> Proposal3D(2, Coordinates(0, y), Direction.R)
                            Direction.L -> Proposal3D(5, Coordinates(o(y) , m), Direction.U)
                            Direction.U -> Proposal3D(0, Coordinates(o(x) , 0), Direction.D)
                            Direction.D -> Proposal3D(4, Coordinates(x , 0), Direction.U)
                        }
                    }
                    else -> throw Exception("unknown face")
                }

            }

        }

        fun run1(fileName: String) {
            val aoc = Aoc202222()
            var map = true
            File(fileName).forEachLine {
                if (map) aoc + it else aoc.navigate(it)
                if (it.isBlank()) map = false
            }
            println( aoc.finalPassword())
        }

        fun run2(fileName: String, example : Boolean = false) {
            TODO("Not yet implemented")
        }

    }
}
