
import com.toto_castaldi.common.CrossDirection
import com.toto_castaldi.common.Numbers
import com.toto_castaldi.common.WindRose
import com.toto_castaldi.common.structure.IntCoordinates
import java.io.File
import java.util.*

class Aoc202224() {

    private val windConf = mutableMapOf<Int, Set<Wind>>()
    private var w: Int = 0
    private var h: Int = 0
    private var winds = mutableSetOf<Wind>()
    private val walls = mutableSetOf<IntCoordinates>()
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
    data class AocState(val player : IntCoordinates, val steps: Int, val winds: Set<Wind>) {
        fun fingerPrint(): String {
            return "" + player.x + "-" + player.y + winds.sortedBy { it.coordinates.x + it.coordinates.y }.fold(" ") { acc, wind -> acc + wind.coordinates.x + "-" + wind.coordinates.y + "-" }

        }
    }

    fun stepToExit(): Int {
        val period = Numbers.lcm(h - 2, w - 2)
        for (i in 0 until period) {
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

            windConf[i] = newWinds
            winds = newWinds
        }




        val q: Queue<AocState> = LinkedList()
        val visited = mutableSetOf<String>()
        var bestSteps = Int.MAX_VALUE

        println(end)
        println(h)

        player = start

        val aocState = AocState(player, 0, winds)
        visited.add(aocState.fingerPrint())
        q.add(aocState)

        while (q.isNotEmpty()) {
            val state = q.remove()

           // println(state.player)

            if (WindRose.s(state.player) == end) {
                println("found ! ${state.steps}")
                if (state.steps < bestSteps) {
                    bestSteps = state.steps
                }
            } else {
                visited.add(state.fingerPrint())


                val currentWinds = windConf[state.steps % period]!!
                val windCoordintas = currentWinds.map { it.coordinates }

                if (state.player.y > 0 && WindRose.n(state.player) !in walls && WindRose.n(state.player) !in windCoordintas) {
                    val s = AocState(WindRose.n(state.player), state.steps + 1, currentWinds)
                    if (s.fingerPrint() !in visited) {
                        q.add(s)
                    }
                }

                if (WindRose.s(state.player) !in walls && WindRose.s(state.player) !in windCoordintas) {
                    val s = AocState(WindRose.s(state.player), state.steps + 1, currentWinds)
                    if (s.fingerPrint() !in visited) {
                        q.add(s)
                    }
                }

                if (WindRose.e(state.player) !in walls && WindRose.e(state.player) !in windCoordintas) {
                    val s = AocState(WindRose.e(state.player), state.steps + 1, currentWinds)
                    if (s.fingerPrint() !in visited) {
                        q.add(s)
                    }
                }

                if (WindRose.w(state.player) !in walls && WindRose.w(state.player) !in windCoordintas) {
                    val s = AocState(WindRose.w(state.player), state.steps + 1, currentWinds)
                    if (s.fingerPrint() !in visited) {
                        q.add(s)
                    }
                }

                if (state.player !in windCoordintas) {
                    val s = AocState(state.player.clone(), state.steps + 1, currentWinds)
                    if (s.fingerPrint() !in visited) {
                        q.add(s)
                    }
                }
            }
        }

        return bestSteps + 1
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
