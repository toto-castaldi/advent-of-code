import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.Matrix2D

class Aoc202222() {
    private lateinit var map: Matrix2D<MapPoint>
    private lateinit var route: String

    private var actionIndex = -1
    private var direction = Direction.R
    private var row: Int = 0
    private var col: Int = 0
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
        createMap()
        var time = 0
        while (hasMoreAction() && (debug == -1 || time < debug)) {
            var action = nextAction()
            if (action.isRotating) {
                when (val r = action.rotateCommand) {
                    RotateDir.CLOCKWISE -> rotateRight()
                    RotateDir.UN_CLOCKWISE -> rotateLeft()
                    else -> throw Exception("Unknown rotation $r")
                }
            } else {
                move(action.steps!!)
            }
            println(map)
            time ++
        }
    }


    private fun hasMoreAction(): Boolean {
        return actionIndex < route.length
    }

    private fun createMap() {
        map = Matrix2D(maxLen, lines.size, MapPoint.EMPTY)
        for ((y, line) in lines.withIndex()) {
            for ((x, char) in line.toCharArray().toList().withIndex()) {
                map[x,y] = when (char) {
                    ' ' -> MapPoint.EMPTY
                    '.' -> MapPoint.FLOOR
                    '#' -> MapPoint.WALL
                    else -> throw Exception("unknown point ($char)")
                }
            }
        }
    }

    fun finalPassword(): Int {
        return 1000 * row + 4 * col + value(direction)
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
            while (route[actionIndex].isDigit()) {
                a += route[actionIndex]
            }
            actionIndex --
            return Action.steps(a.toInt())
        }
    }

    private fun rotateLeft() {
        direction = Direction.values()[(direction.ordinal - 1) % Direction.values().size]
    }

    private fun rotateRight() {
        direction = Direction.values()[(direction.ordinal + 1) % Direction.values().size]
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
            val coord = nextNotEmpty()
            if (map[coord.x, coord.y] != MapPoint.WALL) {
                row = coord.x
                col = coord.y
            }
        }
    }

    private fun nextNotEmpty(): Coordinates {
        return when (direction) {
            Direction.R -> {
                var checkRow = if (row + 1 < map.nx) row + 1 else 0
                if (map[checkRow, col] == MapPoint.EMPTY) checkRow = otherHSide(Direction.R)
                Coordinates(checkRow, col)
            }
            Direction.L -> {
                var checkRow = if (row - 1 > 0) row - 1 else map.nx - 1
                if (map[checkRow, col] == MapPoint.EMPTY) checkRow = otherHSide(Direction.L)
                Coordinates(checkRow, col)
            }
            Direction.D -> {
                var checkCol = if (col + 1 < map.ny) col + 1 else 0
                if (map[row, checkCol] == MapPoint.EMPTY) checkCol = otherVSide(Direction.D)
                Coordinates(row, checkCol)
            }
            Direction.U -> {
                var checkCol = if (col - 1 > 0) col - 1 else map.ny - 1
                if (map[row, checkCol] == MapPoint.EMPTY) checkCol = otherVSide(Direction.U)
                Coordinates(row, checkCol)
            }
        }
    }

    private fun otherHSide(dir: Direction): Int {
        TODO("Not yet implemented")
    }

    private fun otherVSide(dir: Direction): Int {
        TODO("Not yet implemented")
    }

    companion object {
        fun run(fileName: String) {
            println(fileName)
        }

    }
}
