import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.Matrix2D
import java.io.File

class Aoc202222() {
    private lateinit var map: Matrix2D<MapPoint>
    private lateinit var route: String
    private lateinit var direction: Direction

    private var actionIndex = -1
    private var y: Int = 0
    private var x: Int = -1
    private val lines = mutableListOf<String>()
    private var maxLen = 0

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
                //printDebug()
            } else {
                move(action.steps!!)
                //printDebug()
            }
            time ++
        }
        printDebug()
    }

    private fun printDebug() {
        println(map.format() {
            when (it) {
                MapPoint.EMPTY -> " "
                MapPoint.FLOOR -> "."
                MapPoint.WALL -> "#"
                MapPoint.S_R -> ">"
                MapPoint.S_L -> "<"
                MapPoint.S_U -> "^"
                MapPoint.S_D -> "v"
            }
        })

    }


    private fun hasMoreAction(): Boolean {
        return actionIndex < route.length - 1
    }

    private fun init() {
        map = Matrix2D(maxLen, lines.size, MapPoint.EMPTY)
        for ((y, line) in lines.withIndex()) {
            for ((x, char) in line.toCharArray().toList().withIndex()) {
                map[x,y] = when (char) {
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
        direction = Direction.R
        map[x,y] = MapPoint.S_R
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
            val coord = nextSpot()
            if (map[coord.x, coord.y] != MapPoint.WALL) {
                if (map[coord.x, coord.y] == MapPoint.EMPTY) throw Exception("empty.....$direction")
                x = coord.x
                y = coord.y
                markMap()
            }
        }
    }

    private fun markMap() {
        map[x, y] = when (direction) {
            Direction.D -> MapPoint.S_D
            Direction.U -> MapPoint.S_U
            Direction.L -> MapPoint.S_L
            Direction.R -> MapPoint.S_R
        }
    }

    private fun nextSpot(): Coordinates {
        return when (direction) {
            Direction.R -> {
                if (x + 1 == map.nx || map[x + 1, y]  == MapPoint.EMPTY) {
                    var i = 0
                    while (map[i, y] == MapPoint.EMPTY) i ++
                    Coordinates(i , y)
                } else {
                    Coordinates(x + 1, y)
                }
            }
            Direction.L -> {
                if (x == 0 || map[x - 1, y]  == MapPoint.EMPTY) {
                    var i = map.nx - 1
                    while (map[i, y] == MapPoint.EMPTY) i --
                    Coordinates(i, y)
                } else {
                    Coordinates(x - 1, y)
                }
            }
            Direction.D -> {
                if (y + 1 == map.ny || map[x, y + 1]  == MapPoint.EMPTY) {
                    var i = 0
                    while (map[x, i] == MapPoint.EMPTY) i ++
                    Coordinates(x, i)
                } else {
                    Coordinates(x , y + 1)
                }
            }
            Direction.U -> {
                if (y == 0 || map[x , y - 1]  == MapPoint.EMPTY) {
                    var i = map.ny - 1
                    while (map[x, i] == MapPoint.EMPTY) i --
                    Coordinates(x , i )
                } else {
                    Coordinates(x , y - 1)
                }
            }

        }
    }

    companion object {
        fun run(fileName: String) {
            val aoc = Aoc202222()
            var map = true
            File(fileName).forEachLine {
                if (map) aoc + it else aoc.navigate(it)
                if (it.isBlank()) map = false
            }
            println( aoc.finalPassword())
        }

    }
}
