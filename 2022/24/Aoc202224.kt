
import com.toto_castaldi.common.CrossDirection
import com.toto_castaldi.common.WindRose
import com.toto_castaldi.common.structure.IntCoordinates
import com.toto_castaldi.common.structure.Matrix2D
import java.io.File

class Aoc202224() {

    private var w: Int = 0
    private var h: Int = 0
    private var winds = mutableSetOf<Wind>()
    private var walls = mutableSetOf<IntCoordinates>()
    private lateinit var start:IntCoordinates
    private lateinit var end:IntCoordinates
    private lateinit var player:IntCoordinates

    fun map(y: Int, height: Int, mapLine: String) {
        h = height
        w = mapLine.length
        for ((x, c) in mapLine.toCharArray().toList().withIndex()) {
            val p = IntCoordinates(x,y)
            when (c) {
                '#' -> walls.add(p)
                '.' -> if (y == 0) {
                    start = p
                    walls.add(p)
                } else if (y + 1 == height) {
                    end = p
                    walls.add(p)
                }
                '^' -> winds.add(Wind(CrossDirection.U, IntCoordinates(x,y)))
                'v' -> winds.add(Wind(CrossDirection.D, IntCoordinates(x,y)))
                '<' -> winds.add(Wind(CrossDirection.L, IntCoordinates(x,y)))
                '>' -> winds.add(Wind(CrossDirection.R, IntCoordinates(x,y)))
            }

        }
    }

    data class Wind(val crossDirection: CrossDirection,val coordinates: IntCoordinates)

    fun stepToExit(debug:Int = -1): Int {
        player = start
        var steps = 0

        while (player != end && (debug == -1 || steps < debug)) {
            debug()

            val newWinds = mutableSetOf<Wind>()
            for (wind in winds) {
                when (wind.crossDirection) {
                    CrossDirection.U -> {
                        var next = WindRose.n(wind.coordinates)
                        if (next in walls) {
                            next = IntCoordinates(next.x, h - 2)
                        }
                        newWinds.add(Wind(wind.crossDirection, next))
                    }
                    CrossDirection.D -> {
                        var next = WindRose.s(wind.coordinates)
                        if (next in walls) {
                            next = IntCoordinates(next.x, 1)
                        }
                        newWinds.add(Wind(wind.crossDirection, next))
                    }
                    CrossDirection.R -> {
                        var next = WindRose.e(wind.coordinates)
                        if (next in walls) {
                            next = IntCoordinates(1, next.y)
                        }
                        newWinds.add(Wind(wind.crossDirection, next))
                    }
                    CrossDirection.L -> {
                        var next = WindRose.w(wind.coordinates)
                        if (next in walls) {
                            next = IntCoordinates(w - 2, next.y)
                        }
                        newWinds.add(Wind(wind.crossDirection, next))
                    }
                }
            }
            winds = newWinds



            steps += 1
        }

        return steps
    }

    private fun debug() {
        val m = Matrix2D(w, h, '.')

        for (c in winds) {
            m[c.coordinates.x, c.coordinates.y] = when (c.crossDirection) {
                CrossDirection.U -> '^'
                CrossDirection.D -> 'v'
                CrossDirection.L -> '<'
                CrossDirection.R -> '>'
            }
        }

        for (c in walls) {
            m[c.x, c.y] = '#'
        }

        m[start.x, start.y] = '.'

        m[end.x, end.y] = '.'

        m[player.x, player.y] = 'E'

        println(m)
    }

    companion object {

        fun run1(fileName: String) {
            val aoc = Aoc202224()
            var y = 0
            val height = File(fileName).readLines().size
            File(fileName).forEachLine {
                aoc.map(y++, height, it)
            }
            println( aoc.stepToExit())
        }

    }
}
