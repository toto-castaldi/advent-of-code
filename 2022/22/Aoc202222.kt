import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.Matrix2D
import com.toto_castaldi.common.structure.Rubik
import java.io.File

class Aoc202222() {
    private lateinit var rawMap: Matrix2D<MapPoint>
    private lateinit var route: String

    private var rubik: Rubik<Matrix2D<MapPoint>>? = null
    private var direction: Direction = Direction.R
    private var actionIndex = -1
    private var y: Int = 0
    private var x: Int = 0
    private val lines = mutableListOf<String>()
    private var maxLen = Int.MIN_VALUE
    private var minLen = Int.MAX_VALUE

    interface CubeMap {
        fun white(rawData: Matrix2D<MapPoint>, edge: Int): Matrix2D<MapPoint>
        fun orange(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun green(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun blu(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun red(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun yellow(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
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
        rawMap = Matrix2D(maxLen, lines.size, MapPoint.EMPTY)
        for ((iY, line) in lines.withIndex()) {
            for ((iX, char) in line.toCharArray().toList().withIndex()) {
                rawMap[iX, iY] = when (char) {
                    ' ' -> MapPoint.EMPTY
                    '.' -> MapPoint.FLOOR
                    '#' -> MapPoint.WALL
                    else -> throw Exception("unknown point ($char)")
                }
            }
        }

    }


    fun navigate(inputRoute: String, debug : Int = -1) {
        route = inputRoute
        while (resolveMap()[x,y] == MapPoint.EMPTY) x ++ //part1
        markMap()
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
        if (rubik == null) {
            println(format(rawMap))
        } else {
            println(format(rubik!!))
        }
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

    /**

     */
    private fun format(rubik : Rubik<Matrix2D<MapPoint>>): String {
        return format(Matrix2D(maxLen, lines.size, MapPoint.EMPTY)
            .putSubmap(2*minLen, 0,rubik.white)
            .putSubmap(0, minLen, rubik.blu)
            .putSubmap(minLen, minLen, rubik.orange)
            .putSubmap(minLen * 2, minLen, rubik.green)
            .putSubmap(minLen * 2, minLen * 2, rubik.yellow)
            .putSubmap(minLen * 3, minLen * 2, rubik.red)
        )
    }


    private fun hasMoreAction(): Boolean {
        return actionIndex < route.length - 1
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
            if (rubik == null) {
                val coord = nextSpot2D()
                if (resolveMap()[coord.x, coord.y] != MapPoint.WALL) {
                    x = coord.x
                    y = coord.y
                }
            } else {
                val proposal3D = nextSpot3D()
                if (proposal3D.newRubik.currentFront()[proposal3D.x, proposal3D.y] != MapPoint.WALL) {
                    x = proposal3D.x
                    y = proposal3D.y
                    rubik = proposal3D.newRubik
                }
            }
            markMap()
        }
    }

    data class Proposal3D (val x : Int, val y: Int, val newRubik : Rubik<Matrix2D<MapPoint>>)

    private val resolveMap = { if (rubik == null) rawMap else rubik!!.currentFront()}


    private fun markMap() {
        resolveMap()[x, y] = when (direction) {
            Direction.D -> MapPoint.S_D
            Direction.U -> MapPoint.S_U
            Direction.L -> MapPoint.S_L
            Direction.R -> MapPoint.S_R
        }
    }

    private fun nextSpot3D(): Proposal3D {
        val map = resolveMap()
        val newRubik = Rubik(rubik!!.blu, rubik!!.white, rubik!!.green, rubik!!.yellow, rubik!!.red, rubik!!.orange)
        newRubik.set(rubik!!.currentFront(), rubik!!.currentUp())
        return when (direction) {
            Direction.R -> {
                if (x + 1 == map.nx) {
                    Proposal3D(0, y, newRubik.rotateUp())
                } else {
                    Proposal3D(x +1 , y, rubik!!)
                }
            }
            Direction.L -> {
                if (x == 0) {
                    Proposal3D(map.nx - 1, y, newRubik.rotateUp(-1))
                } else {
                    Proposal3D(x -1 , y, rubik!!)
                }
            }
            Direction.D -> {
                if (y + 1 == map.ny) {
                    Proposal3D(x, 0, newRubik.rotateRight())
                } else {
                    Proposal3D(x  , y + 1, rubik!!)
                }
            }
            Direction.U -> {
                if (y == 0) {
                    Proposal3D(x, map.ny - 1, newRubik.rotateRight(-1))
                } else {
                    Proposal3D(x , 0, rubik!!)
                }
            }
        }
    }

    private fun nextSpot2D(): Coordinates {
        return when (direction) {
            Direction.R -> {
                if (x + 1 == rawMap.nx || rawMap[x + 1, y] == MapPoint.EMPTY) {
                    var i = 0
                    while (rawMap[i, y] == MapPoint.EMPTY) i++
                    Coordinates(i, y)
                } else {
                    Coordinates(x + 1, y)
                }
            }

            Direction.L -> {
                if (x == 0 || rawMap[x - 1, y] == MapPoint.EMPTY) {
                    var i = rawMap.nx - 1
                    while (rawMap[i, y] == MapPoint.EMPTY) i--
                    Coordinates(i, y)
                } else {
                    Coordinates(x - 1, y)
                }
            }

            Direction.D -> {
                if (y + 1 == rawMap.ny || rawMap[x, y + 1] == MapPoint.EMPTY) {
                    var i = 0
                    while (rawMap[x, i] == MapPoint.EMPTY) i++
                    Coordinates(x, i)
                } else {
                    Coordinates(x, y + 1)
                }
            }

            Direction.U -> {
                if (y == 0 || rawMap[x, y - 1] == MapPoint.EMPTY) {
                    var i = rawMap.ny - 1
                    while (rawMap[x, i] == MapPoint.EMPTY) i--
                    Coordinates(x, i)
                } else {
                    Coordinates(x, y - 1)
                }
            }
        }
    }

    fun set3DConf(cubeMap: CubeMap) {
        val w = cubeMap!!.white(rawMap, minLen)
        val b = cubeMap!!.blu(rawMap, minLen)
        rubik = Rubik(
            b,
            w,
            cubeMap!!.green(rawMap, minLen),
            cubeMap!!.yellow(rawMap, minLen),
            cubeMap!!.red(rawMap, minLen),
            cubeMap!!.orange(rawMap, minLen)
        )
        rubik?.set(w,b)
    }

    companion object {
        val EXAMPLE_MAP: CubeMap = object : CubeMap {
            override fun white(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                return rawData.sub(len * 2,0, len, len)
            }

            override fun blu(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                return rawData.sub(0,len, len, len)
            }

            override fun orange(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                return rawData.sub(len,len, len, len)
            }

            override fun green(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                return rawData.sub(len * 2,len, len, len)
            }

            override fun yellow(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                return rawData.sub(len * 2,len * 2, len, len)
            }

            override fun red(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                return rawData.sub(len * 3,len * 2, len, len)
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
