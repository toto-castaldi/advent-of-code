import com.toto_castaldi.common.CrossDirection
import com.toto_castaldi.common.RotateDirection
import com.toto_castaldi.common.structure.Matrix2D

abstract class Aoc202222() {
    private lateinit var route: String
    protected lateinit var rawMap: Matrix2D<MapPoint>

    private var actionIndex = -1
    private var y: Int = 0
    private var x: Int = 0
    private var direction = CrossDirection.R

    val rawMapLines = mutableListOf<String>()
    var rawMapWidth = Int.MIN_VALUE

    enum class MapPoint {
        EMPTY, FLOOR, WALL, S_R, S_L, S_U, S_D
    }

    private fun hasMoreAction(): Boolean {
        return actionIndex < route.length - 1
    }

    fun finalPassword(): Int {
        return 1000 * (y + 1) + 4 * (x + 1) + direction.ordinal
    }

    fun navigate(inputRoute: String, debug : Int = -1) {
        route = inputRoute
        while (resolveMap()[x,y] == MapPoint.EMPTY) x ++
        markMap()
        var time = 0
        while (hasMoreAction() && (debug == -1 || time < debug)) {
            val action = nextAction()
            if (action.rotateCommand != null) {
                direction = direction.rotate(action.rotateCommand)
                markMap()
            } else {
                move(action.steps!!)
            }
            time ++
        }
        println(formatMap())
    }

    abstract fun formatMap(): String

    fun move(steps: Int) {
        for (i in 0 until steps) {
            val proposal = nextSpot(x, y, direction)
            if (proposal.map[proposal.x, proposal.y] != MapPoint.WALL) {
                x = proposal.x
                y = proposal.y
                changeCurrentMap(proposal)
                direction = proposal.direction
            }
            markMap()
        }
    }

    abstract fun changeCurrentMap(proposal: NavigationProposal)

    abstract fun nextSpot(x : Int, y : Int, direction: CrossDirection): NavigationProposal

    data class NavigationProposal(val map: Matrix2D<MapPoint>, val x : Int, val y: Int, val direction: CrossDirection)

    abstract fun resolveMap(): Matrix2D<MapPoint>

    private fun markMap() {
        resolveMap()[x, y] = when (direction) {
            CrossDirection.D -> MapPoint.S_D
            CrossDirection.U -> MapPoint.S_U
            CrossDirection.L -> MapPoint.S_L
            CrossDirection.R -> MapPoint.S_R
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

    open operator fun plus(mapLine: String) {
        rawMapLines.add(mapLine)
        if (mapLine.length > rawMapWidth) rawMapWidth = mapLine.length
        rawMap = Matrix2D(rawMapWidth, rawMapLines.size, MapPoint.EMPTY)

        for ((iY, line) in rawMapLines.withIndex()) {
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

    data class Action(val rotateCommand : RotateDirection?, val steps : Int?) {

        companion object {
            fun rotating(c: Char): Action {
                return Action(if (c == 'L') RotateDirection.UN_CLOCKWISE else RotateDirection.CLOCKWISE, null)
            }

            fun steps(steps: Int): Action {
                return Action(null, steps)
            }
        }

    }

    companion object {
        fun format(map: Matrix2D<MapPoint>): String {
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
    }

}
